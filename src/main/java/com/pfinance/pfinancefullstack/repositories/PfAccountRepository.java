package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.PfAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PfAccountRepository extends JpaRepository<PfAccount, Long> {
    PfAccount findByPlaidAccountId(String plaidAccountId);
}
