package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.Aviso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisoRepository extends JpaRepository<Aviso, Long> {
}
