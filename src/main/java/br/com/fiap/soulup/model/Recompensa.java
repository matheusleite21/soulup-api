package br.com.fiap.soulup.model;

import jakarta.persistence.*;

// Catalogo da loja de recompensas
@Entity
@Table(name = "T_SU_RECOMPENSA")
public class Recompensa {
    @Id
    @Column(name = "id_recompensa")
    private Long id;

    @Column(name = "nm_recompensa", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_recompensa", length = 300)
    private String descricao;

    @Column(name = "qt_custo_pontos", nullable = false)
    private Integer custoPontos;

    @Column(name = "ds_tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "st_disponivel", nullable = false, length = 1)
    private String disponivel;

    public Recompensa() {
    }

    public Recompensa(Long id, String nome, String descricao, Integer custoPontos,
                      String tipo, String disponivel) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.custoPontos = custoPontos;
        this.tipo = tipo;
        this.disponivel = disponivel;
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

    public Integer getCustoPontos() {
        return custoPontos;
    }

    public void setCustoPontos(Integer custoPontos) {
        this.custoPontos = custoPontos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }
}
