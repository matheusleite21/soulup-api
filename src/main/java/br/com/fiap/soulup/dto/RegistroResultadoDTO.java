package br.com.fiap.soulup.dto;

import java.util.List;

// Resposta de cadastro/alteracao de registro: o registro, a conta atualizada
// (experiencia, saldo e nivel) e as missoes concluidas nesta operacao
public record RegistroResultadoDTO(
        RegistroResponseDTO registro,
        UsuarioResponseDTO usuario,
        List<String> missoesConcluidas
) {
}
