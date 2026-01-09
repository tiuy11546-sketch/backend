package com.example.test.controller;

import com.example.test.dto.request.UserUpdateRequest;
import com.example.test.dto.response.UserResponseDto;
import com.example.test.dto.validator.UserUpdateValidator;
import com.example.test.service.BankLoginService;
import com.example.test.service.ContactService;
import com.example.test.service.SmsService;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserUpdateValidator validator;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private BankLoginService bankLoginService;

    @GetMapping
    public String getUserTemplate(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<UserResponseDto> users = userService.getAllUsers(pageable);
        model.addAttribute("users", users);
        model.addAttribute("userUpdateRequest", new UserUpdateRequest());
        return "user";
    }
    
    @PostMapping
    public String updateUserTemplate(@ModelAttribute("userUpdateRequest") UserUpdateRequest request,
                                     Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Errors errors = validator.validateObject(request);
        if (errors.hasErrors()) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<UserResponseDto> users = userService.getAllUsers(pageable);
            model.addAttribute("users", users);
            return "user";
        }
        userService.updateUser(request);
        return "redirect:/users";
    }


    @GetMapping("{id}/sms")
    public String getSms(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable("id") UUID id,
            Model model
    ){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("smsPage", smsService.getAllSmsByUserId(id, pageable));
        model.addAttribute("userId", id);
        return "sms";
    }

    @GetMapping("{id}/contacts")
    public String getContacts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable("id") UUID id,
            Model model
    ){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("contactPage", contactService.getAllContactByUser(id, pageable));
        model.addAttribute("userId", id);
        return "contacts";
    }

    @GetMapping("{id}/bank-logins")
    public String getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable("id") UUID id,
            Model model
    ){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("bankPage", bankLoginService.getLogins(id, pageable));
        model.addAttribute("userId", id);
        return "bank-logins";
    }

    // Template: Edit region (show form)
    @GetMapping("/edit/{id}")
    public String editUserTemplate(@PathVariable UUID id,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponseDto> users = userService.getAllUsers(pageable);
        UserResponseDto user = userService.getUserById(id);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(user.getId());
        userUpdateRequest.setEmail(user.getEmail());
        userUpdateRequest.setFirstName(user.getFirstName());
        userUpdateRequest.setLastName(user.getLastName());
        userUpdateRequest.setPanNumber(user.getPanNumber());
        userUpdateRequest.setState(user.getState());
        userUpdateRequest.setLastDigit(user.getLastDigit());
        userUpdateRequest.setCommodity(user.getCommodity());
        userUpdateRequest.setPassword(user.getPassword());
        userUpdateRequest.setUsername(user.getUsername());
        userUpdateRequest.setEnabled(user.isEnabled());
        model.addAttribute("users", users);
        model.addAttribute("userUpdateRequest", userUpdateRequest);
        return "user";
    }
}
