package com.example.test.repository;

import com.example.test.entities.BankLogin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankLoginRepository  extends JpaRepository<BankLogin, UUID> {
    boolean existsByUserNameAndBankUsername(String userName, String bankUsername);
    Optional<BankLogin> findByUserNameAndBankUsername(String userName, String bankUsername);
    Page<BankLogin> findAllByUserId(UUID userId, Pageable pageable);
}
