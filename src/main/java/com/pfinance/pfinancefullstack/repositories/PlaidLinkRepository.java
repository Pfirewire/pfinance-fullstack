package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.PlaidLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaidLinkRepository extends JpaRepository<PlaidLink, Long> {
}
