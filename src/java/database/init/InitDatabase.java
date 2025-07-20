/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.init;

import static database.DB_Connection.getInitialConnection;
import database.tables.EditCustomerTable;
import database.tables.EditEventTable;
import database.tables.EditRegistrationTable;
import database.tables.EditReservationTable;
import database.tables.EditTicketsTable;
import database.init.Resources;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



/*
 *
 * @author micha
 */
public class InitDatabase {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InitDatabase init = new InitDatabase();
        init.initDatabase();
        init.initTables();
        init.addToDatabaseExamples();
        //init.updateRecords();
        //init.databaseToJSON();

        //  init.dropDatabase();
        // init.deleteRecords();
    }

    public void dropDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        String sql = "DROP DATABASE  HY360_2024_25";
        stmt.executeUpdate(sql);
        System.out.println("Database dropped successfully...");
    }

    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE HY360_2024_25");
        stmt.close();
        conn.close();
        System.out.println("Database Created Successfully...");

    }

    public void initTables() throws SQLException, ClassNotFoundException {
        EditCustomerTable eut = new EditCustomerTable();
        eut.createCustomerTable();

        EditEventTable evn = new EditEventTable();
        evn.createEventTable();

        EditRegistrationTable reg = new EditRegistrationTable();
        reg.createRegistrationTable();

        EditReservationTable res = new EditReservationTable();
        res.createReservationTable();
//

        EditTicketsTable tic = new EditTicketsTable();
        tic.createTicketsTable();
    }

    public void addToDatabaseExamples() throws ClassNotFoundException, SQLException {
        //Customers
        System.out.println(Resources.customer1JSON);

        EditCustomerTable eut = new EditCustomerTable();
        //eut.addUserFromJSON(Resources.admin);
        eut.addCustomerFromJSON(Resources.customer1JSON);
        //eut.addUserFromJSON(Resources.user2JSON);
        //eut.addUserFromJSON(Resources.user3JSON);
        //eut.addUserFromJSON(Resources.user4JSON);

        EditEventTable eet = new EditEventTable();
        eet.addEventFromJSON(Resources.event1JSON);

        eet.addEventFromJSON(Resources.event2JSON);

        EditReservationTable ert = new EditReservationTable();
        ert.addReservationFromJSON(Resources.reservation1JSON);
        ert.addReservationFromJSON(Resources.reservation2JSON);
        ert.makeaReservation("Rets Event", "VIP", 5, 0);
        ert.cancelReservetion(0, 0);

        //volunteers
        /*
        EditVolunteersTable editVolunteers = new EditVolunteersTable();
        editVolunteers.addVolunteerFromJSON(Resources.volunteer1JSON);
        editVolunteers.addVolunteerFromJSON(Resources.volunteer2JSON);
        editVolunteers.addVolunteerFromJSON(Resources.volunteer3JSON);
        editVolunteers.addVolunteerFromJSON(Resources.volunteer4JSON);
        //incidents
        EditIncidentsTable editIncidents = new EditIncidentsTable();
        editIncidents.addIncidentFromJSON(Resources.incident1);
        editIncidents.addIncidentFromJSON(Resources.incident2);
        editIncidents.addIncidentFromJSON(Resources.incident3);
        editIncidents.addIncidentFromJSON(Resources.incident4);
        editIncidents.addIncidentFromJSON(Resources.incident5);
        //participants
        EditParticipantsTable editParticipants = new EditParticipantsTable();
        editParticipants.addParticipantFromJSON(Resources.participant1);
        editParticipants.addParticipantFromJSON(Resources.participant2);
        editParticipants.addParticipantFromJSON(Resources.participant3);

        EditMessagesTable editmessages = new EditMessagesTable();
        editmessages.addMessageFromJSON(Resources.message1);
        editmessages.addMessageFromJSON(Resources.message2);
         */
    }
    /*
    public void databaseToJSON() throws ClassNotFoundException, SQLException {
//       //Get info of User
        EditUsersTable eut = new EditUsersTable();
        User su = eut.databaseToUsers("mountanton", "ab$A12cde");
        String json = eut.userToJSON(su);
        System.out.println("User\n" + json + "\n");

        //all fire incidents
        EditIncidentsTable editIncidents = new EditIncidentsTable();
        ArrayList<Incident> allIncidents = new ArrayList<Incident>();
        allIncidents = editIncidents.databaseToIncidentsSearch("fire", "all", "");
        Gson gson2 = new Gson();
        JsonArray incidentsJSON = gson2.toJsonTree(allIncidents).getAsJsonArray();
        System.out.println("All Fire Incidents\n" + incidentsJSON);

        //all running incidents in Hersonissos
        ArrayList<Incident> allIncidents2 = new ArrayList<Incident>();
        allIncidents2 = editIncidents.databaseToIncidentsSearch("all", "running", "Hersonissos");
        JsonArray incidentsJSON2 = gson2.toJsonTree(allIncidents2).getAsJsonArray();
        System.out.println("All Running Incidents in Hersonissos\n" + incidentsJSON2);
        
        
        //       all messages of an incident
        EditMessagesTable editmessages = new EditMessagesTable();
        ArrayList<Message> messagesOfIncident = new ArrayList<Message>();
        int incident_id = 1;
        messagesOfIncident = editmessages.databaseToMessage(incident_id);
        Gson gson7 = new Gson();
        JsonArray messagesOfIncidentJSON = gson7.toJsonTree(messagesOfIncident).getAsJsonArray();
        System.out.println("All messages of Incident with ID " + incident_id + "\n" + messagesOfIncidentJSON + "\n");

        CheckForDuplicatesExample check = new CheckForDuplicatesExample();
        System.out.println("Is username:" + "Mitsos" + " Available? " + check.isUserNameAvailable("mitsos"));

        System.out.println("Is username:" + "mountanton" + " Available? " + check.isUserNameAvailable("mountanton"));
        System.out.println("Is username:" + "nick" + " Available? " + check.isUserNameAvailable("nick"));

    }

    public void updateRecords() throws ClassNotFoundException, SQLException {
        EditUsersTable editUsers = new EditUsersTable();
        editUsers.updateUser("mountanton", "job", "Professor");

        EditParticipantsTable editParticipants = new EditParticipantsTable();
        editParticipants.acceptParticipant(3, "raphael");
        editParticipants.finalStatusParticipant(3, "yes", "Voithise para polu");
        EditIncidentsTable editIncidents = new EditIncidentsTable();
        HashMap<String, String> updateIncident1 = new HashMap<String, String>();
        updateIncident1.put("vehicles", "1");
        updateIncident1.put("firemen", "5");
        editIncidents.updateIncident("1", updateIncident1);

        HashMap<String, String> updateIncident2 = new HashMap<String, String>();
        updateIncident2.put("municipality", "Hersonissos");
        updateIncident2.put("prefecture", "Heraklion");
        updateIncident2.put("danger", "medium");
        updateIncident2.put("status", "running");
        updateIncident2.put("vehicles", "2");
        updateIncident2.put("firemen", "3");
        editIncidents.updateIncident("2", updateIncident2);

        HashMap<String, String> updateIncident3 = new HashMap<String, String>();
        updateIncident3.put("lat", "35.225643");
        updateIncident3.put("lon", "25.183435");
        updateIncident3.put("municipality", "Archanes-Asterousia");
        updateIncident3.put("prefecture", "Heraklion");
        updateIncident3.put("danger", "high");
        updateIncident3.put("status", "finished");
        updateIncident3.put("end_datetime", "2024-10-10 20:10:05");
        updateIncident3.put("finalResult", "I epixirisi itan epituximeni");
        updateIncident3.put("vehicles", "3");
        updateIncident3.put("firemen", "7");
        editIncidents.updateIncident("3", updateIncident3);

    }

    public void deleteRecords() throws ClassNotFoundException, SQLException {

        EditIncidentsTable eb = new EditIncidentsTable();
        String pet_id = "1";
        //   eb.deletePet(pet_id);
    }
*/
}
