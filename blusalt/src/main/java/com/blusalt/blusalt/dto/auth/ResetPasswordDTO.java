package com.blusalt.blusalt.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class ResetPasswordDTO {
    @Setter
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInit{
        @Email(message = "Please provide a valid email/phone number")
        private String email;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResetComplete{

        //@NotNull(message = "All fields required")
//        @NotBlank(message = "All fields required")
        private String email;

        //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters long and include alphanumeric characters and special characters")
//        @NotNull(message = "All fields required")
//        @NotBlank(message = "All fields required")
        private String newPassword;

        // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters long and include alphanumeric characters and special characters")
//        @NotNull(message = "All fields required")
//        @NotBlank(message = "All fields required")
        private String newPasswordConfirmation;

        //@NotNull(message = "All fields required")
//        @NotBlank(message = "All fields required")
        private String otp;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangePasswordRequest {
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters long and include alphanumeric characters and special characters")
        @NotNull(message = "All fields required")
        @NotBlank(message = "All fields required")
        private String newPassword;
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters long and include alphanumeric characters and special characters")
        @NotNull(message = "All fields required")
        @NotBlank(message = "All fields required")
        private String newPasswordConfirmation;

    }
}
