package br.com.gestaoaps.api.repository;

import br.com.gestaoaps.api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
