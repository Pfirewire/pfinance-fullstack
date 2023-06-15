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


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Service to run all Plaid API requests
// Requests are done by creating Plaid API objects and running execute method
// Utilizes PlaidClientService to house Plaid API information required to make request successfully
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Service
public class PlaidApiService {

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

    // Receives PlaidLink object from database
    // Sends Plaid API request to get Accounts information attached to AccessToken in PlaidLink object
    // If not null, returns List of Plaid AccountBase objects
    public List<AccountBase> getAccountsByPlaidLink(PlaidLink plaidLink) throws IOException {
        final String accessToken = plaidLink.getPlaidAccessToken();
        // Packaging request object
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest().accessToken(accessToken);
        // Executing request and receiving response object
        Response<AccountsGetResponse> response = plaidClient
                .createPlaidClient()
                .accountsBalanceGet(request)
                .execute();
        assert response.body() != null;
        return response.body().getAccounts();
    }

    // Receives PfAccount object and int number of days
    // Sends Plaid API request to get list of Transactions in an account, for the amount of days between "days" and now
    // Returns List of ALL PfTransaction objects associated with PfAccount
    public List<PfTransaction> updateAndReturnPfTransactionsByPfAccountAndDays(PfAccount pfAccount, int days) throws IOException {

        // Setting start and end date
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(days);

        // Packaging request object
        TransactionsGetRequest request = new TransactionsGetRequest()
                .accessToken(pfAccount.getPlaidLink().getPlaidAccessToken())
                .startDate(startDate)
                .endDate(currentDate);

        // Executing request and receiving response object
        Response<TransactionsGetResponse> response = plaidClient
                .createPlaidClient()
                .transactionsGet(request)
                .execute();

        // If response body is not null creating list of Plaid Transaction objects
        // Otherwise creating empty list
        List<Transaction> transactions = response.body() != null ? new ArrayList<>(response.body().getTransactions()) : new ArrayList<>();

        // Looping through list to check by Plaid Transaction ID if a transaction exists in database
        // If already exists, do nothing
        for(Transaction transaction : transactions) {
            if(!pfTransactionDao.existsByPlaidTransactionId(transaction.getTransactionId())) {
                PfLocation pfLocation;

                // Checks to see if location exists in database
                // If so, set location to the location in database
                // if not, create new location and save in database
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

                // Creating new PfTransaction object with information from Plaid Transaction object
                // Attaching PfAccount and PfLocation to PfTransaction object
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
                        PfTransaction.ChannelType.valueOf(transaction.getPaymentChannel().getValue()),
                        pfAccount,
                        pfLocation
                );

                // Sets other side of the relationship
                // Saves PfTransaction and PfLocation in database
                pfTransactionDao.save(pfTransaction);
                List<PfTransaction> pfTransactions = pfLocation.getPfTransactions();
                pfTransactions.add(pfTransaction);
                pfLocation.setPfTransactions(pfTransactions);
                pfLocationDao.save(pfLocation);
            }
        }

        // Returns all PfTransactions for account
        return pfAccount.getPfTransactions();
    }

    // Receives PfAccount object
    // Sends PlaidAPI request to get balance by PfAccount
    // Method will also update balance in database
    // Returns PfAccount object with updated balance property
    public PfAccount updateAndReturnBalanceByPfAccount(PfAccount pfAccount) throws IOException {
        final String accessToken = pfAccount.getPlaidLink().getPlaidAccessToken();

        // Packaging request object
        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest().accessToken(accessToken);

        // Executing request and receiving response object
        Response<AccountsGetResponse> response = plaidClient
                .createPlaidClient()
                .accountsBalanceGet(request)
                .execute();

        // As long as response body is not null, sets List of Plaid Account objects
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
