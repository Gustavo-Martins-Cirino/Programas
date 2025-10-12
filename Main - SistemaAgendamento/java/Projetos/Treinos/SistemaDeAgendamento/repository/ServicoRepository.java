package Projetos.Treinos.SistemaDeAgendamento.repository;

import Projetos.Treinos.SistemaDeAgendamento.domain.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
}