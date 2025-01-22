package com.blusalt.blusalt.service;

import com.blusalt.blusalt.dto.auth.LoginDTO;
import com.blusalt.blusalt.dto.auth.OTPRequest;
import com.blusalt.blusalt.dto.auth.ResetPasswordDTO;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ApiResponse<?>> registerUser(String email, String password);
    ResponseEntity<ApiResponse<?>> loginUser(LoginDTO.Request login);



    ResponseEntity<ApiResponse<?>> completeResetPassword(ResetPasswordDTO.ResetComplete resetComplete);
    boolean isUsernameTaken(String username);

    ResponseEntity<ApiResponse<?>> validateUserByOtp(OTPRequest.Request otp);
    ResponseEntity<ApiResponse<?>> regenerateVerificationTokenAndSendEmail(String email);
    ResponseEntity<ApiResponse<?>> initiateResetPassword(String email);

    ResponseEntity<ApiResponse<?>> fetchUserDetails(String username);
    ResponseEntity<ApiResponse<?>> changePassword(String userId, ResetPasswordDTO.ChangePasswordRequest changePasswordRequest);
}
