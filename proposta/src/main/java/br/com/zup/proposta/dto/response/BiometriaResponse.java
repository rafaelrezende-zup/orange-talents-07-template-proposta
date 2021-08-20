package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.model.Biometria;

import java.time.LocalDateTime;

public class BiometriaResponse {

    private Long id;
    private String fingerprint;
    private Long idCartao;
    private LocalDateTime dataCriacao;

    public BiometriaResponse(Biometria biometria) {
        this.id = biometria.getId();
        this.fingerprint = biometria.getFingerprint();
        this.idCartao = biometria.getCartao().getId();
        this.dataCriacao = biometria.getDataCriacao();
    }
}
