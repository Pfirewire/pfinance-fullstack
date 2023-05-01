package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
