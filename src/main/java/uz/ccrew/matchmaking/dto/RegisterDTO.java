package uz.ccrew.matchmaking.dto;

import jakarta.validation.constraints.NotBlank;


public record RegisterDTO (@NotBlank(message = "Login must not be blank") String login,
                           @NotBlank(message = "Login must not be blank") String password){}
