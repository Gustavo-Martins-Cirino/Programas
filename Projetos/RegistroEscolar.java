package Projetos;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Aluno {
    String nome;
    Long matricula;
    ArrayList<Double> notas;
    double media;
    boolean aprovado;

    Aluno(String nome, Long matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.notas = new ArrayList<>();
        this.media = 0.0;
        this.aprovado = false;
    }

    void adicionarNota(double nota) {
        if (nota >= 0 && nota <= 10) { // Validação da nota
            notas.add(nota);
        } else {
            System.out.println("Nota inválida! Insira um valor entre 0 e 10.");
        }
    }

    void calcularMedia(double mediaMinima) {
        if (notas.isEmpty()) {
            media = 0;
        } else {
            double soma = 0.0;
            for (double nota : notas) {
                soma += nota;
            }
            media = soma / notas.size();
        }
        aprovado = media >= mediaMinima;
    }
}

public class RegistroEscolar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Long, Aluno> alunos = new HashMap<>();

        System.out.println("Bem-vindo ao Registro Escolar! Digite a média mínima para aprovação: ");
        double mediaMinima = scanner.nextDouble();
        scanner.nextLine();

        while (true) {
            System.out.println("\nREGISTRO DE APROVAÇÕES:");
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Adicionar novo aluno e suas notas");
            System.out.println("2 - Relatório Final");
            System.out.println("3 - Exportar para CSV");
            System.out.println("4 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do aluno: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite a matrícula do aluno: ");
                    Long matricula = scanner.nextLong();
                    scanner.nextLine();

                    Aluno aluno = new Aluno(nome, matricula);
                    alunos.put(matricula, aluno);

                    System.out.println("Digite as notas do aluno (0 a 10). Para finalizar, digite um número negativo:");

                    while (true) {
                        System.out.println("Digite a nota do aluno: ");
                        double nota = scanner.nextDouble();
                        if (nota < 0) {
                            break;
                        }
                        aluno.adicionarNota(nota);
                    }
                    aluno.calcularMedia(mediaMinima);
                    System.out.println("Projetos.Aluno cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.println("\nRelatório Final:");
                    for (Aluno a : alunos.values()) {
                        System.out.println("Nome: " + a.nome + " | Matrícula: " + a.matricula + " | Média: " + String.format("%.2f", a.media) + " | Status: " + (a.aprovado ? "Aprovado" : "Reprovado"));
                    }
                    break;

                case 3:
                    System.out.println("Exportando dados para arquivo CSV...");
                    try {
                        gerarCSV(alunos);
                        System.out.println("Arquivo CSV gerado com sucesso!");
                    } catch (IOException e) {
                        System.out.println("Erro ao gerar arquivo CSV: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Saindo... Até logo!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    static void gerarCSV(Map<Long, Aluno> alunos) throws IOException {
        FileWriter writer = new FileWriter("alunos.csv");

        writer.write("Nome,Matrícula,Média,Status\n");

        for (Aluno aluno : alunos.values()) {
            String status = aluno.aprovado ? "Aprovado" : "Reprovado";
            writer.write(aluno.nome + "," + aluno.matricula + "," + String.format("%.2f", aluno.media) + "," + status + "\n");
        }
        writer.close();
    }
}
