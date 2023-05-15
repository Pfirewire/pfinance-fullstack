package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.Bucket;
import com.pfinance.pfinancefullstack.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    List<Bucket> findAllByGroup(Group group);
}
