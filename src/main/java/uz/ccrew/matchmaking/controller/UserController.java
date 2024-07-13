package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.user.UserDTO;
import uz.ccrew.matchmaking.dto.user.UserUpdateDTO;
import uz.ccrew.matchmaking.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "User API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    @Operation(summary = "Get user for everyone")
    public ResponseEntity<Response<UserDTO>> get() {
        UserDTO result = userService.get();
        return ResponseMaker.ok(result);
    }

    @GetMapping("/get/{userId}")
    @Operation(summary = "Get user by id for Administrator")
    public ResponseEntity<Response<UserDTO>> getById(@PathVariable(value = "userId") Integer userId) {
        UserDTO result = userService.getById(userId);
        return ResponseMaker.ok(result);
    }

    @PutMapping("/update")
    @Operation(summary = "Update user")
    public ResponseEntity<Response<UserDTO>> update(@RequestBody UserUpdateDTO dto) {
        UserDTO result = userService.update(dto);
        return ResponseMaker.ok(result);
    }

    @PutMapping("/update/{userId}")
    @Operation(summary = "Update user by id for Administrator")
    public ResponseEntity<Response<UserDTO>> updateById(@PathVariable("userId") Integer userId, @RequestBody UserUpdateDTO dto) {
        UserDTO result = userService.updateById(userId, dto);
        return ResponseMaker.ok(result);
    }
}
