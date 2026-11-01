package br.com.fiap.soulup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

// Chave composta da tabela associativa T_SU_USUARIO_MISSAO
@Embeddable
public class UsuarioMissaoId implements Serializable {
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_missao")
    private Long idMissao;

    public UsuarioMissaoId() {
    }

    public UsuarioMissaoId(Long idUsuario, Long idMissao) {
        this.idUsuario = idUsuario;
        this.idMissao = idMissao;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdMissao() {
        return idMissao;
    }

    public void setIdMissao(Long idMissao) {
        this.idMissao = idMissao;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) return true;
        if (!(outro instanceof UsuarioMissaoId id)) return false;
        return Objects.equals(idUsuario, id.idUsuario) && Objects.equals(idMissao, id.idMissao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idMissao);
    }
}
