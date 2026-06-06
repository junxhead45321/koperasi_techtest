package com.example.koperasi.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KoperasiEvent {
    private String eventId;
    private String eventType;
    private UUID memberId;
    private String memberName;
    private String email;
    private UUID loanId;
    private UUID savingId;
    private BigDecimal amount;
    private String status;
    private String savingType;
    private LocalDateTime eventTime;
}
