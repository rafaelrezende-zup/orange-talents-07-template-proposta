package br.com.zup.proposta.model;

import br.com.zup.proposta.dto.response.ParcelaResponse;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String codigo;

    private Integer quantidade;

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @Deprecated
    public Parcela() {
    }

    public Parcela(ParcelaResponse response) {
        this.codigo = response.getId();
        this.quantidade = response.getQuantidade();
        this.valor = response.getValor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcela parcela = (Parcela) o;
        return Objects.equals(codigo, parcela.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
