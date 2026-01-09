package com.example.test.service;

import com.example.test.dto.request.UserUpdateRequest;
import com.example.test.dto.response.LoginResponse;
import com.example.test.dto.response.UserResponseDto;
import com.example.test.entities.Role;
import com.example.test.entities.User;
import com.example.test.exception.UserNotFoundException;
import com.example.test.repository.RoleRepository;
import com.example.test.repository.UserRepository;
import com.example.test.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private NotificationService notificationService;

    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPanNumber(),
                user.getState(),
                user.getLastDigit(),
                user.getCommodity()));
    }

    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPanNumber(),
                user.getState(),
                user.getLastDigit(),
                user.getCommodity());
    }

    public UserResponseDto updateUser(UserUpdateRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getId()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPanNumber(request.getPanNumber());
        user.setState(request.getState());
        user.setLastDigit(request.getLastDigit());
        user.setCommodity(request.getCommodity());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setEnabled(request.isEnabled());
        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPanNumber(),
                user.getState(),
                user.getLastDigit(),
                user.getCommodity());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public LoginResponse login(String username, String password) {
        // Implement login logic here
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User createdUser;
        boolean isNewUser = false;
        Role userRole = roleRepository.findByName("USER").orElseGet(() -> roleRepository.save(new Role("USER")));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(password);
            user.setUpdatedAt(LocalDateTime.now());
            createdUser = userRepository.save(user);
        } else {
            User user = new User();
            user.setUsername(username);
            user.setEnabled(false);
            user.setPassword(password);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setRoles(List.of(userRole));
            createdUser = userRepository.save(user);
            isNewUser = true;
        }

        if (createdUser.enabled) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUsername(createdUser.getUsername());
            loginResponse.setId(createdUser.getId());
            loginResponse.setToken(jwtUtil.generateToken(createdUser.getUsername()));
            return loginResponse;
        } else {
            // Notify admin about inactive user login attempt
            if (isNewUser) {
                notificationService.notifyNewUserRegistration(createdUser.getUsername(), createdUser.getId());
            } else {
                notificationService.notifyUserInactive(createdUser.getUsername(), createdUser.getId());
            }

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUsername(createdUser.getUsername());
            loginResponse.setId(createdUser.getId());
            loginResponse.setToken("");
            return loginResponse;
        }
    }

    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPanNumber(),
                user.getState(),
                user.getLastDigit(),
                user.getCommodity());
    }

}
