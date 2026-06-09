package com.client.orm_registration.repository;

import com.client.orm_registration.entity.ClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long> {

    Optional<ClientDetails> findByClient_ClientId(Long clientId);

}