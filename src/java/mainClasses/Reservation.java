/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author dimos
 */
public class Reservation {
    int ReservationID;
    int CustomerID;
    int EventID;
    int TicketID;
    int NumTickets;
    String ReservationDate;
    int TotalPrice;

    public int getReservationID() {
        return ReservationID;
    }

    public void setReservationID(int ReservationID) {
        this.ReservationID = ReservationID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public int getEventID() {
        return EventID;
    }

    public void setEventID(int EventID) {
        this.EventID = EventID;
    }

    public int getTicketID() {
        return TicketID;
    }

    public void setTicketID(int TicketID) {
        this.TicketID = TicketID;
    }

    public int getNumTickets() {
        return NumTickets;
    }

    public void setNumTickets(int NumTickets) {
        this.NumTickets = NumTickets;
    }

    public String getReservationDate() {
        return ReservationDate;
    }

    public void setReservationDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.ReservationDate = dtf.format(now);

    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

}
