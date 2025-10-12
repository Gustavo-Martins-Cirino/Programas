package Projetos.Treinos.SistemaDeAgendamento.repository;

import Projetos.Treinos.SistemaDeAgendamento.domain.Agendamento;
import Projetos.Treinos.SistemaDeAgendamento.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    /**
     * Encontra todos os agendamentos para um funcionário em uma data específica.
     * @param funcionario O objeto do funcionário a ser verificado.
     * @param dataAgendamento A data do agendamento a ser verificada.
     * @return Uma lista de agendamentos existentes para aquele dia e funcionário.
     */
    List<Agendamento> findByFuncionarioAndDataAgendamento(Funcionario funcionario, LocalDate dataAgendamento);

}