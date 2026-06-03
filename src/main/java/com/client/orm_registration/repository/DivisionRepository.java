package com.client.orm_registration.repository;

import com.client.orm_registration.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {
    List<Division> findByCountryCountryId(Integer countryId);
}