package dolgozok;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    Connection kapcs;
    PreparedStatement ekpar;
    ResultSet eredmeny;
    
    final String dbUrl = "jdbc:mysql://localhost:3306"
            + "?useUnicode=true&characterEncoding=UTF8";
    final String user = "root";
    final String pass = "";
    
    public DB() {
        String s1 = "CREATE DATABASE IF NOT EXISTS dolgozok";
        String s2 = "USE dolgozok";
        String s3 = "CREATE TABLE IF NOT EXISTS adatok (" +
                    "id int(4) NOT NULL AUTO_INCREMENT," +
                    "nev varchar(50)," +
                    "szulido date,"+
                    "fizetes int(7)," +
                    "PRIMARY KEY(id)"+
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4" +
                    "  COLLATE=utf8mb4_hungarian_ci;";
        
        try {
            // kapcsolódás a serverhez
            kapcs = DriverManager.getConnection(dbUrl, user, pass);
            // adatbázis létrehozása
            ekpar = kapcs.prepareStatement(s1);
            ekpar.execute();
            // adatbázis kijelölése
            ekpar = kapcs.prepareStatement(s2);
            ekpar.execute();
            // tábla létrehozása
            ekpar = kapcs.prepareStatement(s3);
            ekpar.execute();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
    }
    
    public void lista() {
        try {
            ekpar = kapcs.prepareStatement("SELECT * FROM adatok");
            eredmeny = ekpar.executeQuery();
            while (eredmeny.next()) {
                System.out.printf("%2d %-50s %s %10d\n",
                                   eredmeny.getInt("id"),
                                   eredmeny.getString("nev"),
                                   eredmeny.getDate("szulido"),
                                   eredmeny.getInt("fizetes"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void uj(String nev, String szulido, int fizetes) {
        String s = "INSERT INTO adatok (nev, szulido, fizetes) "
                 + "VALUES (?, ?, ?);";
        try {
            ekpar = kapcs.prepareStatement(s);
            ekpar.setString(1, nev);
            ekpar.setString(2, szulido);
            ekpar.setInt(3, fizetes);
            ekpar.executeUpdate();
            System.out.println(nev + " hozzáadva.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());            
        }
    }
    
}
