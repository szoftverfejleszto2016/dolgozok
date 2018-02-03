package dolgozok;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
    Connection kapcs;
    PreparedStatement ekpar;
    ResultSet eredmeny;
    
    final String dbUrl = "jdbc:mysql://localhost:3306"
            + "?useUnicode=true&characterEncoding=UTF8";
    final String user = "root";
    final String pass = "";
    
    public DB() {
        try {
            kapcs = DriverManager.getConnection(dbUrl, user, pass);
            System.out.println("ok");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
