package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.repository.PropostaRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartaoResponse {

    @NotBlank
    private String id;

    private LocalDateTime emitidoEm;

    @NotBlank
    private String titular;

    private List<BloqueioResponse> bloqueios = new ArrayList<>();

    private List<AvisoResponse> avisos = new ArrayList<>();

    private List<CarteiraResponse> carteiras = new ArrayList<>();

    private List<ParcelaResponse> parcelas = new ArrayList<>();

    @Positive
    private BigDecimal limite;

    private RenegociacaoResponse renegociacao;

    private VencimentoResponse vencimento;

    private Long idProposta;

    public CartaoResponse(Cartao cartao) {
        this.id = cartao.getId().toString();
        this.titular = cartao.getTitular();
        this.emitidoEm = cartao.getDataEmissao();
    }

    public Cartao toModel(PropostaRepository propostaRepository) {
        Proposta proposta = propostaRepository.getOne(idProposta);
        return new Cartao(this.id,
                this.emitidoEm,
                this.titular,
                this.bloqueios,
                this.avisos,
                this.carteiras,
                this.parcelas,
                this.limite,
                this.renegociacao,
                this.vencimento,
                proposta);
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<BloqueioResponse> getBloqueios() {
        return bloqueios;
    }

    public List<AvisoResponse> getAvisos() {
        return avisos;
    }

    public List<CarteiraResponse> getCarteiras() {
        return carteiras;
    }

    public List<ParcelaResponse> getParcelas() {
        return parcelas;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public RenegociacaoResponse getRenegociacao() {
        return renegociacao;
    }

    public VencimentoResponse getVencimento() {
        return vencimento;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
