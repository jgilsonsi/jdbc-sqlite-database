package com.devroots.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jgilson
 */
public class JMain {

    public static void main(String[] args) {

        //inserir dados --------------------------------------------------------
        try {
            Connection conn = JConnection.getInstance().getConnection();

            //String para armazenar o comando SQL
            String sql = "INSERT INTO posts (title, category) VALUES (?, ?);";

            //cria o objeto a partir da conexão
            PreparedStatement stmt = conn.prepareStatement(sql);

            //ajusta os parâmetros do comando SQL
            stmt.setString(1, "Podcasts"); //parâmetro 1 (title)
            stmt.setString(2, "hello Dev"); //parâmetro 2 (category)

            //executa o comando de atualização e finaliza a execução
            stmt.execute();
            stmt.close();

            //armazena no banco as alterações realizadas e fecha a conexão
            conn.commit();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        //consultar dados ------------------------------------------------------
        try {
            Connection conn = JConnection.getInstance().getConnection();

            //String para armazenar o comando SQL
            String sql = "SELECT * FROM posts;";

            //cria um objeto Statement a partir da conexão para enviar o SQL
            Statement stmt = conn.createStatement();

            //declara o conjunto de dados
            ResultSet rs;

            //executa a instrução de consulta e armazena o resultado
            rs = stmt.executeQuery(sql);

            //exibe os dados armazenados
            while (rs.next()) {
                System.out.println("Título: " + rs.getString("title"));
                System.out.println("Categoria: " + rs.getString("category") + "\n");
            }

            //finaliza a execução e conexão
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
