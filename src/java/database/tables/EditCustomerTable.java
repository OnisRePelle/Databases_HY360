/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database.tables;

import com.google.gson.Gson;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Customer;

/**
 *
 * @author dimos
 */
public class EditCustomerTable {

    public void addCustomerFromJSON(String json) throws ClassNotFoundException {
        Customer cus = jsonToCustomer(json);
        addNewCustomer(cus);
    }

    public Customer jsonToCustomer(String json) {
        Gson gson = new Gson();

        Customer cust = gson.fromJson(json, Customer.class);
        return cust;
    }

    public String CustomerToJSON(Customer cust) {
        Gson gson = new Gson();

        String json = gson.toJson(cust, Customer.class);
        return json;
    }

    public void updateUser(String username, String key, String value) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE Customer SET " + key + "='" + value + "' WHERE Username = '" + username + "'";
        stmt.executeUpdate(update);
        stmt.close();
        con.close();
    }

    public Customer databaseToCustomerFromId(String cuId) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM Customer WHERE CustomerId = '" + cuId + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Customer user = gson.fromJson(json, Customer.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Customer databaseToCustomer(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM Customer WHERE Username = '" + username + "' AND Password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Customer user = gson.fromJson(json, Customer.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String databaseUserToJSON(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM Customer WHERE Username = '" + username + "' AND Password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            return json;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void createCustomerTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE Customer "
                + "(CustomerId INTEGER not NULL AUTO_INCREMENT, "
                + "    Username VARCHAR(30) not null unique,"
                + "    Password VARCHAR(30) not null,"
                + "    Firstname VARCHAR(30) not null,"
                + "    Lastname VARCHAR(30) not null,"
                + "    Email VARCHAR(50) not null unique,	"
                + "    Number  VARCHAR (20) not null,"
                + "    Expiredate VARCHAR(6) not null,"
                + "    Securitycode VARCHAR(3) not null,"
                + "    Balance INTEGER (14) not null,"
                + " PRIMARY KEY (CustomerId))";
        stmt.execute(query);
        stmt.close();
    }

    public void addNewCustomer(Customer cust) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " Customer (Username, Password, Firstname, Lastname, Email, Number, Expiredate, Securitycode,Balance)"
                    + " VALUES ("
                    + "'" + cust.getUsername() + "',"
                    + "'" + cust.getPassword() + "',"
                    + "'" + cust.getFirstname() + "',"
                    + "'" + cust.getLastname() + "',"
                    + "'" + cust.getEmail() + "',"
                    + "'" + cust.getNumber() + "',"
                    + "'" + cust.getExpiredate() + "',"
                    + "'" + cust.getSecuritycode() + "',"
                    + "'" + 0 + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The Customer was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditCustomerTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
