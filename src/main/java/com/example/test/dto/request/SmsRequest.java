package com.example.test.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public class SmsRequest {
    private UUID userId;
    private String sms;
    private String date;
    private String address;

    public SmsRequest(UUID userId, String sms, String date, String address) {
        this.userId = userId;
        this.sms = sms;
        this.date = date;
        this.address = address;
    }

    public SmsRequest() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
