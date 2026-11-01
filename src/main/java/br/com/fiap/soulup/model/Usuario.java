package br.com.fiap.soulup.model;

import jakarta.persistence.*;

import java.time.LocalDate;

// Conta do usuario: experiencia (define o nivel) e saldo (gasto nos resgates)
@Entity
@Table(name = "T_SU_USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqUsuario")
    @SequenceGenerator(name = "sqUsuario", sequenceName = "SQ_SU_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nm_usuario", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_email", nullable = false, length = 150)
    private String email;

    @Column(name = "dt_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "qt_pontos_experiencia", nullable = false)
    private Integer pontosExperiencia;

    @Column(name = "qt_saldo_pontos", nullable = false)
    private Integer saldoPontos;

    @Column(name = "nr_nivel", nullable = false)
    private Integer nivel;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, LocalDate dataCadastro,
                   Integer pontosExperiencia, Integer saldoPontos, Integer nivel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.pontosExperiencia = pontosExperiencia;
        this.saldoPontos = saldoPontos;
        this.nivel = nivel;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Integer getPontosExperiencia() {
        return pontosExperiencia;
    }

    public void setPontosExperiencia(Integer pontosExperiencia) {
        this.pontosExperiencia = pontosExperiencia;
    }

    public Integer getSaldoPontos() {
        return saldoPontos;
    }

    public void setSaldoPontos(Integer saldoPontos) {
        this.saldoPontos = saldoPontos;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
}
