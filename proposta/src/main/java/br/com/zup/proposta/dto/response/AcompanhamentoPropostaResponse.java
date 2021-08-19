package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enumeration.EstadoProposta;

public class AcompanhamentoPropostaResponse {

    private Long id;
    private String documento;
    private String nome;
    private EstadoProposta estadoProposta;

    public AcompanhamentoPropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.estadoProposta = proposta.getEstado();
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public EstadoProposta getEstadoProposta() {
        return estadoProposta;
    }
}
