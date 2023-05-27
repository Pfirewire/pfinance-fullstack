package com.pfinance.pfinancefullstack.utils;

import com.pfinance.pfinancefullstack.models.Bucket;
import com.pfinance.pfinancefullstack.models.Group;
import com.pfinance.pfinancefullstack.repositories.GroupRepository;

public class CalculateGroup {
    public static void allAmounts(Group group, GroupRepository groupDao) {
        double current = 0;
        double recurring = 0;
        double maximum = 0;
        for(Bucket bucket : group.getBuckets()) {
            current += bucket.getCurrentAmount();
            recurring += bucket.getRecurringAmount();
            maximum += bucket.getRecurringAmount();
        }
        group.setTotalCurrentAmount(current);
        group.setTotalRecurringAmount(recurring);
        group.setTotalMaximumAmount(maximum);
        groupDao.save(group);
    }

    public static void addAmount(Group group, Bucket bucket, GroupRepository groupDao) {
        group.setTotalCurrentAmount(group.getTotalCurrentAmount() + bucket.getCurrentAmount());
        group.setTotalRecurringAmount(group.getTotalRecurringAmount() + bucket.getRecurringAmount());
        group.setTotalMaximumAmount(group.getTotalMaximumAmount() + bucket.getMaximumAmount());
        groupDao.save(group);
    }
}
