package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
