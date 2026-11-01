package br.com.fiap.soulup.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Conversa entre o usuario e o avatar Soul
@Entity
@Table(name = "T_SU_INTERACAO")
public class Interacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqInteracao")
    @SequenceGenerator(name = "sqInteracao", sequenceName = "SQ_SU_INTERACAO", allocationSize = 1)
    @Column(name = "id_interacao")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_avatar", nullable = false)
    private Avatar avatar;

    @Column(name = "ds_tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "ds_mensagem_usuario", length = 400)
    private String mensagemUsuario;

    @Column(name = "ds_resposta_avatar", nullable = false, length = 400)
    private String respostaAvatar;

    @Column(name = "dt_interacao", nullable = false)
    private LocalDateTime dataInteracao;

    public Interacao() {
    }

    public Interacao(Long id, Avatar avatar, String tipo, String mensagemUsuario,
                     String respostaAvatar, LocalDateTime dataInteracao) {
        this.id = id;
        this.avatar = avatar;
        this.tipo = tipo;
        this.mensagemUsuario = mensagemUsuario;
        this.respostaAvatar = respostaAvatar;
        this.dataInteracao = dataInteracao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagemUsuario() {
        return mensagemUsuario;
    }

    public void setMensagemUsuario(String mensagemUsuario) {
        this.mensagemUsuario = mensagemUsuario;
    }

    public String getRespostaAvatar() {
        return respostaAvatar;
    }

    public void setRespostaAvatar(String respostaAvatar) {
        this.respostaAvatar = respostaAvatar;
    }

    public LocalDateTime getDataInteracao() {
        return dataInteracao;
    }

    public void setDataInteracao(LocalDateTime dataInteracao) {
        this.dataInteracao = dataInteracao;
    }
}
