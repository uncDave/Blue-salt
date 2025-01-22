package com.blusalt.blusalt.controller.auth;


import com.blusalt.blusalt.dto.auth.LoginDTO;
import com.blusalt.blusalt.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO.Request request) {
        log.info("Login user: {} ", request.getEmail());
        return authService.loginUser(request);
    }
}
