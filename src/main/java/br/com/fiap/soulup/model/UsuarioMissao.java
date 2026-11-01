package br.com.fiap.soulup.model;

import jakarta.persistence.*;

import java.time.LocalDate;

// Status de cada missao para cada usuario (relacionamento N:N entre usuario e missao)
@Entity
@Table(name = "T_SU_USUARIO_MISSAO")
public class UsuarioMissao {
    @EmbeddedId
    private UsuarioMissaoId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @MapsId("idMissao")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_missao")
    private Missao missao;

    @Column(name = "ds_status", nullable = false, length = 10)
    private String status;

    @Column(name = "dt_conclusao")
    private LocalDate dataConclusao;

    @Column(name = "qt_bonus_concedido")
    private Integer bonusConcedido;

    public UsuarioMissao() {
    }

    public UsuarioMissao(Usuario usuario, Missao missao, String status,
                         LocalDate dataConclusao, Integer bonusConcedido) {
        this.id = new UsuarioMissaoId(usuario.getId(), missao.getId());
        this.usuario = usuario;
        this.missao = missao;
        this.status = status;
        this.dataConclusao = dataConclusao;
        this.bonusConcedido = bonusConcedido;
    }

    public UsuarioMissaoId getId() {
        return id;
    }

    public void setId(UsuarioMissaoId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Integer getBonusConcedido() {
        return bonusConcedido;
    }

    public void setBonusConcedido(Integer bonusConcedido) {
        this.bonusConcedido = bonusConcedido;
    }
}
