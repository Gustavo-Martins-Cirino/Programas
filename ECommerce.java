package Projetos.Treinos;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;


public class ECommerce {
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

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public double getPreco() {
            return preco;
        }

        public void setPreco(double preco) {
            this.preco = preco;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public String getCor() {
            return cor;
        }

        public void setCor(String cor) {
            this.cor = cor;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public Long getIdProduto() {
            return idProduto;
        }

        public void setIdProduto(Long idProduto) {
            this.idProduto = idProduto;
        }

        public String toString() {
            return " Produto: " + nome +
                    "|Preço: " + preco +
                    "|Cor: " +cor +
                    "|Quantidade: " + quantidade +
                    "|ID: " + idProduto+
                    "|Descrição: " + descricao;

        }

    }

    public static long gerarIdAleatorio() {
        return new Random().nextLong(100000000);

    }

    static class Carrinho {
        String nome;
        int quantidade;
        Long idProduto;

        public Carrinho(String nome, int quantidade, Long idProduto) {
            this.nome = nome;
            this.quantidade = quantidade;
            this.idProduto = idProduto;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public Long getIdProduto() {
            return idProduto;
        }

        public void setIdProduto(Long idProduto) {
            this.idProduto = idProduto;
        }
    }

    static class Cliente {
        Long idCliente;

        public Cliente(Long idCliente) {
            this.idCliente = idCliente;
        }

        public Long getIdCliente() {
            return idCliente;
        }

        public void setIdCliente(Long idCliente) {
            this.idCliente = idCliente;
        }
    }
    static class Conexao {
        public static Connection conectar() {
            try{
                String url = "jdbc:mysql://localhost:3306/PROJECTS";
                String usuario = "root";
                String senha = "@Gucirino01";
                Connection conn = DriverManager.getConnection(url, usuario, senha);
                System.out.println("Conectado com sucesso!");
                return conn;
            } catch (SQLException e) {
                System.out.println("Erro ao conectar: " + e.getMessage());
                return null;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Produto> produtos = new ArrayList<>();
        ArrayList<Carrinho> carrinhos = new ArrayList<>();
        ArrayList<Cliente> clientes = new ArrayList<>();

        boolean continuar = true;
        while (continuar) {
            System.out.println("1- Cadastrar Produto");
            System.out.println("2- Cadastrar Clinte");
            System.out.println("3- Consultar Produto");
            System.out.println("4- Consultar Cliente");
            System.out.println("5- Carrinho de Compra");
            System.out.println("6- Atualizar dados do produto");
            System.out.println("7- Atualizar dados do Cliente");
            System.out.println("8- Excluir Produto");
            System.out.println("9- Excluir Cliente");
            System.out.println("10- Sair");
            int opcao1 = scanner.nextInt();
            scanner.nextLine();
            switch (opcao1) {
                case 1:
                    System.out.println("CADASTRAR UM PRODUTO: ");
                    System.out.println("Já tem algum ID definido para o produto ");
                    System.out.println("1- Sim");
                    System.out.println("2- Não, gerar ID aleatório");
                    int RespostaId = scanner.nextInt();
                    scanner.nextLine();
                    Long idProduto = null;

                    if (RespostaId == 1) {
                        System.out.println("Digite o ID do produto: ");
                        idProduto = scanner.nextLong();
                        scanner.nextLine();

                    } else if (RespostaId == 2) {
                        idProduto = gerarIdAleatorio();
                        System.out.println("O ID gerado foi: " + idProduto);
                    } else {
                        System.out.println("Opção inválida. ");
                        continue;
                    }
                    System.out.println("Digite o nome do produto:");
                    String nomeProduto = scanner.nextLine();


                    System.out.println("Digite o quantidade do produto: ");
                    int quantidadeProduto = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Digite o cor do produto: ");
                    System.out.println("Obs: Se o produto tiver mais de uma cor disponível em estoque, cadastre como um novo produto. ");
                    String corProduto = scanner.nextLine();

                    System.out.println("Digite o categoria do produto: ");
                    String categoriaProduto = scanner.nextLine();

                    System.out.println("Digite o valor do produto: ");
                    double precoProduto = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Digite uma breve descrição do produto: ");
                    String descricaoProduto = scanner.nextLine();

                    produtos.add(new Produto(nomeProduto, descricaoProduto, precoProduto, quantidadeProduto, corProduto, categoriaProduto, idProduto));
                    System.out.println("Produto adicionado com sucesso! ");

                    // Adicionando no Banco de Dados
                    try (Connection conn = Conexao.conectar()){
                        String sql = "INSERT INTO Produtos (nome, descrição, preço, quantidade, cor, categoria, ID) VALUES (?, ?, ?, ?, ?, ?,?)";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, nomeProduto);
                        stmt.setString(2, descricaoProduto);
                        stmt.setDouble(3, precoProduto);
                        stmt.setInt(4, quantidadeProduto);
                        stmt.setString(5, corProduto);
                        stmt.setString(6, categoriaProduto);
                        stmt.setLong(7, idProduto);
                        stmt.executeUpdate();
                        System.out.println("Aluno salvo no banco de dados!");

                    }catch(SQLException e) {
                        System.out.println("Erro ao salvar produto no banco de dados: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("CADASTRAR CLIENTE\n");
                    System.out.println("O cliente já possui algum ID? ");
                    System.out.println("1- Sim");
                    System.out.println("2- Não, gerar ID aleatório");
                    int opcaoIdCliente = scanner.nextInt();
                    scanner.nextLine();
                    if (opcaoIdCliente == 1) {
                        System.out.println("Digite o ID do cliente: ");
                        Long idCliente = scanner.nextLong();
                        scanner.nextLine();
                        Cliente cliente = new Cliente(idCliente);
                        System.out.println("Cliente adicionado com sucesso! ");

                        try (Connection conn = Conexao.conectar()){
                            String sql = "INSERT INTO Clientes (nome, descrição, preço, quantidade, cor, categoria, ID) VALUES (?, ?, ?, ?, ?)";
                            PreparedStatement stmt = conn.prepareStatement(sql);
                            stmt.setLong(1,idCliente);
                            stmt.executeUpdate();
                            System.out.println("Cliente salvo no banco de dados!");

                        }catch(SQLException e) {
                            System.out.println("Erro ao salvar cliente no banco de dados: " + e.getMessage());
                        }
                        break;

                    }else if (opcaoIdCliente == 2) {
                        System.out.println("Gerando ID aleatório...");
                        long idCliente = gerarIdAleatorio();
                        System.out.println("O ID gerado foi: " + idCliente);
                        break;
                    }else{
                        System.out.println("Opção inválida.");
                        break;
                    }

                case 3:
                    System.out.println("CONSULTAR PRODUTO\n");
                    System.out.println("Digite o ID do produto que deseja consultar: ");
                    boolean achado = false;
                    Long IdConsultar = scanner.nextLong();
                    scanner.nextLine();
                    for (Produto p: produtos){
                        if (p.getIdProduto().equals(IdConsultar)){
                            System.out.println(p.toString());
                            achado = true;
                            break;
                        }else{
                            System.out.println("Produto não encontrado. ");
                            break;
                        }
                    }
                case 4:
                    System.out.println("CONSULTAR CLIENTE\n");
                    System.out.println("Digite o ID do cliente: ");
                    boolean achadoCliente = false;
                    Long IdConsultarCliente = scanner.nextLong();
                    scanner.nextLine();
                    for (Cliente c:clientes){
                        if (c.getIdCliente().equals(IdConsultarCliente)){
                            System.out.println(c.toString());
                            achadoCliente = true;
                            break;
                        }else{
                            System.out.println("Cliente não encontrado. ");
                            break;
                        }
                    }
                case 5:
                    System.out.println("CARRINHO DE COMPRAS\n");
                    System.out.println("PRODUTOS DISPONÍVEIS: ");
                    System.out.println("____________________");
                    for (Produto p: produtos){
                        System.out.println(p.toString());
                    }
                    System.out.println("Digite o ID do produto que deseja adicionar ao carrinho");
                    Long produtoAdicionarAoCarrinho = scanner.nextLong();
                    scanner.nextLine();
                    for (Produto p: produtos){
                        if (p.getIdProduto().equals(produtoAdicionarAoCarrinho)){
                            System.out.println("Selecione a quantidade: ");
                            System.out.println("1- 1 Unidade");
                            System.out.println("2- 2 Unidades");
                            System.out.println("3- 3 Unidades");
                            int QuantidadeCarrinho = scanner.nextInt();
                            scanner.nextLine();
                            carrinhos.add(new Carrinho(p.getNome(), QuantidadeCarrinho,produtoAdicionarAoCarrinho));
                            System.out.println("Produto adicionado ao carrinho! ");
                            System.out.println("Deseja adicionar mais alguma coisa? ");
                            System.out.println("1- Sim");
                            System.out.println("2- Não");
                            int opcaoAdicionarOutroItem = scanner.nextInt();
                            scanner.nextLine();
                            if (opcaoAdicionarOutroItem == 1) {
                                System.out.println("Digite o ID do outro produto: ");
                                Long idOutroProduto = scanner.nextLong();
                                scanner.nextLine();
                                if (p.getIdProduto().equals(idOutroProduto)){
                                    System.out.println("Selecione a quantidade: ");
                                    System.out.println("1- 1 Unidade");
                                    System.out.println("2- 2 Unidades");
                                    System.out.println("3- 3 Unidades");
                                    int QuantidadeCarrinhoOutroProduto = scanner.nextInt();
                                    scanner.nextLine();
                                    carrinhos.add(new Carrinho(p.getNome(),QuantidadeCarrinhoOutroProduto , produtoAdicionarAoCarrinho));
                                    System.out.println("Produto adicionado com sucesso! ");
                                }
                            }
                            break;
                        }else{
                            System.out.println("Produto não encontrado. ");
                            break;
                        }
                    }
                case 6:
                    System.out.println("ATUALIZAR DADOS DO PRODUTO\n");
                    System.out.println("Consegue lembrar do ID do produto que deseja atualizar? ");
                    System.out.println("1- Sim");
                    System.out.println("2- Não");
                    int opcaoAtualizarDadosProduto = scanner.nextInt();
                    scanner.nextLine();
                    if (opcaoAtualizarDadosProduto == 1){
                        System.out.println("Ok! Digite o ID do produto: ");
                        Long IdProdutoAtualizarDados = scanner.nextLong();
                        scanner.nextLine();
                        for(Produto p: produtos){
                            if (p.getIdProduto().equals(IdProdutoAtualizarDados)){
                                System.out.println(p.toString());
                                System.out.println("O que deseja alterar? ");
                                System.out.println("Nome");
                                System.out.println("Preço");
                                System.out.println("Cor");
                                System.out.println("Quantidade");
                                System.out.println("ID");
                                System.out.println("Descrição");
                                int opcaoAtualizarDadosProdutos = scanner.nextInt();
                                scanner.nextLine();
                                switch (opcaoAtualizarDadosProdutos){
                                    case 1:
                                        System.out.println("Digite o novo nome do produto: ");
                                        String novoNome = scanner.nextLine();
                                        p.setNome(novoNome);
                                        System.out.println("Nome atualizado com sucesso! ");
                                        break;
                                    case 2:
                                        System.out.println("Digite o novo preço do produto: ");
                                        double novoPreco = scanner.nextDouble();
                                        scanner.nextLine();
                                        p.setPreco(novoPreco);
                                        System.out.println("Preço atualizado com sucesso! ");
                                        break;
                                    case 3:
                                        System.out.println("Digite a nova cor do produto: ");
                                        String novaCor = scanner.nextLine();
                                        p.setCor(novaCor);
                                        System.out.println("Cor atualizada com sucesso! ");
                                        break;
                                    case 4:
                                        System.out.println("Digite a nova quantidade do produto: ");
                                        int novaQuantidade = scanner.nextInt();
                                        scanner.nextLine();
                                        p.setQuantidade(novaQuantidade);
                                        System.out.println("Quantidade atualizada com sucesso! ");
                                        break;
                                    case 5:
                                        System.out.println("Digite o novo ID do produto: ");
                                        Long novoIdProduto = scanner.nextLong();
                                        scanner.nextLine();
                                        p.setIdProduto(novoIdProduto);
                                        System.out.println("ID do produto atualizado com sucesso! ");
                                        break;
                                    case 6:
                                        System.out.println("Digite a nova descrição do produto: ");
                                        String novaDescricao = scanner.nextLine();
                                        p.setDescricao(novaDescricao);
                                        System.out.println("Descricao atualizada com sucesso! ");
                                        break;
                                    default:
                                        System.out.println("Opção inválida. ");
                                        break;
                                }
                            }
                        }
                    }else if(opcaoAtualizarDadosProduto == 2){
                        System.out.println("Vamos relembrar disso juntos! Digite o nome do produto: ");
                        System.out.println("Obs: Não adicione nenhum espaço nem antes nem depois da palavra! ");
                        String nomeBuscarID = scanner.nextLine();
                        for (Produto p: produtos){
                            if (p.getNome().equals(nomeBuscarID)) {
                                System.out.println(p.toString());
                                System.out.println("O que deseja alterar? ");
                                System.out.println("Nome");
                                System.out.println("Preço");
                                System.out.println("Cor");
                                System.out.println("Quantidade");
                                System.out.println("ID");
                                System.out.println("Descrição");
                                int opcaoAtualizarDadosProdutos = scanner.nextInt();
                                scanner.nextLine();
                                switch (opcaoAtualizarDadosProdutos) {
                                    case 1:
                                        System.out.println("Digite o novo nome do produto: ");
                                        String novoNome = scanner.nextLine();
                                        p.setNome(novoNome);
                                        System.out.println("Nome atualizado com sucesso! ");
                                        break;
                                    case 2:
                                        System.out.println("Digite o novo preço do produto: ");
                                        double novoPreco = scanner.nextDouble();
                                        scanner.nextLine();
                                        p.setPreco(novoPreco);
                                        System.out.println("Preço atualizado com sucesso! ");
                                        break;
                                    case 3:
                                        System.out.println("Digite a nova cor do produto: ");
                                        String novaCor = scanner.nextLine();
                                        p.setCor(novaCor);
                                        System.out.println("Cor atualizada com sucesso! ");
                                        break;
                                    case 4:
                                        System.out.println("Digite a nova quantidade do produto: ");
                                        int novaQuantidade = scanner.nextInt();
                                        scanner.nextLine();
                                        p.setQuantidade(novaQuantidade);
                                        System.out.println("Quantidade atualizada com sucesso! ");
                                        break;
                                    case 5:
                                        System.out.println("Digite o novo ID do produto: ");
                                        Long novoIdProduto = scanner.nextLong();
                                        scanner.nextLine();
                                        p.setIdProduto(novoIdProduto);
                                        System.out.println("ID do produto atualizado com sucesso! ");
                                        break;
                                    case 6:
                                        System.out.println("Digite a nova descrição do produto: ");
                                        String novaDescricao = scanner.nextLine();
                                        p.setDescricao(novaDescricao);
                                        System.out.println("Descricao atualizada com sucesso! ");
                                        break;
                                    default:
                                        System.out.println("Opção inválida. ");
                                        break;
                                }
                            }else{
                                System.out.println("Produto não encontrado.");
                                break;
                            }
                        }
                    }
                case 7:
                    System.out.println("ATUALIZAR DADOS DO CLIENTE\n");
                    System.out.println("Consegue lembrar do ID do cliente que vai ter uma informação atualizada? ");
                    System.out.println("1- Sim");
                    System.out.println("2- Não");
                    int opcaoAtualizarDadosCliente = scanner.nextInt();
                    scanner.nextLine();
                    if (opcaoAtualizarDadosCliente == 1){
                        System.out.println("Ok! Digite o ID do cliente: ");
                        Long IdClienteAtualizarDados = scanner.nextLong();
                        scanner.nextLine();
                        for (Cliente c:clientes){
                            if (c.getIdCliente().equals(IdClienteAtualizarDados)){
                                System.out.println(c.toString());
                                System.out.println("Cliente encontrado! Vamos alterar o ID...");
                                System.out.println("Digite o novo ID do cliente: ");
                                long NovoIdCliente = scanner.nextLong();
                                scanner.nextLine();
                                c.setIdCliente(NovoIdCliente);
                                break;
                            }else{
                                System.out.println("Cliente não encontrado. ");
                                break;
                            }
                        }
                    }else if(opcaoAtualizarDadosCliente == 2){
                        System.out.println("Vamos relembrar o ID, digite o nome do produto: ");
                        System.out.println("Obs: Não adicione nenhum espaço nem antes nem depois da palavra!");
                        String nomeProdutoRelembrarId = scanner.nextLine();
                        for (Produto p:produtos) {
                            if (p.getNome().equals(nomeProdutoRelembrarId)) {
                                System.out.println(p.toString());
                                System.out.println("Caso esse seja o produto que estava procurando, digite o ID do mesmo: ");
                                System.out.println("Obs: Caso não tenha encontrado o produto, faça o processo novamente, verificando se há espaços indevidos ou qualquer mínima alteração" +
                                        "no nome do produto");
                                Long IdClienteAtualizarDados = scanner.nextLong();
                                scanner.nextLine();
                                for (Cliente c:clientes){
                                    if (c.getIdCliente().equals(IdClienteAtualizarDados)){
                                        System.out.println(c.toString());
                                        System.out.println("Cliente encontrado! Vamos alterar o ID...");
                                        System.out.println("Digite o novo ID do cliente: ");
                                        long NovoIdCliente = scanner.nextLong();
                                        scanner.nextLine();
                                        c.setIdCliente(NovoIdCliente);
                                        break;
                                    }else{
                                        System.out.println("Produto não encontrado. ");
                                    }
                                }
                            }
                        }
                    }else{
                        System.out.println("Resposta inválida. ");
                    }
                case 8:
                    System.out.println("EXCLUIR PRODUTO\n");
                    System.out.println("Consegue lembrar do ID do produto que deseja remover? ");
                    System.out.println("1- Sim");
                    System.out.println("2- Não");
                    int opcaoRemoverDadosProduto = scanner.nextInt();
                    scanner.nextLine();
                    if (opcaoRemoverDadosProduto == 1){
                        System.out.println("Ok! Digite o ID do produto: ");
                        Long IdProdutoRemoverDados = scanner.nextLong();
                        scanner.nextLine();
                        Produto ExcluirProduto = null;
                        for (Produto p: produtos){
                            if(p.getIdProduto().equals(IdProdutoRemoverDados)){
                                ExcluirProduto = p;
                                System.out.println("Produto excluído");
                                break;
                            }else{
                                System.out.println("Produto não encontrado. ");
                                break;
                            }
                        }
                    }else{
                        System.out.println("Vamos relembrar o ID, digite o nome do produto: ");
                        System.out.println("Obs: Não adicione nenhum espaço nem antes nem depois da palavra!");
                        String nomeProdutoRelembrarId = scanner.nextLine();
                        for (Produto p:produtos){
                            if (p.getNome().equals(nomeProdutoRelembrarId)){
                                System.out.println(p.toString());
                                System.out.println("Caso esse seja o produto que estava procurando, digite o ID do mesmo: ");
                                System.out.println("Obs: Caso não tenha encontrado o produto, faça o processo novamente, verificando se há espaços indevidos ou qualquer mínima alteração" +
                                        "no nome do produto");
                                Long IdExcluirProduto = scanner.nextLong();
                                scanner.nextLine();
                                Produto ProdutoRemover = null;
                                for (Produto excluir:produtos){
                                    if(p.getIdProduto().equals(IdExcluirProduto)){
                                        ProdutoRemover = excluir;
                                        break;
                                    }else{
                                        System.out.println("Produto não encontrado. ");
                                    }
                                }







                            }else{
                                System.out.println("Produto não encontrado");
                            }


                        }

                    }






                case 9:


                case 10:
                    System.out.println("Saindo ...");
                    return;
            }
        }
    }
}
