package br.com.gestaoaps.api.service;

import br.com.gestaoaps.api.model.Profissional;
import br.com.gestaoaps.api.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    public Optional<Profissional> buscarPorId(Long id) {
        return profissionalRepository.findById(id);
    }

    public Profissional salvar(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }

    public Profissional atualizar(Long id, Profissional profissional) {

        Optional<Profissional> profissionalExistente =
                profissionalRepository.findById(id);

        if (profissionalExistente.isPresent()) {

            Profissional profissionalAtual =
                    profissionalExistente.get();

            profissionalAtual.setNome(profissional.getNome());
            profissionalAtual.setCpf(profissional.getCpf());
            profissionalAtual.setEspecialidade(profissional.getEspecialidade());
            profissionalAtual.setRegistroProfissional(
                    profissional.getRegistroProfissional()
            );
            profissionalAtual.setTelefone(profissional.getTelefone());
            profissionalAtual.setEmail(profissional.getEmail());

            return profissionalRepository.save(profissionalAtual);
        }

        return null;
    }

    public void excluir(Long id) {
        profissionalRepository.deleteById(id);
    }
}
