
package com.example.exam.service;

import com.example.exam.dto.request.AccountCreationRequest;
import com.example.exam.dto.response.AccountCreationResponse;
import com.example.exam.dto.response.AccountInquiryResponse;
import com.example.exam.entity.Account;
import com.example.exam.entity.Customer;
import com.example.exam.enums.AccountType;
import com.example.exam.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private NumberGeneratorService numberGeneratorService;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldCreateCustomerAccount() {

        AccountCreationRequest request =
                new AccountCreationRequest();

        request.setCustomerName("Test Customer");
        request.setCustomerMobile("09123456789");
        request.setCustomerEmail("test@gmail.com");
        request.setAddress1("Angeles City");
        request.setAccountType(AccountType.S);

        when(numberGeneratorService.generateCustomerNumber())
                .thenReturn(12345678L);

        when(numberGeneratorService.generateAccountNumber())
                .thenReturn(10001L);

        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AccountCreationResponse result =
                accountService.createAccount(request);

        assertEquals(12345678L, result.getCustomerNumber());
        assertEquals(201, result.getTransactionStatusCode());
        assertEquals(
                "Customer account created",
                result.getTransactionStatusDescription()
        );

        verify(customerRepository).save(any(Customer.class));
        verify(numberGeneratorService).generateCustomerNumber();
        verify(numberGeneratorService).generateAccountNumber();
    }


    @Test
    void shouldGetCustomerAccount() {

        Long customerNumber = 12345678L;

        Customer customer = new Customer();
        customer.setCustomerNumber(customerNumber);
        customer.setCustomerName("Test Customer");
        customer.setCustomerMobile("09123456789");
        customer.setCustomerEmail("test@gmail.com");
        customer.setAddress1("Angeles City");

        Account account = new Account();
        account.setAccountNumber(10001L);
        account.setAccountType(AccountType.S);
        account.setAvailableBalance(new BigDecimal("500.00"));

        customer.addAccount(account);

        when(customerRepository.findByCustomerNumber(customerNumber))
                .thenReturn(Optional.of(customer));

        AccountInquiryResponse result =
                accountService.getCustomerAccount(customerNumber);

        assertEquals(customerNumber, result.getCustomerNumber());
        assertEquals("Test Customer", result.getCustomerName());
        assertEquals(1, result.getSavings().size());

        assertEquals(10001L,
                result.getSavings().get(0).getAccountNumber());

        assertEquals("Savings",
                result.getSavings().get(0).getAccountType());

        assertEquals(new BigDecimal("500.00"),
                result.getSavings().get(0).getAvailableBalance());

        verify(customerRepository).findByCustomerNumber(customerNumber);
    }

}
