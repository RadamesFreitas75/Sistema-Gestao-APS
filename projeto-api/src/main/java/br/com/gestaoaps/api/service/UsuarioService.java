package br.com.gestaoaps.api.service;

import br.com.gestaoaps.api.model.Usuario;
import br.com.gestaoaps.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> login(String email, String senha) {

        Optional<Usuario> usuarioEncontrado =
                usuarioRepository.findByEmail(email);

        if (usuarioEncontrado.isPresent()
                && usuarioEncontrado.get().getSenha().equals(senha)) {

            return usuarioEncontrado;
        }

        return Optional.empty();
    }
}