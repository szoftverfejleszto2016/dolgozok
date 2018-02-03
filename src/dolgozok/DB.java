package dolgozok;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    Connection kapcs;
    PreparedStatement ekpar;
    ResultSet eredmeny;
    
    final String dbUrl = "jdbc:mysql://localhost:3306"
            + "?useUnicode=true&characterEncoding=UTF8";
    final String user = "root";
    final String pass = "";
}
