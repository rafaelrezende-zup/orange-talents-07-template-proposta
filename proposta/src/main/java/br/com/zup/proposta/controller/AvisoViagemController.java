package br.com.zup.proposta.controller;

import br.com.zup.proposta.component.TransactionExecutor;
import br.com.zup.proposta.config.handler.ErrorHandlerDTO;
import br.com.zup.proposta.dto.request.AvisoClientRequest;
import br.com.zup.proposta.dto.request.AvisoRequest;
import br.com.zup.proposta.dto.response.AvisoClientResponse;
import br.com.zup.proposta.feign.CartaoClient;
import br.com.zup.proposta.model.Aviso;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.enumeration.ResultadoAviso;
import br.com.zup.proposta.repository.AvisoRepository;
import br.com.zup.proposta.repository.CartaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class AvisoViagemController {

    Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    CartaoRepository cartaoRepository;
    AvisoRepository avisoRepository;
    TransactionExecutor transaction;
    CartaoClient client;

    public AvisoViagemController(CartaoRepository cartaoRepository, AvisoRepository avisoRepository, TransactionExecutor transaction, CartaoClient client) {
        this.cartaoRepository = cartaoRepository;
        this.avisoRepository = avisoRepository;
        this.transaction = transaction;
        this.client = client;
    }

    @RequestMapping(value = "cartao/{id}/aviso", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> criaAvisoViagem(@PathVariable Long id, AvisoRequest dto, HttpServletRequest request) {
        logger.trace("Iniciando criação do aviso de viagem");
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorHandlerDTO("cartão", "Cartão de código " + id + " não encontrado."));

        try {
            AvisoClientRequest avisoClientRequest = new AvisoClientRequest(dto.getDetino(), dto.getTerminoViagem());
            AvisoClientResponse avisoViagemResponse = client.avisoCartao(cartao.get().getNumero(), avisoClientRequest);

            if (avisoViagemResponse.getResultadoAviso().equals(ResultadoAviso.CRIADO)) {

                Aviso aviso = dto.toModel(cartao.get(), request.getHeader("User-Agent"), request.getHeader("X-Forwarded-For"));
                cartao.get().adicionaAviso(aviso);

                transaction.inTransaction(() -> {
                    avisoRepository.save(aviso);
                });

                return ResponseEntity.ok().build();

            }

            return ResponseEntity.unprocessableEntity().build();

        } catch (FeignException e) {
            if (e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) return ResponseEntity.unprocessableEntity().build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
