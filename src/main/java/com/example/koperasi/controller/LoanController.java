package com.example.koperasi.controller;

import com.example.koperasi.model.dto.request.LoanRequest;
import com.example.koperasi.model.dto.response.LoanResponse;
import com.example.koperasi.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/v1/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public LoanResponse create(@RequestBody LoanRequest request) {
        return loanService.create(request);
    }

    @GetMapping
    public List<LoanResponse> getAll() {
        return loanService.getAll();
    }

    @GetMapping("/member/{memberId}")
    public List<LoanResponse> getByMember(@PathVariable UUID memberId) {
        return loanService.getByMemberId(memberId);
    }

    @PutMapping("/{loanId}/status")
    public LoanResponse updateStatus(
            @PathVariable UUID loanId,
            @RequestParam String status
    ) {
        return loanService.updateStatus(loanId, status);
    }
}
