package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.PfBucket;
import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfBucketRepository extends JpaRepository<PfBucket, Long> {
    List<PfBucket> findAllByGroup(PfCategory pfCategory);

    List<PfBucket> findAllByUser(User user);
}
