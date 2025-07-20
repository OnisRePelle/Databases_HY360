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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Tickets;

/**
 *
 * @author dimos
 */
public class EditTicketsTable {

    public void addTicketFromJSON(String json) throws ClassNotFoundException {
        Tickets tck = jsonToTicket(json);
        createNewTicket(tck);
    }

    public Tickets jsonToTicket(String json) {
        Gson gson = new Gson();
        Tickets tck = gson.fromJson(json, Tickets.class);
        return tck;
    }

    public String TicketToJSON(Tickets bt) {
        Gson gson = new Gson();

        String json = gson.toJson(bt, Tickets.class);
        return json;

    }

    public ArrayList<Tickets> databaseToTickets() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Tickets> tickets = new ArrayList<Tickets>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM Tickets");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                System.out.println(json);
                Gson gson = new Gson();
                Tickets tck = gson.fromJson(json, Tickets.class);
                tickets.add(tck);
            }
            return tickets;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<String> giveAvailableTickets() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<String> tickets = new ArrayList<String>();
        ResultSet rs;

        try {
            String query = "SELECT * FROM Tickets ";

            System.out.println(query);
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                tickets.add(json);
            }
            return tickets;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;

    }

    /*
    public ArrayList<Incident> databaseToIncidentsSearch(String type, String status, String municipality) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Incident> incidents = new ArrayList<Incident>();
        ResultSet rs;
        String where = "WHERE";
        if (!type.equals("all")) {
            where += " incident_type='" + type + "'";
        }
        if (!status.equals("all")) {
            if (!where.equals("WHERE")) {
                where += " and status='" + status + "'";
            } else {
                where += " status='" + status + "'";
            }
        }
        if (!municipality.equals("all") && !municipality.equals("")) {
            if (!where.equals("WHERE")) {
                where += " and municipality='" + municipality + "'";
            } else {
                where += " municipality='" + municipality + "'";
            }
        }
        try {
            String query = "SELECT * FROM incidents ";
            if (!where.equals("WHERE")) {
                query += where;
            }
            System.out.println(query);
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Incident incident = gson.fromJson(json, Incident.class);
                incidents.add(incident);
            }
            return incidents;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
*/
 /*
    public void updateIncident(String id, HashMap<String, String> updates) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        Incident bt = new Incident();
        for (String key : updates.keySet()) {
            String update = "UPDATE incidents SET " + key + "='" + updates.get(key) + "'" + "WHERE incident_id = '" + id + "'";
            stmt.executeUpdate(update);
        }
        stmt.close();
        con.close();
    }
*/
    public void deleteTicket(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE * FROM Tickets WHERE TicketId='" + id + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }

    public void createTicketsTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String sql = "CREATE TABLE Tickets "
                + "(TicketId INTEGER not NULL AUTO_INCREMENT, "
                + "EventId INTEGER not null,"
                + "Type VARCHAR(10) not null,"
                + "Price INTEGER not null,"
                + "Availability INTEGER not null, "
                + "PRIMARY KEY (TicketId ))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewTicket(Tickets tck) throws ClassNotFoundException {

        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " Tickets ( EventId, Type, Price, Availability )"
                    + " VALUES ("
                    + "'" + tck.getEventId() + "',"
                    + "'" + tck.getType() + "',"
                    + "'" + tck.getPrice() + "',"
                    + "'" + tck.getAvailability() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The Ticket was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditTicketsTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tickets getTicket(int EventId, String Type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try {
            String query = "SELECT * FROM Tickets WHERE EventId = '" + EventId + "' AND Type ='" + Type + "'";

            System.out.println(query);
            rs = stmt.executeQuery(query);
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            System.out.println(rs);
            System.out.println(json);

            Tickets tck = jsonToTicket(json);

            // Extract the value of the "TicketId" key as an integer
            //int value = Integer.parseInt(json.replaceAll("[^0-9]", ""));
            return tck;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;

    }

    public int updateTicket(int ticketId, int n) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        try {
            String query = "UPDATE Tickets SET Availability ='" + n + "' WHERE TicketId = '" + ticketId + "'";

            System.out.println(query);
            stmt.executeUpdate(query);
            // Extract the value of the "TicketId" key as an integer
            //int value = Integer.parseInt(json.replaceAll("[^0-9]", ""));
            return 1;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return -1;

    }
}
