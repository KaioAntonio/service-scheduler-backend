package dev.kaio.dto.user;

import dev.kaio.enums.UserTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Data
public class UserRequestDTO {

    @Schema(
            description = "Nome completo do usuário.",
            example = "Kaio Rodrigues"
    )
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

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

    @Schema(
            description = "Tipo de usuário. Pode ser CLIENTE ou PROFISSIONAL.",
            example = "CLIENTE"
    )
    private UserTypeEnum type;
}
