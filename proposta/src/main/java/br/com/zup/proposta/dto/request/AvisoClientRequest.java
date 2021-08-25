package br.com.zup.proposta.dto.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AvisoClientRequest {

    @NotBlank
    private String destino;

    @NotBlank
    private String validoAte;

    public AvisoClientRequest(String detino, LocalDate terminoViagem) {
        this.destino = detino;
        this.validoAte = terminoViagem.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getDestino() {
        return destino;
    }

    public String getValidoAte() {
        return validoAte;
    }
}
