package com.example.koperasi.services.impl;

import com.example.koperasi.model.dto.request.SavingRequest;
import com.example.koperasi.model.dto.response.SavingResponse;
import com.example.koperasi.model.entity.Member;
import com.example.koperasi.model.entity.Saving;
import com.example.koperasi.repositories.MemberRepository;
import com.example.koperasi.repositories.SavingRepository;
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
public class SavingServiceImpl implements SavingService {

    private final SavingRepository savingRepository;
    private final MemberRepository memberRepository;

    private SavingResponse mapToResponse(Saving saving) {
        return SavingResponse.builder()
                .memberId(saving.getMember().getId())
                .memberName(saving.getMember().getName())
                .amount(saving.getAmount())
                .type(saving.getType())
                .build();
    }

    @CacheEvict(value = {
            "dashboard:summary",
            "dashboard:financial",
            "dashboard:active-members",
            "dashboard:risk-members"
    }, allEntries = true)
    @Override
    public SavingResponse create(SavingRequest request) {

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Saving saving = Saving.builder()
                .member(member)
                .amount(request.getAmount())
                .type(request.getType())
                .createdAt(LocalDateTime.now())
                .build();

        Saving saved = savingRepository.save(saving);

        return mapToResponse(saved);
    }

    @Override
    public List<SavingResponse> getAll() {
        return savingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<SavingResponse> getByMemberId(UUID memberId) {
        return savingRepository.findByMemberId(memberId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BigDecimal getTotalSaving(UUID memberId) {
        return savingRepository.getTotalSavingByMember(memberId);
    }
}
