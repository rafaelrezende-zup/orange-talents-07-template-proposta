package br.com.zup.proposta.controller;

import br.com.zup.proposta.component.TransactionExecutor;
import br.com.zup.proposta.config.handler.ErrorHandlerDTO;
import br.com.zup.proposta.dto.request.CarteiraClientRequest;
import br.com.zup.proposta.dto.response.CarteiraClientResponse;
import br.com.zup.proposta.dto.response.CarteiraResponse;
import br.com.zup.proposta.dto.response.ResultadoCarteira;
import br.com.zup.proposta.feign.CartaoClient;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Carteira;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.repository.CarteiraRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class CarteiraController {

    Logger logger = LoggerFactory.getLogger(CarteiraController.class);

    CartaoRepository cartaoRepository;
    CarteiraRepository carteiraRepository;
    TransactionExecutor transaction;
    CartaoClient client;

    public CarteiraController(CartaoRepository cartaoRepository,
                              CarteiraRepository carteiraRepository,
                              TransactionExecutor transaction,
                              CartaoClient client) {
        this.cartaoRepository = cartaoRepository;
        this.carteiraRepository = carteiraRepository;
        this.transaction = transaction;
        this.client = client;
    }

    @RequestMapping(value = "cartao/{id}/carteira", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> associaCartao(@PathVariable Long id, @RequestBody CarteiraClientRequest request) {
        logger.debug("Iniciando criação de caterira no serviço " + request.getCarteira());
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorHandlerDTO("cartão", "Cartão de código " + id + " não encontrado."));

        try {
            CarteiraClientResponse response = client.associaCartao(cartao.get().getNumero(), request);

            if (response.getResultado().equals(ResultadoCarteira.ASSOCIADA)) {

                Carteira carteira = response.toModel(request.getEmail(), request.getCarteira(), cartao.get());
                cartao.get().adicionaCarteira(carteira);

                transaction.inTransaction(() -> {
                    carteiraRepository.save(carteira);
                });

                URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("cartao/{idCartao}/carteira/{idCarteira}").buildAndExpand(id, carteira.getId()).toUri();
                return ResponseEntity.created(uri).body(new CarteiraResponse(carteira));

            }

            return ResponseEntity.unprocessableEntity().build();

        } catch (FeignException e) {
            if (e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) return ResponseEntity.unprocessableEntity().build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
