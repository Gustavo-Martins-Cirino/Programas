package Projetos.Treinos.SistemaDeAgendamento.controller;

import Projetos.Treinos.SistemaDeAgendamento.domain.Funcionario;
import Projetos.Treinos.SistemaDeAgendamento.repository.FuncionarioRepository;
import Projetos.Treinos.SistemaDeAgendamento.Service.FuncionarioService; // Importante: Importe o Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {


    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @Autowired
    private FuncionarioService funcionarioService;


    @GetMapping
    public List<Funcionario> listarTodosFuncionarios() {
        return funcionarioRepository.findAll();
    }


    @PostMapping
    public Funcionario criarFuncionario(@RequestBody Funcionario novoFuncionario) {
        return funcionarioRepository.save(novoFuncionario);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId(@PathVariable Long id) {
        return funcionarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioAtualizado) {
        try {

            Funcionario funcionario = funcionarioService.atualizar(id, funcionarioAtualizado);
            return ResponseEntity.ok(funcionario);
        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}