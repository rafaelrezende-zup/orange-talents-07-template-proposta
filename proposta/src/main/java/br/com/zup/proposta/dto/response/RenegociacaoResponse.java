package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.model.Renegociacao;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RenegociacaoResponse {

    @NotBlank
    private String id;
    private Integer quantidade;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;

    public RenegociacaoResponse(String id, Integer quantidade, BigDecimal valor, LocalDateTime dataCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataCriacao = dataCriacao;
    }

    public String getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Renegociacao toModel() {
        return new Renegociacao(this.id, this.quantidade, this.valor, this.dataCriacao);
    }
}
