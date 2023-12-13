package com.example.rgpdplatform.model;

import com.example.rgpdplatform.enums.Profile_enum;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends AbstractDocument implements UserDetails {

    private String firstName;

    private String lastName;

    private String function;

    private String telephone;

    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @DocumentReference(lazy = true)
    private Role role;

    private String password;

    private String resetToken;

    @Override
    public String toString() {
        return "Guest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", function='" + function + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role.getRoleName() +
                '}';
    }
}
