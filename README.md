# 🚀 Sistema de Agendamento Full Stack

![Demonstração do Sistema](https://www.linkedin.com/posts/gustavo-mcirino_java-springboot-javascript-activity-7383224688967249920-JvxP?utm_source=share&utm_medium=member_desktop&rcm=ACoAAEkgP6MBeoNbjaja8Uuaub6FZHJFwiQLARs)


## 📋 Sobre o Projeto

Este é um projeto **Full Stack** completo de um sistema de agendamento, construído do zero para ser uma solução robusta e moderna para a gestão de negócios baseados em serviços. A aplicação permite o gerenciamento completo de clientes, funcionários, serviços e agendamentos, com foco em uma interface de usuário rica e em regras de negócio inteligentes no backend para garantir a integridade da agenda.

O projeto foi desenvolvido como uma demonstração prática de habilidades na construção de aplicações web coesas, desde a modelagem do banco de dados relacional até a criação de uma interface de usuário interativa e responsiva.

---

## ✨ Funcionalidades Principais

* **Dashboard Interativo:** Uma visão geral do negócio com métricas essenciais e um painel de ganhos que pode ser filtrado em tempo real por **dia, semana e mês**.
* **Calendário Visual:** Utilizando a biblioteca FullCalendar, todos os agendamentos são exibidos em uma interface de calendário intuitiva, facilitando a visualização da agenda diária e semanal.
* **Motor de Agendamento Inteligente:** A funcionalidade mais crítica do sistema. O backend valida e **previne conflitos de horário**, considerando não apenas o horário de início, mas também a **duração de cada serviço** para evitar sobreposições. A validação também respeita os dias e horários de trabalho de cada funcionário.
* **Gestão Completa (CRUD):** Interface com modais para criar, visualizar, editar e deletar todas as entidades do sistema (Clientes, Funcionários, Serviços e Agendamentos) de forma eficiente e sem recarregar a página.
* **Interface de Usuário Moderna:**
    * Notificações "Toast" para feedback de ações (sucesso, erro).
    * Modais de confirmação para ações destrutivas (exclusão).
    * Animações de carregamento ("skeleton loading") para uma experiência mais fluida.
    * Efeitos de "hover" e transições suaves para uma maior interatividade.

---

## 💻 Stack de Tecnologias

Este projeto foi construído utilizando uma stack moderna e robusta, separando claramente as responsabilidades entre o backend e o frontend.

#### **Backend**
* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA** & **Hibernate:** Para persistência de dados e mapeamento objeto-relacional.
* **Maven:** Para gerenciamento de dependências.

#### **Frontend**
* **HTML5**
* **Tailwind CSS:** Para estilização moderna e responsiva.
* **JavaScript (Vanilla JS):** Para toda a lógica de interatividade, manipulação do DOM e comunicação com a API.
* **FullCalendar.js:** Para a renderização do calendário.
* **Toastify.js** & **SweetAlert2:** Para notificações e modais.

#### **Banco de Dados**
* **MySQL:** Sistema de gerenciamento de banco de dados relacional.
* **SQL Workbench:** Ferramenta utilizada para modelagem e administração do banco de dados.

#### **Arquitetura**
* **API RESTful:** Para uma comunicação padronizada e eficiente entre o frontend e o backend.

---

## ⚙️ Como Executar o Projeto Localmente

Para executar este projeto em seu ambiente local, siga os passos abaixo.

### **Pré-requisitos**
* **JDK 17** ou superior.
* **Maven 3.8** ou superior.
* **MySQL Server 8.0** ou superior.
* Uma IDE Java, como **IntelliJ IDEA** ou **VS Code**.
* **MySQL Workbench** (ou outra ferramenta de sua preferência para gerenciar o banco).

### **1. Backend (Servidor Java)**
1.  Clone este repositório:
    ```bash
    git clone https://github.com/Gustavo-Martins-Cirino/Programas/tree/Main-Branch/Main%20-%20SistemaAgendamento
    ```
2.  Abra o projeto na sua IDE.
3.  Crie um banco de dados no seu MySQL chamado `sistema_agendamento` (ou o nome que preferir).
4.  Configure a conexão com o banco de dados no arquivo `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/sistema_agendamento
    spring.datasource.username=SEU_USUARIO_MYSQL
    spring.datasource.password=SUA_SENHA_MYSQL
    spring.jpa.hibernate.ddl-auto=update
    ```
5.  Execute a classe principal `SistemaDeAgendamentoApplication.java`. O servidor estará rodando em `http://localhost:8080`.

### **2. Frontend (Interface Web)**
1.  Após iniciar o backend, simplesmente abra o arquivo `interface.html` em seu navegador de preferência (Google Chrome, Firefox, etc.).

A aplicação estará totalmente funcional!

---

## ✒️ Autor

Projeto desenvolvido por **[Gustavo Martins Cirino]**.

