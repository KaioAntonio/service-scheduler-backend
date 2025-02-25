package dev.kaio.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Data
public class LoginRequestDTO {


    @Schema(
            description = "Email do usuário.",
            example = "kaio@example.com"
    )
    @Email
    private String email;

    @Schema(
            description = "Senha do usuário.",
            example = "password123"
    )
    @Size(min = 6, max = 20)
    private String password;
}
