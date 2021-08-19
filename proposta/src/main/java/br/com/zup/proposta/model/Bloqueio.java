package br.com.zup.proposta.model;

import br.com.zup.proposta.dto.response.BloqueioResponse;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String codigo;

    private LocalDateTime dataBloqueio;

    private String sistemaResponsavel;

    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(BloqueioResponse response) {
        this.codigo = response.getId();
        this.dataBloqueio = response.getBloqueadoEm();
        this.sistemaResponsavel = response.getSistemaResponsavel();
        this.ativo = response.getAtivo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bloqueio bloqueio = (Bloqueio) o;
        return Objects.equals(codigo, bloqueio.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
