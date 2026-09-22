package br.com.gestaoaps.api.service;

import br.com.gestaoaps.api.model.Agendamento;
import br.com.gestaoaps.api.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Agendamento salvar(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public Agendamento atualizar(Long id, Agendamento agendamento) {

        Optional<Agendamento> agendamentoExistente =
                agendamentoRepository.findById(id);

        if (agendamentoExistente.isPresent()) {

            Agendamento agendamentoAtual =
                    agendamentoExistente.get();

            agendamentoAtual.setData(agendamento.getData());
            agendamentoAtual.setHorario(agendamento.getHorario());
            agendamentoAtual.setTipoAtendimento(
                    agendamento.getTipoAtendimento()
            );
            agendamentoAtual.setStatus(agendamento.getStatus());
            agendamentoAtual.setPacienteId(
                    agendamento.getPacienteId()
            );
            agendamentoAtual.setProfissionalId(
                    agendamento.getProfissionalId()
            );
            agendamentoAtual.setUnidadeSaudeId(
                    agendamento.getUnidadeSaudeId()
            );

            return agendamentoRepository.save(agendamentoAtual);
        }

        return null;
    }

    public void excluir(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
