package br.com.fiap.soulup.dto;

import java.time.LocalDate;

// Missao vista por um usuario: status, data de conclusao e progresso atual ate a meta
public record MissaoUsuarioResponseDTO(
        Long idMissao,
        String nome,
        String descricao,
        Integer pontosRecompensa,
        String dificuldade,
        String tipoCriterio,
        Integer meta,
        Integer progresso,
        String status,
        LocalDate dataConclusao,
        Integer bonusConcedido
) {
}
