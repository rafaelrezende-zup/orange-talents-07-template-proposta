package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Carteira;
import br.com.zup.proposta.model.enumeration.TipoCarteira;

public class CarteiraClientResponse {

    private ResultadoCarteira resultado;
    private String id;

    public ResultadoCarteira getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }

    public Carteira toModel(String email, TipoCarteira carteira, Cartao cartao) {
        return new Carteira(email, carteira, cartao, this.id);
    }
}
