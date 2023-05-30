package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.PlaidCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaidCategoryRepository extends JpaRepository<PlaidCategory, Long> {
}
