package Projetos.Treinos.SistemaDeAgendamento.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgendamento;

    @ManyToOne @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne @JoinColumn(name = "servico_id")
    private Servico servico;

    private LocalDate dataAgendamento;
    private LocalTime horarioAgendamento;

    // Getters e Setters
    public Long getIdAgendamento() { return idAgendamento; }
    public void setIdAgendamento(Long idAgendamento) { this.idAgendamento = idAgendamento; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }
    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }
    public LocalDate getDataAgendamento() { return dataAgendamento; }
    public void setDataAgendamento(LocalDate dataAgendamento) { this.dataAgendamento = dataAgendamento; }
    public LocalTime getHorarioAgendamento() { return horarioAgendamento; }
    public void setHorarioAgendamento(LocalTime horarioAgendamento) { this.horarioAgendamento = horarioAgendamento; }
}