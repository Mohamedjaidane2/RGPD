package com.example.rgpdplatform.dto.auth;

import com.example.rgpdplatform.dto.UserDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ExtendedUser extends User {
    @Getter
    private UserDto userDto;

    public ExtendedUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserDto userDto) {
        super(username, password, authorities);
        this.userDto = userDto;
    }

}
