package com.example.test.controller;

import com.example.test.dto.request.SmsRequest;
import com.example.test.dto.response.AppResponse;
import com.example.test.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/sms")
public class SmsApiController {

    @Autowired
    private SmsService smsService;

    @GetMapping
    public ResponseEntity<AppResponse> getSms(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(AppResponse.success("Sms fetched successfully",
                smsService.getAllSms(pageable)));
    }

    @PostMapping
    public ResponseEntity<AppResponse> createSms(@RequestBody SmsRequest request)
    {
        return ResponseEntity.ok(AppResponse.success("Sms created successfully",
                smsService.createSms(request)));
    }
}
