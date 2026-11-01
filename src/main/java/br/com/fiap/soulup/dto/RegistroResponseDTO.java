package br.com.fiap.soulup.dto;

import java.time.LocalDateTime;

public record RegistroResponseDTO(
        Long id,
        Long idUsuario,
        String nomeUsuario,
        Long idAcao,
        String nomeAcao,
        String categoria,
        String descricao,
        Integer pontuacao,
        String impacto,
        LocalDateTime dataRegistro
) {
}
