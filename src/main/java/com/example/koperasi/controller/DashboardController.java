package com.example.koperasi.controller;

import com.example.koperasi.model.dto.request.LoanRequest;
import com.example.koperasi.model.dto.response.*;
import com.example.koperasi.services.DashboardService;
import com.example.koperasi.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/financial")
    public List<MemberFinancialResponse> getFinancialReport() {
        return dashboardService.getFinancialReport();
    }

    @GetMapping("/loan-approval")
    public List<LoanApprovalResponse> getLoanApproval() {
        return dashboardService.getLoanApprovalRate();
    }

    @GetMapping("/active-members")
    public List<ActiveMemberResponse> getActiveMembers() {
        return dashboardService.getTopActiveMembers();
    }

    @GetMapping("/risk-members")
    public List<RiskMemberResponse> getRiskMembers() {
        return dashboardService.getHighRiskMembers();
    }

}
