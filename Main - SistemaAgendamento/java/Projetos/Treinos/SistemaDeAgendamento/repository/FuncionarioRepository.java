package Projetos.Treinos.SistemaDeAgendamento.repository;

import Projetos.Treinos.SistemaDeAgendamento.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}