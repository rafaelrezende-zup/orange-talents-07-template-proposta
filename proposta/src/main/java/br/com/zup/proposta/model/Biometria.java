package br.com.zup.proposta.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String fingerprint;

    @ManyToOne
    private Cartao cartao;

    private LocalDateTime dataCriacao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String fingerprint, Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometria biometria = (Biometria) o;
        return Objects.equals(fingerprint, biometria.fingerprint) && Objects.equals(dataCriacao, biometria.dataCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fingerprint, dataCriacao);
    }
}
