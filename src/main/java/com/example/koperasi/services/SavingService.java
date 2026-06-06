package com.example.koperasi.services;

import com.example.koperasi.model.dto.request.SavingRequest;
import com.example.koperasi.model.dto.response.SavingResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public interface SavingService {
    SavingResponse create(SavingRequest request);

    List<SavingResponse> getAll();

    List<SavingResponse> getByMemberId(UUID memberId);

    BigDecimal getTotalSaving(UUID memberId);
}