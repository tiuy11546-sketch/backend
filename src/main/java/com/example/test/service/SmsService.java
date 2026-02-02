package com.example.test.service;

import com.example.test.dto.request.SmsRequest;
import com.example.test.entities.Sms;
import com.example.test.repository.SmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SmsService {

    @Autowired
    private SmsRepository smsRepository;

    public Page<Sms> getAllSms(Pageable pageable) {
        return smsRepository.findAll(
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"))
        );
    }

    public Page<Sms> getAllSmsByUserId(UUID userId, Pageable pageable) {
        return smsRepository.findAllByUserId(userId, pageable);
    }

    public Sms createSms(SmsRequest request) {
        // Check if SMS and address already exist for this user
        boolean exists = smsRepository.existsBySmsAndAddressAndUserId(request.getSms(), request.getAddress(), request.getUserId());
        if (exists) {
            throw new IllegalArgumentException("SMS with this address already exists for this user.");
        }
        Sms sms = new Sms();
        sms.setUserId(request.getUserId());
        sms.setSms(request.getSms());
        sms.setAddress(request.getAddress());
        sms.setDate(request.getDate());
        sms.setCreatedAt(LocalDateTime.now());
        sms.setUpdatedAt(LocalDateTime.now());
        return smsRepository.save(sms);
    }
}
