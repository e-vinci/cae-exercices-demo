package be.vinci.ipl.cae.demo.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "be.vinci.ipl.cae.demo.jwt")
public class JwtProperties {

    private String secret;

}
