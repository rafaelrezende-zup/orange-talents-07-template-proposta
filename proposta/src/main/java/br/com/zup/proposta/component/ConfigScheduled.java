package br.com.zup.proposta.component;

import br.com.zup.proposta.dto.response.CartaoResponse;
import br.com.zup.proposta.feign.CartaoClient;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enumeration.EstadoProposta;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.repository.PropostaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigScheduled {

    PropostaRepository propostaRepository;
    CartaoRepository cartaoRepository;
    CartaoClient client;
    TransactionExecutor transaction;

    public ConfigScheduled(PropostaRepository propostaRepository,
                           CartaoRepository cartaoRepository,
                           CartaoClient client,
                           TransactionExecutor transaction) {
        this.propostaRepository = propostaRepository;
        this.cartaoRepository = cartaoRepository;
        this.client = client;
        this.transaction = transaction;
    }

    @Scheduled(cron = "0 0/15 * 1/1 * ?")
    public void associaCartaoComProposta() {
        List<Proposta> listProposta = propostaRepository.findByEstadoAndCartao(EstadoProposta.ELEGIVEL, null);
        listProposta.forEach(proposta -> {
            CartaoResponse response = client.analise(proposta.getId());
            Cartao cartao = response.toModel(propostaRepository);
            transaction.inTransaction(() -> {
                cartaoRepository.save(cartao);
                proposta.atualizaCartao(cartao);
                propostaRepository.save(proposta);
            });
        });
    }

}
