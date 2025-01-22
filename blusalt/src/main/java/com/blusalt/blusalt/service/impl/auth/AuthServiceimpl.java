package com.blusalt.blusalt.service.impl.auth;

import com.blusalt.blusalt.dto.auth.LoginDTO;
import com.blusalt.blusalt.dto.auth.OTPRequest;
import com.blusalt.blusalt.dto.auth.ResetPasswordDTO;
import com.blusalt.blusalt.entity.BaseUser;
import com.blusalt.blusalt.security.jwtutils.JwtService;
import com.blusalt.blusalt.service.AuthService;
import com.blusalt.blusalt.service.JpaService.BaseUserJPAService;
import com.blusalt.blusalt.utils.apiresponseutils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createFailureResponse;
import static com.blusalt.blusalt.utils.apiresponseutils.ResponseUtils.createSuccessResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceimpl implements AuthService {
    private final BaseUserJPAService baseUserJPAService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtUtil;

    @Override
    public ResponseEntity<ApiResponse<?>> registerUser(String email, String password) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> loginUser(LoginDTO.Request request) {

            try {
                Optional<BaseUser> user = baseUserJPAService.findByEmail(request.getEmail());

                if (user.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(createFailureResponse("Invalid Credentials", "Kindly Signup"));
                }
                log.info("This is the user object {}", user.get());
                BaseUser baseUser = user.get();


                if (!baseUser.isEnabled() && !baseUser.isActive() ) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createFailureResponse("Account not verified", "Please verify your account first"));
                }

                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
                var jwtToken = jwtUtil.generateToken(baseUser,baseUser.getRole().getName());

                LoginDTO.Response response = LoginDTO.Response.builder()
                        .jwtToken(jwtToken)
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(createSuccessResponse(response,"Login successful"));
            }catch (BadCredentialsException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createFailureResponse("Invalid credentials", "Login Failed"));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createFailureResponse("Login failed", ex.getMessage()));
            }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> completeResetPassword(ResetPasswordDTO.ResetComplete resetComplete) {
        return null;
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return false;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> validateUserByOtp(OTPRequest.Request otp) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> regenerateVerificationTokenAndSendEmail(String email) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> initiateResetPassword(String email) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> fetchUserDetails(String username) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<?>> changePassword(String userId, ResetPasswordDTO.ChangePasswordRequest changePasswordRequest) {
        return null;
    }
}
