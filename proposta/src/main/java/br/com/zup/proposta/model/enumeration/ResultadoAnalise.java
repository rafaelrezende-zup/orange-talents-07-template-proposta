package br.com.zup.proposta.model.enumeration;

public enum ResultadoAnalise {
    COM_RESTRICAO(EstadoProposta.NAO_ELEGIVEL),
    SEM_RESTRICAO(EstadoProposta.ELEGIVEL);

    EstadoProposta estadoProposta;

    ResultadoAnalise(EstadoProposta proposta) {
        this.estadoProposta = proposta;
    }

    public EstadoProposta getEstadoProposta() {
        return estadoProposta;
    }
}
