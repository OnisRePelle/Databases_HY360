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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Event;
import mainClasses.Tickets;

/**
 *
 * @author dimos
 */
public class EditEventTable {

    public void addEventFromJSON(String json) throws ClassNotFoundException {
        Event bt = jsonToEvent(json);
        //we can check anything here
        /*if (bt.getStart_datetime() == null) {
            bt.setStart_datetime();
        }*/
        createNewEvent(bt);
    }

    public Event jsonToEvent(String json) {
        Gson gson = new Gson();
        Event btest = gson.fromJson(json, Event.class);
        return btest;
    }

    public String eventToJSON(Event bt) {
        Gson gson = new Gson();

        String json = gson.toJson(bt, Event.class);
        return json;
    }

    public ArrayList<Event> databaseToEvent() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Event> events = new ArrayList<Event>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM Event");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Event ev = gson.fromJson(json, Event.class);
                events.add(ev);
            }
            return events;

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
    public void updateEvent(String id, HashMap<String, String> updates) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        Event ev = new Event();
        for (String key : updates.keySet()) {
            String update = "UPDATE Event SET " + key + "='" + updates.get(key) + "'" + "WHERE EventId = '" + id + "'";
            stmt.executeUpdate(update);
        }
        stmt.close();
        con.close();
    }

    public int giveEventIdFromName(String EventName) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT EventId FROM Event WHERE Name = '" + EventName + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            System.out.println(rs);
            System.out.println(json);
            int value = Integer.parseInt(json.replaceAll("[^0-9]", ""));
            return value;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return -1;
    }

    public void deleteEvent(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE * FROM Event WHERE EventId='" + id + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }

    public void createEventTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE Event "
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

    /**
     * Establish a database connection and add in the database.
     *
     * @param ev
     * @throws ClassNotFoundException
     */
    public void createNewEvent(Event ev) throws ClassNotFoundException {

        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();
            String insertQuery = "INSERT INTO "
                    + " Event ( Name, Date, Time, Type,Capacity ) "
                    + " VALUES ("
                    + "'" + ev.getName() + "',"
                    + "'" + ev.getDate() + "',"
                    + "'" + ev.getTime() + "',"
                    + "'" + ev.getType() + "',"
                    + "'" + ev.getCapacity() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The Event was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

            /* should take the Event id form the table*/
            int evid = giveEventIdFromName(ev.getName());
            System.out.println(evid);

            Tickets tv = new Tickets();
            Tickets tn = new Tickets();

            tv.setEventId(evid);
            tv.setType("VIP");
            tv.setPrice(ev.getVipP());
            tv.setAvailability(ev.getVipN());

            tn.setEventId(evid);
            tn.setType("NORMAL");
            tn.setPrice(ev.getNormalP());
            tn.setAvailability(ev.getNormalN());

            EditTicketsTable edt = new EditTicketsTable();
            edt.createNewTicket(tv);
            edt.createNewTicket(tn);

        } catch (SQLException ex) {
            Logger.getLogger(EditEventTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getAvailableEventsTickets() throws SQLException, ClassNotFoundException {

        String query = "SELECT Event.Name, Tickets.Type, Tickets.Availability "
                + "FROM Event "
                + "INNER JOIN Tickets ON Event.EventId = Tickets.EventId";

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        ArrayList<String> list = new ArrayList<String>();
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                list.add(json);
                System.out.println(json);

            }
            return list;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
}
