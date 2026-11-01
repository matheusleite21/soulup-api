package br.com.fiap.soulup.dto;

public record AcaoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Integer pontosPadrao,
        Long idCategoria,
        String categoria
) {
}
