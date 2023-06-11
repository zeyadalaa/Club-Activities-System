package org.bibalex.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {


    static final String url = "jdbc:mysql://localhost:3306/club";
    static final String username = "root";
    static final String password = "123456";
    
    public Connection ConnectToDatabase() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
