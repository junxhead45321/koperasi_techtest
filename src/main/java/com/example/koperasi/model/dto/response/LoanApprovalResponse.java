package com.example.koperasi.model.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApprovalResponse {
    private UUID memberId;
    private String memberName;
    private Long totalLoans;
    private Long approvedLoans;
    private BigDecimal approvalRate;
}
