package br.com.zup.proposta.component;

import br.com.zup.proposta.dto.response.CartaoResponse;
import br.com.zup.proposta.feign.CartaoClient;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enumeration.EstadoProposta;
import br.com.zup.proposta.repository.CartaoRepository;
import br.com.zup.proposta.repository.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigScheduled {

    Logger logger = LoggerFactory.getLogger(ConfigScheduled.class);

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
        logger.trace("Buscando propostas sem cartão e elegíveis");
        List<Proposta> listProposta = propostaRepository.findByEstadoAndCartao(EstadoProposta.ELEGIVEL, null);
        listProposta.forEach(proposta -> {
            try {
                logger.trace("Preparando para solicitar cartão");
                CartaoResponse response = client.analise(proposta.getId());
                Cartao cartao = response.toModel(propostaRepository);
                logger.trace("Cartão recebido: " + cartao.toString());
                transaction.inTransaction(() -> {
                    cartaoRepository.save(cartao);
                    proposta.atualizaCartao(cartao);
                    propostaRepository.save(proposta);
                    logger.debug("Cartão salvo e atralado a propsota");
                });
            } catch (FeignException e) {
                logger.error("Error ao solicitar cartão." + e.toString());
            }
        });
    }

}
