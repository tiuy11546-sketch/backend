package com.example.test.controller;

import com.example.test.dto.request.Activity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        // Add dashboard statistics
        model.addAttribute("totalUsers", 1250);
        model.addAttribute("activeSessions", 42);
        model.addAttribute("securityEvents", 18);
        model.addAttribute("failedLogins", 5);

        // Add recent activities
        List<Activity> recentActivities = Arrays.asList(
                new Activity("Login", "Admin user logged in", "2 min ago"),
                new Activity("User Created", "New user account created", "5 min ago"),
                new Activity("Security Scan", "System security scan completed", "10 min ago"),
                new Activity("Password Change", "User password updated", "15 min ago")
        );
        model.addAttribute("recentActivities", recentActivities);

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
}
