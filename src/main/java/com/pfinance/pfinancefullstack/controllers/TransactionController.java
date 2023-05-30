//package com.pfinance.pfinancefullstack.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pfinance.pfinancefullstack.models.User;
//import com.pfinance.pfinancefullstack.repositories.UserRepository;
//import com.pfinance.pfinancefullstack.services.JsonPrint;
//import com.pfinance.pfinancefullstack.services.PlaidClientService;
//import com.pfinance.pfinancefullstack.utils.UserUtils;
//import com.plaid.client.model.Transaction;
//import com.plaid.client.model.TransactionsGetRequest;
//import com.plaid.client.model.TransactionsGetResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import retrofit2.Response;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@CrossOrigin
//@RequestMapping("/api")
//public class TransactionController {
//
//    @Autowired
//    private PlaidClientService plaidClient;
//
//    @Autowired
//    private JsonPrint jsonPrint;
//    private UserRepository userDao;
//
//    public TransactionController(UserRepository userDao) {
//        this.userDao = userDao;
//    }
//
//    @GetMapping("/transactions")
//    public List<Transaction> getUserTransactions() throws ParseException, IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println("Inside getUserExpenses");
//        User user = UserUtils.currentUser(userDao);
//        jsonPrint.object(user);
//        LocalDate currentDate = LocalDate.now();
//        LocalDate startDate = currentDate.minusWeeks(1);
//
//// Pull transactions for a date range
//
//        TransactionsGetRequest request = new TransactionsGetRequest()
//                .accessToken(user.getAccessToken())
//                .startDate(startDate)
//                .endDate(currentDate);
//
//        Response<TransactionsGetResponse> response = plaidClient.createPlaidClient().transactionsGet(request).execute();
//
//        List<Transaction> transactions = new ArrayList <>();
//        System.out.println("transactions:");
//        jsonPrint.object(response.body().getTransactions());
//
//        transactions.addAll(response.body().getTransactions());
//
//// Manipulate the offset parameter to paginate
//
//// transactions and retrieve all available data
//
////        while (transactions.size() < response.body().getTotalTransactions()) {
////
////            TransactionsGetRequestOptions options = new TransactionsGetRequestOptions()
////
////                    .offset(transactions.size());
////
////            TransactionsGetRequest request = new TransactionsGetRequest()
////
////                    .accessToken(accessToken)
////
////                    .startDate(startDate)
////
////                    .endDate(endDate)
////
////                    .options(options);
////
////            Response<TransactionsGetResponse>
////
////                    response = plaidClient.transactionsGet(request).execute();
////
////            transactions.addAll(response.body().getTransactions());
////        }
//
//        return transactions;
//    }
//}
