
package com.example.exam.service;

import com.example.exam.dto.request.AccountCreationRequest;
import com.example.exam.dto.response.AccountCreationResponse;
import com.example.exam.entity.Account;
import com.example.exam.entity.Customer;
import com.example.exam.enums.AccountType;
import com.example.exam.exception.NotFoundException;
import com.example.exam.repository.AccountRepository;
import com.example.exam.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import com.example.exam.dto.response.AccountInquiryResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final NumberGeneratorService numberGeneratorService;

    public AccountService(CustomerRepository customerRepository,
                          AccountRepository accountRepository,
                          NumberGeneratorService numberGeneratorService) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.numberGeneratorService = numberGeneratorService;
    }

    @Transactional
    public AccountCreationResponse createAccount(AccountCreationRequest request) {

        // 1. Create customer
        Customer customer = new Customer();

        customer.setCustomerNumber(numberGeneratorService.generateCustomerNumber());
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerMobile(request.getCustomerMobile());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setAddress1(request.getAddress1());
        customer.setAddress2(request.getAddress2());

        // 2. Create bank account
        Account account = new Account();

        account.setAccountNumber(numberGeneratorService.generateAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setAvailableBalance(new BigDecimal("500.00"));

        // 3. Connect customer and account
        customer.addAccount(account);

        // 4. Save customer and account
        Customer savedCustomer = customerRepository.save(customer);

        AccountCreationResponse response = new AccountCreationResponse();
        response.setCustomerNumber(savedCustomer.getCustomerNumber());
        response.setTransactionStatusCode(201);
        response.setTransactionStatusDescription("Customer account created");

        return response;
    }

    @Transactional(readOnly = true)
    public AccountInquiryResponse getCustomerAccount(Long customerNumber) {

        Customer customer = customerRepository
                .findByCustomerNumber(customerNumber)
                .orElseThrow(() ->
                        new NotFoundException("Customer not found"));

        List<AccountInquiryResponse.AccountDetails> accountDetails =
                customer.getAccounts().stream()
                        .map(account -> new AccountInquiryResponse.AccountDetails(
                                account.getAccountNumber(),
                                account.getAccountType() == AccountType.S
                                        ? "Savings" : "Checking",
                                account.getAvailableBalance()
                        ))
                        .collect(Collectors.toList());

        AccountInquiryResponse response = new AccountInquiryResponse();

        response.setCustomerNumber(customer.getCustomerNumber());
        response.setCustomerName(customer.getCustomerName());
        response.setCustomerMobile(customer.getCustomerMobile());
        response.setCustomerEmail(customer.getCustomerEmail());
        response.setAddress1(customer.getAddress1());
        response.setAddress2(customer.getAddress2());
        response.setSavings(accountDetails);
        response.setTransactionStatusCode(302);
        response.setTransactionStatusDescription("Customer Account found");

        return response;
    }

}
