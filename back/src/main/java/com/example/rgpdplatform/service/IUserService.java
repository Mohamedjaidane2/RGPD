package com.example.rgpdplatform.service;


import com.example.rgpdplatform.dto.UserDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.model.User;

public interface IUserService {

    Boolean userExistsByEmail(String email);

    UserDto getCurrentUserInfo(String token);

    String deleteUserByEmail(String email);

    //SuccessDto forgotPassword(String email) throws MessagingException;

    //SuccessDto resetPassword(String resetToken, String password) throws MessagingException;

    // changePassword(String token, ChangePasswordDto changePasswordDto);

    SuccessDto logoutUser(String token);

    SuccessDto register(UserDto userDto);

    User getUserById(String userId);
}
