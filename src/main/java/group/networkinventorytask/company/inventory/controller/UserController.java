package group.networkinventorytask.company.inventory.controller;


import group.networkinventorytask.company.inventory.dto.request.UserCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.UserResponse;
import group.networkinventorytask.company.inventory.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody UserCreateRequest request) {

        UserResponse response = userService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(
            @PathVariable String username) {

        userService.delete(username);

        return ResponseEntity.noContent().build();
    }
}