package Projetos.Treinos.SistemaDeAgendamento.controller;

import Projetos.Treinos.SistemaDeAgendamento.domain.Servico;
import Projetos.Treinos.SistemaDeAgendamento.repository.ServicoRepository;
import Projetos.Treinos.SistemaDeAgendamento.Service.ServicoService; // Importante: Importe o Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "*")
public class ServicoController {


    @Autowired
    private ServicoRepository servicoRepository;


    @Autowired
    private ServicoService servicoService;


    @GetMapping
    public List<Servico> listarTodosServicos() {
        return servicoRepository.findAll();
    }


    @PostMapping
    public Servico criarServico(@RequestBody Servico novoServico) {
        return servicoRepository.save(novoServico);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarServicoPorId(@PathVariable Long id) {
        return servicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico servicoAtualizado) {
        try {

            Servico servico = servicoService.atualizar(id, servicoAtualizado);
            return ResponseEntity.ok(servico);
        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirServico(@PathVariable Long id) {
        servicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}