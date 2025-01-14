package dev.kaio.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class PasswordRequestDTO {

    @Schema(
            description = "Senha do usuário.",
            example = "password123"
    )
    @Size(min = 6, max = 20)
    private String password;
}
