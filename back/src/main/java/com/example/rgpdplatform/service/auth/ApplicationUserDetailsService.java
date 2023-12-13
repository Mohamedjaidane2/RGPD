package com.example.rgpdplatform.service.auth;

import com.example.rgpdplatform.dto.UserDto;
import com.example.rgpdplatform.dto.auth.ExtendedUser;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.exception.InvalidOperationException;
import com.example.rgpdplatform.model.Guest;
import com.example.rgpdplatform.model.User;
import com.example.rgpdplatform.repository.IGuestRepository;
import com.example.rgpdplatform.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final IGuestRepository iGuestRepository;
    Logger logger = LoggerFactory.getLogger(ApplicationUserDetailsService.class);

    public ApplicationUserDetailsService(IUserRepository userRepository, ModelMapper modelMapper, IGuestRepository iGuestRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.iGuestRepository = iGuestRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            logger.warn("This User: {} doesn't exist.", email);
            throw new InvalidOperationException("These credentials do not match our records !", ErrorCodes.AUTHENTICATION_USER_NOT_VALID);
        }

        UserDto userDto = modelMapper.map(optionalUser.get(), UserDto.class);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //String authorities;
        authorities.add(new SimpleGrantedAuthority(optionalUser.get().getRole().getRoleName()));

        return new ExtendedUser(optionalUser.get().getEmail(), optionalUser.get().getPassword(),
                authorities, userDto);
    }

    public ExtendedUser findById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            logger.warn("This User: {} doesn't exist.", id);
            throw new InvalidOperationException("These credentials do not match our records !", ErrorCodes.AUTHENTICATION_USER_NOT_VALID);
        }

        UserDto userDto = modelMapper.map(optionalUser.get(), UserDto.class);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //String authorities;
        authorities.add(new SimpleGrantedAuthority(optionalUser.get().getRole().getRoleName()));

        return new ExtendedUser(optionalUser.get().getEmail(), optionalUser.get().getPassword(),
                authorities, userDto);
    }
}
