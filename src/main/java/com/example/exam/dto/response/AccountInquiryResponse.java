package com.example.exam.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class AccountInquiryResponse extends BaseResponse {

    private Long customerNumber;
    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String address1;
    private String address2;
    private List<AccountDetails> savings;

    public static class AccountDetails {
        private Long accountNumber;
        private String accountType;
        private BigDecimal availableBalance;

        public AccountDetails(Long accountNumber, String accountType,
                              BigDecimal availableBalance) {
            this.accountNumber = accountNumber;
            this.accountType = accountType;
            this.availableBalance = availableBalance;
        }

        public Long getAccountNumber() {
            return accountNumber;
        }

        public String getAccountType() {
            return accountType;
        }

        public BigDecimal getAvailableBalance() {
            return availableBalance;
        }
    }

    // Generate getters and setters for the fields above.

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public List<AccountDetails> getSavings() {
        return savings;
    }

    public void setSavings(List<AccountDetails> savings) {
        this.savings = savings;
    }
}