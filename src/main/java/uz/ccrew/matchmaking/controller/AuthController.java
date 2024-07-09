package uz.ccrew.matchmaking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ccrew.matchmaking.dto.auth.JwtRequest;
import uz.ccrew.matchmaking.dto.auth.JwtResponse;
import uz.ccrew.matchmaking.entity.User;
import uz.ccrew.matchmaking.service.AuthService;
import uz.ccrew.matchmaking.service.UserService;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;



    @PostMapping("/login")
    @Operation(summary = "Login User")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Register User")
    public User register(@RequestBody User user){
        return userService.create(user);
    }


    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWTToken")
    public JwtResponse refresh(@RequestBody String refreshToken) throws AccessDeniedException {
        return authService.refresh(refreshToken);
    }

}

