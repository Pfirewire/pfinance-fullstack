package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.TransactionAutoAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionAutoAssignRepository extends JpaRepository<TransactionAutoAssign, Long> {
}
