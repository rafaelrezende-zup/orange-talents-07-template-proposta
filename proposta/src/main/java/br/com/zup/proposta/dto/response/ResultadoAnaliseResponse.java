package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.model.enumeration.EstadoProposta;
import br.com.zup.proposta.model.enumeration.ResultadoAnalise;

public class ResultadoAnaliseResponse {

    private String documento;
    private String nome;
    private ResultadoAnalise resultadoAnalise;
    private Long idProposta;

    public ResultadoAnaliseResponse(ResultadoAnalise resultadoAnalise) {
        this.resultadoAnalise = resultadoAnalise;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public ResultadoAnalise getResultadoAnalise() {
        return resultadoAnalise;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public EstadoProposta recuperaEstadoProposta() {
        return resultadoAnalise.getEstadoProposta();
    }

}
