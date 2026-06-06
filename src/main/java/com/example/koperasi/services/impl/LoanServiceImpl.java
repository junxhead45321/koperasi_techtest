package com.example.koperasi.services.impl;

import com.example.koperasi.model.dto.request.LoanRequest;
import com.example.koperasi.model.dto.request.SavingRequest;
import com.example.koperasi.model.dto.response.LoanResponse;
import com.example.koperasi.model.dto.response.SavingResponse;
import com.example.koperasi.model.entity.Loan;
import com.example.koperasi.model.entity.Member;
import com.example.koperasi.model.entity.Saving;
import com.example.koperasi.repositories.LoanRepository;
import com.example.koperasi.repositories.MemberRepository;
import com.example.koperasi.repositories.SavingRepository;
import com.example.koperasi.services.LoanService;
import com.example.koperasi.services.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;

    // mapping entity -> dto
    private LoanResponse mapToResponse(Loan loan) {
        return LoanResponse.builder()
                .memberId(loan.getMember().getId())
                .memberName(loan.getMember().getName())
                .amount(loan.getAmount())
                .durationMonth(loan.getDurationMonth())
                .interestRate(loan.getInterestRate())
                .status(loan.getStatus())
                .build();
    }

    // CREATE LOAN
    @CacheEvict(value = {
            "dashboard:summary",
            "dashboard:financial",
            "dashboard:active-members",
            "dashboard:risk-members"
    }, allEntries = true)
    @Override
    public LoanResponse create(LoanRequest request) {

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Loan loan = Loan.builder()
                .member(member)
                .amount(request.getAmount())
                .durationMonth(request.getDurationMonth())
                .interestRate(request.getInterestRate())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(loanRepository.save(loan));
    }

    // GET ALL
    @Override
    public List<LoanResponse> getAll() {
        return loanRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY MEMBER
    @Override
    public List<LoanResponse> getByMemberId(UUID memberId) {
        return loanRepository.findByMemberId(memberId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // APPROVE / REJECT LOAN
    @Override
    public LoanResponse updateStatus(UUID loanId, String status) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus(status);

        return mapToResponse(loanRepository.save(loan));
    }
}
