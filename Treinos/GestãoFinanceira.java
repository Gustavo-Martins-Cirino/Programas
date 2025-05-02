package Projetos.Treinos;

import java.util.Scanner;
import java.util.ArrayList;

public class GestãoFinanceira {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Long> valor = new ArrayList<>();
        boolean continuar = true;

        System.out.println("Bem-vindo ao Gerenciador de Finanças!");

        while (continuar) {
            System.out.println("Selecione o que deseja fazer");
            System.out.println("1- Adicionar ou remover valor");
            System.out.println("2- Vincular rendimentos");
            System.out.println("3- Consultar saldo");
            System.out.println("4- Planejamento Financeiro - Extrato e Renda vinculada");
            System.out.println("5- Elaborar um plano de compra");
            System.out.println("6- Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Deseja adicionar ou remover um valor? ");
                    System.out.println("1- Adicionar");
                    System.out.println("2- Remover");
                    int opcao2 = scanner.nextInt();
                    scanner.nextLine();
                    switch (opcao2) {
                        case 1:
                            System.out.println("Quanto foi seu rendimento? ");
                            Long deposito = scanner.nextLong();
                            scanner.nextLine();
                            valor.add(deposito);
                            System.out.println("Valor adicionado!");
                            break;
                        case 2:
                            System.out.println("Qual foi o seu gasto? ");
                            Long gasto = scanner.nextLong();
                            scanner.nextLine();
                            System.out.println("Deseja informar o motivo do gasto para que seja informado em seu extrato? ");
                            System.out.println("1- Sim");
                            System.out.println("2- Não");
                            int opcaoextrato = scanner.nextInt();
                            scanner.nextLine();
                            switch (opcaoextrato) {
                                case 1:
                                    System.out.println("Qual foi o motivo do gasto? ");
                                    String motivogasto = scanner.nextLine();
                                    valor.remove(Long.valueOf(gasto));
                                    System.out.println("Motivo informado: " + motivogasto);
                                    System.out.println("Valor removido!");
                                    break;
                                case 2:
                                    valor.remove(Long.valueOf(gasto));
                                    System.out.println("Valor removido!");
                                    break;
                                default:
                                    System.out.println("Opção inválida.");
                                    break;
                            }
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Você selecionou a sessão de vincular rendimentos! Aqui você deve destinar parte do seu rendimento para gastos vinculados e específicos.");
                    System.out.println("Digite um dos destinos da vinculação de renda");
                    String destino = scanner.nextLine();
                    System.out.println("Quanto você deseja vincular, em porcentagem? Deve ser um número inteiro!");
                    int vinculo = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Vínculo cadastrado com sucesso para " + destino + " com " + vinculo + "% do rendimento!");
                    break;
                case 3:
                    System.out.println("MINHA CONTA:");
                    long saldoTotal = valor.stream().mapToLong(Long::longValue).sum();
                    System.out.println("Saldo total: R$" + saldoTotal);
                    break;
                case 5:
                    System.out.println("Vamos elaborar um plano de compra!");
                    System.out.println("Qual o valor do que deseja comprar?");
                    Long valorcompra = scanner.nextLong();
                    scanner.nextLine();
                    long saldoDisponivel = valor.stream().mapToLong(Long::longValue).sum();
                    System.out.println("Contando que você tem R$" + saldoDisponivel + " em sua conta bancária:");
                    if (saldoDisponivel >= valorcompra) {
                        System.out.println("Você tem dinheiro suficiente para comprar isso! Porém, é aconselhável tomar cuidado com seus gastos!");
                    } else {
                        System.out.println("Você não tem dinheiro suficiente. Talvez seja necessário planejar melhor.");
                    }
                    break;
                case 6:
                    System.out.println("Saindo... Até mais!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
        scanner.close();
    }
}