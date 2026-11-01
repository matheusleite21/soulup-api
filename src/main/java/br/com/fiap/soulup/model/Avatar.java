package br.com.fiap.soulup.model;

import jakarta.persistence.*;

// Avatar Soul: cada usuario possui exatamente um (relacionamento 1:1)
@Entity
@Table(name = "T_SU_AVATAR")
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqAvatar")
    @SequenceGenerator(name = "sqAvatar", sequenceName = "SQ_SU_AVATAR", allocationSize = 1)
    @Column(name = "id_avatar")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nm_avatar", nullable = false, length = 50)
    private String nome;

    @Column(name = "ds_personalidade", nullable = false, length = 50)
    private String personalidade;

    @Column(name = "qt_interacoes", nullable = false)
    private Integer quantidadeInteracoes;

    public Avatar() {
    }

    public Avatar(Long id, Usuario usuario, String nome, String personalidade,
                  Integer quantidadeInteracoes) {
        this.id = id;
        this.usuario = usuario;
        this.nome = nome;
        this.personalidade = personalidade;
        this.quantidadeInteracoes = quantidadeInteracoes;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPersonalidade() {
        return personalidade;
    }

    public void setPersonalidade(String personalidade) {
        this.personalidade = personalidade;
    }

    public Integer getQuantidadeInteracoes() {
        return quantidadeInteracoes;
    }

    public void setQuantidadeInteracoes(Integer quantidadeInteracoes) {
        this.quantidadeInteracoes = quantidadeInteracoes;
    }
}
