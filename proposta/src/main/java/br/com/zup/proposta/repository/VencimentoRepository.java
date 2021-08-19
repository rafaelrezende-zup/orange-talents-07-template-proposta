package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.Vencimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VencimentoRepository extends JpaRepository<Vencimento, Long> {
}
