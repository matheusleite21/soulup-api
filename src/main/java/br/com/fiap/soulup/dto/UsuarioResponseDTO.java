package br.com.fiap.soulup.dto;

import java.time.LocalDate;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        LocalDate dataCadastro,
        Integer pontosExperiencia,
        Integer saldoPontos,
        Integer nivel
) {
}
