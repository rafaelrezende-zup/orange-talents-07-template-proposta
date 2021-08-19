package br.com.zup.proposta.controller;

import br.com.zup.proposta.component.TransactionExecutor;
import br.com.zup.proposta.config.handler.ErrorHandlerDTO;
import br.com.zup.proposta.dto.request.PropostaRequest;
import br.com.zup.proposta.dto.request.SolicitacaoAnaliseRequest;
import br.com.zup.proposta.dto.response.PropostaResponse;
import br.com.zup.proposta.dto.response.ResultadoAnaliseResponse;
import br.com.zup.proposta.feign.SolicitacaoAnaliseClient;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enumeration.ResultadoAnalise;
import br.com.zup.proposta.repository.PropostaRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final SolicitacaoAnaliseClient analiseClient;
    private final TransactionExecutor transaction;

    public PropostaController(PropostaRepository propostaRepository, SolicitacaoAnaliseClient analiseClient, TransactionExecutor transaction) {
        this.propostaRepository = propostaRepository;
        this.analiseClient = analiseClient;
        this.transaction = transaction;
    }

    @PostMapping
    public ResponseEntity<?> cria(@RequestBody @Valid PropostaRequest dto) {
        Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(dto.getDocumento());
        if (possivelProposta.isPresent()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorHandlerDTO("documento", "Já existe uma proposta registrada neste documento."));

        Proposta proposta = dto.toModel();
        transaction.inTransaction(() -> {
            propostaRepository.save(proposta);
        });

        try {
            SolicitacaoAnaliseRequest analiseRequest = new SolicitacaoAnaliseRequest(proposta);
            ResultadoAnaliseResponse resultadoAnaliseResponse = analiseClient.analise(analiseRequest);

            transaction.inTransaction(() -> {
                proposta.atualizaStatus(resultadoAnaliseResponse);
                propostaRepository.save(proposta);
            });

        } catch (FeignException e){
            transaction.inTransaction(() -> {
                proposta.atualizaStatus(new ResultadoAnaliseResponse(ResultadoAnalise.COM_RESTRICAO));
            });
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new PropostaResponse(proposta));
    }
}
