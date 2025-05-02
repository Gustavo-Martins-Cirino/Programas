package Projetos.Treinos;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Estoque {

    static class Produto {
        private Long id;
        private String nome;
        private int quantidade;
        private double preco;

        public Produto(Long id, String nome, int quantidade, double preco) {
            this.id = id;
            this.nome = nome;
            this.quantidade = quantidade;
            this.preco = preco;
        }

        public Long getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public double getPreco() {
            return preco;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public void setPreco(double preco) {
            this.preco = preco;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Nome: " + nome + ", Quantidade: " + quantidade + ", Preço: R$ " + preco;
        }
    }

    public static void gerarCSV(ArrayList<Produto> produtos) throws IOException {
        FileWriter writer = new FileWriter("estoque.csv");
        writer.write("ID        | Nome| Quantidade | Preço\n");
        for (Produto p : produtos) {
            writer.write(p.getId() + "|" + p.getNome() + "   |" + p.getQuantidade() + "          |" + p.getPreco() + "\n");
        }
        writer.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Produto> produtos = new ArrayList<>();

        while (true) {
            System.out.println("\n GERENCIADOR DE ESTOQUE: ");
            System.out.println("1- Cadastrar produto ");
            System.out.println("2- Atualizar o estoque ");
            System.out.println("3- Exibir produtos ");
            System.out.println("4- Gerar relatório de estoque CSV ");
            System.out.println("5- Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Digite o ID do produto: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Digite o nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite a quantidade de produtos: ");
                    int quantidade = scanner.nextInt();
                    System.out.println("Digite o preço do produto: ");
                    double preco = scanner.nextDouble();
                    produtos.add(new Produto(id, nome, quantidade, preco));
                    System.out.println("Produto cadastrad   o com sucesso! ");
                    break;
                case 2:
                    System.out.println("Digite o ID do produto que será atualizado: ");
                    Long idAtualizar = scanner.nextLong();
                    scanner.nextLine();
                    Produto produto = null;
                    for (Produto p : produtos) {
                        if (p.getId() == idAtualizar) {
                            produto = p;
                            break;
                        }
                    }
                    if (produto != null) {
                        System.out.println("Digite a nova quantidade do produto: ");
                        int novaQuantidade = scanner.nextInt();
                        produto.setQuantidade(novaQuantidade);
                        System.out.println("Quantidade atualizada com sucesso! ");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;
                case 3:
                    System.out.println("Lista de produtos: ");
                    for (Produto p : produtos) {
                        System.out.println(p);
                    }
                    break;
                case 4:
                    System.out.println("Gerando relatório de estoque...");
                    try {
                        gerarCSV(produtos);
                        System.out.println("Arquivo CSV gerado com sucesso!");
                    } catch (IOException e) {
                        System.out.println("Erro ao gerar arquivo CSV: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. ");
            }
        }
    }
}
