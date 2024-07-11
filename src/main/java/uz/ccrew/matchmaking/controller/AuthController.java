package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.GoodResponse;
import uz.ccrew.matchmaking.dto.RegisterDTO;
import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.UserDTO;
import uz.ccrew.matchmaking.dto.auth.LoginDTO;
import uz.ccrew.matchmaking.dto.auth.LoginResponseDTO;
import uz.ccrew.matchmaking.service.impl.AuthServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    @Operation(summary = "Login User")
    public ResponseEntity<Response<LoginResponseDTO>> login(@RequestBody @Valid LoginDTO loginRequest) {
        return GoodResponse.ok(authServiceImpl.login(loginRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "Register User")
    public ResponseEntity<Response<UserDTO>> registerDTO(@RequestBody @Valid RegisterDTO dto) {
        return GoodResponse.ok(authServiceImpl.register(dto));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWTToken")
    public ResponseEntity<Response<String>> refresh(HttpServletRequest request) {
        return GoodResponse.ok(authServiceImpl.refresh(request));
    }
}