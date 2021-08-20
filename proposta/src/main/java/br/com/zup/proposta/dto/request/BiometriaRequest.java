package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.model.Biometria;
import br.com.zup.proposta.model.Cartao;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    private String fingerprint;

    public String getFingerprint() {
        return fingerprint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(this.fingerprint, cartao);
    }
}
