package br.com.zup.proposta.feign;

import br.com.zup.proposta.dto.response.CartaoResponse;
import br.com.zup.proposta.util.Constantes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartao", url = Constantes.URL_API_CARTAO)
public interface CartaoClient {

    @GetMapping(value = "/cartoes")
    CartaoResponse analise(@RequestParam Long idProposta);

}
