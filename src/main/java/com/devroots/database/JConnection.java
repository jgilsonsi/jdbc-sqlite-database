package com.devroots.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jgilson
 */
public final class JConnection {

    //declara a classe de conexão
    private static JConnection instance;

    //define a URL onde será armazenado o banco, neste caso, na home do usuário
    private final String urlDatabase = System.getProperty("user.home") + File.separator + "dev_database.db";

    public JConnection() {
        //cria tabelas caso as mesmas não existam no banco        
        Connection conn = getConnection();

        try {
            //cria o objeto a partir da conexão
            Statement stmt = conn.createStatement();

            //string para armazenar o comando SQL
            String sql = "CREATE TABLE IF NOT EXISTS posts ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "title VARCHAR(100) NOT NULL, "
                    + "category VARCHAR(50) NOT NULL);";

            //executa o comando de atualização e finaliza a execução
            stmt.executeUpdate(sql);
            stmt.close();

            //armazena no banco as alterações realizadas e fecha a conexão
            conn.commit();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //utilizado Singleton para que a classe seja instanciada uma única vez
    public static synchronized JConnection getInstance() {
        //verifica se a classe já foi instanciada
        if (instance == null) {
            //instancia a classe de conexão
            instance = new JConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        //declara a conexão
        Connection conn = null;

        try {
            //URL de conexão
            String url = "jdbc:sqlite:" + urlDatabase;

            //carrega o driver do SQLite
            Class.forName("org.sqlite.JDBC");

            //obtém uma instância para o objeto que representa a conexão
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            Logger.getLogger(JConnection.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        //retorna a conexão entre o programa Java e o banco de dados
        return conn;
    }

}
