package br.com.zup.proposta.model;

import br.com.zup.proposta.dto.response.CarteiraResponse;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String codigo;

    private String email;

    private LocalDateTime dataAssociacao;

    private String emissor;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    public Carteira(CarteiraResponse response) {
        this.codigo = response.getId();
        this.email = response.getEmail();
        this.dataAssociacao = response.getAssociadaEm();
        this.emissor = response.getEmissor();
    }

    @Deprecated
    public Carteira() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carteira carteira = (Carteira) o;
        return Objects.equals(codigo, carteira.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

}
