package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.model.Proposta;

public class SolicitacaoAnaliseRequest {

    private String documento;
    private String nome;
    private Long idProposta;

    public SolicitacaoAnaliseRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
