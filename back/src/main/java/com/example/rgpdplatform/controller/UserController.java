package com.example.rgpdplatform.controller;

import com.example.rgpdplatform.config.jwt.JwtProvider;
import com.example.rgpdplatform.dto.UserDto;
import com.example.rgpdplatform.dto.auth.*;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.model.RefreshToken;
import com.example.rgpdplatform.repository.IRefreshTokenRepository;
import com.example.rgpdplatform.service.IUserService;
import com.example.rgpdplatform.service.auth.ApplicationUserDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api("/user")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationUserDetailsService userDetailsService;
    private final IRefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(IUserService userService, AuthenticationManager authenticationManager,
                          ApplicationUserDetailsService userDetailsService, IRefreshTokenRepository refreshTokenRepository, JwtProvider jwtProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProvider = jwtProvider;
    }
    @PostMapping("/login")
    @ApiOperation(value = "Authentication")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final ExtendedUser extendedUser = (ExtendedUser) userDetailsService.loadUserByUsername(loginRequest.getEmail());

        String jwt = jwtProvider.generateAccessToken(extendedUser);

        RefreshToken refreshToken = new RefreshToken();
        refreshTokenRepository.save(refreshToken);



        return ResponseEntity.ok(
                LoginResponse
                        .builder()
                        .token(jwt)
                        .build());

    }


    @GetMapping("/check-auth")
    @ApiOperation(value = "Check if the user is logged in or not")
    public ResponseEntity<Boolean> isExpired(@RequestHeader(name = "Authorization") String token) {
        String jwt = token.substring(7, token.length());
        if(jwt.equals("null")) return ResponseEntity.ok(false);
        boolean validate = jwtProvider.validateAccessToken(jwt);
        if (!validate) return ResponseEntity.ok(validate);
        boolean isExpired = jwtProvider.isTokenExpired(jwt);

        return ResponseEntity.ok(!isExpired && validate);
    }

    @PostMapping("/register")
    @ApiOperation(value = "Check if the user exists or not by Email")
    public ResponseEntity<SuccessDto> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.register(userDto));
    }


    @GetMapping("/exists/{email}")
    @ApiOperation(value = "Check if the user exists or not by Email")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.userExistsByEmail(email));
    }

    @GetMapping("/get/info")
    @ApiOperation(value = "Get the current user's information")
    public ResponseEntity<UserDto> getCurrentUserInfo(@RequestParam(name = "Authorization") String token) {
        return ResponseEntity.ok(userService.getCurrentUserInfo(token));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete a particular user's account using his email")
    public ResponseEntity<String> deleteUserByUsername(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(userService.deleteUserByEmail(email));
    }
}