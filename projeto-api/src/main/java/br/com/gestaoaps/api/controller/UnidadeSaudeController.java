package br.com.gestaoaps.api.controller;

import br.com.gestaoaps.api.model.UnidadeSaude;
import br.com.gestaoaps.api.service.UnidadeSaudeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades-saude")
public class UnidadeSaudeController {

    private final UnidadeSaudeService unidadeSaudeService;

    public UnidadeSaudeController(UnidadeSaudeService unidadeSaudeService) {
        this.unidadeSaudeService = unidadeSaudeService;
    }

    @GetMapping
    public List<UnidadeSaude> listarTodos() {
        return unidadeSaudeService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeSaude> buscarPorId(@PathVariable Long id) {
        return unidadeSaudeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UnidadeSaude> salvar(
            @RequestBody UnidadeSaude unidadeSaude) {

        return ResponseEntity.ok(
                unidadeSaudeService.salvar(unidadeSaude)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeSaude> atualizar(
            @PathVariable Long id,
            @RequestBody UnidadeSaude unidadeSaude) {

        UnidadeSaude unidadeAtualizada =
                unidadeSaudeService.atualizar(id, unidadeSaude);

        if (unidadeAtualizada == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(unidadeAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (unidadeSaudeService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        unidadeSaudeService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}