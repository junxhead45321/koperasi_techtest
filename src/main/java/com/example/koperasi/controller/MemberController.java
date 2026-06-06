package com.example.koperasi.controller;

import com.example.koperasi.model.dto.request.MemberRequest;
import com.example.koperasi.model.dto.response.MemberResponse;
import com.example.koperasi.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/email/{email}")
    public MemberResponse getByEmail(@PathVariable String email) {
        return memberService.getByEmail(email);
    }

    @PostMapping
    public MemberResponse create(@RequestBody MemberRequest request) {
        return memberService.create(request);
    }

    @GetMapping
    public List<MemberResponse> getAll() {
        return memberService.getAll();
    }

    @GetMapping("/{id}")
    public MemberResponse getById(@PathVariable UUID id) {
        return memberService.getById(id);
    }

    @PutMapping("/{id}")
    public MemberResponse update(@PathVariable UUID id,
                                 @RequestBody MemberRequest request) {
        return memberService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        memberService.delete(id);
    }
}
