package com.example.koperasi.model.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ActiveMemberResponse {
    private UUID memberId;
    private String memberName;
    private Long savingCount;
    private Long loanCount;
    private Long activityScore;
}
