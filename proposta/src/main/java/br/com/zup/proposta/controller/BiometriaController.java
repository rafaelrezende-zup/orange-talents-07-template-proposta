package br.com.zup.proposta.controller;

import br.com.zup.proposta.component.TransactionExecutor;
import br.com.zup.proposta.config.handler.ErrorHandlerDTO;
import br.com.zup.proposta.dto.request.BiometriaRequest;
import br.com.zup.proposta.dto.response.BiometriaResponse;
import br.com.zup.proposta.model.Biometria;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.repository.BiometriaRepository;
import br.com.zup.proposta.repository.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class BiometriaController {

    Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    CartaoRepository cartaoRepository;
    BiometriaRepository biometriaRepository;
    TransactionExecutor transaction;

    public BiometriaController(CartaoRepository cartaoRepository,
                               BiometriaRepository biometriaRepository,
                               TransactionExecutor transaction) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
        this.transaction = transaction;
    }

    @RequestMapping(value = "cartao/{id}/biometria", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> criaBiometria(@PathVariable Long id, @RequestBody BiometriaRequest request) {
        logger.trace("Iniciando criação de biometria");
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorHandlerDTO("cartão", "Cartão de código " + id + " não encontrado."));

        Biometria biometria = request.toModel(cartao.get());
        transaction.inTransaction(() -> {
            biometriaRepository.save(biometria);
        });

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("cartao/{idCartao}/biometria/{idBiometria}").buildAndExpand(id, biometria.getId()).toUri();
        return ResponseEntity.created(uri).body(new BiometriaResponse(biometria));
    }

}
