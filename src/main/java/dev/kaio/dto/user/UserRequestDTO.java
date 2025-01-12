package dev.kaio.dto.user;

import dev.kaio.enums.UserTypeEnum;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRequestDTO {

    @Schema(
            description = "Nome completo do usuário.",
            example = "Kaio Rodrigues"
    )
    @NotBlank
    private String name;

    @Schema(
            description = "Email do usuário.",
            example = "kaio@example.com"
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
            description = "Senha do usuário.",
            example = "password123"
    )
    @NotBlank
    private String password;

    @Schema(
            description = "Tipo de usuário. Pode ser CLIENTE ou PROFISSIONAL.",
            example = "CLIENTE"
    )
    private UserTypeEnum type;
}
