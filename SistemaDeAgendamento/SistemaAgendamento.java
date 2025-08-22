package Projetos.Treinos.SistemaDeAgendamento;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class SistemaAgendamento {
    static class Cliente {
        Long IdCliente;
        String NomeCliente;
        String TelefoneCliente;


        protected Cliente(Long idCliente, String nome, String telefoneCliente) {
            this.IdCliente = idCliente;
            this.NomeCliente = nome;
            this.TelefoneCliente = telefoneCliente;
        }

        public Long getIdCliente() {
            return IdCliente;
        }

        public void setIdCliente(Long idCliente) {
            IdCliente = idCliente;
        }

        public String getNomeCliente() {
            return NomeCliente;
        }

        public void setNomeCliente(String nomeCliente) {
            this.NomeCliente = nomeCliente;
        }

        public String getTelefoneCliente() {
            return TelefoneCliente;
        }

        public void setTelefoneCliente(String telefoneCliente) {
            this.TelefoneCliente = telefoneCliente;
        }

        @Override
        public String toString() {
            return "ID: " + IdCliente + ", Nome: " + NomeCliente + ", Telefone: " + TelefoneCliente;
        }
    }

    static class Funcionario {
        Long IdFuncionario;
        String NomeFuncionario;
        String especialidade;
        String TelefoneFuncionario;
        String email;
        LocalTime HorarioTrabalhoInicio;
        LocalTime HorarioTrabalhoFim;
        String DiasDeTrabalho;


        public Funcionario(Long idFuncionario, String nomeFuncionario, String especialidade, String telefone, String email, LocalTime horarioTrabalhoInicio, LocalTime horarioTrabalhoFim, String diasDeTrabalho) {
            this.IdFuncionario = idFuncionario;
            this.NomeFuncionario = nomeFuncionario;
            this.especialidade = especialidade;
            this.TelefoneFuncionario = telefone;
            this.email = email;
            this.HorarioTrabalhoInicio = horarioTrabalhoInicio;
            this.HorarioTrabalhoFim = horarioTrabalhoFim;
            this.DiasDeTrabalho = diasDeTrabalho;
        }

        public Long getIdFuncionario() {
            return IdFuncionario;
        }

        public void setIdFuncionario(Long idFuncionario) {
            IdFuncionario = idFuncionario;
        }

        public String getNomeFuncionario() {
            return NomeFuncionario;
        }

        public void setNomeFuncionario(String nomeFuncionario) {
            NomeFuncionario = nomeFuncionario;
        }

        public String getEspecialidade() {
            return especialidade;
        }

        public void setEspecialidade(String especialidade) {
            this.especialidade = especialidade;
        }

        public String getTelefoneFuncionario() {
            return TelefoneFuncionario;
        }

        public void setTelefoneFuncionario(String telefoneFuncionario) {
            this.TelefoneFuncionario = telefoneFuncionario;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalTime getHorarioTrabalhoInicio() {
            return HorarioTrabalhoInicio;
        }

        public void setHorarioTrabalhoInicio(LocalTime horarioTrabalhoInicio) {
            HorarioTrabalhoInicio = horarioTrabalhoInicio;
        }

        public LocalTime getHorarioTrabalhoFim() {
            return HorarioTrabalhoFim;
        }

        public void setHorarioTrabalhoFim(LocalTime horarioTrabalhoFim) {
            HorarioTrabalhoFim = horarioTrabalhoFim;
        }

        public String getDiasDeTrabalho() {
            return DiasDeTrabalho;
        }

        public void setDiasDeTrabalho(String diasDeTrabalho) {
            DiasDeTrabalho = diasDeTrabalho;
        }

        @Override
        public String toString() {
            return "ID: " + IdFuncionario + ", Nome: " + NomeFuncionario + ", Especialidade: " + especialidade +
                    ", Telefone: " + TelefoneFuncionario + ", Email: " + email +
                    ", Início de Expediente: " + HorarioTrabalhoInicio + ", Fim de Expediente: " + HorarioTrabalhoFim +
                    ", Dias Disponíveis: " + DiasDeTrabalho;
        }
    }

    static class Servico {
        Long IdServico;
        String NomeServico;
        int duracao;
        Double ValorServico;

        public Servico(Long idServico, String nomeServico, int duracao, Double valorServico) {
            this.IdServico = idServico;
            this.NomeServico = nomeServico;
            this.duracao = duracao;
            this.ValorServico = valorServico;
        }

        public Long getIdServico() {
            return IdServico;
        }

        public void setIdServico(Long idServico) {
            IdServico = idServico;
        }

        public String getNomeServico() {
            return NomeServico;
        }

        public void setNomeServico(String nomeServico) {
            NomeServico = nomeServico;
        }

        public int getDuracao() {
            return duracao;
        }

        public void setDuracao(int duracao) {
            this.duracao = duracao;
        }

        public Double getValorServico() {
            return ValorServico;
        }

        public void setValorServico(Double valorServico) {
            ValorServico = valorServico;
        }

        @Override
        public String toString() {
            return "ID: " + IdServico + ", Serviço: " + NomeServico + ", Duração: " + duracao +
                    ", Valor: R$" + ValorServico;
        }
    }

    public static void testarConexao() {
        try (Connection conn = Conexao.conectar()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅- Conexão com o banco de dados estabelecida com sucesso!");
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.err.println("❌- Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    static class Conexao {
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

    static class DBUtils {
        public static boolean executarUpdate(String sql, Object... params) {
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = Conexao.conectar();
                stmt = conn.prepareStatement(sql);

                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }

                int rowsAffected = stmt.executeUpdate();
                Conexao.commit(conn);
                return rowsAffected > 0;
            } catch (SQLException e) {
                Conexao.rollback(conn);
                System.err.println("Erro na operação: " + e.getMessage());
                return false;
            } finally {
                Conexao.fechar(conn, stmt, null);
            }
        }

        public static <T> T executarQuery(String sql, Function<ResultSet, T> handler, Object... params) {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = Conexao.conectar();
                stmt = conn.prepareStatement(sql);

                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }

                rs = stmt.executeQuery();
                return handler.apply(rs);
            } catch (SQLException e) {
                System.err.println("Erro na consulta: " + e.getMessage());
                return null;
            } finally {
                Conexao.fechar(conn, stmt, rs);
            }
        }
    }

    public static Long lerLong(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                Long value = scanner.nextLong();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("❌- Entrada inválida. Por favor, digite um número inteiro longo.");
                scanner.nextLine();
            }
        }
    }

    public static LocalTime lerLocalTime(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                String entrada = scanner.nextLine();
                return LocalTime.parse(entrada);
            } catch (DateTimeParseException e) {
                System.out.println("❌- Entrada inválida. Por favor, digite um horário no formato HH:mm.");
            }
        }
    }

    public static int lerInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("❌- Entrada inválida. Por favor, digite um número inteiro.");
                scanner.nextLine();
            }
        }
    }

    public static double lerDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("❌- Entrada inválida. Por favor, digite um número decimal.");
                scanner.nextLine();
            }
        }
    }

    public static String lerString(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }


    public static boolean cadastrarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (IdFuncionario, NomeFuncionario, especialidade, TelefoneFuncionario, " +
                "email, HorarioTrabalhoInicio, HorarioTrabalhoFim, DiasDeTrabalho) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            return DBUtils.executarUpdate(sql,
                    funcionario.getIdFuncionario(),
                    funcionario.getNomeFuncionario(),
                    funcionario.getEspecialidade(),
                    funcionario.getTelefoneFuncionario(),
                    funcionario.getEmail(),
                    Time.valueOf(funcionario.getHorarioTrabalhoInicio()),
                    Time.valueOf(funcionario.getHorarioTrabalhoFim()),
                    funcionario.getDiasDeTrabalho());
        } catch (Exception e) {
            System.err.println("❌- Erro ao cadastrar funcionário: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Funcionario> consultarTodosFuncionarios() {
        String sql = "SELECT IdFuncionario, NomeFuncionario, especialidade, TelefoneFuncionario, " +
                "email, HorarioTrabalhoInicio, HorarioTrabalhoFim, DiasDeTrabalho FROM Funcionario";

        return DBUtils.executarQuery(sql, rs -> {
            ArrayList<Funcionario> funcionarios = new ArrayList<>();
            try {
                while (rs.next()) {
                    Funcionario f = new Funcionario(
                            rs.getLong("IdFuncionario"),
                            rs.getString("NomeFuncionario"),
                            rs.getString("especialidade"),
                            rs.getString("TelefoneFuncionario"),
                            rs.getString("email"),
                            rs.getTime("HorarioTrabalhoInicio").toLocalTime(),
                            rs.getTime("HorarioTrabalhoFim").toLocalTime(),
                            rs.getString("DiasDeTrabalho")
                    );
                    funcionarios.add(f);
                }
            } catch (SQLException e) {
                System.err.println("❌- Erro ao processar resultados: " + e.getMessage());
                return new ArrayList<>();
            }
            return funcionarios;
        });
    }

    public static Funcionario consultarFuncionarioPorId(Long IdFuncionario) {
        String sql = "SELECT IdFuncionario, NomeFuncionario, especialidade, TelefoneFuncionario, email, HorarioTrabalhoInicio, HorarioTrabalhoFim, DiasDeTrabalho FROM Funcionario WHERE IdFuncionario = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, IdFuncionario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                            rs.getLong("IdFuncionario"),
                            rs.getString("NomeFuncionario"),
                            rs.getString("especialidade"),
                            rs.getString("TelefoneFuncionario"),
                            rs.getString("email"),
                            rs.getTime("HorarioTrabalhoInicio").toLocalTime(),
                            rs.getTime("HorarioTrabalhoFim").toLocalTime(),
                            rs.getString("DiasDeTrabalho")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("❌- Erro ao consultar funcionário por Id no banco de dados: " + e.getMessage());
        }
        return null;
    }
    public static boolean agendamento(Agendamento agendamento) {
        String sql = "INSERT INTO agendamentos (id_agendamento, id_cliente, telefone_cliente, id_funcionario, data_servico, data_agendamento, horario_reservado, id_servico_agendado, valor_servico) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SistemaAgendamentoUI.ConexaoUI.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, agendamento.getIdAgendamento());
            pstmt.setLong(2, agendamento.getIdCliente());
            pstmt.setString(3, agendamento.getTelefoneCliente());
            pstmt.setLong(4, agendamento.getIdFuncionario());
            pstmt.setString(5, agendamento.getDataServico());
            pstmt.setDate(6, agendamento.getDataAgendamento());
            pstmt.setString(7, agendamento.getHorarioReservado());
            pstmt.setLong(8, agendamento.getIdServicoAgendado());
            pstmt.setDouble(9, agendamento.getValorServico());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅- Agendamento realizado com sucesso!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌- Erro ao realizar agendamento: " + e.getMessage());
        }
        return false;
    }

    public static boolean atualizarFuncionario(Funcionario funcionario) {
        String sql = "UPDATE Funcionario SET NomeFuncionario = ?, especialidade = ?, TelefoneFuncionario = ?, " +
                "email = ?, HorarioTrabalhoInicio = ?, HorarioTrabalhoFim = ?, DiasDeTrabalho = ? " +
                "WHERE IdFuncionario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNomeFuncionario());
            stmt.setString(2, funcionario.getEspecialidade());
            stmt.setString(3, funcionario.getTelefoneFuncionario());
            stmt.setString(4, funcionario.getEmail());
            stmt.setTime(5, Time.valueOf(funcionario.getHorarioTrabalhoInicio()));
            stmt.setTime(6, Time.valueOf(funcionario.getHorarioTrabalhoFim()));
            stmt.setString(7, funcionario.getDiasDeTrabalho());
            stmt.setLong(8, funcionario.getIdFuncionario());

            int rowsAffected = stmt.executeUpdate();
            Conexao.commit(conn);

            if (rowsAffected > 0) {
                System.out.println("✅- Funcionário atualizado com sucesso!");
                return true;
            } else {
                System.out.println("⚠️- Nenhum funcionário encontrado com o ID");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌- Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }

    public static boolean excluirFuncionario(Long IdFuncionario) {
        String sql = "DELETE FROM Funcionario WHERE IdFuncionario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, IdFuncionario);
            int rowsAffected = stmt.executeUpdate();
            Conexao.commit(conn);

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌- Erro ao excluir funcionário: " + e.getMessage());
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("⚠️- Funcionário vinculado a agendamentos, exclusão não permitida.");
            }
            return false;
        }
    }


    public static boolean inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (IdCliente, NomeCliente, TelefoneCliente) VALUES (?, ?, ?)";
        return DBUtils.executarUpdate(sql,
                cliente.getIdCliente(),
                cliente.getNomeCliente(),
                cliente.getTelefoneCliente());
    }

    public static ArrayList<Cliente> consultarTodosClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT IdCliente, NomeCliente, TelefoneCliente FROM Cliente";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getLong("IdCliente"),
                        rs.getString("NomeCliente"),
                        rs.getString("TelefoneCliente")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.err.println("❌- Erro ao consultar clientes no banco de dados: " + e.getMessage());
        }
        return clientes;
    }

    public static boolean atualizarClienteNoBanco(Cliente cliente) {
        String sql = "UPDATE Cliente SET NomeCliente = ?, TelefoneCliente = ? WHERE IdCliente = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getTelefoneCliente());
            stmt.setLong(3, cliente.getIdCliente());

            int rowsAffected = stmt.executeUpdate();
            Conexao.commit(conn);

            if (rowsAffected > 0) {
                System.out.println("✅- Cliente atualizado com sucesso!");
                return true;
            } else {
                System.out.println("⚠️- Nenhum cliente encontrado com o ID fornecido:");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌- Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public static Cliente consultarClientePorId(Long IdCliente) {
        String sql = "SELECT IdCliente, NomeCliente, TelefoneCliente FROM Cliente WHERE IdCliente = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, IdCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getLong("IdCliente"),
                            rs.getString("NomeCliente"),
                            rs.getString("TelefoneCliente")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌- Erro ao consultar cliente por Id no banco de dados: " + e.getMessage());
        }
        return null;
    }

    public static boolean excluirCliente(long IdCliente) {
        String sql = "DELETE FROM Cliente WHERE IdCliente = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, IdCliente);
            int affectedRows = pstmt.executeUpdate();
            Conexao.commit(conn);

            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("❌- Erro ao excluir cliente: " + e.getMessage());
            return false;
        }
    }


    public static void debugServico(Long id) {
        Servico s = consultarServicoPorId(id);
        if (s != null) {
            System.out.println("\n[DEBUG] Estado atual do serviço:");
            System.out.println("ID: " + s.getIdServico());
            System.out.println("Nome: " + s.getNomeServico());
            System.out.println("Duração: " + s.getDuracao());
            System.out.println("Valor: " + s.getValorServico());
        } else {
            System.out.println("[DEBUG] Serviço não encontrado!");
        }
    }

    public static Long gerarIdAleatorioComLimite(int digitos) {
        if (digitos < 1 || digitos > 9) {
            throw new IllegalArgumentException("Digite entre 1 e 9 dígitos");
        }

        long min = (long) Math.pow(10, digitos - 1);
        long max = (long) Math.pow(10, digitos) - 1;

        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }


    static class Agendamento {
        Long IdAgendamento;
        Long IdCliente;
        String TelefoneCliente;
        Long IdFuncionario;
        String DataServico;
        Date DataAgendamento;
        String HorarioReservado;
        Long IdServicoAgendado;
        Double ValorServico;


        public Agendamento(Long idAgendamento, Long idCliente, String telefoneCliente, Long idFuncionario, String dataServico, Date dataAgendamento, String horarioReservado, Long IdservicoAgendado, Double valorServico) {
            this.IdAgendamento = idAgendamento;
            this.IdCliente = idCliente;
            this.TelefoneCliente = telefoneCliente;
            this.IdFuncionario = idFuncionario;
            this.DataServico = dataServico;
            this.DataAgendamento = dataAgendamento;
            this.HorarioReservado = horarioReservado;
            this.IdServicoAgendado = IdservicoAgendado;
            this.ValorServico = valorServico;
        }

        public Long getIdAgendamento() {
            return IdAgendamento;
        }

        public void setIdAgendamento(Long idAgendamento) {
            IdAgendamento = idAgendamento;
        }

        public Long getIdCliente() {
            return IdCliente;
        }

        public void setIdCliente(Long idCliente) {
            IdCliente = idCliente;
        }

        public String getTelefoneCliente() {
            return TelefoneCliente;
        }

        public void setTelefoneCliente(String telefoneCliente) {
            TelefoneCliente = telefoneCliente;
        }

        public Long getIdFuncionario() {
            return IdFuncionario;
        }

        public void setIdFuncionario(Long idFuncionario) {
            IdFuncionario = idFuncionario;
        }

        public String getDataServico() {
            return DataServico;
        }

        public void setDataServico(String dataServico) {
            DataServico = dataServico;
        }

        public Date getDataAgendamento() {
            return DataAgendamento;
        }

        public void setDataAgendamento(Date dataAgendamento) {
            DataAgendamento = dataAgendamento;
        }

        public String getHorarioReservado() {
            return HorarioReservado;
        }

        public void setHorarioReservado(String horarioReservado) {
            HorarioReservado = horarioReservado;
        }

        public Long getIdServicoAgendado() {
            return IdServicoAgendado;
        }

        public void setIdServicoAgendado(Long IdservicoAgendado) {
            IdServicoAgendado = IdservicoAgendado;
        }

        public Double getValorServico() {
            return ValorServico;
        }

        public void setValorServico(Double valorServico) {
            ValorServico = valorServico;
        }

        @Override
        public String toString() {
            return "ID do Agendamento: " + IdAgendamento + ", ID do Cliente: " + IdCliente + ", Telefone do Cliente: " + TelefoneCliente +
                    ", Serviço Agendado: " + IdServicoAgendado + ", Data: " + DataServico +
                    ", ID do Funcionário: " + IdFuncionario + ", Horário: " + HorarioReservado + ", Valor a cobrar: " + ValorServico +
                    ", Agendamento realizado em: " + DataAgendamento;
        }
    }

    public static boolean InserirAgendamento(Agendamento agendamento) {
        // Verifica se cliente existe
        if (consultarClientePorId(agendamento.getIdCliente()) == null) {
            System.err.println("❌- Cliente não encontrado!");
            return false;
        }

        // Verifica se funcionário existe
        if (consultarFuncionarioPorId(agendamento.getIdFuncionario()) == null) {
            System.err.println("❌- Funcionário não encontrado!");
            return false;
        }

        // Verifica disponibilidade
        String checkSql = "SELECT COUNT(*) FROM Agendamento WHERE IdFuncionario = ? AND DataServico = ? AND HorarioReservado = ?";

        try {
            Long count = DBUtils.executarQuery(checkSql, rs -> {
                try {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                    return 0L;
                } catch (SQLException e) {
                    System.err.println("❌- Erro ao verificar disponibilidade: " + e.getMessage());
                    return 0L;
                }
            }, agendamento.getIdFuncionario(), agendamento.getDataServico(), agendamento.getHorarioReservado());

            if (count > 0) {
                System.err.println("❌- Horário já ocupado!");
                return false;
            }

            // Insere agendamento
            String insertSql = "INSERT INTO Agendamento (IdAgendamento, IdCliente, TelefoneCliente, IdFuncionario, " +
                    "DataServico, DataAgendamento, HorarioReservado, IdServicoAgendado, ValorServico) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            return DBUtils.executarUpdate(insertSql,
                    agendamento.getIdAgendamento(),
                    agendamento.getIdCliente(),
                    agendamento.getTelefoneCliente(),
                    agendamento.getIdFuncionario(),
                    agendamento.getDataServico(),
                    agendamento.getDataAgendamento(),
                    agendamento.getHorarioReservado(),
                    agendamento.getIdServicoAgendado(),
                    agendamento.getValorServico());

        } catch (Exception e) {
            System.err.println("❌- Erro durante o agendamento: " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Agendamento> consultarTodosAgendamentos() {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT * FROM Agendamento";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Agendamento a = new Agendamento(
                        rs.getLong("IdAgendamento"),
                        rs.getLong("IdCliente"),
                        rs.getString("TelefoneCliente"),
                        rs.getLong("IdFuncionario"),
                        rs.getString("DataServico"),
                        rs.getDate("DataAgendamento"),
                        rs.getString("HorarioReservado"),
                        rs.getLong("IdServicoAgendado"),
                        rs.getDouble("ValorServico")
                );
                agendamentos.add(a);
            }
        } catch (SQLException e) {
            System.err.println("❌- Erro ao consultar todos os agendamentos no banco de dados: " + e.getMessage());
        }
        return agendamentos;
    }

    public static ArrayList<Agendamento> consultarAgendamentosPorFuncionario(Long idFuncionario) {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT * FROM Agendamento WHERE IdFuncionario = ? ORDER BY DataServico, HorarioReservado";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Agendamento a = new Agendamento(
                        rs.getLong("IdAgendamento"),
                        rs.getLong("IdCliente"),
                        rs.getString("TelefoneCliente"),
                        rs.getLong("IdFuncionario"),
                        rs.getString("DataServico"),
                        rs.getDate("DataAgendamento"),
                        rs.getString("HorarioReservado"),
                        rs.getLong("IdServicoAgendado"),
                        rs.getDouble("ValorServico")
                );
                agendamentos.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar agendamentos por funcionário: " + e.getMessage());
        }
        return agendamentos;
    }


    public static boolean excluirAgendamento(Long IdAgendamento) {
        String sql = "DELETE FROM Agendamento WHERE IdAgendamento = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, IdAgendamento);
            int rowsAffected = stmt.executeUpdate();
            Conexao.commit(conn);

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌- Erro ao excluir agendamento: " + e.getMessage());
            return false;
        }
    }


    //Definições Serviços

    public static boolean InserirServico(Servico servico) {
        String insertSql = "INSERT INTO Servico (IdServico, NomeServico, Duracao, ValorServico) VALUES (?,?,?,?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement(insertSql);
            stmt.setLong(1, servico.getIdServico());
            stmt.setString(2, servico.getNomeServico());
            stmt.setInt(3, servico.getDuracao());
            stmt.setDouble(4, servico.getValorServico());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                Conexao.commit(conn);
                System.out.println("✅- Serviço adicionado com sucesso!");
                return true;
            } else {
                System.err.println("❌- Falha ao inserir serviço.");
                return false;
            }
        } catch (SQLException e) {
            Conexao.rollback(conn);
            System.err.println("❌ Erro SQL ao inserir serviço:");
            System.err.println("Código de erro: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
            System.err.println("Mensagem: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn, stmt, null);
        }
    }

    public static ArrayList<Servico> consultarTodosServicos() {
        ArrayList<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM Servico";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Servico s = new Servico(
                        rs.getLong("IdServico"),
                        rs.getString("NomeServico"),
                        rs.getInt("Duracao"),
                        rs.getDouble("ValorServico")
                );
                servicos.add(s);
            }
        } catch (SQLException e) {
            System.err.println("❌- Erro ao consultar todos os serviços no banco de dados: " + e.getMessage());
        }
        return servicos;
    }

    public static Servico consultarServicoPorId(Long IdServico) {
        String sql = "SELECT * FROM Servico WHERE IdServico = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, IdServico);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Servico(
                            rs.getLong("IdServico"),
                            rs.getString("NomeServico"),
                            rs.getInt("Duracao"),
                            rs.getDouble("ValorServico")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌- Erro ao consultar serviço por ID no banco de dados: " + e.getMessage());
        }
        return null;
    }

    public static boolean atualizarServico(Servico servico) {
        String sql = "UPDATE Servico SET NomeServico = ?, Duracao = ?, ValorServico = ? WHERE IdServico = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configura os parâmetros
            stmt.setString(1, servico.getNomeServico());
            stmt.setInt(2, servico.getDuracao());
            stmt.setDouble(3, servico.getValorServico());
            stmt.setLong(4, servico.getIdServico());

            // Executa a atualização
            int linhasAfetadas = stmt.executeUpdate();
            Conexao.commit(conn);

            if (linhasAfetadas > 0) {
                System.out.println("✅- Serviço atualizado com sucesso no banco de dados!");
                return true;
            } else {
                System.out.println("⚠️- Nenhum serviço foi atualizado. Verifique se o ID existe.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌- Erro ao atualizar serviço: " + e.getMessage());
            return false;
        }
    }

    public static boolean excluirServico(Long IdServico) {
        String checkSql = "SELECT COUNT(*) FROM Agendamento WHERE IdServicoAgendado = ?";
        String deleteSql = "DELETE FROM Servico WHERE IdServico = ?";

        try (Connection conn = Conexao.conectar()) {
            // Primeiro verifica se existe vínculo
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setLong(1, IdServico);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.err.println("⚠️- Serviço vinculado a agendamentos, exclusão não permitida.");
                    return false;
                }
            }


            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setLong(1, IdServico);
                int rowsAffected = deleteStmt.executeUpdate();
                Conexao.commit(conn);

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.err.println("❌- Erro ao excluir serviço: " + e.getMessage());
            return false;
        }
    }


    public static <Int> void main(String[] args) {
        System.out.println("Testando conexão com o banco de dados...");
        testarConexao();
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- Sistema de Agendamento ---");
            System.out.println("1. Agendar um serviço");
            System.out.println("2. Cadastrar Cliente");
            System.out.println("3. Consultar Clientes");
            System.out.println("4. Atualizar Cliente");
            System.out.println("5. Excluir Cliente");
            System.out.println("6. Cadastrar Funcionário");
            System.out.println("7. Consultar Funcionários");
            System.out.println("8. Atualizar Funcionário");
            System.out.println("9. Excluir Funcionário");
            System.out.println("10. Cadastrar Serviço");
            System.out.println("11. Consultar Serviços");
            System.out.println("12. Atualizar Serviço");
            System.out.println("13. Excluir Serviço");
            System.out.println("14. Consultar Todos Agendamentos");
            System.out.println("15. Consultar Agendamento por Funcionário");
            System.out.println("16. Excluir Agendamento");
            System.out.println("0. Sair");
            option = lerInt(scanner, "Escolha uma opção:");

            switch (option) {
                case 1:
                    System.out.println("\n--- Agendar Serviço ---");

                    Long IdAgendamento = gerarIdAleatorioComLimite(5);
                    Long IdCliente = lerLong(scanner, "Digite o ID do cliente: ");
                    String TelefoneCliente = lerString(scanner, "Digite o telefone do cliente: ");
                    Long IdFuncionario = lerLong(scanner, "Digite o ID do funcionário que prestará o serviço: ");
                    Date DataAgendamento = new Date(System.currentTimeMillis());
                    String DataServico = lerString(scanner, "Digite a data de agendamento: ");
                    String HorarioReservado = lerString(scanner, "Digite o horário da reserva: ");
                    Long IdServicoAgendado = lerLong(scanner, "Digite o ID do serviço agendado: ");
                    Double ValorServico = lerDouble(scanner, "Digite o valor do serviço: ");

                    Servico servicoSelecionado = consultarServicoPorId(IdServicoAgendado);
                    if (servicoSelecionado == null) {
                        System.out.println("⚠️- Serviço não encontrado. Tente novamente.");
                        break;
                    }
                    String ServicoAgendado = servicoSelecionado.getNomeServico();

                    Agendamento novoAgendamento = new Agendamento(
                            IdAgendamento,
                            IdCliente,
                            TelefoneCliente,
                            IdFuncionario,
                            DataServico,
                            DataAgendamento,
                            HorarioReservado,
                            IdServicoAgendado,
                            ValorServico
                    );
                    if (InserirAgendamento(novoAgendamento)) {
                        System.out.println("✅- Agendamento cadastrado com sucesso!");
                    } else {
                        System.out.println("❌- Falha ao cadastrar agendamento.");
                    }
                    break;
                case 2:
                    Long idCliente = gerarIdAleatorioComLimite(5);
                    System.out.println("\n--- Cadastrar Cliente ---");
                    String nomeCliente = lerString(scanner, "Digite o nome do cliente: ");
                    String telefoneCliente = lerString(scanner, "Digite o telefone do cliente: ");
                    Cliente novoCliente = new Cliente(idCliente, nomeCliente, telefoneCliente);
                    if (inserirCliente(novoCliente)) {
                        System.out.println("✅- Cliente cadastrado com sucesso!");
                    } else {
                        System.out.println("❌- Falha ao cadastrar cliente.");
                    }
                    break;
                case 3:
                    System.out.println("\n--- Clientes Cadastrados ---");
                    ArrayList<Cliente> clientes = consultarTodosClientes();
                    if (clientes.isEmpty()) {
                        System.out.println("⚠️- Nenhum cliente cadastrado.");
                    } else {
                        for (Cliente c : clientes) {
                            System.out.println(c);
                        }
                    }
                    break;
                case 4:
                    System.out.println("\n--- Atualizar Cliente ---");
                    Long IdParaAtualizar = lerLong(scanner, "Digite o ID do cliente que vai receber atualização: ");
                    Cliente clienteParaAtualizar = consultarClientePorId(IdParaAtualizar);

                    if (clienteParaAtualizar != null) {
                        System.out.println("Cliente encontrado! Qual dado deseja atualizar? ");
                        System.out.println("1- Nome do Cliente ");
                        System.out.println("2- Telefone do Cliente ");
                        int respostaDadoClienteAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        boolean atualizado = false;

                        if (respostaDadoClienteAtualizar == 1) {
                            String novoNomeCliente = lerString(scanner, "Digite o novo nome do Cliente: ");
                            clienteParaAtualizar.setNomeCliente(novoNomeCliente);
                            atualizado = atualizarClienteNoBanco(clienteParaAtualizar);
                        } else if (respostaDadoClienteAtualizar == 2) {
                            String novoTelefoneCliente = lerString(scanner, "Digite o novo telefone do Cliente: ");
                            clienteParaAtualizar.setTelefoneCliente(novoTelefoneCliente);
                            atualizado = atualizarClienteNoBanco(clienteParaAtualizar);
                        } else {
                            System.out.println("❌- Opção inválida.");
                        }

                        if (atualizado) {
                            System.out.println("✅- Dados do cliente atualizados com sucesso!");
                        }
                    } else {
                        System.out.println("⚠️- Cliente não encontrado. Tente outro ID");
                    }
                    break;

                case 5:
                    System.out.println("\n--- Excluir Cliente ---");
                    Long idClienteExcluir = lerLong(scanner, "Digite o ID do cliente a ser excluído: ");
                    Cliente clienteParaExcluir = consultarClientePorId(idClienteExcluir);
                    if (clienteParaExcluir != null) {
                        excluirCliente(idClienteExcluir);
                        System.out.println("✅- Cliente excluído com sucesso! ");
                        break;
                    } else {
                        System.out.println("⚠️- Cliente não encontrado. Tente outro ID");
                        break;
                    }
                case 6:
                    System.out.println("\n--- Cadastrar Funcionários ---");
                    Long idFuncionario = gerarIdAleatorioComLimite(5);
                    String nomeFuncionario = lerString(scanner, "Digite o nome do funcionário: ");
                    String especialidade = lerString(scanner, "Digite a especialidade do funcionário: ");
                    String telefoneFuncionario = lerString(scanner, "Digite o telefone do funcionário: ");
                    String emailFuncionario = lerString(scanner, "Digite o email do funcionário: ");
                    LocalTime inicioExpediente = LocalTime.parse(lerString(scanner, "Digite o horário de início (HH:MM): "));
                    LocalTime fimExpediente = LocalTime.parse(lerString(scanner, "Digite o horário de fim (HH:MM): "));
                    String diasTrabalho = lerString(scanner, "Digite os dias de trabalho (ex: SEG,TER,QUA): ");

                    Funcionario novoFuncionario = new Funcionario(idFuncionario, nomeFuncionario, especialidade, telefoneFuncionario, emailFuncionario, inicioExpediente, fimExpediente, diasTrabalho);
                    cadastrarFuncionario(novoFuncionario);
                    System.out.println("✅ - Funcionário cadastrado com sucesso! ");
                    break;
                case 7:
                    System.out.println("\n--- Funcionários Cadastrados ---");
                    ArrayList<Funcionario> funcionarios = consultarTodosFuncionarios();
                    if (funcionarios.isEmpty()) {
                        System.out.println("⚠️- Nenhum funcionário cadastrado.");
                    } else {
                        for (Funcionario f : funcionarios) {
                            System.out.println(f);
                        }
                    }
                    break;
                case 8:// Atualizar Funcionário
                    System.out.println("\n--- Atualizar Funcionário ---");

                    ArrayList<Funcionario> funcionariosparaAtualizar = consultarTodosFuncionarios();
                    if (funcionariosparaAtualizar.isEmpty()) {
                        System.out.println("⚠️- Nenhum funcionário cadastrado.");
                    } else {
                        for (Funcionario f : funcionariosparaAtualizar) {
                            System.out.println(f);
                        }
                    }
                    Long IdFuncionarioParaAtualizar = lerLong(scanner, "Digite o ID do funcionário que vai receber atualização: ");
                    Funcionario funcionarioParaAtualizar = consultarFuncionarioPorId(IdFuncionarioParaAtualizar);

                    if (funcionarioParaAtualizar != null) {
                        System.out.println("\nFuncionário encontrado! Qual dado deseja atualizar? ");
                        System.out.println("1- Nome do Funcionário ");
                        System.out.println("2- Especialidade do Funcionário ");
                        System.out.println("3- Telefone do Funcionário ");
                        System.out.println("4- E-Mail do Funcionário ");
                        System.out.println("5- Início de Expediente ");
                        System.out.println("6- Fim de Expediente ");
                        System.out.println("7- Dias de Trabalho");
                        int respostaDadoFuncionarioAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        boolean atualizado = false;

                        if (respostaDadoFuncionarioAtualizar == 1) {
                            String novoNomeFuncionario = lerString(scanner, "Digite o novo nome do Funcionário: ");
                            funcionarioParaAtualizar.setNomeFuncionario(novoNomeFuncionario);
                            atualizado = atualizarFuncionario(funcionarioParaAtualizar);
                            System.out.println("✅- Dados do funcionário atualizados com sucesso!");
                            break;
                        } else if (respostaDadoFuncionarioAtualizar == 2) {
                            String novaEspecialidade = lerString(scanner, "Digite a nova especialidade: ");
                            funcionarioParaAtualizar.setEspecialidade(novaEspecialidade);
                            atualizado = atualizarFuncionario(funcionarioParaAtualizar);
                            System.out.println("✅- Dados do funcionário atualizados com sucesso!");
                            break;
                        } else if (respostaDadoFuncionarioAtualizar == 3) {
                            String novoTelefone = lerString(scanner, "Digite o novo telefone: ");
                            funcionarioParaAtualizar.setTelefoneFuncionario(novoTelefone);
                            atualizado = atualizarFuncionario(funcionarioParaAtualizar);
                            System.out.println("✅- Dados do funcionário atualizados com sucesso!");
                            break;
                        } else if (respostaDadoFuncionarioAtualizar == 4) {
                            String novoEmail = lerString(scanner, "Digite o novo e-mail: ");
                            funcionarioParaAtualizar.setEmail(novoEmail);
                            atualizado = atualizarFuncionario(funcionarioParaAtualizar);
                            System.out.println("✅- Dados do funcionário atualizados com sucesso!");
                            break;
                        } else if (respostaDadoFuncionarioAtualizar == 5) {
                            LocalTime novoInicio = lerLocalTime(scanner, "Digite o novo horário de início (HH:mm): ");
                            funcionarioParaAtualizar.setHorarioTrabalhoInicio(novoInicio);
                            atualizado = atualizarFuncionario(funcionarioParaAtualizar);
                            System.out.println("✅- Dados do funcionário atualizados com sucesso!");
                            break;
                        } else if (respostaDadoFuncionarioAtualizar == 6) {
                            LocalTime novoFim = lerLocalTime(scanner, "Digite o novo horário de fim (HH:mm): ");
                            funcionarioParaAtualizar.setHorarioTrabalhoFim(novoFim);
                            atualizado = atualizarFuncionario(funcionarioParaAtualizar);
                            System.out.println("✅- Dados do funcionário atualizados com sucesso!");
                            break;
                        } else if (respostaDadoFuncionarioAtualizar == 7) {
                            String novosDias = lerString(scanner, "Digite os novos dias de trabalho (ex: SEG,TER,QUA): ");
                            funcionarioParaAtualizar.setDiasDeTrabalho(novosDias);
                            atualizado = atualizarFuncionario(funcionarioParaAtualizar);
                            System.out.println("✅- Dados do funcionário atualizados com sucesso!");
                            break;
                        } else {
                            System.out.println("❌- Opção inválida.");
                            break;
                        }

                    } else {
                        System.out.println("⚠️- Funcionário não encontrado. Tente outro ID");
                    }
                    break;


                case 9:
                    System.out.println("\n--- Excluir Funcionário ---");
                    Long idFuncionarioExcluir = lerLong(scanner, "Digite o ID do Funcionário a ser excluído: ");
                    Funcionario funcionarioParaExcluir = consultarFuncionarioPorId(idFuncionarioExcluir);
                    if (funcionarioParaExcluir != null) {
                        excluirFuncionario(idFuncionarioExcluir);
                        System.out.println("✅ - Funcionário excluído com sucesso! ");
                        break;
                    } else {
                        System.out.println("⚠️- Funcionário não encontrado. Tente outro ID");
                        break;
                    }
                case 10:
                    System.out.println("\n--- Cadastrar Serviço ---");
                    String NomeServico = lerString(scanner, "Digite o nome do serviço: ");
                    int duracao = lerInt(scanner, "Digite a duração(em minutos) do serviço: ");
                    Double valorServico = lerDouble(scanner, "Digite o valor do serviço: ");
                    Long idServico = lerLong(scanner, "Digite o ID do serviço (ou 0 para gerar um aleatório):");
                    if (idServico == 0) {
                        boolean idJaExiste = true;
                        do {
                            idServico = gerarIdAleatorioComLimite(5);
                            idJaExiste = (consultarServicoPorId(idServico) != null);
                            if (idJaExiste) {
                                System.out.println("ID de serviço gerado(" + idServico + ") já existe. Tentanto novamente...");
                            }
                        } while (idJaExiste);
                        System.out.println("ID do serviço gerado: " + idServico);
                    }
                    Servico servico = new Servico(idServico, NomeServico, duracao, valorServico);
                    if (InserirServico(servico)) {
                        System.out.println("✅- Serviço cadastrado com sucesso!");
                    } else {
                        System.out.println("❌- Falha ao cadastrar serviço.");
                    }
                    break;
                case 11:
                    System.out.println("\n--- Serviços Cadastrados ---");
                    ArrayList<Servico> servicos = consultarTodosServicos();
                    if (servicos.isEmpty()) {
                        System.out.println("⚠️- Nenhum serviço cadastrado.");
                    } else {
                        for (Servico s : servicos) {
                            System.out.println(s);
                        }
                    }
                    break;
                case 12:
                    System.out.println("\n--- Atualizar Serviço ---");
                    Long IdParaAtualizarServico = lerLong(scanner, "Digite o ID do serviço que vai receber atualização: ");
                    Servico servicoParaAtualizar = consultarServicoPorId(IdParaAtualizarServico);
                    atualizarServico(servicoParaAtualizar);

                    if (servicoParaAtualizar != null) {
                        System.out.println("Serviço encontrado! Qual dado deseja atualizar? ");
                        System.out.println("1- Nome do Serviço ");
                        System.out.println("2- Duração do Serviço ");
                        System.out.println("3- Valor do Serviço ");
                        int respostaDadoServicoAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        if (respostaDadoServicoAtualizar == 1) {
                            String novoNomeServico = lerString(scanner, "Digite o novo nome do Serviço: ");
                            servicoParaAtualizar.setNomeServico(novoNomeServico);
                            atualizarServico(servicoParaAtualizar);
                            System.out.println("✅- Informação atualizada com sucesso! O novo nome do Serviço é:  " + novoNomeServico);
                            break;
                        } else if (respostaDadoServicoAtualizar == 2) {
                            int novaDuracaoServico = lerInt(scanner, "Digite a nova duração do Serviço (em minutos): ");
                            servicoParaAtualizar.setDuracao(novaDuracaoServico);
                            atualizarServico(servicoParaAtualizar);
                            System.out.println("✅- Informação atualizada com sucesso! A nova duração do Serviço são: " + novaDuracaoServico + "minutos");
                            break;
                        } else if (respostaDadoServicoAtualizar == 3) {
                            Double novoValorServico = lerDouble(scanner, "Digite o novo valor do Serviço: ");
                            servicoParaAtualizar.setValorServico(novoValorServico);
                            atualizarServico(servicoParaAtualizar);
                            System.out.println("✅- Informação atualizada com sucesso! O novo valor do Serviço é de R$" + novoValorServico);
                            break;
                        }
                    } else {
                        System.out.println("⚠️- Cliente não encontrado. Tente outro ID");
                        break;
                    }

                case 13:
                    System.out.println("\n--- Excluir Serviço ---");
                    Long IdServico = lerLong(scanner, "Digite o ID do serviço para excluir: ");

                    Servico servicoEncontrado = consultarServicoPorId(IdServico);
                    if (servicoEncontrado == null) {
                        System.out.println("❌ Erro: Serviço não encontrado com ID " + IdServico);
                        break;
                    }

                    System.out.println("\nVocê está prestes a excluir:");
                    System.out.println("ID: " + servicoEncontrado.getIdServico());
                    System.out.println("Nome: " + servicoEncontrado.getNomeServico());
                    System.out.println("Duração: " + servicoEncontrado.getDuracao() + " mins");
                    System.out.println("Valor: R$" + servicoEncontrado.getValorServico());

                    System.out.println("\n1- Confirmar exclusão");
                    System.out.println("2- Cancelar");
                    int opcao = lerInt(scanner, "Digite sua opção: ");

                    if (opcao == 1) {
                        if (excluirServico(IdServico)) {
                            System.out.println("✅ Serviço excluído com sucesso!");
                        }
                    } else {
                        System.out.println("Operação cancelada pelo usuário");
                    }
                    break;
                case 14:
                    System.out.println("\n--- Todos os Agendamentos ---");
                    ArrayList<Agendamento> todosAgendamentos = consultarTodosAgendamentos();
                    if (todosAgendamentos.isEmpty()) {
                        System.out.println("⚠️- Nenhum agendamento encontrado.");
                    } else {
                        for (Agendamento a : todosAgendamentos) {
                            System.out.println(a);
                        }
                    }
                    break;
                case 15:
                    System.out.println("\n--- Consultar Agendamentos por Funcionário ---");
                    Long idFuncionarioConsultar = lerLong(scanner, "Digite o ID do funcionário para consultar: ");

                    ArrayList<Agendamento> agendamentosFuncionario = consultarAgendamentosPorFuncionario(idFuncionarioConsultar);
                    if (agendamentosFuncionario != null && !agendamentosFuncionario.isEmpty()) {
                        System.out.println("\n📅 Agendamentos do funcionário ID " + idFuncionarioConsultar+ ":");
                        for(Agendamento agendamento:agendamentosFuncionario){
                            System.out.println(agendamento);
                        }
                    } else {
                        System.out.println("Nenhum agendamento encontrado para o funcionário com ID: " + idFuncionarioConsultar);
                    }
                    break;
                case 16:
                    System.out.println("\n--- Excluir Agendamento ---");
                    ArrayList<Agendamento> EscolherAgendamentoParaExcluir = consultarTodosAgendamentos();
                    if (EscolherAgendamentoParaExcluir.isEmpty()) {
                        System.out.println("⚠️- Nenhum agendamento encontrado.");
                        break;
                    } else {
                        for (Agendamento a : EscolherAgendamentoParaExcluir) {
                            System.out.println(a);
                            Long idAgendamentoExcluir = lerLong(scanner, "Digite o ID do agendamento para excluir: ");
                            excluirAgendamento(idAgendamentoExcluir);
                            break;
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("❌- Opção inválida. Tente novamente.");
            }
        } while (option != 0);

        scanner.close();
    }
}
