package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public Connection con;
    public Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "123456");       
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }    
}