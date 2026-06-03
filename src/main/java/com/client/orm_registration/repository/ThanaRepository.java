package com.client.orm_registration.repository;

import com.client.orm_registration.entity.Thana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThanaRepository extends JpaRepository<Thana, Integer> {
    List<Thana> findByDistrictDistrictId(Integer districtId);
}