package br.com.zup.proposta.model;

import br.com.zup.proposta.dto.response.ResultadoAnaliseDTO;
import br.com.zup.proposta.model.enumeration.EstadoProposta;
import br.com.zup.proposta.validator.Documento;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoProposta estadoProposta;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String endereco, String nome, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.endereco = endereco;
        this.nome = nome;
        this.salario = salario;
        this.estadoProposta = EstadoProposta.NAO_ELEGIVEL;
    }

    public Long getId() {
        return id;
    }

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

    public void atualizaStatus(ResultadoAnaliseDTO resultadoAnaliseDTO) {
        this.estadoProposta = resultadoAnaliseDTO.recuperaEstadoProposta();
    }
}
