package Projetos.Treinos.SistemaDeAgendamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {

    // COLOQUE AQUI OS DADOS EXATOS DO SEU application.properties
    private static final String URL = "jdbc:mysql://localhost:3306/agendamento";
    private static final String USUARIO = "root";
    private static final String SENHA = "@Gucirino";

    public static void main(String[] args) {
        Connection conexao = null;
        try {

            System.out.println("Tentando conectar ao banco de dados...");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);


            System.out.println("------------------------------------");
            System.out.println("✅ Conexão bem-sucedida!");
            System.out.println("------------------------------------");

        } catch (SQLException e) {
            System.out.println("------------------------------------");
            System.out.println("❌ FALHA na conexão!");
            System.out.println("Causa do erro:");
            e.printStackTrace();
            System.out.println("------------------------------------");

        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                    System.out.println("Conexão fechada.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}