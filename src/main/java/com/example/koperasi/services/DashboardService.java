package com.example.koperasi.services;

import com.example.koperasi.model.dto.request.LoanRequest;
import com.example.koperasi.model.dto.response.*;

import java.util.List;
import java.util.UUID;

public interface DashboardService {

    List<MemberFinancialResponse> getFinancialReport();

    List<LoanApprovalResponse> getLoanApprovalRate();

    List<ActiveMemberResponse> getTopActiveMembers();

    List<RiskMemberResponse> getHighRiskMembers();
}