package br.com.zup.proposta.controller;

import br.com.zup.proposta.config.handler.ErrorHandlerDTO;
import br.com.zup.proposta.dto.request.PropostaRequest;
import br.com.zup.proposta.dto.response.PropostaResponse;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.repository.PropostaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cria(@RequestBody @Valid PropostaRequest dto) {
        Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(dto.getDocumento());
        if (possivelProposta.isPresent()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorHandlerDTO("documento", "Já existe uma proposta registrada neste documento."));

        Proposta proposta = dto.toModel();
        propostaRepository.save(proposta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new PropostaResponse(proposta));
    }
}
