package br.com.gestaoaps.api.controller;

import br.com.gestaoaps.api.model.Profissional;
import br.com.gestaoaps.api.service.ProfissionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public List<Profissional> listarTodos() {
        return profissionalService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscarPorId(@PathVariable Long id) {
        return profissionalService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profissional> salvar(
            @RequestBody Profissional profissional) {

        return ResponseEntity.ok(
                profissionalService.salvar(profissional)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profissional> atualizar(
            @PathVariable Long id,
            @RequestBody Profissional profissional) {

        Profissional profissionalAtualizado =
                profissionalService.atualizar(id, profissional);

        if (profissionalAtualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profissionalAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (profissionalService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        profissionalService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
