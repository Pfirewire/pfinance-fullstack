//package com.pfinance.pfinancefullstack.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pfinance.pfinancefullstack.models.User;
//import com.pfinance.pfinancefullstack.repositories.UserRepository;
//import com.pfinance.pfinancefullstack.services.PlaidClientService;
//import com.pfinance.pfinancefullstack.utils.UserUtils;
//import com.plaid.client.model.AccountBase;
//import com.plaid.client.model.AccountsBalanceGetRequest;
//import com.plaid.client.model.AccountsGetResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import retrofit2.Response;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@CrossOrigin
//@RequestMapping("/api")
//public class BalanceController {
//
//    @Autowired
//    private PlaidClientService plaidClient;
//
//    private UserRepository userDao;
//
//    public BalanceController(UserRepository userDao) {
//        this.userDao = userDao;
//    }
//
//    @GetMapping("/balance")
//    public List<AccountBase> testGetBudget() throws IOException {
//        System.out.println("Inside testGetBudget");
//        ObjectMapper mapper = new ObjectMapper();
//        User user = UserUtils.currentUser(userDao);
//        final String accessToken = user.getAccessToken();
//        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest().accessToken(accessToken);
//
//        Response<AccountsGetResponse> response = plaidClient.createPlaidClient()
//
//                .accountsBalanceGet(request)
//
//                .execute();
//        assert response.body() != null;
//        List<AccountBase> accounts = response.body().getAccounts();
//        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(accounts));
//        return accounts;
//    }
//}
