package br.com.fiap.soulup.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Troca de pontos do saldo por uma recompensa
@Entity
@Table(name = "T_SU_RESGATE")
public class Resgate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqResgate")
    @SequenceGenerator(name = "sqResgate", sequenceName = "SQ_SU_RESGATE", allocationSize = 1)
    @Column(name = "id_resgate")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_recompensa", nullable = false)
    private Recompensa recompensa;

    @Column(name = "qt_pontos_debitados", nullable = false)
    private Integer pontosDebitados;

    @Column(name = "dt_resgate", nullable = false)
    private LocalDateTime dataResgate;

    public Resgate() {
    }

    public Resgate(Long id, Usuario usuario, Recompensa recompensa, Integer pontosDebitados,
                   LocalDateTime dataResgate) {
        this.id = id;
        this.usuario = usuario;
        this.recompensa = recompensa;
        this.pontosDebitados = pontosDebitados;
        this.dataResgate = dataResgate;
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

    public Recompensa getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(Recompensa recompensa) {
        this.recompensa = recompensa;
    }

    public Integer getPontosDebitados() {
        return pontosDebitados;
    }

    public void setPontosDebitados(Integer pontosDebitados) {
        this.pontosDebitados = pontosDebitados;
    }

    public LocalDateTime getDataResgate() {
        return dataResgate;
    }

    public void setDataResgate(LocalDateTime dataResgate) {
        this.dataResgate = dataResgate;
    }
}
