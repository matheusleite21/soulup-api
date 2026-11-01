package br.com.fiap.soulup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "T_SU_CATEGORIA")
public class Categoria {
    @Id
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "nm_categoria", nullable = false, length = 30)
    private String nome;

    public Categoria() {
    }

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
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
}
