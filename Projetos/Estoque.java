package Projetos;

import java.util.ArrayList;
import java.util.Scanner;

public class Estoque {

    static class Produto {
        private int id;
        private String nome;
        private int quantidade;
        private double preco;

        public Produto(int id, String nome, int quantidade, double preco) {
            this.id = id;
            this.nome = nome;
            this.quantidade = quantidade;
            this.preco = preco;
        }

        public int getID() {
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

        public void setID(int id) {
            this.id = id;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public void setPreco(double preco) {
            this.preco = preco;
        }

        public String toString() {
            return "ID: " + id + ", Nome: " + nome + ", Quantidade: " + quantidade + ", Preço: R$ " + preco;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Produto> produtos = new ArrayList<>();

        while (true) {
            System.out.println("\n GERENCIADOR DE ESTOQUE: ");
            System.out.println("1- Cadastrar produto ");
            System.out.println("2- Atualizar o estoque ");
            System.out.println("3- Exibir produtos ");
            System.out.println("4- Buscar um produto ");
            System.out.println("5- Gerar relatório de estoque ");
            System.out.println("6- Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar o buffer
            switch (opcao) {
                case 1:
                    System.out.println("Digite o ID do produto: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite o nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite a quantidade de produtos: ");
                    int quantidade = scanner.nextInt();
                    System.out.println("Digite o preço do produto: ");
                    double preco = scanner.nextDouble();
                    produtos.add(new Produto(id, nome, quantidade, preco));
                    System.out.println("Produto cadastrado com sucesso! ");
                    break;
                case 2:
                    System.out.println("Digite o ID do produto que vai ser atualizado: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();
                    Produto produto = null;
                    for (Produto p : produtos) {
                        if (p.getID() == idAtualizar) {
                            produto = p;
                            break;
                        }
                    }
                    if (produto != null) {
                        System.out.println("O que deseja alterar ?");
                        System.out.println("1- Nome do produto ");
                        System.out.println("2- Quantidade do produto ");
                        System.out.println("3- Preço do produto");
                        System.out.println("4- ID do produto ");
                        int opcaoAlterar = scanner.nextInt();
                        scanner.nextLine();
                        switch (opcaoAlterar) {
                            case 1:
                                System.out.println("Digite o novo nome do produto: ");
                                String novoNome = scanner.nextLine();
                                produto.setNome(novoNome);
                                System.out.println("Nome atualizado com sucesso! ");
                                break;
                            case 2:
                                System.out.println("Digite a nova quantidade do produto: ");
                                int novaQuantidade = scanner.nextInt();
                                produto.setQuantidade(novaQuantidade);
                                System.out.println("Quantidade atualizada com sucesso! ");
                                break;
                            case 3:
                                System.out.println("Digite o novo preço do produto: ");
                                double novoPreco = scanner.nextDouble();
                                produto.setPreco(novoPreco);
                                System.out.println("Preço atualizado com sucesso! ");
                                break;
                            case 4:
                                System.out.println("Digite o novo ID: ");
                                int novoID = scanner.nextInt();
                                produto.setID(novoID);
                                System.out.println("ID atualizado com sucesso! ");
                                break;
                            default:
                                System.out.println("Opção inválida. ");
                        }
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
                    System.out.println("Deseja buscar por ID ou nome? ");
                    System.out.println("1- ID");
                    System.out.println("2- Nome ");
                    int opcaoBusca = scanner.nextInt();
                    scanner.nextLine();
                    switch (opcaoBusca) {
                        case 1:
                            System.out.println("Digite o ID do produto: ");
                            int idBusca = scanner.nextInt();
                            Produto produtoBuscaID = null;
                            for (Produto p : produtos) {
                                if (p.getID() == idBusca) {
                                    produtoBuscaID = p;
                                    break;
                                }
                            }
                            if (produtoBuscaID != null) {
                                System.out.println(produtoBuscaID);
                            } else {
                                System.out.println("Produto não encontrado. ");
                            }
                            break;
                        case 2:
                            System.out.println("Digite o nome do produto: ");
                            String nomeBusca = scanner.nextLine();
                            Produto produtoBuscaNome = null;
                            for (Produto p : produtos) {
                                if (p.getNome().equals(nomeBusca)) {
                                    produtoBuscaNome = p;
                                    break;
                                }
                            }
                            if (produtoBuscaNome != null) {
                                System.out.println(produtoBuscaNome);
                            } else {
                                System.out.println("Produto não encontrado ");
                            }
                            break;
                        default:
                            System.out.println("Opção inválida. ");
                    }
                    break;
                case 5:
                    double valorTotalEstoque = 0;
                    for (Produto p : produtos) {
                        valorTotalEstoque += p.getQuantidade() * p.getPreco();
                    }
                    System.out.println("Valor total do estoque: R$ " + valorTotalEstoque);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. ");
            }
        }
    }
}
