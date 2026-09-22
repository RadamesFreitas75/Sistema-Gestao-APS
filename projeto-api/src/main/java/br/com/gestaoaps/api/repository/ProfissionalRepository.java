package br.com.gestaoaps.api.repository;

import br.com.gestaoaps.api.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
}
