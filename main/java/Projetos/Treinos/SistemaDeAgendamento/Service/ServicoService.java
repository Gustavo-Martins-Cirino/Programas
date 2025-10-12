package Projetos.Treinos.SistemaDeAgendamento.Service;

import Projetos.Treinos.SistemaDeAgendamento.domain.Servico;
import Projetos.Treinos.SistemaDeAgendamento.repository.ServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Transactional
    public Servico atualizar(Long id, Servico servicoAtualizado) {

        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com o id: " + id));


        servicoExistente.setNomeServico(servicoAtualizado.getNomeServico());
        servicoExistente.setDuracao(servicoAtualizado.getDuracao());
        servicoExistente.setValorServico(servicoAtualizado.getValorServico());


        return servicoExistente;
    }
}