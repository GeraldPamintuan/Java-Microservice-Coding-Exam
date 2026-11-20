package com.example.exam.dto.response;

public class AccountCreationResponse extends BaseResponse {

    private Long customerNumber;

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }
}