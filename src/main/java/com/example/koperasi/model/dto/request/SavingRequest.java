package com.example.koperasi.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class SavingRequest {
    private UUID memberId;
    private BigDecimal amount;
    private String type;
}
