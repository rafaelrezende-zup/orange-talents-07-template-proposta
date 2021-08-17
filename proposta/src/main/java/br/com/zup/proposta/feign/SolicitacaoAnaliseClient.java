package br.com.zup.proposta.feign;

import br.com.zup.proposta.dto.request.SolicitacaoAnaliseDTO;
import br.com.zup.proposta.dto.response.ResultadoAnaliseDTO;
import br.com.zup.proposta.util.Constantes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "solicitacaoAnalise", url = Constantes.URL_API_ANALISE)
public interface SolicitacaoAnaliseClient {

    @PostMapping(value = "/solicitacao")
    ResultadoAnaliseDTO analise(SolicitacaoAnaliseDTO dto);

}
