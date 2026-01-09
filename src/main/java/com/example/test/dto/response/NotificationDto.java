package com.example.test.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private UUID id;
    private String type;
    private String title;
    private String message;
    private String username;
    private UUID userId; // Added to enable redirect to user edit page
    private String redirectUrl; // URL to redirect when notification is clicked
    private LocalDateTime timestamp;
    private boolean read;

    // Static factory method for user inactive notification
    public static NotificationDto userInactiveNotification(String username, UUID userId) {
        NotificationDto dto = new NotificationDto();
        dto.setId(UUID.randomUUID());
        dto.setType("USER_INACTIVE");
        dto.setTitle("Inactive User Login Attempt");
        dto.setMessage("User '" + username
                + "' attempted to login but account is not active. Please review and activate if appropriate.");
        dto.setUsername(username);
        dto.setUserId(userId);
        dto.setRedirectUrl("/users/edit/" + userId + "?page=0&size=10");
        dto.setTimestamp(LocalDateTime.now());
        dto.setRead(false);
        return dto;
    }

    // Static factory method for new user registration
    public static NotificationDto newUserNotification(String username, UUID userId) {
        NotificationDto dto = new NotificationDto();
        dto.setId(UUID.randomUUID());
        dto.setType("NEW_USER");
        dto.setTitle("New User Registration");
        dto.setMessage("New user '" + username + "' has registered and is awaiting activation.");
        dto.setUsername(username);
        dto.setUserId(userId);
        dto.setRedirectUrl("/users/edit/" + userId + "?page=0&size=10");
        dto.setTimestamp(LocalDateTime.now());
        dto.setRead(false);
        return dto;
    }
}
