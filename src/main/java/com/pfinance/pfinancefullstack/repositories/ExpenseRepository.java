package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
