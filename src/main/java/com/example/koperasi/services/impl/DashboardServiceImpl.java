package com.example.koperasi.services.impl;

import com.example.koperasi.model.dto.response.*;
import com.example.koperasi.repositories.DashboardRepository;
import com.example.koperasi.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository repository;




    @Override
    @Cacheable(value = "dashboard:financial", key = "'financial'")
    public List<MemberFinancialResponse> getFinancialReport() {
        return repository.getMemberFinancialReport()
                .stream()
                .map(o -> MemberFinancialResponse.builder()
                        .memberId(UUID.fromString(o[0].toString()))
                        .memberName(o[1].toString())
                        .totalSaving((BigDecimal) o[2])
                        .totalLoan((BigDecimal) o[3])
                        .netBalance((BigDecimal) o[4])
                        .build())
                .toList();
    }

    @Override
    @Cacheable(value = "dashboard:active-members", key = "'active'")
    public List<LoanApprovalResponse> getLoanApprovalRate() {
        return repository.getLoanApprovalRate()
                .stream()
                .map(o -> LoanApprovalResponse.builder()
                        .memberId(UUID.fromString(o[0].toString()))
                        .memberName(o[1].toString())
                        .totalLoans(((Number) o[2]).longValue())
                        .approvedLoans(((Number) o[3]).longValue())
                        .approvalRate((BigDecimal) o[4])
                        .build())
                .toList();
    }

    @Override
    @Cacheable(value = "dashboard:top-active-members", key = "'top'")
    public List<ActiveMemberResponse> getTopActiveMembers() {
        return repository.getTopActiveMembers()
                .stream()
                .map(o -> ActiveMemberResponse.builder()
                        .memberId(UUID.fromString(o[0].toString()))
                        .memberName(o[1].toString())
                        .savingCount(((Number) o[2]).longValue())
                        .loanCount(((Number) o[3]).longValue())
                        .activityScore(((Number) o[4]).longValue())
                        .build())
                .toList();
    }

    @Override
    @Cacheable(value = "dashboard:risk-members", key = "'risk'")
    public List<RiskMemberResponse> getHighRiskMembers() {
        return repository.getHighRiskMembers()
                .stream()
                .map(o -> RiskMemberResponse.builder()
                        .memberId(UUID.fromString(o[0].toString()))
                        .memberName(o[1].toString())
                        .saving((BigDecimal) o[2])
                        .loan((BigDecimal) o[3])
                        .riskGap((BigDecimal) o[4])
                        .build())
                .toList();
    }
}
