package dolgozok;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
    
    public void beolvas(String fnev) {
        try (Scanner be = new Scanner(new File(fnev))) {
            while (be.hasNextLine()) {
                String[] sor = be.nextLine().split(",");
                uj(sor[0],sor[1],Integer.parseInt(sor[2]));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }            
    }

    public void kiir(String fnev) {
        try (PrintWriter ki = new PrintWriter(fnev)) {
            try {
                ekpar = kapcs.prepareStatement("SELECT * FROM adatok");
                eredmeny = ekpar.executeQuery();
                while (eredmeny.next()) {
                    ki.println(eredmeny.getString("nev") + "," +
                               eredmeny.getDate("szulido") + "," +
                               eredmeny.getInt("fizetes"));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void emel(int szazalek) {
        String s = "UPDATE adatok SET fizetes = (1+?/100)*fizetes;";
        try {
            ekpar = kapcs.prepareStatement(s);
            ekpar.setInt(1, szazalek);
            int sorok = ekpar.executeUpdate();
            System.out.println(sorok + " sor módosítva.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());            
        }        
    }

    public void torol() {
        String s = "DELETE FROM adatok;";
        try {
            ekpar = kapcs.prepareStatement(s);
            int sorok = ekpar.executeUpdate();
            System.out.println(sorok + " sor törölve");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
    }    
}
