package Projetos.Treinos.Ecommerce;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.InputMismatchException;

public class ECommerce {
    public static void adicionarItemAoCarrinho(Long idCliente, Long idProduto, int quantidade) {
    }

    static class Produto {
        String nome;
        String descricao;
        double preco;
        int quantidade;
        String cor;
        String categoria;
        Long idProduto;

        protected Produto(String nome, String descricao, double preco, int quantidade, String cor, String categoria, Long idProduto) {
            this.nome = nome;
            this.descricao = descricao;
            this.preco = preco;
            this.quantidade = quantidade;
            this.cor = cor;
            this.categoria = categoria;
            this.idProduto = idProduto;
        }

        // Getters
        public String getNome() { return nome; }
        public String getDescricao() { return descricao; }
        public double getPreco() { return preco; }
        public int getQuantidade() { return quantidade; }
        public String getCor() { return cor; }
        public String getCategoria() { return categoria; }
        public Long getIdProduto() { return idProduto; }

        // Setters
        public void setNome(String nome) { this.nome = nome; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public void setPreco(double preco) { this.preco = preco; }
        public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
        public void setCor(String cor) { this.cor = cor; }
        public void setCategoria(String categoria) { this.categoria = categoria; }
        public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }

        @Override
        public String toString() {
            return "ID: " + idProduto + ", Nome: " + nome + ", Descrição: " + descricao +
                    ", Preço: R$" + String.format("%.2f", preco) + ", Estoque: " + quantidade +
                    ", Cor: " + cor + ", Categoria: " + categoria;
        }
    }

    static class Cliente {
        Long idCliente;
        String nomeCliente;

        protected Cliente(String nomeCliente, Long idCliente) {
            this.nomeCliente = nomeCliente;
            this.idCliente = idCliente;
        }

        // Getters
        public Long getIdCliente() { return idCliente; }
        public String getNomeCliente() { return nomeCliente; }

        // Setters
        public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
        public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

        @Override
        public String toString() {
            return "ID Cliente: " + idCliente + ", Nome: " + nomeCliente;
        }
    }

    // Classe CarrinhoItem com PK e FK
    static class CarrinhoItem {
        Long idCliente;
        Long idProduto;
        int quantidade;

        protected CarrinhoItem(Long idCliente, Long idProduto, int quantidade) {
            this.idCliente = idCliente;
            this.idProduto = idProduto;
            this.quantidade = quantidade;
        }

        // Getters e Setters
        public Long getIdCliente() { return idCliente; }
        public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

        public Long getIdProduto() { return idProduto; }
        public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }

        public int getQuantidade() { return quantidade; }
        public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

        @Override
        public String toString() {
            return "Item no Carrinho: Cliente ID=" + idCliente +
                    ", Produto ID=" + idProduto +
                    ", Quantidade: " + quantidade;
        }
    }

    // Conexão com BD
    static class Conexao {
        private static final String URL = "jdbc:mysql://localhost:3306/ecommerce?useSSL=false&serverTimezone=UTC";
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

    public static double lerDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número decimal.");
                scanner.nextLine();
            }
        }
    }

    public static String lerString(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }


    // Acesso ao BD para Produto

    public static void inserirProduto(Produto produto) {
        String sql = "INSERT INTO produto (idProduto, nome, descricao, preco, quantidade, cor, categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, produto.getIdProduto());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setDouble(4, produto.getPreco());
            stmt.setInt(5, produto.getQuantidade());
            stmt.setString(6, produto.getCor());
            stmt.setString(7, produto.getCategoria());
            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso no banco de dados!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto no DB: " + e.getMessage());
        }
    }

    public static ArrayList<Produto> consultarTodosProdutos() {
        ArrayList<Produto> produtos = new ArrayList<>();
        String sql = "SELECT idProduto, nome, descricao, preco, quantidade, cor, categoria FROM Produto";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto p = new Produto(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade"),
                        rs.getString("cor"),
                        rs.getString("categoria"),
                        rs.getLong("idProduto")
                );
                produtos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar produtos no DB: " + e.getMessage());
        }
        return produtos;
    }

    public static Produto consultarProdutoPorId(Long id) {
        String sql = "SELECT idProduto, nome, descricao, preco, quantidade, cor, categoria FROM Produto WHERE idProduto = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getDouble("preco"),
                            rs.getInt("quantidade"),
                            rs.getString("cor"),
                            rs.getString("categoria"),
                            rs.getLong("idProduto")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar produto por ID no DB: " + e.getMessage());
        }
        return null;
    }

    public static void atualizarProduto(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, descricao = ?, preco = ?, quantidade = ?, cor = ?, categoria = ? WHERE idProduto = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidade());
            stmt.setString(5, produto.getCor());
            stmt.setString(6, produto.getCategoria());
            stmt.setLong(7, produto.getIdProduto());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto atualizado com sucesso no banco de dados!");
            } else {
                System.out.println("Nenhum produto encontrado com o ID fornecido para atualização.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto no DB: " + e.getMessage());
        }
    }

    public static void excluirProduto(Long id) {
        String sql = "DELETE FROM Produto WHERE idProduto = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto excluído com sucesso do banco de dados!");
            } else {
                System.out.println("Nenhum produto encontrado com o ID fornecido para exclusão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto do DB: " + e.getMessage());
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Não é possível excluir o produto, pois ele está sendo referenciado em um carrinho. Remova as referências primeiro.");
            }
        }
    }

    // Acesso ao BD para Cliente

    public static void inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (idCliente, nomeCliente) VALUES (?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNomeCliente());
            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso no banco de dados!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar cliente no DB: " + e.getMessage());
        }
    }

    public static ArrayList<Cliente> consultarTodosClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT idCliente, nomeCliente FROM Cliente";
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getString("nomeCliente"),
                        rs.getLong("idCliente")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar clientes no DB: " + e.getMessage());
        }
        return clientes;
    }

    public static Cliente consultarClientePorId(Long id) {
        String sql = "SELECT idCliente, nomeCliente FROM Cliente WHERE idCliente = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getString("nomeCliente"),
                            rs.getLong("idCliente")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar cliente por ID no DB: " + e.getMessage());
        }
        return null;
    }

    public static void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET nomeCliente = ? WHERE idCliente = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setLong(2, cliente.getIdCliente());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente atualizado com sucesso no banco de dados!");
            } else {
                System.out.println("Nenhum cliente encontrado com o ID fornecido para atualização.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente no DB: " + e.getMessage());
        }
    }

    public static void excluirCliente(Long id) {
        String sql = "DELETE FROM Cliente WHERE idCliente = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente excluído com sucesso do banco de dados!");
            } else {
                System.out.println("Nenhum cliente encontrado com o ID fornecido para exclusão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente do DB: " + e.getMessage());
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                System.err.println("Não é possível excluir o cliente, pois ele possui itens em um carrinho. Remova os itens do carrinho primeiro.");
            }
        }
    }
    //Limite na geração de números aleatórios
    public static Long gerarIdAleatorioComLimite (int numDigitos) {
        if(numDigitos <=0 || numDigitos >19){
            throw new IllegalArgumentException("Número de digitos inválido. Deve ter no máximo 19 caracteres! ");
        }
        long min = (long) Math.pow(10, numDigitos - 1);
        if (numDigitos ==1){
            min = 0;
        }
        long max =(long) Math.pow(10, numDigitos)-1;
        if(numDigitos==19){
            max = Long.MAX_VALUE;
            min = 1_000_000_000_000_000_000L;
        }
        Random random = new Random();
        long generatedId;
        do{
            generatedId = min +(Math.abs(random.nextLong())% (max-min+1));
        }while (generatedId <min);

        return generatedId;
    }

    // Acesso ao BD para Carrinho
    public static void adicionarItemAoCarrinho(CarrinhoItem item) {
        String checkSql = "SELECT quantidade FROM Carrinho WHERE idCliente = ? AND idProduto = ?";
        String insertSql = "INSERT INTO carrinho (idCliente, idProduto, quantidade) VALUES (?, ?, ?)";
        String updateCarrinhoSql = "UPDATE Carrinho SET quantidade = ? WHERE idCliente = ? AND idProduto = ?";
        String updateProdutoEstoqueSql = "UPDATE Produto SET quantidade = quantidade - ? WHERE idProduto = ?";

        Connection conn = null;
        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);

            // 1) Verificar se o produto existe e tem estoque suficiente
            Produto produtoNoEstoque = consultarProdutoPorId(item.getIdProduto());
            if (produtoNoEstoque == null) {
                System.err.println("Erro: Produto com ID " + item.getIdProduto() + " não encontrado.");
                conn.rollback();
                return;
            }
            if (produtoNoEstoque.getQuantidade() < item.getQuantidade()) {
                System.err.println("Erro: Quantidade em estoque (" + produtoNoEstoque.getQuantidade() + ") insuficiente para o produto " + produtoNoEstoque.getNome() + ".");
                conn.rollback();
                return;
            }

            // 2) Verificar se o cliente existe
            Cliente clienteExiste = consultarClientePorId(item.getIdCliente());
            if (clienteExiste == null) {
                System.err.println("Erro: Cliente com ID " + item.getIdCliente() + " não encontrado.");
                conn.rollback();
                return;
            }

            // 3) Tentar encontrar o item no carrinho do cliente
            int quantidadeExistenteNoCarrinho = 0;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setLong(1, item.getIdCliente());
                checkStmt.setLong(2, item.getIdProduto());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        quantidadeExistenteNoCarrinho = rs.getInt("quantidade");
                    }
                }
            }

            if (quantidadeExistenteNoCarrinho > 0) {
                int novaQuantidadeTotalNoCarrinho = quantidadeExistenteNoCarrinho + item.getQuantidade();
                try (PreparedStatement updateStmt = conn.prepareStatement(updateCarrinhoSql)) {
                    updateStmt.setInt(1, novaQuantidadeTotalNoCarrinho);
                    updateStmt.setLong(2, item.getIdCliente());
                    updateStmt.setLong(3, item.getIdProduto());
                    updateStmt.executeUpdate();
                    System.out.println("Quantidade do produto " + item.getIdProduto() + " no carrinho do cliente " + item.getIdCliente() + " atualizada para " + novaQuantidadeTotalNoCarrinho + ".");
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setLong(1, item.getIdCliente());
                    insertStmt.setLong(2, item.getIdProduto());
                    insertStmt.setInt(3, item.getQuantidade());
                    insertStmt.executeUpdate();
                    System.out.println("Produto " + item.getIdProduto() + " adicionado ao carrinho do cliente " + item.getIdCliente() + ".");
                }
            }

            // 4) Diminuir a quantidade do produto no estoque
            try (PreparedStatement updateEstoqueStmt = conn.prepareStatement(updateProdutoEstoqueSql)) {
                updateEstoqueStmt.setInt(1, item.getQuantidade()); // Diminui a quantidade que foi adicionada
                updateEstoqueStmt.setLong(2, item.getIdProduto());
                updateEstoqueStmt.executeUpdate();
                System.out.println("Estoque do produto " + item.getIdProduto() + " atualizado.");
            }

            conn.commit();
            System.out.println("Operação de adição ao carrinho concluída com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar item ao carrinho no DB: " + e.getMessage());
            try {
                if (conn != null) {
                    System.err.println("Tentando reverter transação...");
                    conn.rollback();
                    System.err.println("Transação revertida.");
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao reverter transação: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }


    public static ArrayList<CarrinhoItem> listarItensDoCarrinho(Long idCliente) {
        ArrayList<CarrinhoItem> itensCarrinho = new ArrayList<>();
        String sql = "SELECT idCliente, idProduto, quantidade FROM Carrinho WHERE idCliente = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CarrinhoItem item = new CarrinhoItem(
                            rs.getLong("idCliente"),
                            rs.getLong("idProduto"),
                            rs.getInt("quantidade")
                    );
                    itensCarrinho.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar itens do carrinho no DB: " + e.getMessage());
        }
        return itensCarrinho;
    }

    public static void removerItemDoCarrinho(Long idCliente, Long idProduto, int quantidadeParaRemover) {
        String selectSql = "SELECT quantidade FROM Carrinho WHERE idCliente = ? AND idProduto = ?";
        String updateCarrinhoSql = "UPDATE Carrinho SET quantidade = ? WHERE idCliente = ? AND idProduto = ?";
        String deleteSql = "DELETE FROM Carrinho WHERE idCliente = ? AND idProduto = ?";
        String updateProdutoEstoqueSql = "UPDATE Produto SET quantidade = quantidade + ? WHERE idProduto = ?";

        Connection conn = null;
        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false);

            int quantidadeAtualNoCarrinho = 0;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setLong(1, idCliente);
                selectStmt.setLong(2, idProduto);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        quantidadeAtualNoCarrinho = rs.getInt("quantidade");
                    } else {
                        System.out.println("Produto com ID " + idProduto + " não encontrado no carrinho do cliente " + idCliente + ".");
                        conn.rollback();
                        return;
                    }
                }
            }

            if (quantidadeParaRemover <= 0) {
                System.out.println("A quantidade a remover deve ser maior que zero.");
                conn.rollback();
                return;
            }

            if (quantidadeParaRemover > quantidadeAtualNoCarrinho) {
                System.out.println("Não é possível remover " + quantidadeParaRemover + " unidades. Há apenas " + quantidadeAtualNoCarrinho + " no carrinho.");
                conn.rollback();
                return;
            }


            int quantidadeARestaurarEstoque = quantidadeParaRemover;

            if (quantidadeParaRemover == quantidadeAtualNoCarrinho) {
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setLong(1, idCliente);
                    deleteStmt.setLong(2, idProduto);
                    deleteStmt.executeUpdate();
                    System.out.println("Produto " + idProduto + " removido completamente do carrinho do cliente " + idCliente + ".");
                }
            } else {
                int novaQuantidadeNoCarrinho = quantidadeAtualNoCarrinho - quantidadeParaRemover;
                try (PreparedStatement updateStmt = conn.prepareStatement(updateCarrinhoSql)) {
                    updateStmt.setInt(1, novaQuantidadeNoCarrinho);
                    updateStmt.setLong(2, idCliente);
                    updateStmt.setLong(3, idProduto);
                    updateStmt.executeUpdate();
                    System.out.println("Quantidade do produto " + idProduto + " no carrinho do cliente " + idCliente + " atualizada para " + novaQuantidadeNoCarrinho + ".");
                }
            }

            try (PreparedStatement updateEstoqueStmt = conn.prepareStatement(updateProdutoEstoqueSql)) {
                updateEstoqueStmt.setInt(1, quantidadeARestaurarEstoque);
                updateEstoqueStmt.setLong(2, idProduto);
                updateEstoqueStmt.executeUpdate();
                System.out.println("Estoque do produto " + idProduto + " atualizado.");
            }

            conn.commit();
            System.out.println("Operação de remoção do carrinho concluída com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao remover item do carrinho no DB: " + e.getMessage());
            try {
                if (conn != null) {
                    System.err.println("Tentando reverter transação...");
                    conn.rollback();
                    System.err.println("Transação revertida.");
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao reverter transação: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }


    // main:

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random(); //

        while (true) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Consultar Produto");
            System.out.println("3 - Cadastrar Cliente");
            System.out.println("4 - Consultar Cliente");
            System.out.println("5 - Adicionar Produto ao Carrinho");
            System.out.println("6 - Atualizar dados do Produto");
            System.out.println("7 - Excluir Produto");
            System.out.println("8 - Excluir Cliente");
            System.out.println("9 - Listar Itens do Carrinho de um Cliente");
            System.out.println("10 - Remover Item do Carrinho");
            System.out.println("11 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = lerInt(scanner, "");

            switch (opcao) {
                case 1: // Cadastrar Produto
                    String nomeProduto = lerString(scanner, "Digite o nome do produto:");
                    String descricaoProduto = lerString(scanner, "Digite a descrição do produto:");
                    double precoProduto = lerDouble(scanner, "Digite o preço do produto:");
                    int quantidadeProduto = lerInt(scanner, "Digite a quantidade inicial em estoque do produto:");
                    String corProduto = lerString(scanner, "Digite a cor do produto:");
                    String categoriaProduto = lerString(scanner, "Digite a categoria do produto:");

                    Long idProdutoCadastrar = lerLong(scanner, "Digite o ID do produto (ou 0 para gerar um aleatório):");
                    if (idProdutoCadastrar == 0) {
                        boolean idJaExiste = true;
                        do{
                            idProdutoCadastrar = gerarIdAleatorioComLimite(5);
                            idJaExiste =(consultarProdutoPorId(idProdutoCadastrar)!=null);
                            if (idJaExiste){
                                System.out.println("ID de produto gerado("+idProdutoCadastrar+") já existe. Tentanto novamente...");
                            }
                        }while(idJaExiste);
                        System.out.println("ID do produto gerado: " + idProdutoCadastrar);

                    }

                    Produto novoProduto = new Produto(nomeProduto, descricaoProduto, precoProduto, quantidadeProduto, corProduto, categoriaProduto, idProdutoCadastrar);
                    inserirProduto(novoProduto); // Insere no BD
                    break;

                case 2: // Consultar Produto
                    System.out.println("Como deseja consultar o produto?");
                    System.out.println("1 - Lembro o ID");
                    System.out.println("2 - Não lembro o ID (Listar todos)");
                    int opcaoConsultaProduto = lerInt(scanner, "Escolha uma opção:");

                    if (opcaoConsultaProduto == 1) {
                        Long idParaConsultar = lerLong(scanner, "Digite o ID do produto:");
                        Produto produtoEncontrado = consultarProdutoPorId(idParaConsultar); // Consulta no BD
                        if (produtoEncontrado != null) {
                            System.out.println(produtoEncontrado.toString());
                        } else {
                            System.out.println("Produto não encontrado.");
                        }
                    } else if (opcaoConsultaProduto == 2) {
                        ArrayList<Produto> todosProdutos = consultarTodosProdutos(); // Consulta todos do BD
                        if (todosProdutos.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                        } else {
                            System.out.println("\n--- Lista de Produtos ---");
                            for (Produto p : todosProdutos) {
                                System.out.println(p.toString());
                            }
                            System.out.println("-------------------------\n");
                        }
                    } else {
                        System.out.println("Opção inválida.");
                    }
                    break;

                case 3: // Cadastrar Cliente
                    String nomeCliente = lerString(scanner, "Digite o nome do cliente:");
                    Long idClienteCadastrar = lerLong(scanner, "Digite o ID do cliente (ou 0 para gerar um aleatório):");
                    if (idClienteCadastrar == 0) {
                        idClienteCadastrar = Math.abs(random.nextLong()); // Garante ID positivo
                        System.out.println("ID gerado: " + idClienteCadastrar);
                    }
                    Cliente novoCliente = new Cliente(nomeCliente, idClienteCadastrar);
                    inserirCliente(novoCliente); // Insere no BD
                    break;

                case 4: // Consultar Cliente
                    System.out.println("Como deseja consultar o cliente?");
                    System.out.println("1 - Lembro o ID");
                    System.out.println("2 - Não lembro o ID (Listar todos)");
                    int opcaoConsultaCliente = lerInt(scanner, "Escolha uma opção:");

                    if (opcaoConsultaCliente == 1) {
                        Long idParaConsultarCliente = lerLong(scanner, "Digite o ID do cliente:");
                        Cliente clienteEncontrado = consultarClientePorId(idParaConsultarCliente); // Consulta no BD
                        if (clienteEncontrado != null) {
                            System.out.println(clienteEncontrado.toString());
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                    } else if (opcaoConsultaCliente == 2) {
                        ArrayList<Cliente> todosClientes = consultarTodosClientes(); // Consulta todos do BD
                        if (todosClientes.isEmpty()) {
                            System.out.println("Nenhum cliente cadastrado.");
                        } else {
                            System.out.println("\n--- Lista de Clientes ---");
                            for (Cliente c : todosClientes) {
                                System.out.println(c.toString());
                            }
                            System.out.println("-------------------------\n");
                        }
                    } else {
                        System.out.println("Opção inválida.");
                    }
                    break;

                case 5: // Adicionar Produto ao Carrinho
                    ArrayList<Produto> produtosDisponiveis = consultarTodosProdutos();
                    if (produtosDisponiveis.isEmpty()){
                        System.out.println("Nenhum produto disponível para adicionar ao carrinho");
                        break;
                    }else{
                        System.out.println("\n--- Produtos Disponíveis ---");
                        for (Produto p:produtosDisponiveis){
                            System.out.println(p.toString());
                        }
                        System.out.println("----------------------------\n");
                    }
                    Long idClienteParaCarrinho = lerLong(scanner, "Digite o ID do cliente que está adicionando ao carrinho:");
                    Long idProdutoParaCarrinho = lerLong(scanner, "Digite o ID do produto a ser adicionado:");
                    int quantidadeParaAdicionar = lerInt(scanner, "Digite a quantidade a ser adicionada:");

                    if (quantidadeParaAdicionar <= 0) {
                        System.out.println("A quantidade deve ser maior que zero.");
                        break;
                    }

                    CarrinhoItem novoItemCarrinho = new CarrinhoItem(idClienteParaCarrinho, idProdutoParaCarrinho, quantidadeParaAdicionar);
                    adicionarItemAoCarrinho(novoItemCarrinho);
                    break;

                case 6: // Atualizar dados do Produto
                    Long idParaAtualizarProduto = lerLong(scanner, "Digite o ID do produto que deseja atualizar:");
                    Produto produtoParaAtualizar = consultarProdutoPorId(idParaAtualizarProduto); // Busca no BD

                    if (produtoParaAtualizar != null) {
                        System.out.println("Produto encontrado. Informe os novos dados (deixe em branco para string, -1 para números para não alterar):");

                        String novoNome = lerString(scanner, "Digite o novo nome (atual: " + produtoParaAtualizar.getNome() + "):");
                        if (!novoNome.isEmpty()) { produtoParaAtualizar.setNome(novoNome); }

                        String novaDescricao = lerString(scanner, "Digite a nova descrição (atual: " + produtoParaAtualizar.getDescricao() + "):");
                        if (!novaDescricao.isEmpty()) { produtoParaAtualizar.setDescricao(novaDescricao); }

                        double novoPreco = lerDouble(scanner, "Digite o novo preço (atual: " + String.format("%.2f", produtoParaAtualizar.getPreco()) + ", digite -1 para não alterar):");
                        if (novoPreco != -1) { produtoParaAtualizar.setPreco(novoPreco); }

                        int novaQuantidade = lerInt(scanner, "Digite a nova quantidade em estoque (atual: " + produtoParaAtualizar.getQuantidade() + ", digite -1 para não alterar):");
                        if (novaQuantidade != -1) { produtoParaAtualizar.setQuantidade(novaQuantidade); }

                        String novaCor = lerString(scanner, "Digite a nova cor (atual: " + produtoParaAtualizar.getCor() + "):");
                        if (!novaCor.isEmpty()) { produtoParaAtualizar.setCor(novaCor); }

                        String novaCategoria = lerString(scanner, "Digite a nova categoria (atual: " + produtoParaAtualizar.getCategoria() + "):");
                        if (!novaCategoria.isEmpty()) { produtoParaAtualizar.setCategoria(novaCategoria); }

                        atualizarProduto(produtoParaAtualizar); // Atualiza no BD
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 7: // Excluir Produto
                    Long idParaExcluirProduto = lerLong(scanner, "Digite o ID do produto que deseja excluir:");
                    excluirProduto(idParaExcluirProduto); // Exclui do BD
                    break;

                case 8: // Excluir Cliente
                    Long idParaExcluirCliente = lerLong(scanner, "Digite o ID do cliente que deseja excluir:");
                    excluirCliente(idParaExcluirCliente); // Exclui do BD
                    break;

                case 9: // Listar Itens do Carrinho de um Cliente
                    Long idClienteListarCarrinho = lerLong(scanner, "Digite o ID do cliente para listar o carrinho:");
                    ArrayList<CarrinhoItem> itensDoCliente = listarItensDoCarrinho(idClienteListarCarrinho);
                    if (itensDoCliente.isEmpty()) {
                        System.out.println("O carrinho do cliente " + idClienteListarCarrinho + " está vazio ou cliente não encontrado.");
                    } else {
                        System.out.println("\n--- Itens no Carrinho do Cliente " + idClienteListarCarrinho + " ---");
                        for (CarrinhoItem item : itensDoCliente) {
                            Produto prod = consultarProdutoPorId(item.getIdProduto());
                            String nomeProd = (prod != null) ? prod.getNome() : "Produto Desconhecido";
                            System.out.println("  Produto: " + nomeProd + " (ID: " + item.getIdProduto() + "), Quantidade: " + item.getQuantidade());
                        }
                        System.out.println("--------------------------------------------------\n");
                    }
                    break;

                case 10: // Remover Item do Carrinho
                    Long idClienteRemover = lerLong(scanner, "Digite o ID do cliente:");
                    Long idProdutoRemover = lerLong(scanner, "Digite o ID do produto a ser removido do carrinho:");
                    int quantidadeRemover = lerInt(scanner, "Digite a quantidade a remover (digite a quantidade exata ou a quantidade total para remover todas as unidades):");

                    if (quantidadeRemover <= 0) {
                        System.out.println("Quantidade a remover deve ser positiva.");
                    } else {
                        removerItemDoCarrinho(idClienteRemover, idProdutoRemover, quantidadeRemover);
                    }
                    break;

                case 11: // Sair
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        }
    }
}