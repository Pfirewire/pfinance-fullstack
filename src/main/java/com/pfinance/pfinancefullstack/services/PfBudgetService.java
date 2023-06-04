package com.pfinance.pfinancefullstack.services;

import com.pfinance.pfinancefullstack.models.PfBucket;
import com.pfinance.pfinancefullstack.models.PfBudget;
import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.PfBucketRepository;
import com.pfinance.pfinancefullstack.repositories.PfBudgetRepository;
import com.pfinance.pfinancefullstack.repositories.PfCategoryRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PfBudgetService {

    private final UserRepository userDao;
    private final PfBudgetRepository pfBudgetDao;
    private final PfCategoryRepository pfCategoryDao;
    private final PfBucketRepository pfBucketDao;

    public PfBudgetService(UserRepository userDao, PfBudgetRepository pfBudgetDao, PfCategoryRepository pfCategoryDao, PfBucketRepository pfBucketDao) {
        this.userDao = userDao;
        this.pfBudgetDao = pfBudgetDao;
        this.pfCategoryDao = pfCategoryDao;
        this.pfBucketDao = pfBucketDao;
    }

    public PfBudget createNextMonthBudget(User user) {
        PfBudget oldPfBudget = getMostRecentPfBudget(user);
        PfBudget nextPfBudget;
        if(oldPfBudget.getMonth() == 12) {
            nextPfBudget = new PfBudget(
                    1,
                    oldPfBudget.getYear() + 1,
                    user
            );
        } else {
            nextPfBudget = new PfBudget(
                    oldPfBudget.getMonth() + 1,
                    oldPfBudget.getYear(),
                    user
            );
        }
        pfBudgetDao.save(nextPfBudget);
        fillPfBudgetWithEmptyPfCategoriesAndPfBuckets(nextPfBudget, oldPfBudget);
        List<PfBudget> userPfBudgets = user.getPfBudgets();
        userPfBudgets.add(nextPfBudget);
        user.setPfBudgets(userPfBudgets);
        userDao.save(user);
        return nextPfBudget;
    }

    public PfBudget getMostRecentPfBudget(User user) {
        List<PfBudget> userPfBudgets = pfBudgetDao.findAllByUser(user);
        PfBudget mostRecentPfBudget = userPfBudgets.get(0);
        for(PfBudget pfBudget : userPfBudgets) {
            if(mostRecentPfBudget.getYear() > pfBudget.getYear()) {
                continue;
            }
            if(mostRecentPfBudget.getYear() < pfBudget.getYear() || (mostRecentPfBudget.getYear() == pfBudget.getYear() && mostRecentPfBudget.getMonth() < pfBudget.getMonth())) {
                mostRecentPfBudget = pfBudget;
            }
        }
        return mostRecentPfBudget;
    }

    public void fillPfBudgetWithEmptyPfCategoriesAndPfBuckets(PfBudget pfBudget, PfBudget oldPfBudget) {
        List<PfCategory> oldPfCategories = oldPfBudget.getPfCategories();
        List<PfCategory> pfCategories = new ArrayList<>();
        for(PfCategory oldPfCategory : oldPfCategories) {
            PfCategory pfCategory = new PfCategory(oldPfCategory.getName(), pfBudget);
            pfCategoryDao.save(pfCategory);
            pfCategories.add(pfCategory);
            List<PfBucket> oldPfBuckets = oldPfCategory.getPfBuckets();
            List<PfBucket> pfBuckets = new ArrayList<>();
            for(PfBucket oldPfBucket : oldPfBuckets) {
                PfBucket pfBucket = new PfBucket(
                        oldPfBucket.getName(),
                        oldPfBucket.isAutofill(),
                        oldPfBucket.getRecurringType(),
                        oldPfBucket.getRecurringInterval(),
                        oldPfBucket.getRecurringAmount(),
                        0,
                        oldPfBucket.getMaximumAmount(),
                        0,
                        pfCategory
                );
                pfBucketDao.save(pfBucket);
                pfBuckets.add(pfBucket);
            }
            pfCategory.setPfBuckets(pfBuckets);
            pfCategoryDao.save(pfCategory);
        }
        pfBudget.setPfCategories(pfCategories);
        pfBudgetDao.save(pfBudget);
    }
}
