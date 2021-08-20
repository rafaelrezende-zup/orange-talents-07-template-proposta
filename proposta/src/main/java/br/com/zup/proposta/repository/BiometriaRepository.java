package br.com.zup.proposta.repository;

import br.com.zup.proposta.model.Biometria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiometriaRepository extends JpaRepository<Biometria, Long> {
}
