package Projetos.Treinos;

import java.util.ArrayList;
import java.util.Scanner;


public class Tarefas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tarefas = new ArrayList<>();
        ArrayList<Boolean> concluido = new ArrayList<>();
        while (true) {
            System.out.println("\nGERENCIADOR DE TAREFAS");
            System.out.println("1- Adicionar Tarefa");
            System.out.println("2- Listar Tarefas" );
            System.out.println("3- Marcar Tarefa como Concluída");
            System.out.println("4- Sair");
            System.out.println("Escolha uma opção");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar o buffer, isto é, espaço da memória usado pra armazenar dados temporários

            switch (opcao) {
                case 1:
                    System.out.println("Digite a nova tarefa: ");
                    String tarefa = scanner.nextLine();
                    tarefas.add(tarefa);
                    concluido.add(false);
                    System.out.println("Tarefa adicionada! ");
                    break;
                case 2:
                    if (tarefas.isEmpty()){
                        System.out.println("Nenhuma tarefa cadastrada. ");
                    } else {
                        System.out.println("\n Lista de Tarefas: ");
                        for (int i = 0; i<tarefas.size(); i++) {
                            String status = concluido.get(i) ?"[✔]" : "[ ]";
                            System.out.println((i+1) + "." + status + "" + tarefas.get(i));
                        }
                    }
                    break;

                case 3:
                    if(tarefas.isEmpty()){
                        System.out.println("Nenhuma tarefa para concluir.");
                    } else {
                        System.out.println("Digite o número da tarefa concluída: ");
                        int num = scanner.nextInt();
                        if (num > 0 && num <= tarefas.size()){
                            concluido.set(num-1, true);
                            System.out.println("Tarefa marcada como concluída! ");
                        } else {
                            System.out.println("Número inválido");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Saindo... Até mais! ");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
