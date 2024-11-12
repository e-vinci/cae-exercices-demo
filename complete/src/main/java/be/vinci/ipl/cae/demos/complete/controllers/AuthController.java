package be.vinci.ipl.cae.demos.complete.controllers;

import be.vinci.ipl.cae.demos.complete.models.dtos.PasswordChange;
import be.vinci.ipl.cae.demos.complete.models.dtos.UserCredentials;
import be.vinci.ipl.cae.demos.complete.models.entities.User;
import be.vinci.ipl.cae.demos.complete.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody UserCredentials credentials) {
        if (userService.invalidCredentials(credentials)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }

        if (userService.usernameExists(credentials.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        return userService.register(credentials.getUsername(), credentials.getPassword());
    }

    @PatchMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody PasswordChange credentials
    ) {
        if (userService.invalidJwtToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

        String username = userService.getUsernameFromToken(token);

        if (!userService.usernameExists(username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found");
        }

        if (!userService.verifyPassword(username, credentials.getOldPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        userService.changePassword(username, credentials.getNewPassword());
    }

    @PostMapping("/login")
    public String login(@RequestBody UserCredentials credentials) {
        if (userService.invalidCredentials(credentials)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }

        if (!userService.usernameExists(credentials.getUsername())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found");
        }

        if (!userService.verifyPassword(credentials.getUsername(), credentials.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return userService.createJwtToken(credentials.getUsername());
    }

}
