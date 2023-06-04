package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.PfBucket;
import com.pfinance.pfinancefullstack.models.PfBudget;
import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfBudgetRepository extends JpaRepository<PfBudget, Long> {
    List<PfBudget> findAllByUser(User user);
    PfBudget findByPfCategories(PfCategory pfCategory);
    PfBudget findByUserAndMonthAndYear(User user, String month, String year);
}
