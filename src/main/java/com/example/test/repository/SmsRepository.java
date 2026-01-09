package com.example.test.repository;

import com.example.test.entities.Sms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SmsRepository extends JpaRepository<Sms, Long> {
    boolean existsBySmsAndAddressAndUserId(String sms, String address, UUID userId);

    Page<Sms> findAllByUserId(UUID userId, Pageable pageable);
}
