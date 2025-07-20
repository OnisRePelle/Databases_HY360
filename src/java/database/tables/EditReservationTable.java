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
import mainClasses.Reservation;
import mainClasses.Tickets;

/**
 *
 * @author dimos
 */
public class EditReservationTable {

    public void addReservationFromJSON(String json) throws ClassNotFoundException {
        Reservation res = jsonToReservation(json);
        createNewReservation(res);
    }

    public Reservation jsonToReservation(String json) {
        Gson gson = new Gson();
        Reservation res = gson.fromJson(json, Reservation.class);
        res.setReservationDate();
        return res;
    }

    public String reservationToJSON(Reservation msg) {
        Gson gson = new Gson();

        String json = gson.toJson(msg, Reservation.class);
        return json;
    }

    /*

    public ArrayList<Message> databaseToMessage(int incident_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Message> messages = new ArrayList<Message>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM messages WHERE incident_id= '" + incident_id + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Message msg = gson.fromJson(json, Message.class);
                messages.add(msg);
            }
            return messages;
        } catch (Exception e) {
            System.err.println("Got an exception! ");

        }
        return null;
    }
*/
    public void createReservationTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE Reservation "
                + "(ReservationId INTEGER not NULL AUTO_INCREMENT, "
                + "CustomerId INTEGER not NULL, "
                + "EventId INTEGER not NULL, "
                + "TicketId INTEGER not NULL, "
                + "NumTickets INTEGER not NULL, "
                + "ReservationDate DATE not NULL,"
                + "TotalPrice INTEGER not NULL,"
                + "PRIMARY KEY ( ReservationId ))";

        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @param res
     * @throws ClassNotFoundException
     */
    public void createNewReservation(Reservation res) throws ClassNotFoundException {

        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();
            String insertQuery = "INSERT INTO "
                    + " Reservation (CustomerId, EventId, TicketId, NumTickets, ReservationDate, TotalPrice) "
                    + " VALUES ("
                    + "'" + res.getCustomerID() + "',"
                    + "'" + res.getEventID() + "',"
                    + "'" + res.getTicketID() + "',"
                    + "'" + res.getNumTickets() + "',"
                    + "'" + res.getReservationDate() + "',"
                    + "'" + res.getTotalPrice() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The reservation was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditReservationTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void makeaReservation(String EventName, String Type, int Number, int userid) throws SQLException, ClassNotFoundException {
        //shouldserach if user exists
        System.out.println("Making A Reservation");
        //should search for event name if there is and take back the EventId
        EditEventTable eet = new EditEventTable();
        int event_id = eet.giveEventIdFromName(EventName);
        if (event_id == -1) {
            System.out.println("Error Event Name not found");
            return;
        }
        //should search the ticket with EventId == EventId and Typ == Type
        EditTicketsTable ett = new EditTicketsTable();
        Tickets ticket = ett.getTicket(event_id, Type);
        if (ticket == null) {
            System.out.println("Error Event Name not found");
            return;
        }
        System.out.println(event_id);
        System.out.println(ticket.getTicketId());

        //all good should make a reservation
        ett.updateTicket(ticket.getTicketId(), ticket.getAvailability() - Number);
        System.out.println("Ticket Updated");

        Reservation res = new Reservation();
        res.setCustomerID(userid);
        res.setEventID(event_id);
        res.setTicketID(userid);
        res.setNumTickets(Number);
        res.setTotalPrice(Number * ticket.getPrice());
        res.setReservationDate();

        createNewReservation(res);

    }

    public void cancelReservetion(int res_id, int refund) throws SQLException, ClassNotFoundException {
        /*
        else if ( refund == 1 ){
            String getId_Balance = "SELECT CustomerId,Balance FROM Reservation WHERE ReservationId = '" + res_id + "'";
            String deleteQuery = "DELETE * FROM Reservation WHERE ReservationId='" + res_id + "'";
            stmt.execute(deleteQuery);
        }*/
        System.out.println("INSIDE CANSER_RESERVATION");
        Connection con = DB_Connection.getConnection();

        try {
            //should take the Tickets and update them
            Statement stmt = con.createStatement();
            ResultSet rs;
            String query_tickets = "SELECT TicketId, NumTickets FROM Reservation WHERE ReservationId = '" + res_id + "'";

            System.out.println(query_tickets);
            rs = stmt.executeQuery(query_tickets);
            //rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            System.out.println(rs);
            System.out.println(json);

            //should take the customerid and price to give it back
            //String query = "SELECT * FROM Tickets WHERE EventId = '" + EventId + "' AND Type ='" + Type + "'";

            //Tickets tck = jsonToTicket(json);

            // Extract the value of the "TicketId" key as an integer
            //int value = Integer.parseInt(json.replaceAll("[^0-9]", ""));
            //return tck;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        //return null;

    }
}
