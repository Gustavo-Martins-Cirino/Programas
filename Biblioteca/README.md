# 📚 Sistema de Biblioteca em Java

Sistema de gerenciamento de biblioteca desenvolvido como projeto de treino, utilizando **Java** e integração com banco de dados **MySQL** via **JDBC**.  
Permite o controle completo de livros, alunos e empréstimos, com funcionalidades CRUD e uma interface via console.

---

## 🚀 Funcionalidades

- 📘 Cadastrar, listar, buscar, editar e excluir livros
- 🎓 Cadastrar, listar, buscar e excluir alunos
- 🔄 Registrar e finalizar empréstimos de livros
- 📊 Atualização automática da quantidade de exemplares disponíveis
- 🔐 Geração automática de IDs e validações para evitar duplicidade

---

## 🛠️ Tecnologias Utilizadas

- Java 17  
- JDBC (Java Database Connectivity)  
- MySQL  
- IntelliJ IDEA  
- SQL (DDL e DML)

---

## 🧠 Conceitos Aplicados

- Programação Orientada a Objetos (POO)
- Boas práticas de estruturação de código
- Manipulação de dados com `ArrayList`
- Tratamento de exceções (`try/catch`)
- Interação com usuário via `Scanner`
- Criação de menus e controle de fluxo no console
- Validação de dados de entrada
- Relacionamento entre entidades (Livro, Aluno, Empréstimo)

---

## 📁 Estrutura do Projeto
Biblioteca/
├── Biblioteca.java
│
├── Classes internas:
│   ├── Livro
│   ├── Aluno
│   ├── Emprestimo
│   └── Conexao (classe de conexão com banco de dados)
