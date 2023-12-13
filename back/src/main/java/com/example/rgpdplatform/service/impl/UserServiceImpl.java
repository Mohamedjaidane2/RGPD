package com.example.rgpdplatform.service.impl;

import com.example.rgpdplatform.config.jwt.JwtProvider;
import com.example.rgpdplatform.dto.UserDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.enums.Role_enum;
import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.model.Role;
import com.example.rgpdplatform.model.User;
import com.example.rgpdplatform.repository.IRoleRepository;
import com.example.rgpdplatform.repository.IUserRepository;
import com.example.rgpdplatform.service.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.rgpdplatform.exception.ErrorCodes.*;
import static com.example.rgpdplatform.utils.SuccessMessages.SUCCESSFULLY_CREATED;

@Service
@Transactional
public class UserServiceImpl implements IUserService {


    private final IRoleRepository iRoleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;
    //private final IValidatorService validatorService;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(IRoleRepository iRoleRepository, IUserRepository userRepository, PasswordEncoder encoder, JwtProvider jwtProvider, ModelMapper modelMapper) {
        this.iRoleRepository = iRoleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
        this.modelMapper = modelMapper;
    }


    @Override
    public Boolean userExistsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }


    @Override
    public UserDto getCurrentUserInfo(String token) {
        logger.info("User's token: {}", token);
        String email = jwtProvider.extractUsernameFromAuthorizationHeader(token);
        logger.info("Email's user: {}", email);
        User user = checkUserExistenceByEmail(email);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public String deleteUserByEmail(String email) {
        this.userRepository.deleteUserByEmail(email);
        return "Successfully done";
    }

    @Override
    public SuccessDto logoutUser(String token) {
        return null;
    }

    @Override
    public SuccessDto register(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if(userOptional.isPresent()) throw new EntityNotFoundException("User already Exist", USER_NOT_VALID);
        User user =  new User();
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setFunction(userDto.getFunction());
        user.setTelephone(userDto.getTelephone());
        user.setFirstName(userDto.getFirstName());

        String encodedPassword = encoder.encode(userDto.getPassword());

        user.setPassword(encodedPassword);
        Optional<Role> role = iRoleRepository.findByRoleName(Role_enum.ROLE_ADMIN.name());
        if(role.isEmpty()) throw new EntityNotFoundException("Role not Found", ROLE_NOT_FOUND);
        user.setRole(role.get());
        userRepository.save(user);
        return SuccessDto.builder()
                .message(SUCCESSFULLY_CREATED)
                .build();
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new EntityNotFoundException("User not found!", USER_NOT_FOUND);
        return user.get();
    }

    /*@Override
    public String createToken() {
        Role role = roleRepository.findByRoleName("ROLE_ADMIN");
        User adminInstance = User
                .builder()
                .role(role)
                .email(user.getEmail())
                .password(user.getPassword()) //password
                .phone(53064090)
                .logo("logo-relead.jpg")
                .build();
        adminRepository.save(adminInstance);
        return adminInstance;
    }*/

    private User checkUserExistenceByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found", USER_NOT_FOUND);
        }
        return optionalUser.get();
    }
}
