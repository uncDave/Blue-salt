package com.blusalt.blusalt.dto.auth;

import lombok.*;

@Getter
@Setter
public class OTPRequest {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private String confirmationToken;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String otpId;
    }
}
