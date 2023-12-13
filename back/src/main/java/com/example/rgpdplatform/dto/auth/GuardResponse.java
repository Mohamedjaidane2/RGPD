package com.example.rgpdplatform.dto.auth;

import com.example.rgpdplatform.enums.Role_enum;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GuardResponse {
    private String email;
    private String role;
    private Boolean isExpired;
}
