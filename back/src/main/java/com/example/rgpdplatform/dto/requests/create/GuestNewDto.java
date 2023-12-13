package com.example.rgpdplatform.dto.requests.create;

import com.example.rgpdplatform.enums.Profile_enum;
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
public class GuestNewDto {

    @NotNull(message = "firstName cannot be empty")
    private String firstName;

    @NotNull(message = "lastName cannot be empty")
    private String lastName;

    @NotNull(message = "function cannot be empty")
    private String function;

    @NotNull(message = "telephone cannot be empty")
    private String telephone;

    @Email
    private String email;

    @NotNull(message = "expertise Field cannot be empty")
    private String expertiseField;

    @NotNull(message = "profile cannot be empty")
    private Profile_enum profile;

    @NotNull(message = "organisation cannot be empty")
    private String organisation_name;


}
