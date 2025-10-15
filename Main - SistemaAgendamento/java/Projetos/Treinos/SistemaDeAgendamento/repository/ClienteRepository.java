package Projetos.Treinos.SistemaDeAgendamento.repository;

import Projetos.Treinos.SistemaDeAgendamento.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}