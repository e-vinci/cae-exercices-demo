package be.vinci.ipl.cae.demos.auths.services;

import be.vinci.ipl.cae.demos.auths.models.dtos.AuthenticatedUser;
import be.vinci.ipl.cae.demos.auths.models.entities.User;
import be.vinci.ipl.cae.demos.auths.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private static final String jwtSecret = "ilovemypizza!";
    private static final long lifetimeJwt = 24*60*60*1000; // 24 hours
    private static final int saltRounds = 10;

    private static final Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthenticatedUser createJwtToken(String username) {
        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + lifetimeJwt))
                .sign(algorithm);

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUsername(username);
        authenticatedUser.setToken(token);

        return authenticatedUser;
    }

    public AuthenticatedUser login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;

        if (!BCrypt.checkpw(password, user.getPassword())) {
            return null;
        }

        return createJwtToken(username);
    }

    public AuthenticatedUser register(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) return null;

        createOne(username, password);

        return createJwtToken(username);
    }

    public void createOne(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(saltRounds));

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }


}
