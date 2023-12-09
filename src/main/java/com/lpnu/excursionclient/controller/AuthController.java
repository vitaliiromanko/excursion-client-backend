package com.lpnu.excursionclient.controller;

import com.lpnu.excursionclient.dto.request.auth.*;
import com.lpnu.excursionclient.dto.response.auth.*;
import com.lpnu.excursionclient.security.JwtPair;
import com.lpnu.excursionclient.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final Long refreshTokenTimeLife;
    private static final String COOKIE_NAME_FOR_REFRESH = "client_refresh_token";

    public AuthController(
            AuthService authService,
            @Value("${application.security.refresh-jwt.time-life}") Long refreshTokenTimeLife) {
        this.authService = authService;
        this.refreshTokenTimeLife = refreshTokenTimeLife;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest,
            HttpServletRequest request
    ) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/register").toUriString());
        authService.register(registerRequest, request.getHeader("Origin"));
        return ResponseEntity.created(uri).body(new RegisterResponse("success"));
    }

    @PostMapping("/activation-token-again")
    public ResponseEntity<ActivationTokenAgainResponse> sendActivationTokenAgain(
            @Valid @RequestBody ActivationTokenAgainRequest activationTokenAgainRequest,
            HttpServletRequest request
    ) {
        authService.sendActivationTokenAgain(activationTokenAgainRequest, request.getHeader("Origin"));
        return ResponseEntity.ok().body(new ActivationTokenAgainResponse("success"));
    }

    @PutMapping("/activate")
    public ResponseEntity<ActivateResponse> activate(
            @Valid @RequestBody ActivateRequest activateRequest,
            HttpServletResponse response
    ) {
        JwtPair jwtPair = authService.activate(activateRequest);
        createRefreshCookie(response, jwtPair.refreshJwt().token());
        return ResponseEntity.ok().body(new ActivateResponse(jwtPair.accessJwt().token()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        JwtPair jwtPair = authService.login(loginRequest);
        createRefreshCookie(response, jwtPair.refreshJwt().token());
        return ResponseEntity.ok().body(new LoginResponse(jwtPair.accessJwt().token()));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(
            @CookieValue(COOKIE_NAME_FOR_REFRESH) String refreshToken,
            HttpServletResponse response
    ) {
        removeRefreshCookie(response);
        authService.logout(refreshToken);
        return ResponseEntity.ok().body(new LogoutResponse("success"));
    }

    @PostMapping("/forgot")
    public ResponseEntity<ForgotPasswordResponse> forgot(
            @Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest,
            HttpServletRequest request
    ) {
        authService.forgot(forgotPasswordRequest.email(), request.getHeader("Origin"));
        return ResponseEntity.ok().body(new ForgotPasswordResponse("success"));
    }

    @PutMapping("/reset")
    public ResponseEntity<ResetPasswordResponse> recovery(
            @Valid @RequestBody ResetPasswordRequest resetPasswordRequest
    ) {
        authService.reset(resetPasswordRequest);
        return ResponseEntity.ok().body(new ResetPasswordResponse("success"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(
            @CookieValue(COOKIE_NAME_FOR_REFRESH) String refreshToken,
            HttpServletResponse response
    ) {
        JwtPair jwtPair = authService.refresh(refreshToken);
        createRefreshCookie(response, jwtPair.refreshJwt().token());
        return ResponseEntity.ok().body(new RefreshResponse(jwtPair.accessJwt().token()));
    }

    private void createRefreshCookie(HttpServletResponse response, String refreshToken) {
        addRefreshCookie(response, refreshToken, refreshTokenTimeLife);
    }

    private void removeRefreshCookie(HttpServletResponse response) {
        addRefreshCookie(response, "", 0L);
    }

    private static void addRefreshCookie(HttpServletResponse response, String value, Long maxAge) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME_FOR_REFRESH, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("none")
                .maxAge(Duration.ofMinutes(maxAge))
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
