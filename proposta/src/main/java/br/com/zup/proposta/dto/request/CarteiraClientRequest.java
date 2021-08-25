package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.model.enumeration.TipoCarteira;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraClientRequest {

    @NotBlank
    private String email;

    @NotNull
    private TipoCarteira carteira;

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }

}
