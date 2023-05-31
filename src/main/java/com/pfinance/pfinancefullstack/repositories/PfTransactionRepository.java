package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.PfTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PfTransactionRepository extends JpaRepository<PfTransaction, Long> {
    boolean existsByPlaidTransactionId(String plaidTransactionId);
}
