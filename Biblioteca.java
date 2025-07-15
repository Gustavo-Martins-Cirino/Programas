package Projetos.Treinos.Biblioteca;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;
import java.time.format.DateTimeFormatter;

public class Biblioteca {

    // Método para gerar ID aleatório único para Livro (agora retorna int)
    public static int gerarIdLivroAleatorioUnico() {
        int generatedId;
        Random random = new Random();
        do {
            // Gera um ID de 5 dígitos (exemplo, você pode ajustar o limite)
            generatedId = 10000 + random.nextInt(90000); // Garante 5 dígitos
            // Verifica se o ID já existe no banco de dados
            // Usamos consultarLivroPorId para isso
        } while (consultarLivroPorId(generatedId) != null); // Continua gerando até ser único
        return generatedId;
    }

    // Método original (parece ser para Matrícula de Aluno, que é Long)
    public static Long gerarIdAleatorioComLimite(int numDigitos) {
        if (numDigitos <= 0 || numDigitos > 19) {
            throw new IllegalArgumentException("Número de dígitos inválido. Deve ter no máximo 19 caracteres! ");
        }
        long min = (long) Math.pow(10, numDigitos - 1);
        if (numDigitos == 1) {
            min = 0;
        }
        long max = (long) Math.pow(10, numDigitos) - 1;
        if (numDigitos == 19) {
            max = Long.MAX_VALUE;
            min = 1_000_000_000_000_000_000L; // Menor número de 19 dígitos
        }
        Random random = new Random();
        long generatedId;
        do {
            generatedId = min + (Math.abs(random.nextLong()) % (max - min + 1));
        } while (generatedId < min); // Garante que não seja menor que 'min' devido ao Math.abs()

        return generatedId;
    }

    static class Livro {
        String titulo;
        String autor;
        String IBSN;
        int ano;
        int quantidadeDisponivel;
        int Id; // Atributo Id

        // NOVO CONSTRUCTOR: Para criar um livro antes de inserir no DB (Id será gerado ou fornecido)
        public Livro(String IBSN, String titulo, String autor, int ano, int quantidadeDisponivel) {
            this.IBSN = IBSN;
            this.titulo = titulo;
            this.autor = autor;
            this.ano = ano;
            this.quantidadeDisponivel = quantidadeDisponivel;
            this.Id = 0; // Valor padrão, será atualizado após a geração ou consulta
        }

        // CONSTRUCTOR EXISTENTE: Para criar um livro quando você já tem o ID (lendo do DB)
        // Ordem dos parâmetros ajustada para consistência
        protected Livro(int Id, String IBSN, String titulo, String autor, int ano, int quantidadeDisponivel) {
            this.Id = Id;
            this.IBSN = IBSN;
            this.titulo = titulo;
            this.autor = autor;
            this.ano = ano;
            this.quantidadeDisponivel = quantidadeDisponivel;
        }


        // SETTERS AND GETTERS (renomeado Id() para getId() )
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }

        public String getAutor() { return autor; }
        public void setAutor(String autor) { this.autor = autor; }

        public String getIBSN() { return IBSN; }
        public void setIBSN(String IBSN) { this.IBSN = IBSN; }

        public int getAno() { return ano; }
        public void setAno(int ano) { this.ano = ano; }

        public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
        public void setQuantidadeDisponivel(int quantidadeDisponivel) { this.quantidadeDisponivel = quantidadeDisponivel; }

        public int getId(){ // Renomeado de Id() para getId()
            return Id;
        }
        public void setId(int Id){
            this.Id = Id;
        }

        @Override
        public String toString() {
            return " |Id: " + Id +
                    "|Título: " + titulo +
                    "|Autor: " + autor +
                    "|IBSN: " + IBSN +
                    "|Ano: " + ano +
                    "|Quantidade: " + quantidadeDisponivel;
        }
    }

    // DEFINIÇÃO DA CLASSE Aluno (Sem alterações, apenas incluído para contexto)
    static class Aluno {
        String nome;
        Long matricula;
        String contato;

        public Aluno(String nome, Long matricula, String contato) {
            this.nome = nome;
            this.matricula = matricula;
            this.contato = contato;
        }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public Long getMatricula() { return matricula; }
        public void setMatricula(Long matricula) { this.matricula = matricula; }

        public String getContato() { return contato; }
        public void setContato(String contato) { this.contato = contato; }

        @Override
        public String toString() {
            return " |Matrícula: " + matricula +
                    "|Nome: " + nome +
                    "|Contato: " + contato;
        }
    }


    static class Emprestimo {
        Long matriculaAluno;
        int id;
        LocalDate dataEmprestimo;
        LocalDate dataDevolucaoPrevista;

        // CONSTRUCTOR
        protected Emprestimo(Long matriculaAluno, int id, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
            this.matriculaAluno = matriculaAluno;
            this.id = id;
            this.dataEmprestimo = dataEmprestimo;
            this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        }

        // SETTERS AND GETTERS
        public Long getMatriculaAluno() { return matriculaAluno; }
        public void setMatriculaAluno(Long matriculaAluno) { this.matriculaAluno = matriculaAluno; }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public LocalDate getdataEmprestimo() { return dataEmprestimo; }
        public void setdataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

        public LocalDate getdataDevolucaoPrevista() { return dataDevolucaoPrevista; }
        public void setdataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }

        //FORMATANDO A DATA PARA SAÍDA
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        @Override
        public String toString() {
            return " |Matrícula do Aluno: " + matriculaAluno +
                    "|Id do Livro: " + id +
                    "|Data Empréstimo: " + dataEmprestimo.format(formatter) +
                    "|Data Devolução Prevista: " + dataDevolucaoPrevista.format(formatter);
        }
    }

    static class Conexao {
        private static final String URL = "jdbc:mysql://localhost:3306/biblioteca?useSSL=false&serverTimezone=UTC";
        private static final String USER = "root";
        private static final String PASSWORD = "@Gucirino01";
        public static Connection conectar() throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC não encontrado: " + e.getMessage());
            }
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    public static void adicionarLivro(Livro livro) {
        String sql = "INSERT INTO livro (Id, IBSN, titulo, autor, ano, quantidadeDisponivel) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, livro.getId());
            stmt.setString(2, livro.getIBSN());
            stmt.setString(3, livro.getTitulo());
            stmt.setString(4, livro.getAutor());
            stmt.setInt(5, livro.getAno());
            stmt.setInt(6, livro.getQuantidadeDisponivel());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro cadastrado com sucesso no banco de dados! ");
            } else {
                System.out.println("Falha ao cadastrar livro.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar livro no banco de dados: " + e.getMessage());

            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: ID ou IBSN já existem no banco de dados. Por favor, forneça um ID ou IBSN único.");
            }
        }
    }

    public static ArrayList<Livro> listarLivros() {
        ArrayList<Livro> livros = new ArrayList<>();

        String sql = "SELECT Id, IBSN, titulo, autor, ano, quantidadeDisponivel FROM livro";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro l = new Livro(
                        rs.getInt("Id"),
                        rs.getString("IBSN"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("ano"),
                        rs.getInt("quantidadeDisponivel")
                );
                livros.add(l);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar livros no banco de dados: " + e.getMessage());
        }
        return livros;
    }


    public static Livro consultarLivroPorId(int Id) {
        String sql = "SELECT Id, IBSN, titulo, autor, ano, quantidadeDisponivel FROM livro WHERE Id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Livro(
                            rs.getInt("Id"),
                            rs.getString("IBSN"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("ano"),
                            rs.getInt("quantidadeDisponivel")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar livro por ID no banco de dados: " + e.getMessage());
        }
        return null;
    }


    public static Livro consultarLivroPorTitulo(String titulo) {
        String sql = "SELECT Id, IBSN, titulo, autor, ano, quantidadeDisponivel FROM livro WHERE titulo = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Livro(
                            rs.getInt("Id"),
                            rs.getString("IBSN"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("ano"),
                            rs.getInt("quantidadeDisponivel")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar livros por título no banco de dados: " + e.getMessage());
        }
        return null;
    }


    public static Livro consultarLivroPorIBSN(String IBSN) {
        String sql = "SELECT Id, IBSN, titulo, autor, ano, quantidadeDisponivel FROM livro WHERE IBSN = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, IBSN);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Livro(
                            rs.getInt("Id"),
                            rs.getString("IBSN"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("ano"),
                            rs.getInt("quantidadeDisponivel")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar livros por IBSN no banco de dados: " + e.getMessage());
        }
        return null;
    }


    public static void atualizarLivro(Livro livro) {
        String sql = "UPDATE Livro SET titulo = ?, autor = ?, ano = ?, quantidadeDisponivel = ? WHERE IBSN = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAno());
            stmt.setInt(4, livro.getQuantidadeDisponivel());
            stmt.setString(5, livro.getIBSN());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro atualizado com sucesso no banco de dados! ");
            } else {
                System.out.println("Nenhum livro encontrado com o código IBSN fornecido para atualização.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar livro no banco de dados: " + e.getMessage());
        }
    }

    public static void excluirLivro(String IBSN) { 
        String sql = "DELETE FROM Livro WHERE IBSN = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, IBSN);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro excluído com sucesso no banco de dados! ");
            } else {
                System.out.println("Nenhum livro encontrado com o código IBSN fornecido para exclusão");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir livro no banco de dados: " + e.getMessage());
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Não é possível excluir o livro, pois ele está sendo referenciado em um empréstimo. Remova as referências primeiro");
            }
        }
    }

    public static void inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno (matricula, nome, contato) VALUES (?,?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getContato());
            stmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso no banco de dados!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastro aluno no banco de dados: " + e.getMessage());
            // Adicione verificação de duplicidade de matrícula (chave primária)
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Erro: Matrícula já existe no banco de dados. Por favor, forneça uma matrícula única.");
            }
        }
    }

    public static ArrayList<Aluno> consultarTodosAlunos() {
        ArrayList<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT nome, matricula, contato FROM Aluno";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno a = new Aluno(
                        rs.getString("nome"),
                        rs.getLong("matricula"),
                        rs.getString("contato")
                );
                alunos.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar alunos no banco de dados: " + e.getMessage());
        }
        return alunos;
    }

    public static Aluno consultarAlunoPorMatricula(Long matricula) {
        String sql = "SELECT nome, matricula , contato FROM Aluno WHERE matricula =?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                            rs.getString("nome"),
                            rs.getLong("matricula"),
                            rs.getString("contato")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar aluno por matrícula no banco de dados: " + e.getMessage());
        }
        return null;
    }

    public static void excluirAluno(Long matricula) {
        String sql = "DELETE FROM Aluno WHERE matricula = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, matricula);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Aluno excluído com sucesso do banco de dados!");
            } else {
                System.out.println("Nenhum aluno encontrado com a matrícula fornecida para exclusão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluno do banco de dados: " + e.getMessage());
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Não é possível excluir o aluno, pois ele possui livros emprestados. Encerre o empréstimo primeiro.");
            }
        }
    }


    public static void registrarEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimos (matricula, id, dataEmprestimo, dataDevolucaoPrevista) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, emprestimo.getMatriculaAluno());
            stmt.setInt(2, emprestimo.getId());
            stmt.setDate(3, java.sql.Date.valueOf(emprestimo.getdataEmprestimo()));
            stmt.setDate(4, java.sql.Date.valueOf(emprestimo.getdataDevolucaoPrevista()));
            stmt.executeUpdate();
            System.out.println("Empréstimo registrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao registrar empréstimo: " + e.getMessage());
        }
    }


    public static ArrayList<Emprestimo> listarEmprestimos() {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT matricula, id, dataEmprestimo, dataDevolucaoPrevista FROM emprestimos";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Emprestimo e = new Emprestimo(
                        rs.getLong("matricula"),
                        rs.getInt("id"),
                        rs.getDate("dataEmprestimo").toLocalDate(),
                        rs.getDate("dataDevolucaoPrevista").toLocalDate()
                );
                emprestimos.add(e);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar empréstimos: " + e.getMessage());
        }
        return emprestimos;
    }


    public static void finalizarEmprestimo(Long matricula, int id) {
        String sql = "DELETE FROM emprestimos WHERE matricula = ? AND id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, matricula);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Empréstimo finalizado com sucesso!");
            } else {
                System.out.println("Nenhum empréstimo encontrado para finalizar com a matrícula e ID do livro fornecidos.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao finalizar empréstimo: " + e.getMessage());
        }
    }

    public static void updateLivroQuantidade(String ISBN, int change) {
        String sql = "UPDATE livro SET quantidadeDisponivel = quantidadeDisponivel + ? WHERE IBSN = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, change);
            stmt.setString(2, ISBN);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar quantidade do livro: " + e.getMessage());
        }
    }

    // DEFININDO MÉTODOS PARA LEITURA (sem alterações)
    public static String lerString(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
    public static Long lerLong(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                Long value = scanner.nextLong();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro longo.");
                scanner.nextLine();
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
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
                scanner.nextLine();
            }
        }
    }

    // MAIN
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        boolean continuar = true;
        while(continuar){
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1- Gerenciar Livros");
            System.out.println("2- Gerenciar Usuários");
            System.out.println("3- Gerenciar Empréstimos");
            System.out.println("4- Sair");

            int opcaoMainMenu = lerInt(scanner, "Escolha uma opção: "); // Usar lerInt
            switch (opcaoMainMenu){
                case 1:
                    System.out.println("\n--- Gerenciar Livros ---");
                    System.out.println("1- Adicionar Livro");
                    System.out.println("2- Listar Livros");
                    System.out.println("3- Buscar Livro");
                    System.out.println("4- Atualizar Quantidade");
                    System.out.println("5- Excluir Livro");
                    int opcaoGerenciarLivros = lerInt(scanner, "Escolha uma opção: ");

                    switch(opcaoGerenciarLivros){
                        case 1:
                            String titulo = lerString(scanner, "Digite o título do livro: ");
                            String IBSN = lerString(scanner, "Digite o número IBSN do livro: ");
                            String autor = lerString(scanner, "Digite o autor do livro: ");
                            int ano = lerInt(scanner, "Digite o ano do lançamento do livro: ");
                            int quantidadeDisponivel = lerInt(scanner, "Digite a quantidade disponível do livro: ");
                            int Id;

                            do {
                                Id = lerInt(scanner, "Digite o ID do livro (ou 0 para gerar um aleatório): ");
                                if (Id == 0) {
                                    Id = gerarIdLivroAleatorioUnico();
                                    System.out.println("ID gerado: " + Id);
                                }
                                if (consultarLivroPorId(Id) != null) {
                                    System.out.println("Este ID já existe. Por favor, digite outro ou 0 para gerar um novo.");
                                    Id = 0;
                                }
                            } while (Id == 0);

                            Livro novoLivro = new Livro(Id, IBSN, titulo, autor, ano, quantidadeDisponivel);
                            adicionarLivro(novoLivro);
                            break;

                        case 2:
                            System.out.println("\n--- Livros Cadastrados ---");
                            ArrayList<Livro> livros = listarLivros();
                            if (livros.isEmpty()) {
                                System.out.println("Nenhum livro cadastrado.");
                            } else {
                                for (Livro l : livros) {
                                    System.out.println(l);
                                }
                            }
                            break;

                        case 3: // Buscar Livro (agora por ID)
                            System.out.println("\n--- Buscar Livro ---");
                            int idBusca = lerInt(scanner, "Digite o ID do livro a ser buscado: ");
                            Livro livroEncontrado = consultarLivroPorId(idBusca);
                            if (livroEncontrado != null) {
                                System.out.println("Livro encontrado:");
                                System.out.println(livroEncontrado);
                            } else {
                                System.out.println("Livro com ID " + idBusca + " não encontrado.");
                            }
                            break;

                        case 4: // Atualizar Quantidade
                            System.out.println("\n--- Atualizar Quantidade de Livro ---");
                            String ibsnAtualizar = lerString(scanner, "Digite o IBSN do livro para atualizar a quantidade: ");
                            int change = lerInt(scanner, "Digite a mudança na quantidade (ex: 5 para adicionar, -2 para remover): ");
                            updateLivroQuantidade(ibsnAtualizar, change);
                            break;

                        case 5: // Excluir Livro
                            System.out.println("\n--- Excluir Livro ---");
                            String ibsnExcluir = lerString(scanner, "Digite o IBSN do livro a ser excluído: ");
                            excluirLivro(ibsnExcluir);
                            break;

                        default:
                            System.out.println("Opção inválida para Gerenciar Livros.");
                            break;
                    }
                    break; // Importante para sair do switch do menu principal

                case 2: // Gerenciar Usuários (exemplo de como continuar)
                    System.out.println("\n--- Gerenciar Usuários ---");
                    System.out.println("1- Adicionar Aluno");
                    System.out.println("2- Listar Alunos");
                    System.out.println("3- Buscar Aluno por Matrícula");
                    System.out.println("4- Excluir Aluno");

                    int opcaoGerenciarAlunos = lerInt(scanner, "Escolha uma opção: ");

                    switch (opcaoGerenciarAlunos) {
                        case 1:
                            String nomeAluno = lerString(scanner, "Digite o nome do aluno: ");
                            Long matriculaAluno;
                            do {
                                matriculaAluno = lerLong(scanner, "Digite a matrícula do aluno (ou 0 para gerar aleatória): ");
                                if (matriculaAluno == 0) {
                                    // Gere uma matrícula aleatória com um limite razoável de dígitos
                                    matriculaAluno = gerarIdAleatorioComLimite(10); // Ex: 10 dígitos para matrícula
                                    System.out.println("Matrícula gerada: " + matriculaAluno);
                                }
                                if (consultarAlunoPorMatricula(matriculaAluno) != null) {
                                    System.out.println("Esta matrícula já existe. Por favor, digite outra ou 0 para gerar uma nova.");
                                    matriculaAluno = 0L; // Para manter o loop
                                }
                            } while (matriculaAluno == 0);


                            String contatoAluno = lerString(scanner, "Digite o contato do aluno: ");
                            Aluno novoAluno = new Aluno(nomeAluno, matriculaAluno, contatoAluno);
                            inserirAluno(novoAluno);
                            break;
                        case 2:
                            System.out.println("\n--- Alunos Cadastrados ---");
                            ArrayList<Aluno> alunos = consultarTodosAlunos();
                            if (alunos.isEmpty()) {
                                System.out.println("Nenhum aluno cadastrado.");
                            } else {
                                for (Aluno a : alunos) {
                                    System.out.println(a);
                                }
                            }
                            break;
                        case 3:
                            System.out.println("\n--- Buscar Aluno ---");
                            Long matriculaBusca = lerLong(scanner, "Digite a matrícula do aluno a ser buscado: ");
                            Aluno alunoEncontrado = consultarAlunoPorMatricula(matriculaBusca);
                            if (alunoEncontrado != null) {
                                System.out.println("Aluno encontrado:");
                                System.out.println(alunoEncontrado);
                            } else {
                                System.out.println("Aluno com matrícula " + matriculaBusca + " não encontrado.");
                            }
                            break;
                        case 4:
                            System.out.println("\n--- Excluir Aluno ---");
                            Long matriculaExcluir = lerLong(scanner, "Digite a matrícula do aluno a ser excluído: ");
                            excluirAluno(matriculaExcluir);
                            break;
                        default:
                            System.out.println("Opção inválida para Gerenciar Usuários.");
                            break;
                    }
                    break;

                case 3: // Gerenciar Empréstimos
                    System.out.println("\n--- Gerenciar Empréstimos ---");
                    System.out.println("1- Registrar Empréstimo");
                    System.out.println("2- Listar Empréstimos");
                    System.out.println("3- Finalizar Empréstimo");

                    int opcaoGerenciarEmprestimos = lerInt(scanner, "Escolha uma opção: ");

                    switch (opcaoGerenciarEmprestimos) {
                        case 1:
                            Long matriculaEmprestimo = lerLong(scanner, "Digite a matrícula do aluno: ");
                            // Verificar se o aluno existe
                            Aluno alunoEmprestimo = consultarAlunoPorMatricula(matriculaEmprestimo);
                            if (alunoEmprestimo == null) {
                                System.out.println("Aluno com matrícula " + matriculaEmprestimo + " não encontrado.");
                                break;
                            }

                            int idLivroEmprestimo = lerInt(scanner, "Digite o ID do livro: ");
                            // Verificar se o livro existe e está disponível
                            Livro livroEmprestimo = consultarLivroPorId(idLivroEmprestimo);
                            if (livroEmprestimo == null) {
                                System.out.println("Livro com ID " + idLivroEmprestimo + " não encontrado.");
                                break;
                            }
                            if (livroEmprestimo.getQuantidadeDisponivel() <= 0) {
                                System.out.println("Livro não disponível para empréstimo.");
                                break;
                            }


                            LocalDate dataEmprestimo = LocalDate.now();
                            LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(7); // Empréstimo por 7 dias

                            Emprestimo novoEmprestimo = new Emprestimo(matriculaEmprestimo, idLivroEmprestimo, dataEmprestimo, dataDevolucaoPrevista);
                            registrarEmprestimo(novoEmprestimo);
                            updateLivroQuantidade(livroEmprestimo.getIBSN(), -1);
                            break;

                        case 2:
                            System.out.println("\n--- Empréstimos Ativos ---");
                            ArrayList<Emprestimo> emprestimos = listarEmprestimos();
                            if (emprestimos.isEmpty()) {
                                System.out.println("Nenhum empréstimo ativo.");
                            } else {
                                for (Emprestimo e : emprestimos) {
                                    System.out.println(e);
                                }
                            }
                            break;

                        case 3:
                            Long matriculaFinalizar = lerLong(scanner, "Digite a matrícula do aluno para finalizar o empréstimo: ");
                            int idLivroFinalizar = lerInt(scanner, "Digite o ID do livro para finalizar o empréstimo: ");

                            Livro livroDevolvido = consultarLivroPorId(idLivroFinalizar);
                            if (livroDevolvido != null) {
                                updateLivroQuantidade(livroDevolvido.getIBSN(), 1);
                                finalizarEmprestimo(matriculaFinalizar, idLivroFinalizar);
                            }else{
                                System.out.println("Livro não encontrado para devolução. ");
                            }
                            break;
                        default:
                            System.out.println("Opção inválida para Gerenciar Empréstimos.");
                            break;
                    }
                    break;

                case 4:
                    continuar = false;
                    System.out.println("Saindo do sistema. Obrigado!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha novamente.");
                    break;
            }
        }
        scanner.close();
    }
}