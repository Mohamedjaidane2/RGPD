package com.example.rgpdplatform.dto;
import com.example.rgpdplatform.enums.Profile_enum;
import com.example.rgpdplatform.model.Guest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GuestDto {

    private String id;

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
    private OrganisationDto organisation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date created;

    public static GuestDto customMapping(Guest guest){
        return GuestDto.builder()
                .id(guest.getId())
                .telephone(guest.getTelephone())
                .profile(guest.getProfile())
                .organisation(OrganisationDto.customMapping(guest.getOrganisation()))
                .lastName(guest.getLastName())
                .function(guest.getFunction())
                .firstName(guest.getFirstName())
                .expertiseField(guest.getExpertiseField())
                .email(guest.getEmail())
                .created(guest.getCreationDate())
                .build();
    }
}
