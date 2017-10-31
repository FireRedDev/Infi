/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Benutzer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isi
 */
public class DatenbankRepository {

    private Statement statement;

    private static DatenbankRepository instance = null;

    private DatenbankRepository() {
        try {
            String url = "jdbc:derby://localhost:1527/infiDB";
            String user = "infi";
            String pwd = "infi";
            Connection connection = DriverManager.getConnection(url, user, pwd);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DatenbankRepository.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    public static DatenbankRepository getInstance() {
        //wenn noch keine Instance vorhanden, dann erzeugen
        if (instance == null) {
            instance = new DatenbankRepository();
        }
        return instance;
    }

    public boolean login(Benutzer user) {
        try {
            String sqlQuery = "select username, password from benutzer where username='"+user.getUsername()+"'";

            ResultSet rSet = statement.executeQuery(sqlQuery);

            while (rSet.next()) {
                String password = rSet.getString("password");
                boolean access = user.getPassword().equals(password);
                return access;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatenbankRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void init() {

    }

}
