
package com.example.exam.controller;

import com.example.exam.exception.GlobalExceptionHandler;
import com.example.exam.exception.NotFoundException;
import com.example.exam.service.AccountService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import(GlobalExceptionHandler.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;


    @Test
    void shouldReturnBadRequestWhenEmailIsMissing() throws Exception {

        String request = "{"
                + "\"customerName\":\"Test Customer\","
                + "\"customerMobile\":\"09123456789\","
                + "\"address1\":\"Angeles City\","
                + "\"accountType\":\"S\""
                + "}";

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400))
                .andExpect(jsonPath("$.transactionStatusDescription")
                        .value("Email is required"));

        verifyNoInteractions(accountService);
    }


    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {

        String request = "{"
                + "\"customerName\":\"Test Customer\","
                + "\"customerMobile\":\"09123456789\","
                + "\"customerEmail\":\"invalid-email\","
                + "\"address1\":\"Angeles City\","
                + "\"accountType\":\"S\""
                + "}";

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400))
                .andExpect(jsonPath("$.transactionStatusDescription")
                        .value("Invalid email format"));

        verifyNoInteractions(accountService);
    }

    @Test
    void shouldReturnBadRequestWhenCustomerNameIsMissing() throws Exception {

        String request = "{"
                + "\"customerMobile\":\"09123456789\","
                + "\"customerEmail\":\"test@gmail.com\","
                + "\"address1\":\"Angeles City\","
                + "\"accountType\":\"S\""
                + "}";

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400))
                .andExpect(jsonPath("$.transactionStatusDescription")
                        .value("Customer name is required"));

        verifyNoInteractions(accountService);
    }

    @Test
    void shouldReturnBadRequestWhenAccountTypeIsMissing() throws Exception {

        String request = "{"
                + "\"customerName\":\"Test Customer\","
                + "\"customerMobile\":\"09123456789\","
                + "\"customerEmail\":\"test@gmail.com\","
                + "\"address1\":\"Angeles City\""
                + "}";

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400))
                .andExpect(jsonPath("$.transactionStatusDescription")
                        .value("Account type is required"));

        verifyNoInteractions(accountService);
    }

    @Test
    void shouldReturnBadRequestWhenAccountTypeIsInvalid() throws Exception {

        String request = "{"
                + "\"customerName\":\"Test Customer\","
                + "\"customerMobile\":\"09123456789\","
                + "\"customerEmail\":\"test@gmail.com\","
                + "\"address1\":\"Angeles City\","
                + "\"accountType\":\"X\""
                + "}";

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400));

        verifyNoInteractions(accountService);
    }


    @Test
    void shouldReturnUnauthorizedWhenCustomerNotFound() throws Exception {

        Long customerNumber = 99999999L;

        when(accountService.getCustomerAccount(customerNumber))
                .thenThrow(new NotFoundException("Customer not found"));

        mockMvc.perform(get("/api/v1/account/{customerNumber}", customerNumber))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.transactionStatusCode").value(401))
                .andExpect(jsonPath("$.transactionStatusDescription")
                        .value("Customer not found"));

        verify(accountService).getCustomerAccount(customerNumber);
    }


}
