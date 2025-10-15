package Projetos.Treinos.SistemaDeAgendamento.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalTime;

@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;

    private String nomeFuncionario;
    private String especialidade;
    private String telefoneFuncionario;
    private String email;


    @Column(columnDefinition = "TIME")
    private LocalTime horarioTrabalhoInicio;


    @Column(columnDefinition = "TIME")
    private LocalTime horarioTrabalhoFim;

    private String diasDeTrabalho;


    public Long getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(Long id) { this.idFuncionario = id; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nome) { this.nomeFuncionario = nome; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String esp) { this.especialidade = esp; }
    public String getTelefoneFuncionario() { return telefoneFuncionario; }
    public void setTelefoneFuncionario(String tel) { this.telefoneFuncionario = tel; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalTime getHorarioTrabalhoInicio() { return horarioTrabalhoInicio; }
    public void setHorarioTrabalhoInicio(LocalTime inicio) { this.horarioTrabalhoInicio = inicio; }
    public LocalTime getHorarioTrabalhoFim() { return horarioTrabalhoFim; }
    public void setHorarioTrabalhoFim(LocalTime fim) { this.horarioTrabalhoFim = fim; }
    public String getDiasDeTrabalho() { return diasDeTrabalho; }
    public void setDiasDeTrabalho(String dias) { this.diasDeTrabalho = dias; }
}