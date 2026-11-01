package br.com.fiap.soulup.dto;

public record MissaoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Integer pontosRecompensa,
        String dificuldade,
        String tipoCriterio,
        Integer meta
) {
}
