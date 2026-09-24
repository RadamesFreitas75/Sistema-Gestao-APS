package br.com.gestaoaps.api.controller;

import br.com.gestaoaps.api.model.Usuario;
import br.com.gestaoaps.api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(
            @PathVariable Long id) {

        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> salvar(
            @RequestBody Usuario usuario) {

        return ResponseEntity.ok(
                usuarioService.salvar(usuario)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> dados) {

        String email = dados.get("email");
        String senha = dados.get("senha");

        return usuarioService.login(email, senha)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.status(401).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        return usuarioService.buscarPorId(id)
                .map(usuario -> {

                    usuarioService.excluir(id);

                    return ResponseEntity.noContent().<Void>build();

                })
                .orElse(ResponseEntity.notFound().build());
    }
}