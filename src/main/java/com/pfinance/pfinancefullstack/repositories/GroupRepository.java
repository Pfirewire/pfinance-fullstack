package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
