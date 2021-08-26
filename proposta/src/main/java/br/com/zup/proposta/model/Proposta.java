package br.com.zup.proposta.model;

import br.com.zup.proposta.config.converter.DocumentoConverter;
import br.com.zup.proposta.dto.response.ResultadoAnaliseResponse;
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
    @Column(unique = true)
    @Convert(converter = DocumentoConverter.class)
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
    private EstadoProposta estado;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "cartao_id", referencedColumnName = "id")
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String endereco, String nome, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.endereco = endereco;
        this.nome = nome;
        this.salario = salario;
        this.estado = EstadoProposta.NAO_ELEGIVEL;
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

    public EstadoProposta getEstado() {
        return estado;
    }

    public void atualizaStatus(ResultadoAnaliseResponse resultadoAnaliseResponse) {
        this.estado = resultadoAnaliseResponse.recuperaEstadoProposta();
    }

    public void atualizaCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
