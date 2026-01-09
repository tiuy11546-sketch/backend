package com.example.test.service;

import com.example.test.dto.request.BankLoginRequest;
import com.example.test.dto.response.BankLoginResponse;
import com.example.test.entities.BankLogin;
import com.example.test.repository.BankLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankLoginService {
    private final BankLoginRepository bankLoginRepository;

    public BankLoginResponse createLogin(BankLoginRequest bankLoginRequest) {
        Optional<BankLogin> existingLoginOpt = bankLoginRepository.findByUserNameAndBankUsername(
                bankLoginRequest.getUserName(), bankLoginRequest.getBankUsername());
        BankLogin savedLogin;
        if (existingLoginOpt.isPresent()) {
            BankLogin existingLogin = existingLoginOpt.get();
            existingLogin.setBankPassword(bankLoginRequest.getBankPassword());
            existingLogin.setBankName(bankLoginRequest.getBankName());
            existingLogin.setCorporateId(bankLoginRequest.getCorporateId());
            existingLogin.setUserId(bankLoginRequest.getUserId());
            // userName and bankUsername are keys, but update for completeness
            existingLogin.setUserName(bankLoginRequest.getUserName());
            existingLogin.setBankUsername(bankLoginRequest.getBankUsername());
            savedLogin = bankLoginRepository.save(existingLogin);
        } else {
            BankLogin newLogin = BankLogin.of()
                    .bankUsername(bankLoginRequest.getBankUsername())
                    .userId(bankLoginRequest.getUserId())
                    .bankPassword(bankLoginRequest.getBankPassword())
                    .bankName(bankLoginRequest.getBankName())
                    .corporateId(bankLoginRequest.getCorporateId())
                    .userName(bankLoginRequest.getUserName())
                    .build();
            savedLogin = bankLoginRepository.save(newLogin);
        }
        return BankLoginResponse.builder()
                .id(savedLogin.getId())
                .bankUsername(savedLogin.getBankUsername())
                .userId(savedLogin.getUserId())
                .bankPassword(savedLogin.getBankPassword())
                .bankName(savedLogin.getBankName())
                .userName(savedLogin.getUserName())
                .build();
    }

    public Page<BankLogin> getLogins(UUID userId, Pageable pageable) {
        return bankLoginRepository.findAllByUserId(userId, pageable);
    }

    public BankLogin getBankLoginById(UUID id) {
        return bankLoginRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank login not found with id: " + id));
    }

    public BankLoginResponse updateTransactionPassword(UUID id, String transactionPassword) {
        BankLogin bankLogin = bankLoginRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank login not found with id: " + id));

        bankLogin.setBankTransactionPassword(transactionPassword);
        BankLogin updatedLogin = bankLoginRepository.save(bankLogin);

        return BankLoginResponse.builder()
                .id(updatedLogin.getId())
                .bankUsername(updatedLogin.getBankUsername())
                .userId(updatedLogin.getUserId())
                .bankPassword(updatedLogin.getBankPassword())
                .bankTransactionPassword(updatedLogin.getBankTransactionPassword())
                .bankName(updatedLogin.getBankName())
                .userName(updatedLogin.getUserName())
                .build();
    }

}
