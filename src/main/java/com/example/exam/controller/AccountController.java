
package com.example.exam.controller;

import com.example.exam.dto.request.AccountCreationRequest;
import com.example.exam.dto.response.AccountCreationResponse;
import com.example.exam.service.AccountService;
import com.example.exam.dto.response.AccountInquiryResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountCreationResponse> createAccount(
            @Valid @RequestBody AccountCreationRequest request) {

        AccountCreationResponse response =
                accountService.createAccount(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<AccountInquiryResponse> getCustomerAccount(
            @PathVariable Long customerNumber) {

        AccountInquiryResponse response =
                accountService.getCustomerAccount(customerNumber);

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
}
