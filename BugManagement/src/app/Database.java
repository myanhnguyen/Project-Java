package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public Connection con;
    public Connection getConnection() {
        String user = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/project";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    
}