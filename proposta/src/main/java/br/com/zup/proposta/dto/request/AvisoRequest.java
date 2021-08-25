package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.model.Aviso;
import br.com.zup.proposta.model.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoRequest {

    @NotBlank
    private String detino;

    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate terminoViagem;

    public String getDetino() {
        return detino;
    }

    public LocalDate getTerminoViagem() {
        return terminoViagem;
    }

    public Aviso toModel(Cartao cartao, String userAgent, String ip) {
        return new Aviso(this.detino, this.terminoViagem, cartao, userAgent, ip);
    }
}
