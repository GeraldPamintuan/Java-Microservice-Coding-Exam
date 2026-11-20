package com.example.exam.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class NumberGeneratorService {

    private final JdbcTemplate jdbcTemplate;

    public NumberGeneratorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long generateCustomerNumber() {
        return jdbcTemplate.queryForObject(
                "SELECT NEXT VALUE FOR customer_number_seq",
                Long.class
        );
    }

    public Long generateAccountNumber() {
        return jdbcTemplate.queryForObject(
                "SELECT NEXT VALUE FOR account_number_seq",
                Long.class
        );
    }
}