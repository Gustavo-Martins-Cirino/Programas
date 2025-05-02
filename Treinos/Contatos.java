package Projetos.Treinos;

import java.util.ArrayList;
import java.util.Scanner;

public class Contatos {

    static class Agenda {
        private String name;
        private Long number;
        private String email;

        public Agenda(String name, Long number, String email) {
            this.name = name;
            this.number = number;
            this.email = email;
        }

        public Long getNumber() {
            return number;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNumber(Long number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "Nome: " + name + "\nNúmero: " + number + "\nE-mail: " + email + "\n";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Agenda> agenda = new ArrayList<>();
        while (true) {
            System.out.println("\nAGENDA DE CONTATOS:");
            System.out.println("1- Adicionar um contato");
            System.out.println("2- Listar todos contatos");
            System.out.println("3- Buscar um contato");
            System.out.println("4- Atualizar informações");
            System.out.println("5- Remover um contato");
            System.out.println("6- Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do contato: ");
                    String name = scanner.nextLine();
                    System.out.println("Digite o telefone do contato: ");
                    Long number = scanner.nextLong();
                    scanner.nextLine(); // Limpar buffer
                    System.out.println("Digite o E-mail do contato: ");
                    String email = scanner.nextLine();
                    agenda.add(new Agenda(name, number, email));
                    System.out.println("Contato cadastrado com sucesso! ");
                    break;
                case 2:
                    System.out.println("Lista de Contatos: ");
                    for (Agenda a : agenda) {
                        System.out.println(a);
                    }
                    break;
                case 3:
                    System.out.println("Deseja buscar um contato pelo nome, pelo número ou pelo E-mail? ");
                    System.out.println("1- Nome");
                    System.out.println("2- Número ");
                    System.out.println("3- E-mail ");
                    int opcaoBusca = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer
                    switch (opcaoBusca) {
                        case 1:
                            System.out.println("Digite o nome do contato: ");
                            String nameBusca = scanner.nextLine();
                            for (Agenda a : agenda) {
                                if (a.getName().equals(nameBusca)) {
                                    System.out.println(a);
                                }
                            }
                            break;
                        case 2:
                            System.out.println("Digite o número do contato: ");
                            Long numberBusca = scanner.nextLong();
                            scanner.nextLine(); // Limpar buffer
                            for (Agenda a : agenda) {
                                if (a.getNumber().equals(numberBusca)) {
                                    System.out.println(a);
                                }
                            }
                            break;
                        case 3:
                            System.out.println("Digite o E-mail do contato: ");
                            String emailBusca = scanner.nextLine();
                            for (Agenda a : agenda) {
                                if (a.getEmail().equals(emailBusca)) {
                                    System.out.println(a);
                                }
                            }
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                    break;
                case 4:
                    System.out.println("Digite o número do contato que vai ser alterado: ");
                    Long numberAtualizar = scanner.nextLong();
                    scanner.nextLine(); // Limpar buffer
                    Agenda contatoAtualizar = null;
                    for (Agenda a : agenda) {
                        if (a.getNumber().equals(numberAtualizar)) {
                            contatoAtualizar = a;
                            break;
                        }
                    }
                    if (contatoAtualizar != null) {
                        System.out.println("O que deseja alterar? ");
                        System.out.println("1- Número ");
                        System.out.println("2- E-mail ");
                        System.out.println("3- Nome ");
                        int opcaoAlterar = scanner.nextInt();
                        scanner.nextLine(); // Limpar buffer
                        switch (opcaoAlterar) {
                            case 1:
                                System.out.println("Digite o novo número do contato: ");
                                Long novoNumber = scanner.nextLong();
                                scanner.nextLine(); // Limpar buffer
                                contatoAtualizar.setNumber(novoNumber);
                                System.out.println("Número atualizado com sucesso! ");
                                break;
                            case 2:
                                System.out.println("Digite o novo E-mail do contato: ");
                                String novoEmail = scanner.nextLine();
                                contatoAtualizar.setEmail(novoEmail);
                                System.out.println("E-mail atualizado com sucesso! ");
                                break;
                            case 3:
                                System.out.println("Digite o novo nome do contato: ");
                                String novoNome = scanner.nextLine();
                                contatoAtualizar.setName(novoNome);
                                System.out.println("Nome atualizado com sucesso! ");
                                break;
                            default:
                                System.out.println("Opção inválida.");
                        }
                    } else {
                        System.out.println("Contato não encontrado.");
                    }
                    break;
                case 5:
                    System.out.println("Digite o nome do contato que vai ser removido: ");
                    String nameRemover = scanner.nextLine();
                    Agenda contatoRemover = null;
                    for (Agenda a : agenda) {
                        if (a.getName().equals(nameRemover)) {
                            contatoRemover = a;
                            break;
                        }
                    }
                    if (contatoRemover != null) {
                        agenda.remove(contatoRemover);
                        System.out.println("Contato removido com sucesso! ");
                    } else {
                        System.out.println("Contato não encontrado.");
                    }
                    break;
                case 6:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
