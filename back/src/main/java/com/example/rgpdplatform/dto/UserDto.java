package com.example.rgpdplatform.dto;

import com.example.rgpdplatform.enums.Profile_enum;
import com.example.rgpdplatform.model.Role;
import com.example.rgpdplatform.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private String id;

    private String firstName;

    private String lastName;

    private String function;

    private String telephone;

    private String email;

    private String password;

    @Override
    public String toString() {
        return "Guest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", function='" + function + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
