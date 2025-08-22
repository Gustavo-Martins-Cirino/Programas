# 📅 Sistema de Agendamento Online em Java

Este projeto é um **treino prático** desenvolvido em Java, simulando um sistema de agendamento e gestão de clientes. O objetivo é criar uma solução completa para pequenos e médios negócios (como salões de beleza, clínicas e consultórios) que precisam gerenciar horários e informações de clientes.

- ✅ **Versão com interface gráfica (GUI)**

---

## 🚀 Funcionalidades

- Login e autenticação
- Dashboard com indicadores de agendamentos e faturamento
- Cadastro, consulta, atualização e exclusão de clientes e funcionários
- Cadastro e gerenciamento de serviços (preço, duração)
- Sistema de agendamento inteligente que verifica a disponibilidade de horários
- Geração de IDs automáticos com verificação no banco
- Relatórios básicos (ex: agendamentos do dia)

---

## 🧰 Tecnologias Utilizadas

| Tecnologia / Ferramenta | Finalidade |
|--------------------------|------------|
| `Java SE 8+`             | Lógica da aplicação |
| `JavaFX`                 | Interface Gráfica (GUI) |
| `JDBC`                   | Conexão com banco de dados |
| `MySQL`                  | Banco de dados relacional |

---

## 💡 Competências Exercitadas

- Programação Orientada a Objetos (classes, herança, encapsulamento, etc.)
- Manipulação de banco de dados com JDBC e SQL
- Construção de interfaces gráficas com JavaFX
- Geração e verificação de IDs únicos
- Tratamento de exceções (`SQLException`, `DateTimeParseException`)
- Estrutura de projeto e modularização do código
- Uso de `PreparedStatement` e `try-with-resources`

---

## 🖼️ Screenshots

![Tela de Login](Screenshots/login.png)
![Dashboard](Screenshots/dashboard.png)
![Tela de Agendamento](Screenshots/agendamento.png)

---

## 🧪 Como Executar

1.  Certifique-se de ter o **Java JDK** instalado (versão 8 ou superior).
2.  Crie o banco de dados `agendamento` no MySQL com as tabelas adequadas (Cliente, Funcionario, Servico, Agendamento) usando o arquivo `SistemaAgendamento - SQL.txt`.
3.  Ajuste a string de conexão na sua classe de persistência para corresponder às suas credenciais.
4.  Execute a classe principal.

---

## ⚠️ Observações

- Este projeto foi desenvolvido para fins de estudo e prática de habilidades de desenvolvimento full-stack.
- O código foi concentrado em algumas classes para fins de didática e praticidade.

---

## 📁 Estrutura do Projeto
📦 SistemaDeAgendamento
┣ 📁 Screenshots               # Pasta com as imagens do README
┣ 📄 SistemaAgendamentoUI.java       # Interface gráfica e inicialização
┣ 📄 SistemaAgendamento.java         # Lógica de negócio e banco de dados
┣ 📁 model

┃  ┣ 📄 Cliente.java
┃  ┣ 📄 Funcionario.java
┃  ┣ 📄 Servico.java
┃  ┗ 📄 Agendamento.java
---

## ✍️ Autor

**Gustavo Martins Cirino** Estudante de Desenvolvimento de Sistemas • Java & Banco de Dados  
[GitHub Profile] https://github.com/Gustavo-Martins-Cirino  

---

## 📌 Licença

Este projeto é livre para uso educacional e pessoal. Sinta-se à vontade para estudar, adaptar e compartilhar.
