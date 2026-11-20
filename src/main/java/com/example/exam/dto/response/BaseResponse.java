package com.example.exam.dto.response;

public class BaseResponse {

    private int transactionStatusCode;
    private String transactionStatusDescription;

    public int getTransactionStatusCode() {
        return transactionStatusCode;
    }

    public void setTransactionStatusCode(int transactionStatusCode) {
        this.transactionStatusCode = transactionStatusCode;
    }

    public String getTransactionStatusDescription() {
        return transactionStatusDescription;
    }

    public void setTransactionStatusDescription(
            String transactionStatusDescription) {
        this.transactionStatusDescription = transactionStatusDescription;
    }
}