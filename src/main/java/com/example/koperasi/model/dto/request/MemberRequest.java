package com.example.koperasi.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {
    private String name;
    private String email;
    private String phone;
}
