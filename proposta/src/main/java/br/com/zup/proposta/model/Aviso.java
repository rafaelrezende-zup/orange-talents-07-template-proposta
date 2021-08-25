package br.com.zup.proposta.model;

import br.com.zup.proposta.dto.response.AvisoResponse;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate validoAte;

    private String destino;

    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    private String ipCliente;

    private String userAgent;

    public Aviso(AvisoResponse response) {
        this.validoAte = response.getValidoAte();
        this.destino = response.getDestino();
    }

    @Deprecated
    public Aviso() {
    }

    public Aviso(String detino, LocalDate terminoViagem, Cartao cartao, String userAgent, String ip) {
        this.destino = detino;
        this.validoAte = terminoViagem;
        this.cartao = cartao;
        this.dataCriacao = LocalDateTime.now();
        this.ipCliente = ip;
        this.userAgent = userAgent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aviso aviso = (Aviso) o;
        return Objects.equals(validoAte, aviso.validoAte) && Objects.equals(destino, aviso.destino) && Objects.equals(cartao, aviso.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validoAte, destino, cartao);
    }
}
