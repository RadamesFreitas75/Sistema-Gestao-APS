package br.com.gestaoaps.api.controller;

import br.com.gestaoaps.api.model.Agendamento;
import br.com.gestaoaps.api.service.AgendamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public List<Agendamento> listarTodos() {
        return agendamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(
            @PathVariable Long id) {

        return agendamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Agendamento> salvar(
            @RequestBody Agendamento agendamento) {

        return ResponseEntity.ok(
                agendamentoService.salvar(agendamento)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizar(
            @PathVariable Long id,
            @RequestBody Agendamento agendamento) {

        Agendamento agendamentoAtualizado =
                agendamentoService.atualizar(id, agendamento);

        if (agendamentoAtualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        if (agendamentoService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        agendamentoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
