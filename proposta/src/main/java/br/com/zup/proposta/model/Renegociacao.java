package br.com.zup.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Renegociacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String codigo;

    private Integer quantidade;

    private BigDecimal valor;

    private LocalDateTime dataCriacao;

    @OneToOne(mappedBy = "renegociacao")
    private Cartao cartao;

    @Deprecated
    public Renegociacao() {
    }

    public Renegociacao(String id, Integer quantidade, BigDecimal valor, LocalDateTime dataCriacao) {
        this.codigo = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataCriacao = LocalDateTime.now();
    }
}
