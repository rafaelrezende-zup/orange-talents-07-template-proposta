package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enumeration.EstadoProposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByDocumento(String documento);

    List<Proposta> findByEstadoAndCartao(EstadoProposta estado, Cartao cartao);

}
