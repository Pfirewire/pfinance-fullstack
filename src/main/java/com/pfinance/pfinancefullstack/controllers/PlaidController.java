package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.CreateLinkTokenService;
import com.pfinance.pfinancefullstack.services.PlaidClientService;
import com.plaid.client.model.ItemPublicTokenExchangeRequest;
import com.plaid.client.model.ItemPublicTokenExchangeResponse;
import com.plaid.client.request.PlaidApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/plaid")
public class PlaidController {

    @Autowired
    private CreateLinkTokenService createLinkTokenService;

    private UserRepository userDao;
    private PlaidApi plaidClient;


    @Autowired
    private PlaidClientService plaidClientService;


    public PlaidController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/create-link-token")
    public String createLinkToken() throws IOException {
        System.out.println("Inside createLinkToken");
        User user = userDao.findById(1L).get();
        return createLinkTokenService.getLinkToken(user);
    }

    @PostMapping("/exchange-public-token")
    public String exchangePublicToken(@RequestBody String publicToken) throws IOException {
        User user = userDao.findById(1L).get();
        System.out.println("Inside exchangePublicToken");
        System.out.println(publicToken);

        plaidClient = plaidClientService.createPlaidClient();

        ItemPublicTokenExchangeRequest request = new ItemPublicTokenExchangeRequest()
                .publicToken(publicToken);

        Response<ItemPublicTokenExchangeResponse> response = plaidClient
                .itemPublicTokenExchange(request)
                .execute();

        if(response.code() == 400) {
            return "400 Error";
        }

        user.setAccessToken(response.body().getAccessToken());
        user.setItemId(response.body().getItemId());
        userDao.save(user);

        return "tested";
    }

}
