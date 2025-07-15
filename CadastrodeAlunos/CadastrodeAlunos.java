package Academia;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

// OLDEST VERSION

public class CadastrodeAlunos {
    static class Aluno {
        String nome;
        int idade;
        Long cpf;
        int planoescolhido;
        boolean pagamentoRealizado;

        //SETTERS E GETTERS - ALUNO
        public String getNome() {
            return nome;
        }

        public boolean isPagamentoRealizado() {
            return pagamentoRealizado;
        }

        public void setPagamentoRealizado(boolean pagamentoRealizado) {
            this.pagamentoRealizado = pagamentoRealizado;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getIdade() {
            return idade;
        }

        public void setIdade(int idade) {
            this.idade = idade;
        }

        public Long getCpf() {
            return cpf;
        }

        public void setCpf(Long cpf) {
            this.cpf = cpf;
        }

        public int getPlanoescolhido() {
            return planoescolhido;
        }

        public void setPlanoescolhido(int planoescolhido) {
            this.planoescolhido = planoescolhido;
        }

        //CONSTRUCTOR - ALUNO
        public Aluno(String nome, int idade, Long cpf, int planoescolhido, boolean pagamentoRealizado) {
            this.nome = nome;
            this.idade = idade;
            this.cpf = cpf;
            this.planoescolhido = planoescolhido;
            this.pagamentoRealizado = pagamentoRealizado;
        }
    }

    static class Plano {
        private String nomePlano;
        private int valor;
        private int duracao;
        private
        String servicos;
        private boolean personal;
        private int number;

        //CONSTRUCTOR - PLANO
        public Plano(String nomePlano, int valor, int duracao, String servicos, boolean personal, int number) {
            this.nomePlano = nomePlano;
            this.valor = valor;
            this.duracao = duracao;
            this.servicos = servicos;
            this.personal = personal;
            this.number = number;
        }

        //GETTERS E SETTERS - PLANO
        public String getNomePlano() {
            return nomePlano;
        }

        public int getValor() {
            return valor;
        }

        public boolean isPersonal() {
            return personal;
        }

        public int getDuracao() {
            return duracao;
        }

        public String getServicos() {
            return servicos;
        }

        public int getNumber() {
            return number;
        }

        public void setNomePlano(String nomePlano) {
            this.nomePlano = nomePlano;
        }

        public void setValor(int valor) {
            this.valor = valor;
        }

        public void setPersonal() {
            this.personal = personal;
        }

        public void setDuracao() {
            this.duracao = duracao;
        }

        public void setServicos() {
            this.servicos = servicos;
        }

        public void setNumber() {
            this.number = number;
        }
        @Override
        public String toString() {
            return "Plano: " + nomePlano +
                    " | Valor: R$" + valor +
                    " | Duração: " + duracao + " dias" +
                    " | Serviços: " + servicos +
                    " | Personal: " + (personal ? "Sim" : "Não") +
                    " | Número: " + number;
        }
    }

    static class Pagos {
        Long cpfPago;
        boolean pago;

        public Pagos(Long cpfPago, boolean pago) {
            this.cpfPago = cpfPago;
            this.pago = pago;
        }

        public Long getCpfPago() {
            return cpfPago;
        }

        public void setCpfPago(Long cpf) {
            this.cpfPago = cpfPago;
        }

        public boolean isPago() {
            return pago;
        }

        public void setPago(boolean pago) {
            this.pago = pago;
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
        ArrayList<Aluno> alunos = new ArrayList<>();
        ArrayList<Plano> planos = new ArrayList<>();
        ArrayList<Pagos> pagos = new ArrayList<>();
        boolean continuar = true;

        while (true) {
            System.out.println("SISTEMA DA ACADEMIA\n");
            System.out.println("1- Cadastrar novo aluno");
            System.out.println("2- Configurar Plano");
            System.out.println("3- Alterar Dados ou plano do aluno");
            System.out.println("4- Conferir Planos");
            System.out.println("5- Configurar Pagamento");
            System.out.println("6- Cancelar Matrícula");
            System.out.println("7- Ver lista de alunos inadimplentes ");
            System.out.println("8- Sair");
            int opcao1 = scanner.nextInt();
            scanner.nextLine();

            switch (opcao1) {
                case 1:
                    System.out.println("CADATRO DE NOVO ALUNO");
                    System.out.println("Digite o nome do aluno: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite o CPF do aluno: ");
                    Long cpf = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("Digite a idade do aluno: ");
                    int idade = scanner.nextInt();
                    System.out.println("Digite o número do plano escolhido pelo aluno: ");
                    int planoescolhido = scanner.nextInt();
                    boolean pagamentoRealizado = false;
                    if (idade >= 70) {
                        System.out.println("Você precisa analisar um atestado médico de capacidade física também");
                        System.out.println("O aluno apresentou atestado? ");
                        System.out.println("1-Sim");
                        System.out.println("2- Não");
                        int opcaoAtestado = scanner.nextInt();
                        scanner.nextLine();
                        if (opcaoAtestado == 1) {
                            alunos.add(new Aluno(nome, idade, cpf, planoescolhido, pagamentoRealizado)); // Precisa estar exatamente nessa ordem
                            System.out.println("Aluno cadastrado com sucesso! ");
                            break;
                        } else {
                            System.out.println("Não é possível concluir o cadastro. ");
                        }
                    } else {
                        alunos.add(new Aluno(nome, idade, cpf, planoescolhido, pagamentoRealizado)); // Precisa estar exatamente nessa ordem
                        try (Connection conn = Conexao.conectar()) {
                            String sql = "INSERT INTO Alunos (nome, idade, cpf, planoescolhido, pagamentoRealizado) VALUES (?, ?, ?, ?, ?)";
                            PreparedStatement stmt = conn.prepareStatement(sql);
                            stmt.setString(1, nome);
                            stmt.setInt(2, idade);
                            stmt.setLong(3, cpf);
                            stmt.setInt(4, planoescolhido);
                            stmt.setBoolean(5, pagamentoRealizado);
                            stmt.executeUpdate();
                            System.out.println("Aluno salvo no banco de dados!");
                        } catch (SQLException e) {
                            System.out.println("Erro ao salvar aluno no banco: " + e.getMessage());
                        }

                        break;
                    }
                case 2:
                    System.out.println("CONFIGURAR PLANO\n");
                    System.out.println("Digite o nome do plano que deseja adicionar: ");
                    String nomePlanoCriado = scanner.nextLine();
                    System.out.println("Digite o valor do plano, como um valor inteiro: ");
                    int valorDefinido = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite os benefícios do plano: ");
                    String beneficios = scanner.nextLine();
                    System.out.println("Digite a duração do plano: (Em meses) ");
                    int duracao = scanner.nextInt();
                    System.out.println("Digite o número do plano: ");
                    int number = scanner.nextInt();
                    System.out.println("O plano contará com a presença de personal? ");
                    System.out.println("1- Sim");
                    System.out.println("2- Não");
                    int temPersonal = scanner.nextInt();
                    scanner.nextLine();
                    boolean personal = temPersonal == 1;

                    boolean existe = false;
                    for (Plano p : planos) {
                        if (p.getNumber() == number) {
                            existe = true;
                            break;
                        }
                    }
                    if (existe) {
                        System.out.println("Já existe um plano com esse número.");
                        break;
                    }
                    Plano novoPlano = new Plano(nomePlanoCriado, valorDefinido, duracao, beneficios, personal, number);
                    planos.add(novoPlano);
                    try (Connection conn = Conexao.conectar()) {
                        String sql = "INSERT INTO Planos (nomePlano, valor, duracao, servicos, personal, number) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, nomePlanoCriado);
                        stmt.setInt(2, valorDefinido);
                        stmt.setInt(3, duracao);
                        stmt.setString(4, beneficios);
                        stmt.setBoolean(5, personal);
                        stmt.setInt(6, number);
                        stmt.executeUpdate();
                        System.out.println("Plano salvo no banco de dados!");
                    } catch (SQLException e) {
                        System.out.println("Erro ao salvar plano no banco: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("ALTERAR DADOS OU PLANO\n");
                    System.out.println("Digite o CPF do aluno que deseja alterar: ");
                    Long cpfAtualizar = scanner.nextLong();
                    scanner.nextLine();
                    Aluno aluno = null;
                    for (Aluno a : alunos) {
                        if (a.getCpf().equals(cpfAtualizar)) {
                            aluno = a;
                            break;
                        }
                        if (aluno != null) {
                            System.out.println("O que deseja alterar? ");
                            System.out.println("1- Nome");
                            System.out.println("2- CPF");
                            System.out.println("3- Idade");
                            System.out.println("4- Plano Escolhido");
                            System.out.println("5- Sair");
                            int opcaoalterar = scanner.nextInt();
                            scanner.nextLine();
                            switch (opcaoalterar) {
                                case 1:
                                    System.out.println("Digite o novo nome do aluno: ");
                                    String novoNome = scanner.nextLine();
                                    aluno.setNome(novoNome);
                                    System.out.println("Nome atualizado com sucesso! ");
                                    break;
                                case 2:
                                    System.out.println("Digite o novo CPF do aluno: ");
                                    Long novoCpf = scanner.nextLong();
                                    scanner.nextLine();
                                    aluno.setCpf(novoCpf);
                                    System.out.println("CPF atualizado com sucesso! ");
                                    break;
                                case 3:
                                    System.out.println("Digite a nova idade do aluno: ");
                                    int novaIdade = scanner.nextInt();
                                    scanner.nextLine();
                                    aluno.setIdade(novaIdade);
                                    System.out.println("Idade atualizada com sucesso! ");
                                case 4:
                                    System.out.println("ALTERAR PLANO\n");
                                    System.out.println("Digite o número do plano antigo escolhido pelo aluno:  ");
                                    int antigoPlano = scanner.nextInt();
                                    Plano plano = null;
                                    for (Plano np : planos) {
                                        if (np.getNumber() == antigoPlano) {
                                            plano = np;
                                            break;
                                        }
                                    }
                                    if (plano != null) {
                                        System.out.println("Digite o número do novo plano do aluno: ");
                                        int novoPlanoEscolhido = scanner.nextInt();
                                        aluno.setPlanoescolhido(novoPlanoEscolhido);
                                        System.out.println("Plano alterado com sucesso! ");
                                    } else {
                                        System.out.println("Plano não encontrado. ");
                                        break;
                                    }
                            }
                        } else {
                            System.out.println("Aluno não encontrado. ");
                        }
                    }
                    break;
                case 4:
                    System.out.println("PLANOS\n");
                    for (Plano p : planos) {
                        System.out.println(planos);
                    }
                    break;

                case 5:
                    System.out.println("EFETUAR PAGAMENTO\n");
                    System.out.println("Digite o CPF do aluno: ");
                    Long alunoBuscaPagamento = scanner.nextLong();
                    scanner.nextLine();
                    Pagos pago = null;
                    for (Aluno a : alunos) {
                        if (a.getCpf().equals(alunoBuscaPagamento)) {
                            System.out.println("O aluno realizou o pagamento? ");
                            System.out.println("1- Sim");
                            System.out.println("2- Não");
                            int opcaoPagamento = scanner.nextInt();
                            switch (opcaoPagamento) {
                                case 1:
                                    pagos.add(new Pagos(alunoBuscaPagamento, true));
                                    Boolean conferido = true;
                                    System.out.println("Pagamento registrado! ");
                                    break;
                                case 2:
                                    System.out.println("Ok! Aluno cadastrado na lista de inadimplentes. ");
                                    break;
                                default:
                                    System.out.println("Opção inválida. ");
                            }
                        } else {
                            System.out.println("Aluno não encontrado. ");
                            break;
                        }
                    }
                    break;
                case 6:
                    System.out.println("CANCELAR MATRÍCULA\n");
                    System.out.println("Digite o CPF do aluno que irá cancelar a matrícula: ");
                    Long CpfCancelar = scanner.nextLong();
                    scanner.nextLine();
                    Aluno CancelarMatricula = null;
                    for (Aluno a : alunos) {
                        if (a.getCpf().equals(CpfCancelar)) {
                            CancelarMatricula = a;
                            System.out.println("Matrícula cancelada. ");
                            break;
                        } else {
                            System.out.println("Aluno não encontrado. ");
                            break;
                        }
                    }
                case 7:
                    System.out.println("ALUNOS INADIMPLENTES\n");
                    boolean algumInandimplente = false;
                    for (Aluno a : alunos) {
                        boolean encontrado = false;

                        for (Pagos p : pagos) {
                            if (p.getCpfPago().equals(a.getCpf())) {
                                encontrado = true;
                                if (!p.isPago()) {
                                    System.out.println("Nome: " + a.getNome() + "| CPF: " + a.getCpf() + "| Plano: " + a.getPlanoescolhido() + "| Situação: Inadimplente. ");
                                    algumInandimplente = true;
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case 8:
                    System.out.println("Saindo...");
                    return;

                default:
                    System.out.println("Opção inválida. ");
                    break;
            }
        }
    }
}













