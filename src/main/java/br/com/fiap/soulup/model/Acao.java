package br.com.fiap.soulup.model;

import jakarta.persistence.*;

// Catalogo de acoes sustentaveis que o usuario pode registrar
@Entity
@Table(name = "T_SU_ACAO")
public class Acao {
    @Id
    @Column(name = "id_acao")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Column(name = "nm_acao", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_acao", length = 300)
    private String descricao;

    @Column(name = "qt_pontos_padrao", nullable = false)
    private Integer pontosPadrao;

    public Acao() {
    }

    public Acao(Long id, Categoria categoria, String nome, String descricao, Integer pontosPadrao) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
        this.descricao = descricao;
        this.pontosPadrao = pontosPadrao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPontosPadrao() {
        return pontosPadrao;
    }

    public void setPontosPadrao(Integer pontosPadrao) {
        this.pontosPadrao = pontosPadrao;
    }
}
