package group.networkinventorytask.company.inventory.service;

import group.networkinventorytask.company.inventory.Exception.Grouped.UserBadRequest;
import group.networkinventorytask.company.inventory.Exception.Grouped.UserConflict;
import group.networkinventorytask.company.inventory.Exception.Grouped.UserNotFound;
import group.networkinventorytask.company.inventory.dto.request.UserCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserDetailsManager userDetailsManager,
            PasswordEncoder passwordEncoder) {

        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse create(UserCreateRequest request) {

        if (request.username() == null ||
                request.username().isBlank() ||
                request.password() == null ||
                request.password().isBlank()) {

            throw new UserBadRequest(
                    "Username and password are required"
            );
        }

        if (userDetailsManager.userExists(request.username())) {

            throw new UserConflict(
                    "Username already exists"
            );
        }

        var user = User.withUsername(request.username())
                .password(
                        passwordEncoder.encode(request.password())
                )
                .authorities("ROLE_USER")
                .build();

        userDetailsManager.createUser(user);

        return new UserResponse(
                request.username(),
                true,
                "ROLE_USER"
        );

    }

    public void delete(String username) {

        if (username == null || username.isBlank()) {
            throw new UserBadRequest(
                    "Username cannot be empty"
            );
        }

        if (!userDetailsManager.userExists(username)) {
            throw new UserNotFound(
                    "User not found: " + username
            );
        }

        userDetailsManager.deleteUser(username);
    }
}
