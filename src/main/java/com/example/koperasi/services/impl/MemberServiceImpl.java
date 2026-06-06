package com.example.koperasi.services.impl;

import com.example.koperasi.model.dto.request.MemberRequest;
import com.example.koperasi.model.dto.response.MemberResponse;
import com.example.koperasi.model.entity.Member;
import com.example.koperasi.repositories.MemberRepository;
import com.example.koperasi.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private MemberResponse mapToResponse(Member member) {
        return MemberResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .build();
    }

    // DTO -> ENTITY
    private Member mapToEntity(MemberRequest request) {
        return Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // CREATE (SAVE)
    @Override
    public MemberResponse create(MemberRequest request) {
        Member member = mapToEntity(request);
        return mapToResponse(memberRepository.save(member));
    }

    // FIND ALL
    @Override
    public List<MemberResponse> getAll() {
        return memberRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // FIND BY ID
    @Override
    public MemberResponse getById(UUID id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return mapToResponse(member);
    }

    // FIND BY EMAIL
    @Override
    public MemberResponse getByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return mapToResponse(member);
    }

    // UPDATE
    @Override
    public MemberResponse update(UUID id, MemberRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // update field
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());

        return mapToResponse(memberRepository.save(member));
    }

    // DELETE
    @Override
    public void delete(UUID id) {
        memberRepository.deleteById(id);
    }
}
