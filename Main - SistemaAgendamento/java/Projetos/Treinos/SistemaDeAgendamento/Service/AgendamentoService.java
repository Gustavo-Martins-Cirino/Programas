package Projetos.Treinos.SistemaDeAgendamento.Service;

import Projetos.Treinos.SistemaDeAgendamento.domain.Agendamento;
import Projetos.Treinos.SistemaDeAgendamento.domain.Funcionario;
import Projetos.Treinos.SistemaDeAgendamento.domain.Servico;
import Projetos.Treinos.SistemaDeAgendamento.repository.AgendamentoRepository;
import Projetos.Treinos.SistemaDeAgendamento.repository.FuncionarioRepository;
import Projetos.Treinos.SistemaDeAgendamento.repository.ServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    @Autowired
    private ServicoRepository servicoRepository;

    @Transactional
    public Agendamento salvar(Agendamento agendamento) {

        Funcionario funcionario = funcionarioRepository.findById(agendamento.getFuncionario().getIdFuncionario())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));

        Servico servico = servicoRepository.findById(agendamento.getServico().getIdServico())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado."));

        agendamento.setFuncionario(funcionario);
        agendamento.setServico(servico);

        LocalDate data = agendamento.getDataAgendamento();
        LocalTime horarioInicioNovo = agendamento.getHorarioAgendamento();

        LocalTime horarioFimNovo = horarioInicioNovo.plusMinutes(servico.getDuracao());


        List<Agendamento> agendamentosExistentes = agendamentoRepository.findByFuncionarioAndDataAgendamento(funcionario, data);

        for (Agendamento existente : agendamentosExistentes) {
            LocalTime horarioInicioExistente = existente.getHorarioAgendamento();
            LocalTime horarioFimExistente = horarioInicioExistente.plusMinutes(existente.getServico().getDuracao());


            if (horarioInicioNovo.isBefore(horarioFimExistente) && horarioFimNovo.isAfter(horarioInicioExistente)) {
                throw new RuntimeException("Conflito de horário! O período solicitado já está parcial ou totalmente ocupado.");
            }
        }


        if (funcionario.getDiasDeTrabalho() == null || funcionario.getHorarioTrabalhoInicio() == null || funcionario.getHorarioTrabalhoFim() == null) {
            throw new RuntimeException("O funcionário selecionado não tem um expediente de trabalho configurado.");
        }


        return agendamentoRepository.save(agendamento);
    }


    private String getDiaDaSemanaEmPortugues(DayOfWeek dia) {

        return "";
    }
}