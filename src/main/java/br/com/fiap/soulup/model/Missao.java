package br.com.fiap.soulup.model;

import jakarta.persistence.*;

// Catalogo de missoes. tipoCriterio e meta foram acrescentados na Sprint 4
// para que a conclusao seja automatica (QUANTIDADE_ACOES, CATEGORIAS_DISTINTAS,
// PONTOS_EXPERIENCIA ou NIVEL).
@Entity
@Table(name = "T_SU_MISSAO")
public class Missao {
    @Id
    @Column(name = "id_missao")
    private Long id;

    @Column(name = "nm_missao", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_missao", nullable = false, length = 300)
    private String descricao;

    @Column(name = "qt_pontos_recompensa", nullable = false)
    private Integer pontosRecompensa;

    @Column(name = "ds_dificuldade", nullable = false, length = 10)
    private String dificuldade;

    @Column(name = "ds_tipo_criterio", length = 30)
    private String tipoCriterio;

    @Column(name = "qt_meta")
    private Integer meta;

    public Missao() {
    }

    public Missao(Long id, String nome, String descricao, Integer pontosRecompensa,
                  String dificuldade, String tipoCriterio, Integer meta) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.pontosRecompensa = pontosRecompensa;
        this.dificuldade = dificuldade;
        this.tipoCriterio = tipoCriterio;
        this.meta = meta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPontosRecompensa() {
        return pontosRecompensa;
    }

    public void setPontosRecompensa(Integer pontosRecompensa) {
        this.pontosRecompensa = pontosRecompensa;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getTipoCriterio() {
        return tipoCriterio;
    }

    public void setTipoCriterio(String tipoCriterio) {
        this.tipoCriterio = tipoCriterio;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }
}
