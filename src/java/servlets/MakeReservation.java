/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import database.tables.EditCustomerTable;
import database.tables.EditEventTable;
import database.tables.EditReservationTable;
import database.tables.EditTicketsTable;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dimos
 */
@WebServlet(name = "MakeReservation", urlPatterns = {"/MakeReservation"})
public class MakeReservation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MakeReservation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MakeReservation at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {

            int cus_id = Integer.parseInt(request.getParameter("cusid"));
            String event_name = request.getParameter("EventName");
            int num_tickets = Integer.parseInt(request.getParameter("Bnumber"));
            String type_tickets = request.getParameter("Btype");

            System.out.println("Customer ID: " + cus_id);
            System.out.println("Event Name: " + event_name);
            System.out.println("Number of Tickets: " + num_tickets);
            System.out.println("Ticket Type: " + type_tickets);

            EditCustomerTable ect = new EditCustomerTable();
            EditEventTable eet = new EditEventTable();
            EditTicketsTable ett = new EditTicketsTable();

                EditReservationTable ert = new EditReservationTable();
                ert.makeaReservation(event_name, type_tickets, num_tickets, cus_id);
                response.setStatus(200);
                out.write("{\"Success\": \"Reservation Created Successfuly\"}");


            //out.write("{\"message\": \"Reservation successful\"}");
        } catch (Exception e) {
            response.setStatus(407);
            out.write("{\"message\": \"Error processing reservation\"}");
        } finally {
            out.close();
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
