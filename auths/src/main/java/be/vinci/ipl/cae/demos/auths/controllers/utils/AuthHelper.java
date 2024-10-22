package be.vinci.ipl.cae.demos.auths.controllers.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Aspect
@Controller
public class AuthHelper {

    private static final String jwtSecret = "ilovemypizza!";
    private static final Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

    @Before("@annotation(Authorize)")
    public void authorize(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Authorize authorize = signature.getMethod().getAnnotation(Authorize.class);

        String token = (String) joinPoint.getArgs()[0];

        if (token == null || token.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String username;
        try {
            username = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(token)
                    .getClaim("username")
                    .asString();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (authorize.admin() && !Objects.equals(username, "admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

}
