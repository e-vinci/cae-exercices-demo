package be.vinci.ipl.cae.demos.complete.services;

import be.vinci.ipl.cae.demos.complete.models.dtos.UserCredentials;
import be.vinci.ipl.cae.demos.complete.models.entities.User;
import be.vinci.ipl.cae.demos.complete.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private static final String SECRET =
            System.getenv("JWT_SECRET") != null ?
                    System.getenv("JWT_SECRET") :
                    "TODO change this secret";

    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean invalidCredentials(UserCredentials credentials) {
        return credentials.getUsername() == null ||
                credentials.getUsername().isBlank() ||
                credentials.getPassword() == null ||
                credentials.getPassword().isBlank() ||
                credentials.getPassword().length() < 8 ||
                !credentials.getPassword().contains("[a-z]") ||
                !credentials.getPassword().contains("[A-Z]") ||
                !credentials.getPassword().contains("[0-9]") ||
                !credentials.getPassword().contains("@#$%^&+-=]");
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User register(String username, String password) {
        String encryptedPassword = encryptPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        user.setPassword(encryptPassword(newPassword));
        userRepository.save(user);
    }

    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        return BCrypt.checkpw(password, user.getPassword());
    }

    public String createJwtToken(String username) {
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 1 day expiration
                .sign(algorithm);
    }

    public boolean invalidJwtToken(String token) {
        try {
            JWT.require(algorithm).withIssuer("auth0").build().verify(token);
            return false;
        } catch (JWTVerificationException e) {
            return true;
        }
    }

    public String getUsernameFromToken(String token) {
        return JWT.decode(token).getClaim("username").asString();
    }

    public boolean isNotAdmin(String token) {
        String username = getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        return !user.isAdmin();
    }

}
