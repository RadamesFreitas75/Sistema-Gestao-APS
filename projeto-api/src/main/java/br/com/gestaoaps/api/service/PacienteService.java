package br.com.gestaoaps.api.service;

import br.com.gestaoaps.api.model.Paciente;
import br.com.gestaoaps.api.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente salvar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente atualizar(Long id, Paciente paciente) {

        Optional<Paciente> pacienteExistente =
                pacienteRepository.findById(id);

        if (pacienteExistente.isPresent()) {

            Paciente pacienteAtual =
                    pacienteExistente.get();

            pacienteAtual.setNome(
                    paciente.getNome()
            );

            pacienteAtual.setCpf(
                    paciente.getCpf()
            );

            pacienteAtual.setDataNascimento(
                    paciente.getDataNascimento()
            );

            pacienteAtual.setSexo(
                    paciente.getSexo()
            );

            pacienteAtual.setTelefone(
                    paciente.getTelefone()
            );

            pacienteAtual.setEndereco(
                    paciente.getEndereco()
            );

            pacienteAtual.setEmail(
                    paciente.getEmail()
            );

            return pacienteRepository.save(
                    pacienteAtual
            );
        }

        return null;
    }

    public void excluir(Long id) {
        pacienteRepository.deleteById(id);
    }
}