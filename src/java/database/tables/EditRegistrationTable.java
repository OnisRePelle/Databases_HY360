/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database.tables;

import database.DB_Connection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dimos
 */
public class EditRegistrationTable {

    public void createRegistrationTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE Registration "
                + "(EventId INTEGER not NULL AUTO_INCREMENT, "
                + "Name VARCHAR(100) not null,"
                + "Date DATE not null,"
                + "Time DATETIME  not null, "
                + "Type VARCHAR(100) not null,"
                + "Capacity INTEGER, "
                + "PRIMARY KEY (EventId ))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }

}
