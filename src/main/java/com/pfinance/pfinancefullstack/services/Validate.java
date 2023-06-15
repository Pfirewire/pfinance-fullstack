package com.pfinance.pfinancefullstack.services;

import com.pfinance.pfinancefullstack.models.*;
import com.pfinance.pfinancefullstack.repositories.*;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                 SERVICE TO HANDLE LOGIC TO VALIDATE USER OWNS ITEMS THEY ARE DOING CRUD FUNCTIONALITY ON
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Service
public class Validate {

    private final UserRepository userDao;
    private final PfAccountRepository pfAccountDao;
    private final PfBudgetRepository pfBudgetDao;
    private final PfCategoryRepository pfCategoryDao;
    private final PfBucketRepository pfBucketDao;

    public Validate(UserRepository userDao, PfAccountRepository pfAccountDao, PfBudgetRepository pfBudgetDao, PfCategoryRepository pfCategoryDao, PfBucketRepository pfBucketDao) {
        this.userDao = userDao;
        this.pfAccountDao = pfAccountDao;
        this.pfBudgetDao = pfBudgetDao;
        this.pfCategoryDao = pfCategoryDao;
        this.pfBucketDao = pfBucketDao;
    }

    public PfAccount userOwnsPfAccount(long id) {
        User user = UserUtils.currentUser(userDao);
        if(!pfAccountDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        PfAccount pfAccount = pfAccountDao.findById(id).get();
        if(!user.equals(userDao.findByPlaidLinks_PfAccounts(pfAccount))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return pfAccount;
    }

    public PfBudget userOwnsPfBudget(long id) {
        User user = UserUtils.currentUser(userDao);
        if(!pfBudgetDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        PfBudget pfBudget = pfBudgetDao.findById(id).get();
        if(!user.getPfBudgets().contains(pfBudget)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return pfBudget;
    }

    public PfCategory userOwnsPfCategory(long id) {
        User user = UserUtils.currentUser(userDao);
        if(!pfCategoryDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        PfCategory pfCategory = pfCategoryDao.findById(id).get();
        if(!user.equals(userDao.findByPfBudgets_PfCategories(pfCategory))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return pfCategory;
    }

    public PfBucket userOwnsPfBucket(long id) {
        System.out.println("Inside validate.userOwnsPfBucket");
        User user = UserUtils.currentUser(userDao);
        System.out.println("Username of currently logged in user: " + user.getUsername());
        if(!pfBucketDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        System.out.println("Bucket exists by id");
        PfBucket pfBucket = pfBucketDao.findById(id).get();
        System.out.println("Bucket name: " + pfBucket.getName());
        System.out.println("User who owns bucket: " + userDao.findByPfBudgets_PfCategories_PfBuckets(pfBucket).getUsername());
        if(!user.equals(userDao.findByPfBudgets_PfCategories_PfBuckets(pfBucket))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return pfBucket;
    }
}
