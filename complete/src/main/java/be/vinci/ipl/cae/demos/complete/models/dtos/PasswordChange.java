package be.vinci.ipl.cae.demos.complete.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordChange {
    private String oldPassword;
    private String newPassword;
}
