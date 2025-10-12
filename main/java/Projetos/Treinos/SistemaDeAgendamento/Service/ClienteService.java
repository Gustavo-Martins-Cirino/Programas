package Projetos.Treinos.SistemaDeAgendamento.Service;

import Projetos.Treinos.SistemaDeAgendamento.domain.Cliente;
import Projetos.Treinos.SistemaDeAgendamento.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id: " + id));


        clienteExistente.setNomeCliente(clienteAtualizado.getNomeCliente());
        clienteExistente.setTelefoneCliente(clienteAtualizado.getTelefoneCliente());


        return clienteExistente;
    }
}