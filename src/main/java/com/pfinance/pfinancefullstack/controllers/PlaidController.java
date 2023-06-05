package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.PfAccount;
import com.pfinance.pfinancefullstack.models.PlaidLink;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.PlaidLinkRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.CreateLinkTokenService;
import com.pfinance.pfinancefullstack.services.JsonPrint;
import com.pfinance.pfinancefullstack.services.PlaidApiService;
import com.pfinance.pfinancefullstack.services.PlaidClientService;
import com.plaid.client.model.ItemPublicTokenExchangeRequest;
import com.plaid.client.model.ItemPublicTokenExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/plaid")
public class PlaidController {

    @Autowired
    private CreateLinkTokenService createLinkTokenService;
    @Autowired
    private PlaidClientService plaidClientService;
    @Autowired
    private JsonPrint jsonPrint;
    @Autowired
    private PlaidApiService plaidApi;

    private final UserRepository userDao;
    private final PlaidLinkRepository plaidLinkDao;


    public PlaidController(UserRepository userDao, PlaidLinkRepository plaidLinkDao) {
        this.userDao = userDao;
        this.plaidLinkDao = plaidLinkDao;
    }

    @PostMapping("/create-link-token")
    public String createLinkToken() throws IOException {
        System.out.println("Inside createLinkToken");
        User user = userDao.findById(1L).get();
        return createLinkTokenService.getLinkToken(user);
    }

    @PostMapping("/exchange-public-token")
    public List<PfAccount> exchangePublicToken(@RequestBody String publicToken) throws IOException {
        User user = userDao.findById(1L).get();
        System.out.println("Inside exchangePublicToken");
        System.out.println(publicToken);


        ItemPublicTokenExchangeRequest request = new ItemPublicTokenExchangeRequest()
                .publicToken(publicToken);

        Response<ItemPublicTokenExchangeResponse> response = plaidClientService.createPlaidClient()
                .itemPublicTokenExchange(request)
                .execute();

        if(response.code() == 400) {
            System.out.println("400 error in exchangePublicToken");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        jsonPrint.object(response.body());

        PlaidLink plaidLink = new PlaidLink(response.body().getAccessToken(), response.body().getItemId(), user);
        plaidLinkDao.save(plaidLink);
        List<PlaidLink> userPlaidLinks = user.getPlaidLinks();
        userPlaidLinks.add(plaidLink);
        user.setPlaidLinks(userPlaidLinks);
        userDao.save(user);

        return plaidApi.createPfAccountsWithPlaidLink(plaidLink, user);
    }

}
