package com.example.koperasi.services;

import com.example.koperasi.model.dto.request.MemberRequest;
import com.example.koperasi.model.dto.response.MemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface MemberService {
    MemberResponse create(MemberRequest request);
    List<MemberResponse> getAll();
    MemberResponse getById(UUID id);
    MemberResponse getByEmail(String email);
    MemberResponse update(UUID id, MemberRequest request);
    void delete(UUID id);
}