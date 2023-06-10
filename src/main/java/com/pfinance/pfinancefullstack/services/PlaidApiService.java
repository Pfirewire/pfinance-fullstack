package com.pfinance.pfinancefullstack.services;

import com.pfinance.pfinancefullstack.models.*;
import com.pfinance.pfinancefullstack.repositories.*;
import com.plaid.client.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaidApiService {

    @Autowired
    private JsonPrint jsonPrint;
    @Autowired
    private PlaidClientService plaidClient;

    private final UserRepository userDao;
    private final PfAccountRepository pfAccountDao;
    private final PfTransactionRepository pfTransactionDao;
    private final PfLocationRepository pfLocationDao;
    private final PlaidLinkRepository plaidLinkDao;

    public PlaidApiService(UserRepository userDao, PfAccountRepository pfAccountDao, PfTransactionRepository pfTransactionDao, PfLocationRepository pfLocationDao, PlaidLinkRepository plaidLinkDao) {
        this.userDao = userDao;
        this.pfAccountDao = pfAccountDao;
        this.pfTransactionDao = pfTransactionDao;
        this.pfLocationDao = pfLocationDao;
        this.plaidLinkDao = plaidLinkDao;
    }

    public List<AccountBase> getAccountsByPlaidLink(PlaidLink plaidLink) throws IOException {
        final String accessToken = plaidLink.getPlaidAccessToken();
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest().accessToken(accessToken);
        Response<AccountsGetResponse> response = plaidClient
                .createPlaidClient()
                .accountsBalanceGet(request)
                .execute();
        assert response.body() != null;
        return response.body().getAccounts();
    }

    public List<PfTransaction> updateAndReturnPfTransactionsByPfAccountAndDays(PfAccount pfAccount, int days) throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(days);

        // Pull transactions for a date range

        TransactionsGetRequest request = new TransactionsGetRequest()
                .accessToken(pfAccount.getPlaidLink().getPlaidAccessToken())
                .startDate(startDate)
                .endDate(currentDate);

        Response<TransactionsGetResponse> response = plaidClient
                .createPlaidClient()
                .transactionsGet(request)
                .execute();

        List<Transaction> transactions = response.body() != null ? new ArrayList<>(response.body().getTransactions()) : new ArrayList<>();
        for(Transaction transaction : transactions) {
            jsonPrint.object(transaction);
            System.out.println(transaction.getPaymentChannel().name());
            if(!pfTransactionDao.existsByPlaidTransactionId(transaction.getTransactionId())) {
                PfLocation pfLocation;
                if(pfLocationDao.existsByAddressAndCityAndState(
                        transaction.getLocation().getAddress(),
                        transaction.getLocation().getCity(),
                        transaction.getLocation().getRegion())
                ) {
                    pfLocation = pfLocationDao.getByAddressAndCityAndState(
                        transaction.getLocation().getAddress(),
                        transaction.getLocation().getCity(),
                        transaction.getLocation().getRegion()
                    );
                } else {
                    pfLocation = new PfLocation(
                        transaction.getLocation().getAddress(),
                        transaction.getLocation().getCity(),
                        transaction.getLocation().getRegion()
                    );
                    pfLocationDao.save(pfLocation);
                }
                PfTransaction pfTransaction = new PfTransaction(
                        transaction.getAccountId(),
                        transaction.getPendingTransactionId(),
                        transaction.getTransactionId(),
                        transaction.getAmount(),
                        transaction.getName(),
                        transaction.getMerchantName(),
                        transaction.getIsoCurrencyCode(),
                        transaction.getDatetime() != null ? transaction.getDatetime().toString() : null,
                        transaction.getAuthorizedDatetime() != null ? transaction.getAuthorizedDatetime().toString() : null,
                        transaction.getPending(),
                        PfTransaction.ChannelType.valueOf(transaction.getPaymentChannel().name()),
                        pfAccount,
                        pfLocation
                );
                pfTransactionDao.save(pfTransaction);
                List<PfTransaction> pfTransactions = pfLocation.getPfTransactions() != null ? pfLocation.getPfTransactions() : new ArrayList<>();
                pfTransactions.add(pfTransaction);
                pfLocation.setPfTransactions(pfTransactions);
                pfLocationDao.save(pfLocation);
            }
        }
        return pfAccount.getPfTransactions();
    }

    public PfAccount updateAndReturnBalanceByPfAccount(PfAccount pfAccount) throws IOException {
        final String accessToken = pfAccount.getPlaidLink().getPlaidAccessToken();
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest().accessToken(accessToken);
        Response<AccountsGetResponse> response = plaidClient
                .createPlaidClient()
                .accountsBalanceGet(request)
                .execute();
        assert response.body() != null;
        List<AccountBase> accounts = response.body().getAccounts();
        for(AccountBase account : accounts) {
            if(
                    account.getAccountId().equals(pfAccount.getPlaidAccountId()) &&
                    account.getBalances().getAvailable() != null &&
                    account.getBalances().getCurrent() != null
            ) {
                pfAccount.setAvailableBalance(account.getBalances().getAvailable());
                pfAccount.setCurrentBalance(account.getBalances().getCurrent());
            }
        }
        pfAccountDao.save(pfAccount);
        return pfAccount;
    }

    public List<PfAccount> createPfAccountsWithPlaidLink(PlaidLink plaidLink, User user) throws IOException {
        final String accessToken = plaidLink.getPlaidAccessToken();
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest().accessToken(accessToken);
        Response<AccountsGetResponse> response = plaidClient
                .createPlaidClient()
                .accountsBalanceGet(request)
                .execute();
        assert response.body() != null;
        List<AccountBase> accounts = response.body().getAccounts();
        List<PfAccount> userPfAccounts = user.getPfAccounts();
        List<PfAccount> newPfAccounts = new ArrayList<>();
        for(AccountBase account : accounts) {
            if(!pfAccountDao.existsByPlaidAccountId(account.getAccountId())){
                PfAccount newPfAccount = new PfAccount(
                        account.getAccountId(),
                        account.getBalances().getAvailable(),
                        account.getBalances().getCurrent(),
                        account.getBalances().getIsoCurrencyCode(),
                        account.getMask(),
                        account.getName(),
                        account.getOfficialName(),
                        account.getType().toString(),
                        account.getSubtype() != null ? account.getSubtype().toString() : null,
                        user,
                        plaidLink
                );
                userPfAccounts.add(newPfAccount);
                newPfAccounts.add(newPfAccount);
                pfAccountDao.save(newPfAccount);
            }
        }
        user.setPfAccounts(userPfAccounts);
        userDao.save(user);
        plaidLink.setPfAccounts(newPfAccounts);
        plaidLinkDao.save(plaidLink);
        return newPfAccounts;
    }
}
