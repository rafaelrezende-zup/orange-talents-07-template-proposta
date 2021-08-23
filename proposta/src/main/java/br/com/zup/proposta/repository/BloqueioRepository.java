package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.Bloqueio;
import br.com.zup.proposta.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {
    Optional<Bloqueio> findByCartao(Cartao cartao);
}
