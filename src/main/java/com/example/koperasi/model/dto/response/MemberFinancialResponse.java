package com.example.koperasi.model.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MemberFinancialResponse {
    private UUID memberId;
    private String memberName;
    private BigDecimal totalSaving;
    private BigDecimal totalLoan;
    private BigDecimal netBalance;
}
