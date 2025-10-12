package Projetos.Treinos.SistemaDeAgendamento.controller;

import Projetos.Treinos.SistemaDeAgendamento.Service.AgendamentoService;
import Projetos.Treinos.SistemaDeAgendamento.domain.Agendamento;
import Projetos.Treinos.SistemaDeAgendamento.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping
    public List<Agendamento> listarTodosAgendamentos() {
        return agendamentoRepository.findAll();
    }
    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<?> criarAgendamento(@RequestBody Agendamento novoAgendamento) {
        try {
            Agendamento agendamentoSalvo = agendamentoService.salvar(novoAgendamento);
            return ResponseEntity.ok(agendamentoSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAgendamento(@PathVariable Long id) {
        agendamentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}