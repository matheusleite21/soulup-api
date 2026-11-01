package br.com.fiap.soulup.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Acao sustentavel efetivamente realizada por um usuario
@Entity
@Table(name = "T_SU_REGISTRO_ACAO")
public class RegistroAcao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqRegistro")
    @SequenceGenerator(name = "sqRegistro", sequenceName = "SQ_SU_REGISTRO", allocationSize = 1)
    @Column(name = "id_registro")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_acao", nullable = false)
    private Acao acao;

    @Column(name = "ds_registro", nullable = false, length = 300)
    private String descricao;

    @Column(name = "qt_pontuacao", nullable = false)
    private Integer pontuacao;

    @Column(name = "ds_impacto", nullable = false, length = 20)
    private String impacto;

    @Column(name = "dt_registro", nullable = false)
    private LocalDateTime dataRegistro;

    public RegistroAcao() {
    }

    public RegistroAcao(Long id, Usuario usuario, Acao acao, String descricao,
                        Integer pontuacao, String impacto, LocalDateTime dataRegistro) {
        this.id = id;
        this.usuario = usuario;
        this.acao = acao;
        this.descricao = descricao;
        this.pontuacao = pontuacao;
        this.impacto = impacto;
        this.dataRegistro = dataRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
