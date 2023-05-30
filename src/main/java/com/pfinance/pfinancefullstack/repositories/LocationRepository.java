package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
