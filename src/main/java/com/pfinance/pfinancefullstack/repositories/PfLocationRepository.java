package com.pfinance.pfinancefullstack.repositories;

import com.pfinance.pfinancefullstack.models.PfLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PfLocationRepository extends JpaRepository<PfLocation, Long> {
    boolean existsByAddressAndCityAndState(String address, String city, String state);
    PfLocation getByAddressAndCityAndState(String address, String city, String state);
}
