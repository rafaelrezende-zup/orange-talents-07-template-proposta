package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
