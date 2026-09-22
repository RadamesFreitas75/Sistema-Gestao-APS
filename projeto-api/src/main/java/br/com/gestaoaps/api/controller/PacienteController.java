package br.com.gestaoaps.api.controller;

import br.com.gestaoaps.api.model.Paciente;
import br.com.gestaoaps.api.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<Paciente> listarTodos() {
        return pacienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return pacienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> salvar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.salvar(paciente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(
            @PathVariable Long id,
            @RequestBody Paciente paciente) {

        Paciente pacienteAtualizado = pacienteService.atualizar(id, paciente);

        if (pacienteAtualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pacienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (pacienteService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pacienteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
