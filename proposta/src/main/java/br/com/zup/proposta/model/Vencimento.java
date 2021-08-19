package br.com.zup.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Vencimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String codigo;

    private Integer dia;

    private LocalDateTime dataCriacao;

    @OneToOne(mappedBy = "vencimento")
    private Cartao cartao;

    @Deprecated
    public Vencimento() {
    }

    public Vencimento(String id, Integer dia, LocalDateTime dataCriacao) {
        this.codigo = id;
        this.dia = dia;
        this.dataCriacao = LocalDateTime.now();
    }
}
