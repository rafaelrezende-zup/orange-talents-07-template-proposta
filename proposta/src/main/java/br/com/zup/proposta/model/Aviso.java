package br.com.zup.proposta.model;

import br.com.zup.proposta.dto.response.AvisoResponse;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate validoAte;

    private String destino;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    public Aviso(AvisoResponse response) {
        this.validoAte = response.getValidoAte();
        this.destino = response.getDestino();
    }

    @Deprecated
    public Aviso() {
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
