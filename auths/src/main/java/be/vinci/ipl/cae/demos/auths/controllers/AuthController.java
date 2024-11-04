package be.vinci.ipl.cae.demos.auths.controllers;

import be.vinci.ipl.cae.demos.auths.models.dtos.AuthenticatedUser;
import be.vinci.ipl.cae.demos.auths.models.dtos.Credentials;
import be.vinci.ipl.cae.demos.auths.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auths")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    private boolean isInvalidCredentials(Credentials credentials){
        return credentials == null ||
                credentials.getUsername() == null ||
                credentials.getUsername().isBlank() ||
                credentials.getPassword() == null ||
                credentials.getPassword().isBlank();
    }

    @PostMapping("/register")
    public AuthenticatedUser register(@RequestBody Credentials credentials) {
        if (isInvalidCredentials(credentials))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        AuthenticatedUser user = userService.register(credentials.getUsername(), credentials.getPassword());

        if (user == null)
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        return user;
    }

    @PostMapping("/login")
    public AuthenticatedUser login(@RequestBody Credentials credentials) {
        if (isInvalidCredentials(credentials))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        AuthenticatedUser user = userService.login(credentials.getUsername(), credentials.getPassword());

        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return user;
    }

}
