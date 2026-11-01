package br.com.fiap.soulup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para registrar ou corrigir uma ação sustentável realizada")
public record RegistroRequestDTO(
        @Schema(description = "Identificador do usuário que realizou a ação", example = "3")
        @NotNull(message = "O usuário é obrigatório")
        @Positive(message = "O ID do usuário deve ser positivo")
        Long idUsuario,

        @Schema(description = "Identificador da ação no catálogo", example = "3")
        @NotNull(message = "A ação é obrigatória")
        @Positive(message = "O ID da ação deve ser positivo")
        Long idAcao,

        @Schema(description = "O que foi feito", example = "Separei o lixo reciclável da semana")
        @NotBlank(message = "A descrição é obrigatória")
        @Size(min = 5, max = 300, message = "A descrição deve ter entre 5 e 300 caracteres")
        String descricao
) {
}
