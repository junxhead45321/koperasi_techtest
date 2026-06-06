package com.example.koperasi.controller;

import com.example.koperasi.model.dto.request.SavingRequest;
import com.example.koperasi.model.dto.response.SavingResponse;
import com.example.koperasi.services.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/api/saving")
@RequiredArgsConstructor
public class SavingController {

    private final SavingService savingService;

    @PostMapping
    public SavingResponse create(@RequestBody SavingRequest request) {
        return savingService.create(request);
    }

    @GetMapping
    public List<SavingResponse> getAll() {
        return savingService.getAll();
    }

    @GetMapping("/member/{memberId}")
    public List<SavingResponse> getByMember(@PathVariable UUID memberId) {
        return savingService.getByMemberId(memberId);
    }

    @GetMapping("/member/{memberId}/total")
    public BigDecimal getTotalSaving(@PathVariable UUID memberId) {
        return savingService.getTotalSaving(memberId);
    }
}
