package com.example.koperasi.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SavingResponse {
    public UUID memberId;
    public String memberName;
    public BigDecimal amount;
    public String type;
}
