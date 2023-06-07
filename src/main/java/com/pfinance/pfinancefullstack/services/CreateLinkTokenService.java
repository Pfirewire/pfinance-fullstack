package com.pfinance.pfinancefullstack.services;

import com.pfinance.pfinancefullstack.models.PfAccount;
import com.pfinance.pfinancefullstack.models.PlaidLink;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.PfAccountRepository;
import com.pfinance.pfinancefullstack.repositories.PlaidLinkRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import com.plaid.client.model.*;
import com.plaid.client.request.PlaidApi;
import com.plaid.client.model.LinkTokenCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CreateLinkTokenService {

    @Autowired
    private PlaidTokenService plaidToken;
    @Autowired
    private PlaidApiService plaidApi;
    @Autowired
    private PlaidClientService plaidClientService;
    @Autowired
    private JsonPrint jsonPrint;

    private PlaidApi plaidClient;
    private final UserRepository userDao;
    private final PlaidLinkRepository plaidLinkDao;
    private final PfAccountRepository pfAccountDao;


    public CreateLinkTokenService(UserRepository userDao, PlaidLinkRepository plaidLinkDao, PfAccountRepository pfAccountDao) {
        this.userDao = userDao;
        this.plaidLinkDao = plaidLinkDao;
        this.pfAccountDao = pfAccountDao;
    }

    public String getLinkToken(User currentUser) throws IOException {
        System.out.println("Inside getLinkToken");

        plaidClient = plaidClientService.createPlaidClient();

        System.out.println("Plaid Client: ");
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println(gson.toJson(plaidClient));


        String linkToken = "";

        LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser()
                .clientUserId(currentUser.getId().toString())
//                .legalName(currentUser.getName())
//                .phoneNumber(currentUser.getPhoneNumber())
//                .emailAddress(currentUser.getEmail())
        ;

        DepositoryFilter types = new DepositoryFilter()
                .accountSubtypes(Arrays.asList(DepositoryAccountSubtype.CHECKING));

        LinkTokenAccountFilters accountFilters = new LinkTokenAccountFilters()
                .depository(types);

        LinkTokenCreateRequest request = new LinkTokenCreateRequest()
                .clientId(plaidToken.getPlaidClientId())
                .secret(plaidToken.getPlaidDevelopmentSecret())
                .user(user)
                .clientName("Pfinance")
                .products(Arrays.asList(Products.AUTH, Products.TRANSACTIONS))
                .countryCodes(Arrays.asList(CountryCode.US))
                .language("en")
//                .redirectUri("https://localhost:3000/budget")
                .linkCustomizationName("default")
//                .accountFilters(accountFilters)
        ;

        System.out.println(request);

        Response<LinkTokenCreateResponse> response = plaidClient
                .linkTokenCreate(request)
                .execute();

        System.out.println(response);

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println(gson.toJson(response));

        if(response.code() == 400) {
            return "400 Error";
        }
        linkToken = response.body().getLinkToken();

        System.out.println(linkToken);
        return linkToken;
    }

//    @Override
//    public void handle(ServerHttpRequest request, ServerHttpResponse response) {
//        return null;
//    }

    public List<PfAccount> newPlaidLink(PlaidLink plaidLink) throws IOException {
        List<PfAccount> pfAccounts = new ArrayList<>();
        List<AccountBase> plaidAccounts = plaidApi.getAccountsByPlaidLink(plaidLink);
        for(AccountBase plaidAccount : plaidAccounts) {
            if(!pfAccountDao.existsByPlaidAccountId(plaidAccount.getAccountId())) {
                PfAccount pfAccount = new PfAccount(
                        plaidAccount.getAccountId(),
                        plaidAccount.getBalances().getAvailable(),
                        plaidAccount.getBalances().getCurrent(),
                        plaidAccount.getBalances().getIsoCurrencyCode(),
                        plaidAccount.getMask(),
                        plaidAccount.getName(),
                        plaidAccount.getOfficialName(),
                        plaidAccount.getType().toString(),
                        plaidAccount.getSubtype() != null ? plaidAccount.getSubtype().toString() : null,
                        UserUtils.currentUser(userDao),
                        plaidLink
                );
                pfAccounts.add(pfAccount);
                pfAccountDao.save(pfAccount);
                jsonPrint.object(pfAccount);
            }
        }
        plaidLink.setPfAccounts(pfAccounts);
        plaidLinkDao.save(plaidLink);
        return pfAccounts;
    }

}
