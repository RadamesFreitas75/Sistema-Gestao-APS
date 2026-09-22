package br.com.gestaoaps.api.service;

import br.com.gestaoaps.api.model.UnidadeSaude;
import br.com.gestaoaps.api.repository.UnidadeSaudeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeSaudeService {

    private final UnidadeSaudeRepository unidadeSaudeRepository;

    public UnidadeSaudeService(UnidadeSaudeRepository unidadeSaudeRepository) {
        this.unidadeSaudeRepository = unidadeSaudeRepository;
    }

    public List<UnidadeSaude> listarTodos() {
        return unidadeSaudeRepository.findAll();
    }

    public Optional<UnidadeSaude> buscarPorId(Long id) {
        return unidadeSaudeRepository.findById(id);
    }

    public UnidadeSaude salvar(UnidadeSaude unidadeSaude) {
        return unidadeSaudeRepository.save(unidadeSaude);
    }

    public UnidadeSaude atualizar(Long id, UnidadeSaude unidadeSaude) {
        Optional<UnidadeSaude> unidadeExistente =
                unidadeSaudeRepository.findById(id);

        if (unidadeExistente.isPresent()) {
            UnidadeSaude unidadeAtual = unidadeExistente.get();

            unidadeAtual.setNome(unidadeSaude.getNome());
            unidadeAtual.setCnes(unidadeSaude.getCnes());
            unidadeAtual.setEndereco(unidadeSaude.getEndereco());
            unidadeAtual.setTelefone(unidadeSaude.getTelefone());

            return unidadeSaudeRepository.save(unidadeAtual);
        }

        return null;
    }

    public void excluir(Long id) {
        unidadeSaudeRepository.deleteById(id);
    }
}
