package br.com.zup.proposta.feign;

import br.com.zup.proposta.dto.request.SolicitacaoAnaliseRequest;
import br.com.zup.proposta.dto.response.ResultadoAnaliseResponse;
import br.com.zup.proposta.util.Constantes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "solicitacaoAnalise", url = Constantes.URL_API_ANALISE)
public interface SolicitacaoAnaliseClient {

    @PostMapping(value = "/solicitacao")
    ResultadoAnaliseResponse analise(SolicitacaoAnaliseRequest dto);

}
