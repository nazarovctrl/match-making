package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.auth.RegisterDTO;
import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.dto.auth.LoginDTO;
import uz.ccrew.matchmaking.dto.auth.LoginResponseDTO;
import uz.ccrew.matchmaking.service.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Authorization API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login User")
    public ResponseEntity<Response<LoginResponseDTO>> login(@RequestBody @Valid LoginDTO loginRequest) {
        return ResponseMaker.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "Register User")
    public ResponseEntity<Response<UserDTO>> registerDTO(@RequestBody @Valid RegisterDTO dto) {
        return ResponseMaker.ok(authService.register(dto));
    }

    @PostMapping("/refresh")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Refresh Access token")
    public ResponseEntity<Response<String>> refresh() {
        return ResponseMaker.ok(authService.refresh());
    }
}