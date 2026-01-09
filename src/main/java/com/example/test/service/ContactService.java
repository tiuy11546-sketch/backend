package com.example.test.service;

import com.example.test.dto.request.ContactRequest;
import com.example.test.entities.Contact;
import com.example.test.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact createContact(ContactRequest contactRequest) {
        // Check if phone already exists for this user
        boolean phoneExists = contactRepository.existsByPhoneAndUserId(contactRequest.getPhone(), contactRequest.getUserId());
        if (phoneExists) {
            throw new IllegalArgumentException("Phone number already exists for this user.");
        }
        Contact contact = new Contact();
        contact.setUserId(contactRequest.getUserId());
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());
        contact.setName(contactRequest.getName());
        contact.setPhone(contactRequest.getPhone());
        // Logic to save contact to the database
        return contactRepository.save(contact);
    }

    public Page<Contact> getAllContacts(Pageable pageable) {
        return contactRepository.findAll(pageable);
    }

    public Page<Contact> getAllContactByUser(UUID id, Pageable pageable) {
        return contactRepository.findAllByUserId(id,pageable);
    }
}
