package be.vinci.ipl.cae.demos.complete.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCredentials {
    private String username;
    private String password;
}
