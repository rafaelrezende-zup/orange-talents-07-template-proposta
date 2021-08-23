package br.com.zup.proposta.controller;

import br.com.zup.proposta.component.TransactionExecutor;
import br.com.zup.proposta.config.handler.ErrorHandlerDTO;
import br.com.zup.proposta.dto.response.BloqueioCartaoResponse;
import br.com.zup.proposta.feign.CartaoClient;
import br.com.zup.proposta.model.Bloqueio;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.repository.BloqueioRepository;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.util.Constantes;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class BloqueioController {

    Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    CartaoRepository cartaoRepository;
    BloqueioRepository bloqueioRepository;
    TransactionExecutor transaction;
    CartaoClient client;

    public BloqueioController(CartaoRepository cartaoRepository,
                              BloqueioRepository bloqueioRepository,
                              TransactionExecutor transaction,
                              CartaoClient client) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.transaction = transaction;
        this.client = client;
    }

    @RequestMapping(value = "cartao/{id}/bloqueio", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> bloquearCartao(@PathVariable Long id, HttpServletRequest request) {
        logger.trace("Iniciando bloqueio cartão");
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorHandlerDTO("cartão", "Cartão de código " + id + " não encontrado."));

        Optional<Bloqueio> existBloqueio = bloqueioRepository.findByCartao(cartao.get());
        if (existBloqueio.isPresent()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorHandlerDTO("cartão", "Cartão de código " + id + " já bloqueado."));

        Map<String, String> header = getHeader(request);

        try {
            BloqueioCartaoResponse bloqueioCartaoResponse = client.bloqueiaCartao(cartao.get().getNumero(), getBody());
            cartao.get().bloqueiaCartao(bloqueioCartaoResponse, request.getHeader("User-Agent"), request.getHeader("X-Forwarded-For"));

            Bloqueio bloqueio = new Bloqueio(cartao.get(), request.getHeader("User-Agent"), request.getHeader("X-Forwarded-For"));

            transaction.inTransaction(() -> {
                bloqueioRepository.save(bloqueio);
            });

            return ResponseEntity.ok().build();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) return ResponseEntity.unprocessableEntity().build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private Map<String, String> getBody() {
        Map<String, String> map = new HashMap<>();
        map.put("sistemaResponsavel", Constantes.API_PROPOSTA);
        return map;
    }

    public Map<String, String> getHeader(HttpServletRequest request) {
        HashMap<String, String> header = new HashMap<>();

        header.put("userAgent",request.getHeader("User-Agent"));
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) ipAddress = request.getRemoteAddr();
        header.put("ip", ipAddress);

        return header;
    }

}
