package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.model.Vencimento;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class VencimentoResponse {

    @NotBlank
    private String id;
    private Integer dia;
    private LocalDateTime dataCriacao;

    public VencimentoResponse(String id, Integer dia, LocalDateTime dataCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataCriacao = dataCriacao;
    }

    public String getId() {
        return id;
    }

    public Integer getDia() {
        return dia;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Vencimento toModel() {
        return new Vencimento(this.id, this.dia, this.dataCriacao);
    }
}
