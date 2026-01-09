package com.example.test.service;

import com.example.test.dto.response.NotificationDto;
import com.example.test.util.AppLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Send notification to all admin users listening on /topic/admin/notifications
     */
    public void notifyAdmins(NotificationDto notification) {
        AppLogger.info("Sending notification to admins: " + notification.getType() + " - " + notification.getMessage());
        messagingTemplate.convertAndSend("/topic/admin/notifications", notification);
    }

    /**
     * Notify admin when an inactive user tries to login
     */
    public void notifyUserInactive(String username, UUID userId) {
        NotificationDto notification = NotificationDto.userInactiveNotification(username, userId);
        notifyAdmins(notification);
    }

    /**
     * Notify admin when a new user registers
     */
    public void notifyNewUserRegistration(String username, UUID userId) {
        NotificationDto notification = NotificationDto.newUserNotification(username, userId);
        notifyAdmins(notification);
    }

    /**
     * Send custom notification to admins
     */
    public void sendCustomNotification(String type, String title, String message, String username, UUID userId,
            String redirectUrl) {
        NotificationDto notification = new NotificationDto();
        notification.setId(UUID.randomUUID());
        notification.setType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setUsername(username);
        notification.setUserId(userId);
        notification.setRedirectUrl(redirectUrl);
        notification.setTimestamp(java.time.LocalDateTime.now());
        notification.setRead(false);
        notifyAdmins(notification);
    }
}
