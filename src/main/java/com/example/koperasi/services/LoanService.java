package com.example.koperasi.services;

import com.example.koperasi.model.dto.request.LoanRequest;
import com.example.koperasi.model.dto.request.SavingRequest;
import com.example.koperasi.model.dto.response.LoanResponse;
import com.example.koperasi.model.dto.response.SavingResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LoanService {

    LoanResponse create(LoanRequest request);

    List<LoanResponse> getAll();

    List<LoanResponse> getByMemberId(UUID memberId);

    LoanResponse updateStatus(UUID loanId, String status);
}