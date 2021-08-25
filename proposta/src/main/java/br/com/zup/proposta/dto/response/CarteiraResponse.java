package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.model.Carteira;
import br.com.zup.proposta.model.enumeration.TipoCarteira;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CarteiraResponse {

    @NotBlank
    private String id;
    private String email;
    private LocalDateTime associadaEm;
    private String emissor;
    private CartaoResponse cartao;
    private TipoCarteira tipoCarteira;

    public CarteiraResponse(String id, String email, LocalDateTime associadaEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public CarteiraResponse(Carteira carteira) {
        this.id = carteira.getId().toString();
        this.email = carteira.getEmail();
        this.associadaEm = carteira.getDataAssociacao();
        this.emissor = carteira.getEmissor();
        this.cartao = new CartaoResponse(carteira.getCartao());
        this.tipoCarteira = carteira.getTipoCarteira();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }

    public String getEmissor() {
        return emissor;
    }
}
