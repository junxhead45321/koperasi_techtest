package com.example.koperasi.model.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RiskMemberResponse {
    private UUID memberId;
    private String memberName;
    private BigDecimal saving;
    private BigDecimal loan;
    private BigDecimal riskGap;
}
