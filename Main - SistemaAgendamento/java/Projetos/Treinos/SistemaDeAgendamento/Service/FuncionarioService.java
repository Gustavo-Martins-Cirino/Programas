package Projetos.Treinos.SistemaDeAgendamento.Service;

import Projetos.Treinos.SistemaDeAgendamento.domain.Funcionario;
import Projetos.Treinos.SistemaDeAgendamento.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional
    public Funcionario atualizar(Long id, Funcionario funcionarioAtualizado) {

        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o id: " + id));


        funcionarioExistente.setNomeFuncionario(funcionarioAtualizado.getNomeFuncionario());
        funcionarioExistente.setEspecialidade(funcionarioAtualizado.getEspecialidade());
        funcionarioExistente.setTelefoneFuncionario(funcionarioAtualizado.getTelefoneFuncionario());
        funcionarioExistente.setEmail(funcionarioAtualizado.getEmail());
        funcionarioExistente.setHorarioTrabalhoInicio(funcionarioAtualizado.getHorarioTrabalhoInicio());
        funcionarioExistente.setHorarioTrabalhoFim(funcionarioAtualizado.getHorarioTrabalhoFim());
        funcionarioExistente.setDiasDeTrabalho(funcionarioAtualizado.getDiasDeTrabalho());


        return funcionarioExistente;
    }
}