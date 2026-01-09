package com.example.test.controller;

import com.example.test.dto.request.ContactRequest;
import com.example.test.dto.request.SmsRequest;
import com.example.test.dto.response.AppResponse;
import com.example.test.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<AppResponse> getContacts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page-1, size);
        return ResponseEntity.ok(AppResponse.success("Contacts fetched successfully",
                contactService.getAllContacts(pageable)));
    }

    @PostMapping
    public ResponseEntity<AppResponse> createContact(@RequestBody ContactRequest request)
    {
        return ResponseEntity.ok(AppResponse.success("Contact created successfully",
                contactService.createContact(request)));
    }
}
