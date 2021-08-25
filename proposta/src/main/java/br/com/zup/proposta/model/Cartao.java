package br.com.zup.proposta.model;

import br.com.zup.proposta.dto.response.*;
import br.com.zup.proposta.model.enumeration.EstadoCartao;
import br.com.zup.proposta.model.enumeration.ResultadoBloqueio;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String numero;

    private LocalDateTime dataEmissao;

    @NotBlank
    private String titular;

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Bloqueio> bloqueios = new HashSet<>();

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Aviso> avisos = new HashSet<>();

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Carteira> carteiras = new HashSet<>();

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Parcela> parcelas = new HashSet<>();

    @Positive
    private BigDecimal limite;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "renegociacao_id", referencedColumnName = "id")
    private Renegociacao renegociacao;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "vencimento_id", referencedColumnName = "id")
    private Vencimento vencimento;

    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;

    @Enumerated(EnumType.STRING)
    private EstadoCartao statusCartao;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id,
                  LocalDateTime emitidoEm,
                  String titular,
                  List<BloqueioResponse> bloqueios,
                  List<AvisoResponse> avisos,
                  List<CarteiraResponse> carteiras,
                  List<ParcelaResponse> parcelas,
                  BigDecimal limite,
                  RenegociacaoResponse renegociacao,
                  VencimentoResponse vencimento,
                  Proposta proposta) {
        this.numero = id;
        this.titular = titular;
        this.dataEmissao = emitidoEm;
        this.bloqueios = bloqueios.stream().map(Bloqueio::new).collect(Collectors.toSet());
        this.avisos = avisos.stream().map(Aviso::new).collect(Collectors.toSet());
        this.carteiras = carteiras.stream().map(Carteira::new).collect(Collectors.toSet());
        this.parcelas = parcelas.stream().map(Parcela::new).collect(Collectors.toSet());
        this.limite = limite;
        this.renegociacao = Objects.nonNull(renegociacao) ? renegociacao.toModel() : null;
        this.vencimento = Objects.nonNull(vencimento) ? vencimento.toModel() : null;
        this.proposta = proposta;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public String getTitular() {
        return titular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cartao cartao = (Cartao) o;
        return Objects.equals(numero, cartao.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    public void bloqueiaCartao(BloqueioCartaoResponse bloqueioCartaoResponse, String userAgent, String ip) {
        if (bloqueioCartaoResponse.getBloqueio().equals(ResultadoBloqueio.BLOQUEADO)) {
            this.statusCartao = EstadoCartao.BLOQUEADO;
            this.bloqueios.add(new Bloqueio(this, userAgent, ip));
        }
    }

    public void adicionaAviso(Aviso aviso) {
        this.avisos.add(aviso);
    }

    public void adicionaCarteira(Carteira carteira) {
        this.carteiras.add(carteira);
    }
}
