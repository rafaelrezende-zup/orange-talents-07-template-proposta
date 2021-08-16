package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.validator.Documento;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class PropostaRequest {

    @NotBlank
    @Documento
    private String documento;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @DecimalMin(value = "0.0")
    private BigDecimal salario;

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Proposta toModel() {
        return new Proposta(this.documento, this.email, this.endereco, this.nome, this.salario);
    }
}
