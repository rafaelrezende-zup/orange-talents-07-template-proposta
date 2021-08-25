package br.com.zup.proposta.feign;

import br.com.zup.proposta.dto.request.AvisoClientRequest;
import br.com.zup.proposta.dto.response.AvisoClientResponse;
import br.com.zup.proposta.dto.response.BloqueioCartaoResponse;
import br.com.zup.proposta.dto.response.CartaoResponse;
import br.com.zup.proposta.util.Constantes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "cartao", url = Constantes.URL_API_CARTAO)
public interface CartaoClient {

    @GetMapping(value = "/cartoes")
    CartaoResponse analise(@RequestParam Long idProposta);

    @PostMapping(value = "/cartoes/{id}/bloqueios")
    BloqueioCartaoResponse bloqueiaCartao(@PathVariable String id, @RequestBody Map<String, String> body);

    @PostMapping(value = "/cartoes/{id}/avisos")
    AvisoClientResponse avisoCartao(@PathVariable String id, AvisoClientRequest avisoClientRequest);

}
