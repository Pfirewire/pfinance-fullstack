package com.pfinance.pfinancefullstack.utils;

import com.pfinance.pfinancefullstack.models.PfBucket;
import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.repositories.PfCategoryRepository;

public class CalculateGroup {
    public static void allAmounts(PfCategory pfCategory, PfCategoryRepository groupDao) {
        double available = 0;
        double assigned = 0;
        for(PfBucket pfBucket : pfCategory.getPfBuckets()) {
            available += pfBucket.getAvailableAmount();
            assigned += pfBucket.getAssignedAmount();
        }
        pfCategory.setTotalAvailableAmount(available);
        pfCategory.setTotalAssignedAmount(assigned);
        groupDao.save(pfCategory);
    }

    public static void addAmount(PfCategory pfCategory, PfBucket pfBucket, PfCategoryRepository groupDao) {
        pfCategory.setTotalAvailableAmount(pfCategory.getTotalAvailableAmount() + pfBucket.getAvailableAmount());
        pfCategory.setTotalAssignedAmount(pfCategory.getTotalAssignedAmount() + pfBucket.getAssignedAmount());
        groupDao.save(pfCategory);
    }
}
