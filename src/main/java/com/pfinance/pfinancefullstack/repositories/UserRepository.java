package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.PfAccount;
import com.pfinance.pfinancefullstack.models.PfBucket;
import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByPfBudgets_PfCategories_PfBuckets(PfBucket pfBucket);
    User findByPfBudgets_PfCategories(PfCategory pfCategory);
    User findByPlaidLinks_PfAccounts(PfAccount pfAccount);
}
