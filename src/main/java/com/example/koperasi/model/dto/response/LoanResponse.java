package com.example.koperasi.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class LoanResponse {
    private UUID memberId;
    private String memberName;
    private BigDecimal amount;
    private Integer durationMonth;
    private BigDecimal interestRate;
    private String status;
}
