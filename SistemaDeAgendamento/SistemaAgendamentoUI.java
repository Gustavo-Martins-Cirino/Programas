package Projetos.Treinos.SistemaDeAgendamento;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.paint.Color;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class SistemaAgendamentoUI extends Application {

    //MODELOS DA UI


    public static class Funcionario {
        private Long idFuncionario;
        private String nomeFuncionario;
        private String especialidade;
        private String telefoneFuncionario;
        private String email;
        private LocalTime horarioTrabalhoInicio;
        private LocalTime horarioTrabalhoFim;
        private String diasDeTrabalho;

        // CONSTRUTOR
        public Funcionario(Long idFuncionario, String nomeFuncionario, String especialidade, String telefoneFuncionario, String email, LocalTime horarioTrabalhoInicio, LocalTime horarioTrabalhoFim, String diasDeTrabalho) {
            this.idFuncionario = idFuncionario;
            this.nomeFuncionario = nomeFuncionario;
            this.especialidade = especialidade;
            this.telefoneFuncionario = telefoneFuncionario;
            this.email = email;
            this.horarioTrabalhoInicio = horarioTrabalhoInicio;
            this.horarioTrabalhoFim = horarioTrabalhoFim;
            this.diasDeTrabalho = diasDeTrabalho;
        }

        // Getters e Setters
        public Long getIdFuncionario() {
            return idFuncionario;
        }

        public void setIdFuncionario(Long idFuncionario) {
            this.idFuncionario = idFuncionario;
        }

        public String getNomeFuncionario() {
            return nomeFuncionario;
        }

        public void setNomeFuncionario(String nomeFuncionario) {
            this.nomeFuncionario = nomeFuncionario;
        }

        public String getEspecialidade() {
            return especialidade;
        }

        public void setEspecialidade(String especialidade) {
            this.especialidade = especialidade;
        }

        public String getTelefoneFuncionario() {
            return telefoneFuncionario;
        }

        public void setTelefoneFuncionario(String telefoneFuncionario) {
            this.telefoneFuncionario = telefoneFuncionario;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalTime getHorarioTrabalhoInicio() {
            return horarioTrabalhoInicio;
        }

        public void setHorarioTrabalhoInicio(LocalTime horarioTrabalhoInicio) {
            this.horarioTrabalhoInicio = horarioTrabalhoInicio;
        }

        public LocalTime getHorarioTrabalhoFim() {
            return horarioTrabalhoFim;
        }

        public void setHorarioTrabalhoFim(LocalTime horarioTrabalhoFim) {
            this.horarioTrabalhoFim = horarioTrabalhoFim;
        }

        public String getDiasDeTrabalho() {
            return diasDeTrabalho;
        }

        public void setDiasDeTrabalho(String diasDeTrabalho) {
            this.diasDeTrabalho = diasDeTrabalho;
        }

        @Override
        public String toString() {
            return nomeFuncionario;
        } 
    }

    public static class Servico {
        private Long idServico;
        private String nomeServico;
        private int duracao;
        private double valorServico;

        // CONSTRUTOR
        public Servico(Long idServico, String nomeServico, int duracao, double valorServico) {
            this.idServico = idServico;
            this.nomeServico = nomeServico;
            this.duracao = duracao;
            this.valorServico = valorServico;
        }

        // Getters e Setters
        public Long getIdServico() {
            return idServico;
        }

        public void setIdServico(Long idServico) {
            this.idServico = idServico;
        }

        public String getNomeServico() {
            return nomeServico;
        }

        public void setNomeServico(String nomeServico) {
            this.nomeServico = nomeServico;
        }

        public int getDuracao() {
            return duracao;
        }

        public void setDuracao(int duracao) {
            this.duracao = duracao;
        }

        public double getValorServico() {
            return valorServico;
        }

        public void setValorServico(double valorServico) {
            this.valorServico = valorServico;
        }

        @Override
        public String toString() {
            return nomeServico;
        }
    }


    //CONEXÃO COM BANCO
    static class ConexaoUI {
        private static final String URL = "jdbc:mysql://localhost:3306/agendamento?" +
                "useSSL=true&" +
                "requireSSL=true&" +
                "serverTimezone=UTC&" +
                "allowPublicKeyRetrieval=true&" +
                "verifyServerCertificate=false";
        private static final String USER = "root";
        private static final String PASSWORD = "@Gucirino01";

        public static Connection conectar() throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                conn.setAutoCommit(false);
                return conn;
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC não encontrado: " + e.getMessage());
            }
        }

        public static void commit(Connection conn) {
            try {
                if (conn != null) conn.commit();
            } catch (SQLException e) {
                System.err.println("Erro no commit: " + e.getMessage());
            }
        }

        public static void rollback(Connection conn) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException e) {
                System.err.println("Erro no rollback: " + e.getMessage());
            }
        }

        public static void fechar(Connection conn, Statement stmt, ResultSet rs) {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }


    // ===== LISTAS E TABELAS DA UI =====
    private ObservableList<SistemaAgendamento.Cliente> clientes = FXCollections.observableArrayList();
    private ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();
    private ObservableList<Servico> servicos = FXCollections.observableArrayList();
    private ObservableList<SistemaAgendamento.Agendamento> agendamento = FXCollections.observableArrayList();
    private TableView<SistemaAgendamento.Cliente> tabelaClientes;
    private TableView<Funcionario> tabelaFuncionarios;
    private TableView<Servico> tabelaServicos;
    private TableView<SistemaAgendamento.Agendamento> tabelaAgendamentos;

    private void carregarDadosDoBanco() {
        clientes.clear();
        funcionarios.clear();
        servicos.clear();
        agendamento.clear();

        // Carregar dados do BD
        clientes.addAll(SistemaAgendamento.consultarTodosClientes().stream()
                .map(c -> new SistemaAgendamento.Cliente(c.getIdCliente(), c.getNomeCliente(), c.getTelefoneCliente()))
                .toList());

        funcionarios.addAll(SistemaAgendamento.consultarTodosFuncionarios().stream()
                .map(f -> new Funcionario(f.getIdFuncionario(), f.getNomeFuncionario(), f.getEspecialidade(),
                        f.getTelefoneFuncionario(), f.getEmail(),
                        f.getHorarioTrabalhoInicio(), f.getHorarioTrabalhoFim(), f.getDiasDeTrabalho()))
                .toList());

        servicos.addAll(SistemaAgendamento.consultarTodosServicos().stream()
                .map(s -> new Servico(s.getIdServico(), s.getNomeServico(), s.getDuracao(), s.getValorServico()))
                .toList());

        agendamento.addAll(SistemaAgendamento.consultarTodosAgendamentos().stream()
                .map(a -> new SistemaAgendamento.Agendamento(a.getIdAgendamento(), a.getIdCliente(), a.getTelefoneCliente(),
                        a.getIdFuncionario(), a.getDataServico(),
                        a.getDataAgendamento(),
                        a.getHorarioReservado(), a.getIdServicoAgendado(), a.getValorServico()))
                .toList());

       
        if (tabelaClientes != null) tabelaClientes.setItems(clientes);
        if (tabelaFuncionarios != null) tabelaFuncionarios.setItems(funcionarios);
        if (tabelaServicos != null) tabelaServicos.setItems(servicos);
        if (tabelaAgendamentos != null) tabelaAgendamentos.setItems(agendamento);
    }


    //MAIN
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mostrarTelaLogin(primaryStage);
    }

    private boolean confirmarAcao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    //LOGIN
    private void mostrarTelaLogin(Stage stage) {
        stage.setTitle(" GustaTech - Sistema de Agendamento");

        // Layout principal
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #667eea 0%, #764ba2 100%);");

        // Logo/título
        Label title = new Label("\uD83D\uDCBB GustaTech");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("Sistema de Agendamento");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setTextFill(Color.WHITESMOKE);

        // Formulário de login
        GridPane loginForm = new GridPane();
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setHgap(15);
        loginForm.setVgap(15);
        loginForm.setMaxWidth(300);

        Label lblUsuario = new Label("👤 Usuário:");
        lblUsuario.setTextFill(Color.WHITE);
        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Digite seu usuário");
        txtUsuario.setStyle("-fx-background-radius: 20; -fx-padding: 8;");

        Label lblSenha = new Label("🔒 Senha:");
        lblSenha.setTextFill(Color.WHITE);
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite sua senha");
        txtSenha.setStyle("-fx-background-radius: 20; -fx-padding: 8;");

        Button btnEntrar = new Button("🚀 Entrar");
        btnEntrar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 20; -fx-padding: 10 20;");
        btnEntrar.setMaxWidth(Double.MAX_VALUE);

        Label lblMensagem = new Label();
        lblMensagem.setTextFill(Color.YELLOW);

        loginForm.add(lblUsuario, 0, 0);
        loginForm.add(txtUsuario, 1, 0);
        loginForm.add(lblSenha, 0, 1);
        loginForm.add(txtSenha, 1, 1);
        loginForm.add(btnEntrar, 0, 2, 2, 1);
        loginForm.add(lblMensagem, 0, 3, 2, 1);

        btnEntrar.setOnAction(e -> {
            if (autenticar(txtUsuario.getText(), txtSenha.getText())) {
                stage.close();
                mostrarTelaPrincipal();
            } else {
                lblMensagem.setText("❌ Usuário ou senha inválidos!");
            }
        });

        mainLayout.getChildren().addAll(title, subtitle, loginForm);

        Scene scene = new Scene(mainLayout, 500, 400);
        stage.setScene(scene);
        stage.show();

    }

    private boolean autenticar(String usuario, String senha) {
        return "gustavo".equals(usuario) && "admin".equals(senha);
    }

    private void mostrarFormularioServico(Servico servico) {
        Stage stage = new Stage();
        stage.setTitle(servico == null ? "Novo Serviço" : "Editar Serviço");

        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 10;");

        TextField txtNome = new TextField(servico != null ? servico.getNomeServico() : "");
        TextField txtDuracao = new TextField(servico != null ? String.valueOf(servico.getDuracao()) : "");
        TextField txtValor = new TextField(servico != null ? String.valueOf(servico.getValorServico()) : "");

        txtNome.setPromptText("Nome do serviço");
        txtDuracao.setPromptText("Duração em minutos");
        txtValor.setPromptText("Valor do serviço");

        Button btnSalvar = criarBotao("💾 Salvar", "#27ae60");
        Button btnCancelar = criarBotao("❌ Cancelar", "#e74c3c");


        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().isEmpty() || txtDuracao.getText().isEmpty() || txtValor.getText().isEmpty()) {
                mostrarAlerta("Erro de Validação", "Todos os campos são obrigatórios.");
                return;
            }

            try {
                int duracao = Integer.parseInt(txtDuracao.getText());
                double valor = Double.parseDouble(txtValor.getText().replace(",", "."));

                SistemaAgendamento.Servico servicoDB = new SistemaAgendamento.Servico(
                        servico == null ? ThreadLocalRandom.current().nextLong(1000, 1000000) : servico.getIdServico(),
                        txtNome.getText(),
                        duracao,
                        valor
                );

                boolean sucesso;
                if (servico == null) {
                    sucesso = SistemaAgendamento.InserirServico(servicoDB);
                } else {
                    sucesso = SistemaAgendamento.atualizarServico(servicoDB);
                }

                if (sucesso) {
                    carregarDadosDoBanco();
                    if (tabelaServicos != null) tabelaServicos.refresh();
                    stage.close();
                } else {
                    mostrarAlerta("Erro de Banco de Dados", "A operação com o serviço falhou.");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro de Formato", "Duração e Valor devem ser números válidos.");
            }
        });

        btnCancelar.setOnAction(e -> stage.close());

        HBox botoes = new HBox(15, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(
                new Label("Nome:"), txtNome,
                new Label("Duração (min):"), txtDuracao,
                new Label("Valor (R$):"), txtValor,
                botoes
        );

        Scene scene = new Scene(vbox, 350, 300);
        stage.setScene(scene);
        stage.show();
    }


    private void atualizarDashboard(Label lblTotalAgendamentos, Label lblTotalClientes, Label lblFaturamento) {
        lblTotalAgendamentos.setText(String.valueOf(agendamento.size()));
        lblTotalClientes.setText(String.valueOf(clientes.size()));

        double faturamentoTotal = agendamento.stream()
                .mapToDouble(SistemaAgendamento.Agendamento::getValorServico)
                .sum();
        lblFaturamento.setText(String.format("R$ %.2f", faturamentoTotal));
    }

    //TELA PRINCIPAL
    private void mostrarTelaPrincipal() {
        Stage stage = new Stage();
        stage.setTitle("\uD83D\uDCBB GustaTech - Sistema de Agendamento");

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-background-color: #f8f9fa;");


        carregarDadosDoBanco();

// Abas
        Tab tabDashboard = new Tab("📊 Dashboard", criarDashboard());
        Tab tabAgendamentos = new Tab("📅 Agendamentos", criarAbaAgendamentos());
        Tab tabClientes = new Tab("👥 Clientes", criarAbaClientes());
        Tab tabFuncionarios = new Tab("💇 Funcionários", criarAbaFuncionarios());
        Tab tabServicos = new Tab("✂️ Serviços", criarAbaServicos());

        tabPane.getTabs().addAll(tabDashboard, tabAgendamentos, tabClientes, tabFuncionarios, tabServicos);

        Scene scene = new Scene(tabPane, 1200, 700);
        stage.setScene(scene);
        stage.show();
    }

    private VBox criarDashboard() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #f4f7f9;");

        Text titulo = new Text("Dashboard de Performance");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setFill(Color.web("#2c3e50"));


        HBox painelMetricas = new HBox(30);
        painelMetricas.setAlignment(Pos.CENTER);


        Label lblTotalAgendamentos = new Label("0");
        Label lblTotalClientes = new Label("0");
        Label lblFaturamento = new Label("R$ 0,00");


        VBox cartaoAgendamentos = criarCartaoMetrica("📅", "Agendamentos Totais", lblTotalAgendamentos, "#3498db");
        VBox cartaoClientes = criarCartaoMetrica("👥", "Total de Clientes", lblTotalClientes, "#2ecc71");
        VBox cartaoFaturamento = criarCartaoMetrica("💰", "Faturamento Total", lblFaturamento, "#e67e22");

        painelMetricas.getChildren().addAll(cartaoAgendamentos, cartaoClientes, cartaoFaturamento);


        atualizarDashboard(lblTotalAgendamentos, lblTotalClientes, lblFaturamento);

        layout.getChildren().addAll(titulo, new Separator(), painelMetricas);
        return layout;
    }


    private VBox criarCartaoMetrica(String icone, String titulo, Label valorLabel, String corIcone) {
        VBox cartao = new VBox(10);
        cartao.setAlignment(Pos.CENTER);
        cartao.setPadding(new Insets(20));
        cartao.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);" // Sombra
        );
        cartao.setMinWidth(200);

        Text iconeText = new Text(icone);
        iconeText.setFont(Font.font(36));
        iconeText.setFill(Color.web(corIcone));

        Text tituloText = new Text(titulo);
        tituloText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        tituloText.setFill(Color.web("#555"));

        valorLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 24));
        valorLabel.setStyle("-fx-text-fill: #333;");

        cartao.getChildren().addAll(iconeText, tituloText, valorLabel);
        return cartao;
    }

    private VBox criarCard(String titulo, String valor, String cor) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: " + cor + "; -fx-background-radius: 15;");
        card.setPrefSize(200, 120);

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        card.getChildren().addAll(lblTitulo, lblValor);
        return card;
    }

    private Button criarBotao(String texto, String cor) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-background-color: " + cor + "; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 15; -fx-padding: 10 20;");
        return btn;
    }

    // ===== CLIENTES =====
    private VBox criarAbaClientes() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titulo = new Label("👥 Gerenciamento de Clientes");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        tabelaClientes = new TableView<>(clientes);
        tabelaClientes.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");

        TableColumn<SistemaAgendamento.Cliente, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("IdCliente"));

        TableColumn<SistemaAgendamento.Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));

        TableColumn<SistemaAgendamento.Cliente, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefoneCliente"));

        tabelaClientes.getColumns().addAll(colId, colNome, colTelefone);
        tabelaClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaClientes.setPrefHeight(400);

        HBox botoes = new HBox(15);
        botoes.setAlignment(Pos.CENTER_LEFT);

        Button btnNovo = criarBotao("➕ Novo Cliente", "#27ae60");
        Button btnEditar = criarBotao("✏️ Editar", "#f39c12");
        Button btnExcluir = criarBotao("🗑️ Excluir", "#e74c3c");
        Button btnAtualizar = criarBotao("🔄 Atualizar", "#3498db");

        btnNovo.setOnAction(e -> mostrarFormularioCliente(null));
        btnEditar.setOnAction(e -> {
            SistemaAgendamento.Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
            if (selecionado != null) mostrarFormularioCliente(selecionado);
        });
        btnExcluir.setOnAction(e -> {
            SistemaAgendamento.Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();

            if (selecionado == null) {
                mostrarAlerta("Nenhum cliente selecionado", "Por favor, selecione um cliente na tabela para excluir.");
                return;
            }


            if (confirmarAcao("Excluir Cliente", "Tem certeza que deseja excluir o cliente " + selecionado.getNomeCliente() + "?")) {


                boolean usadoEmAgendamento = agendamento.stream()
                        .anyMatch(agendamento -> agendamento.getIdCliente().equals(selecionado.getIdCliente()));

                if (usadoEmAgendamento) {
                    mostrarAlerta("Operação não permitida", "Este cliente não pode ser excluído pois possui agendamentos associados a ele.");
                    return;
                }


                if (SistemaAgendamento.excluirCliente(selecionado.getIdCliente())) {


                    clientes.remove(selecionado);
                    tabelaClientes.refresh();

                } else {
                    mostrarAlerta("Erro de Banco de Dados", "Não foi possível excluir o cliente.");
                }
            }
        });
        btnAtualizar.setOnAction(e -> tabelaClientes.refresh());

        botoes.getChildren().addAll(btnNovo, btnEditar, btnExcluir, btnAtualizar);
        vbox.getChildren().addAll(titulo, tabelaClientes, botoes);

        return vbox;
    }

    private void mostrarFormularioCliente(SistemaAgendamento.Cliente cliente) {
        Stage stage = new Stage();
        stage.setTitle(cliente == null ? "Novo Cliente" : "Editar Cliente");

        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: rgba(170,170,170,0.15); -fx-background-radius: 10;");

        TextField txtNome = new TextField(cliente != null ? cliente.getNomeCliente() : "");
        TextField txtTelefone = new TextField(cliente != null ? cliente.getTelefoneCliente() : "");

        txtNome.setPromptText("Nome completo");
        txtTelefone.setPromptText("Telefone");

        Button btnSalvar = criarBotao("💾 Salvar", "#27ae60");
        Button btnCancelar = criarBotao("❌ Cancelar", "#e74c3c");


        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().isEmpty() || txtTelefone.getText().isEmpty()) {
                mostrarAlerta("Erro de Validação", "Todos os campos são obrigatórios.");
                return;
            }

            SistemaAgendamento.Cliente clienteDB = new SistemaAgendamento.Cliente(
                    cliente == null ? ThreadLocalRandom.current().nextLong(1000, 1000000) : cliente.getIdCliente(),
                    txtNome.getText(),
                    txtTelefone.getText()
            );

            boolean sucesso;
            if (cliente == null) {
                sucesso = SistemaAgendamento.inserirCliente(clienteDB);
            } else {
                sucesso = SistemaAgendamento.atualizarClienteNoBanco(clienteDB);
            }

            if (sucesso) {
                carregarDadosDoBanco();
                if (tabelaClientes != null) tabelaClientes.refresh();

                stage.close();
            } else {
                mostrarAlerta("Erro de Banco de Dados", "A operação com o cliente falhou.");
            }
        });

        btnCancelar.setOnAction(e -> stage.close());

        HBox botoes = new HBox(15, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(
                new Label("Nome:"), txtNome,
                new Label("Telefone:"), txtTelefone,
                botoes
        );

        Scene scene = new Scene(vbox, 350, 250);
        stage.setScene(scene);
        stage.show();
    }

    // ===== FUNCIONÁRIOS =====
    private VBox criarAbaFuncionarios() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titulo = new Label("💇 Gerenciamento de Funcionários");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        tabelaFuncionarios = new TableView<>(funcionarios);

        tabelaFuncionarios.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");

        TableColumn<Funcionario, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idFuncionario"));

        TableColumn<Funcionario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));

        TableColumn<Funcionario, String> colEspecialidade = new TableColumn<>("Especialidade");
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        TableColumn<Funcionario, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefoneFuncionario"));

        TableColumn<Funcionario, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Funcionario, String> colInicio = new TableColumn<>("Início");
        colInicio.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getHorarioTrabalhoInicio().toString()));

        TableColumn<Funcionario, String> colFim = new TableColumn<>("Fim");
        colFim.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getHorarioTrabalhoFim().toString()));

        TableColumn<Funcionario, String> colDias = new TableColumn<>("Dias");
        colDias.setCellValueFactory(new PropertyValueFactory<>("diasDeTrabalho"));

        tabelaFuncionarios.getColumns().addAll(colId, colNome, colEspecialidade, colTelefone, colEmail, colInicio, colFim, colDias);
        tabelaFuncionarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaFuncionarios.setPrefHeight(400);

        HBox botoes = new HBox(15);
        botoes.setAlignment(Pos.CENTER_LEFT);

        Button btnNovo = criarBotao("➕ Novo Funcionário", "#27ae60");
        Button btnEditar = criarBotao("✏️ Editar", "#f39c12");
        Button btnExcluir = criarBotao("🗑️ Excluir", "#e74c3c");
        Button btnAtualizar = criarBotao("🔄 Atualizar", "#3498db");

        btnNovo.setOnAction(e -> mostrarFormularioFuncionario(null, tabelaFuncionarios));
        btnEditar.setOnAction(e -> {
            Funcionario selecionado = tabelaFuncionarios.getSelectionModel().getSelectedItem();
            if (selecionado != null) mostrarFormularioFuncionario(selecionado, tabelaFuncionarios);
        });
        btnExcluir.setOnAction(e -> {
            Funcionario selecionado = tabelaFuncionarios.getSelectionModel().getSelectedItem();

            if (selecionado == null) {
                mostrarAlerta("Nenhum funcionário selecionado", "Por favor, selecione um funcionário na tabela para excluir.");
                return;
            }


            if (confirmarAcao("Excluir Funcionário", "Tem certeza que deseja excluir o funcionário " + selecionado.getNomeFuncionario() + "?")) {

                boolean usadoEmAgendamento = agendamento.stream()
                        .anyMatch(agendamento -> agendamento.getIdFuncionario().equals(selecionado.getIdFuncionario()));

                if (usadoEmAgendamento) {
                    mostrarAlerta("Operação não permitida", "Este funcionário não pode ser excluído pois possui agendamentos associados a ele.");
                    return;
                }


                if (SistemaAgendamento.excluirFuncionario(selecionado.getIdFuncionario())) {


                    funcionarios.remove(selecionado);
                    tabelaFuncionarios.refresh();

                } else {
                    mostrarAlerta("Erro de Banco de Dados", "Não foi possível excluir o funcionário.");
                }
            }
        });
        btnAtualizar.setOnAction(e -> tabelaFuncionarios.refresh());

        botoes.getChildren().addAll(btnNovo, btnEditar, btnExcluir, btnAtualizar);
        vbox.getChildren().addAll(titulo, tabelaFuncionarios, botoes);

        return vbox;
    }

    private void mostrarFormularioFuncionario(Funcionario f, TableView<Funcionario> tabela) {
        Stage stage = new Stage();
        VBox v = new VBox(10);
        v.setPadding(new Insets(15));

        TextField txtId = new TextField();
        txtId.setDisable(true);
        TextField txtNome = new TextField();
        TextField txtEsp = new TextField();
        TextField txtTel = new TextField();
        TextField txtEmail = new TextField();
        TextField txtInicio = new TextField();
        txtInicio.setPromptText("HH:mm");
        TextField txtFim = new TextField();
        txtFim.setPromptText("HH:mm");
        TextField txtDias = new TextField();
        txtDias.setPromptText("SEG,TER,QUA,QUI,SEX");

        DateTimeFormatter hm = DateTimeFormatter.ofPattern("HH:mm");

        if (f != null) {
            txtId.setText(f.getIdFuncionario().toString());
            txtNome.setText(f.getNomeFuncionario());
            txtEsp.setText(f.getEspecialidade());
            txtTel.setText(f.getTelefoneFuncionario());
            txtEmail.setText(f.getEmail());
            txtInicio.setText(f.getHorarioTrabalhoInicio().format(hm));
            txtFim.setText(f.getHorarioTrabalhoFim().format(hm));
            txtDias.setText(f.getDiasDeTrabalho());
        } else {
            txtId.setText(gerarNovoId(funcionarios.stream().map(Funcionario::getIdFuncionario).toArray(Long[]::new)).toString());
            txtInicio.setText("09:00");
            txtFim.setText("18:00");
            txtDias.setText("SEG,TER,QUA,QUI,SEX");
        }

        Button btnSalvar = new Button("Salvar");

        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().isEmpty() || txtEsp.getText().isEmpty() || txtInicio.getText().isEmpty() || txtFim.getText().isEmpty() || txtDias.getText().isEmpty()) {
                mostrarAlerta("Erro de Validação", "Todos os campos são obrigatórios.");
                return;
            }

            try {
                LocalTime inicio = LocalTime.parse(txtInicio.getText());
                LocalTime fim = LocalTime.parse(txtFim.getText());
                String dias = txtDias.getText();

                SistemaAgendamento.Funcionario funcionarioDB = new SistemaAgendamento.Funcionario(
                        f == null ? ThreadLocalRandom.current().nextLong(1000, 1000000) : f.getIdFuncionario(),
                        txtNome.getText(),
                        txtEsp.getText(),
                        txtTel.getText(),
                        txtEmail.getText(),
                        inicio,
                        fim,
                        dias
                );

                boolean sucesso;
                if (f == null) {
                    sucesso = SistemaAgendamento.cadastrarFuncionario(funcionarioDB);
                } else {
                    sucesso = SistemaAgendamento.atualizarFuncionario(funcionarioDB);
                }

                if (sucesso) {
                    carregarDadosDoBanco();
                    if (tabelaFuncionarios != null) tabelaFuncionarios.refresh();
                    stage.close();
                } else {
                    mostrarAlerta("Erro de Banco de Dados", "A operação com o funcionário falhou.");
                }
            } catch (DateTimeParseException ex) {
                mostrarAlerta("Erro de Formato", "O formato da hora está inválido. Use HH:MM.");
            }
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());

        v.getChildren().addAll(new Label("ID:"), txtId, new Label("Nome:"), txtNome, new Label("Especialidade:"), txtEsp,
                new Label("Telefone:"), txtTel, new Label("Email:"), txtEmail, new Label("Início (HH:mm):"), txtInicio,
                new Label("Fim (HH:mm):"), txtFim, new Label("Dias de Trabalho:"), txtDias, new HBox(10, btnSalvar, btnCancelar));

        stage.setScene(new Scene(v, 360, 520));
        stage.setTitle(f == null ? "Novo Funcionário" : "Editar Funcionário");
        stage.show();
    }

    // ===== SERVIÇOS =====
    private VBox criarAbaServicos() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titulo = new Label("✂️ Gerenciamento de Serviços");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        tabelaServicos = new TableView<>(servicos);

        tabelaServicos.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");

        TableColumn<Servico, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idServico"));

        TableColumn<Servico, String> colNome = new TableColumn<>("Serviço");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeServico"));

        TableColumn<Servico, Integer> colDuracao = new TableColumn<>("Duração (min)");
        colDuracao.setCellValueFactory(new PropertyValueFactory<>("duracao"));

        TableColumn<Servico, Double> colValor = new TableColumn<>("Valor (R$)");
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorServico"));

        tabelaServicos.getColumns().addAll(colId, colNome, colDuracao, colValor);
        tabelaServicos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaServicos.setPrefHeight(400);

        HBox botoes = new HBox(15);
        botoes.setAlignment(Pos.CENTER_LEFT);

        Button btnNovo = criarBotao("➕ Novo Serviço", "#27ae60");
        Button btnEditar = criarBotao("✏️ Editar", "#f39c12");
        Button btnExcluir = criarBotao("🗑️ Excluir", "#e74c3c");
        Button btnAtualizar = criarBotao("🔄 Atualizar", "#3498db");

        btnNovo.setOnAction(e -> mostrarFormularioServico(null));

        btnEditar.setOnAction(e -> {
            Servico selecionado = tabelaServicos.getSelectionModel().getSelectedItem();
            if (selecionado != null) mostrarFormularioServico(selecionado);
        });

        btnExcluir.setOnAction(e -> {
            Servico selecionado = tabelaServicos.getSelectionModel().getSelectedItem();

            if (selecionado == null) {
                mostrarAlerta("Nenhum serviço selecionado", "Por favor, selecione um serviço na tabela para excluir.");
                return;
            }


            if (confirmarAcao("Excluir Serviço", "Tem certeza que deseja excluir o serviço " + selecionado.getNomeServico() + "?")) {

                boolean usadoEmAgendamento = agendamento.stream()
                        .anyMatch(agendamento -> agendamento.getIdServicoAgendado().equals(selecionado.getIdServico()));

                if (usadoEmAgendamento) {
                    mostrarAlerta("Operação não permitida", "Este serviço não pode ser excluído pois possui agendamentos associados a ele.");
                    return;
                }


                if (SistemaAgendamento.excluirServico(selecionado.getIdServico())) {


                    servicos.remove(selecionado);
                    tabelaServicos.refresh();

                } else {
                    mostrarAlerta("Erro de Banco de Dados", "Não foi possível excluir o serviço.");
                }
            }
        });
        btnAtualizar.setOnAction(e -> tabelaServicos.refresh());

        botoes.getChildren().addAll(btnNovo, btnEditar, btnExcluir, btnAtualizar);
        vbox.getChildren().addAll(titulo, tabelaServicos, botoes);

        return vbox;
    }


    // ===== AGENDAMENTOS =====

    private VBox criarAbaAgendamentos() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titulo = new Label("📅 Gerenciamento de Agendamentos");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        tabelaAgendamentos = new TableView<>(agendamento);

        tabelaAgendamentos.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");

        TableColumn<SistemaAgendamento.Agendamento, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("IdAgendamento"));

        TableColumn<SistemaAgendamento.Agendamento, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(cd -> {
            Long id = cd.getValue().getIdCliente();
            return new SimpleStringProperty(clientes.stream().filter(c -> c.getIdCliente().equals(id)).findFirst().map(SistemaAgendamento.Cliente::getNomeCliente).orElse("Desconhecido"));
        });

        TableColumn<SistemaAgendamento.Agendamento, String> colFunc = new TableColumn<>("Funcionário");
        colFunc.setCellValueFactory(cd -> {
            Long id = cd.getValue().getIdFuncionario();
            return new SimpleStringProperty(funcionarios.stream().filter(f -> f.getIdFuncionario().equals(id)).findFirst().map(Funcionario::getNomeFuncionario).orElse("?"));
        });

        TableColumn<SistemaAgendamento.Agendamento, String> colServico = new TableColumn<>("Serviço");
        colServico.setCellValueFactory(cd -> {
            Long id = cd.getValue().getIdServicoAgendado();
            return new SimpleStringProperty(servicos.stream().filter(s -> s.getIdServico().equals(id)).findFirst().map(Servico::getNomeServico).orElse("?"));
        });

        TableColumn<SistemaAgendamento.Agendamento, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataServico"));

        TableColumn<SistemaAgendamento.Agendamento, String> colHora = new TableColumn<>("Hora");
        colHora.setCellValueFactory(new PropertyValueFactory<>("horarioReservado"));

        TableColumn<SistemaAgendamento.Agendamento, String> colTel = new TableColumn<>("Telefone");
        colTel.setCellValueFactory(new PropertyValueFactory<>("telefoneCliente"));

        TableColumn<SistemaAgendamento.Agendamento, String> colRegistro = new TableColumn<>("Registrado em");
        colRegistro.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getDataAgendamento().toString()));

        TableColumn<SistemaAgendamento.Agendamento, String> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(a -> new SimpleStringProperty(String.format("R$ %.2f", a.getValue().getValorServico())));

        tabelaAgendamentos.getColumns().addAll(colId, colCliente, colFunc, colServico, colData, colHora, colTel, colRegistro, colValor);
        tabelaAgendamentos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaAgendamentos.setPrefHeight(400);

        HBox botoes = new HBox(15);
        botoes.setAlignment(Pos.CENTER_LEFT);

        Button btnNovo = criarBotao("➕ Novo Agendamento", "#27ae60");
        Button btnEditar = criarBotao("✏️ Editar", "#f39c12");
        Button btnExcluir = criarBotao("🗑️ Excluir", "#e74c3c");
        Button btnAtualizar = criarBotao("🔄 Atualizar", "#3498db");

        btnNovo.setOnAction(e -> mostrarFormularioAgendamento(null));
        btnEditar.setOnAction(e -> {
            SistemaAgendamento.Agendamento selecionado = tabelaAgendamentos.getSelectionModel().getSelectedItem();
            if (selecionado != null) mostrarFormularioAgendamento(selecionado);
        });
        btnExcluir.setOnAction(e -> {
            SistemaAgendamento.Agendamento selecionado = tabelaAgendamentos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                mostrarAlerta("Nenhum agendamento selecionado", "Por favor, selecione um agendamento na tabela para excluir.");
                return;
            }


            if (confirmarAcao("Excluir Agendamento", "Tem certeza que deseja excluir o agendamento " + selecionado.getIdAgendamento() + "?")) {
                if (SistemaAgendamento.excluirAgendamento(selecionado.getIdAgendamento())) {
                    agendamento.remove(selecionado);
                    tabelaAgendamentos.refresh();

                } else {
                    mostrarAlerta("Erro de Banco de Dados", "Não foi possível excluir o agendamento.");
                }
            }
        });

        btnAtualizar.setOnAction(e -> tabelaAgendamentos.refresh());

        botoes.getChildren().

                addAll(btnNovo, btnEditar, btnExcluir, btnAtualizar);
        vbox.getChildren().

                addAll(titulo, tabelaAgendamentos, botoes);

        return vbox;
    }

    private void mostrarFormularioAgendamento(SistemaAgendamento.Agendamento a) {
        Stage stage = new Stage();
        stage.setTitle(agendamento == null ? "Novo Agendamento" : "Editar Agendamento");

        VBox v = new VBox(10);
        v.setPadding(new Insets(20));
        v.setStyle("-fx-background-color: rgba(170,170,170,0.15); -fx-background-radius: 10;");

        ComboBox<SistemaAgendamento.Cliente> cbCliente = new ComboBox<>(clientes);
        cbCliente.setPromptText("Selecione o Cliente");

        ComboBox<Funcionario> cbFuncionario = new ComboBox<>(funcionarios);
        cbFuncionario.setPromptText("Selecione o Funcionário");

        ComboBox<Servico> cbServico = new ComboBox<>(servicos);
        cbServico.setPromptText("Selecione o Serviço");


        DatePicker dpData = new DatePicker();

        TextField txtValor = new TextField();
        ComboBox<String> cbHora = new ComboBox<>();
        cbHora.setEditable(true);
        cbHora.setPromptText("HH:mm");


        for (int h = 7; h <= 21; h++) {
            for (int m = 0; m < 60; m += 30) {
                cbHora.getItems().add(String.format("%02d:%02d", h, m));
            }
        }
        TextField txtValorAgendamento = new TextField();

        if (a != null) {
            cbCliente.setValue(clientes.stream()
                    .filter(c -> c.getIdCliente().equals(a.getIdCliente()))
                    .findFirst().orElse(null));
            cbFuncionario.setValue(funcionarios.stream()
                    .filter(f -> f.getIdFuncionario().equals(a.getIdFuncionario()))
                    .findFirst().orElse(null));
            cbServico.setValue(servicos.stream()
                    .filter(s -> s.getIdServico().equals(a.getIdServicoAgendado()))
                    .findFirst().orElse(null));
            cbHora.setValue(a.getHorarioReservado());
            dpData.setValue(a.getDataAgendamento().toLocalDate());
            txtValor.setText(String.valueOf(a.getValorServico()));
        }


        txtValor.setEditable(true);
        txtValor.setDisable(false);


        dpData.setEditable(true);
        dpData.setDisable(false);

        cbCliente.setEditable(false);
        cbFuncionario.setEditable(false);
        cbServico.setEditable(false);

        java.util.function.UnaryOperator<javafx.scene.control.TextFormatter.Change> filtroHora = change -> {
            String novo = change.getControlNewText();
            if (novo.matches("([01]?\\d|2[0-3])?(:[0-5]?\\d?)?") || novo.isEmpty()) {
                return change;
            }
            return null;
        };


        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> stage.close());


        btnSalvar.setOnAction(e -> {

            SistemaAgendamento.Cliente clienteSelecionado = cbCliente.getValue();
            Funcionario funcionarioSelecionado = cbFuncionario.getValue();
            Servico servicoSelecionado = cbServico.getValue();
            LocalDate dataSelecionada = dpData.getValue();
            String horaSelecionada = cbHora.getValue();

            if (clienteSelecionado == null || funcionarioSelecionado == null || servicoSelecionado == null || dataSelecionada == null || horaSelecionada == null) {
                mostrarAlerta("Erro de Validação", "Todos os campos são obrigatórios.");
                return;
            }

            SistemaAgendamento.Agendamento agendamentoDB = new SistemaAgendamento.Agendamento(
                    ThreadLocalRandom.current().nextLong(1000, 1000000),
                    clienteSelecionado.getIdCliente(),
                    clienteSelecionado.getTelefoneCliente(),
                    funcionarioSelecionado.getIdFuncionario(),
                    dataSelecionada.toString(),
                    java.sql.Date.valueOf(dataSelecionada),
                    horaSelecionada,
                    servicoSelecionado.getIdServico(),
                    servicoSelecionado.getValorServico()
            );

           
            if (SistemaAgendamento.InserirAgendamento(agendamentoDB)) {
                carregarDadosDoBanco();
                stage.close();
            } else {
                mostrarAlerta("Erro de Banco de Dados", "Não foi possível salvar o agendamento.");
            }
        });

        v.getChildren().addAll(new Label("Cliente:"), cbCliente, new Label("Funcionário:"), cbFuncionario,
                new Label("Data:"), dpData, new Label("Hora:"), cbHora,
                new Label("Serviço:"), cbServico, new Label("Valor (R$):"), txtValor, new HBox(10, btnSalvar, btnCancelar));

        stage.setScene(new Scene(v, 420, 600));
        stage.setTitle("Novo Agendamento");
        stage.show();
    }

    private void preencherHoras(ComboBox<String> cbHora, Funcionario f) {
        cbHora.getItems().clear();
        if (f == null) return;

        LocalTime t = f.getHorarioTrabalhoInicio();
        while (!t.isAfter(f.getHorarioTrabalhoFim().minusMinutes(30))) {
            cbHora.getItems().add(t.format(DateTimeFormatter.ofPattern("HH:mm")));
            t = t.plusMinutes(30);
        }
    }

    // ===== UTIL =====
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private boolean confirmar(String titulo, String conteudo) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, conteudo, ButtonType.OK, ButtonType.CANCEL);
        a.setTitle(titulo);
        a.setHeaderText(null);
        return a.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private Long gerarNovoId(Long[] existentes) {
        long max = 0;
        for (Long v : existentes) if (v != null && v > max) max = v;
        return max + 1;
    }

}
