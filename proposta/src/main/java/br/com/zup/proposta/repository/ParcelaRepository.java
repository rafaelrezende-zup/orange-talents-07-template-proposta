package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
}
