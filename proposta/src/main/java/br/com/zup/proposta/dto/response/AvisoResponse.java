package br.com.zup.proposta.dto.response;

import java.time.LocalDate;

public class AvisoResponse {

    private LocalDate validoAte;
    private String destino;

    public AvisoResponse(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }
}
