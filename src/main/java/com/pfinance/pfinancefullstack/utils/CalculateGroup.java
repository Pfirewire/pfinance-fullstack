package com.pfinance.pfinancefullstack.utils;

import com.pfinance.pfinancefullstack.models.PfBucket;
import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.repositories.PfCategoryRepository;

public class CalculateGroup {
    public static void allAmounts(PfCategory pfCategory, PfCategoryRepository groupDao) {
        double current = 0;
        double recurring = 0;
        double maximum = 0;
        for(PfBucket pfBucket : pfCategory.getBuckets()) {
            current += pfBucket.getCurrentAmount();
            recurring += pfBucket.getRecurringAmount();
            maximum += pfBucket.getRecurringAmount();
        }
        pfCategory.setTotalCurrentAmount(current);
        pfCategory.setTotalRecurringAmount(recurring);
        pfCategory.setTotalMaximumAmount(maximum);
        groupDao.save(pfCategory);
    }

    public static void addAmount(PfCategory pfCategory, PfBucket pfBucket, PfCategoryRepository groupDao) {
        pfCategory.setTotalCurrentAmount(pfCategory.getTotalCurrentAmount() + pfBucket.getCurrentAmount());
        pfCategory.setTotalRecurringAmount(pfCategory.getTotalRecurringAmount() + pfBucket.getRecurringAmount());
        pfCategory.setTotalMaximumAmount(pfCategory.getTotalMaximumAmount() + pfBucket.getMaximumAmount());
        groupDao.save(pfCategory);
    }
}
