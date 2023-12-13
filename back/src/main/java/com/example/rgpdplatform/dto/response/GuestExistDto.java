package com.example.rgpdplatform.dto.response;

import com.example.rgpdplatform.enums.Profile_enum;
import com.example.rgpdplatform.model.Organisation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class GuestExistDto {

    private String id;

    @NotNull(message = "firstName cannot be empty")
    private String firstName;

    @NotNull(message = "lastName cannot be empty")
    private String lastName;

    @NotNull(message = "fonction cannot be empty")
    private String fonction;

    @NotNull(message = "telephone cannot be empty")
    private String telephone;

    @Email
    private String email;

    @NotNull(message = "expertise Field cannot be empty")
    private String expertiseField;

    @NotNull(message = "profil cannot be empty")
    private Profile_enum profil;

    @NotNull(message = "organisation cannot be empty")
    private Organisation organisation;
}
