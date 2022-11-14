import java.util.*;
/**
 *  Buyer class that contains a buyer.
 *  with a list of appointments.
 *
 * @author Daryn Wang, lab sec 13
 * @version 11/13/2022
 */
public class Buyer {
    private String name;
    private String databaseName;
    private Database database;
    private ArrayList<String> confirmedAppointments;
    private ArrayList<String> requestedAppointments;


    /**
     * Instantiates a Buyer object.
     *
     * @param name name of buyer
     * @param bCA Confirmed Appointments
     * @param bRA Requested Appointments
     */
    public Buyer(String databaseName, String name, ArrayList<String> bCA, ArrayList<String> bRA) throws Exception {
        this.databaseName = databaseName;
        this.name = name;
        confirmedAppointments = bCA;
        requestedAppointments = bRA;
        database = new Database(databaseName);
        ArrayList<String> temp =
                database.searchAllByField("dataType: Request, requestName: " + name);
        for (String each : temp) {
            requestedAppointments.add(database.get(each, "appointmentID"));
        }
        temp =
                database.searchAllByField("dataType: Appointment, buyerName: " + name);
        for (String each : temp) {
            confirmedAppointments.add(database.get(each, "appointmentID"));
        }
    }

    /**
     * Instantiates buyer name with param name. ConfirmedAppointments set to empty.
     * RequestedAppointments set to empty.
     *
     * @param name  name of buyer
     */
    public Buyer(String databaseName, String name) throws Exception {
        this(databaseName, name, new ArrayList<String>(), new ArrayList<String>());

    }



    /**
     * Instantiates a Buyer object.
     *
     * @param name name of Buyer
     * @param bCA Confirmed Appointments
     */
    public Buyer(String databaseName, String name, ArrayList<String> bCA) throws Exception {
        this(databaseName, name, bCA, new ArrayList<String>());

    }

    /**
     * gets the buyer's name.
     *
     * @return returns name variable
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name using a string param.
     *
     * @param name The string that you want the
     *             name to change to.
     */
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getConfirmedAppointments() {
        return confirmedAppointments;
    }

    public void setConfirmedAppointments(ArrayList<String> confirmedAppointments) {
        this.confirmedAppointments = confirmedAppointments;
    }

    public ArrayList<String> getRequestedAppointments() {
        return requestedAppointments;
    }

    public void setRequestedAppointments(ArrayList<String> requestedAppointments) {
        this.requestedAppointments = requestedAppointments;
    }

    public void addRequest(String appointmentID, String name) {
        ArrayList<String> temp;

        try {
            temp = database.searchAllByField("dataType: Request, " +
                    "appointmentID: " + appointmentID + ", " +
                    "requestName: " + name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (temp.isEmpty()) {
            String documentID = "";
            try {
                documentID = database.createDocument();
            } catch (Exception e) {
                System.out.println("Document ID duplicate");
            }
            try {
                database.add(documentID, "dataType", "Request");
                database.add(documentID, "appointmentID", appointmentID);
                database.add(documentID, "requestName", name);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteRequest(String appointmentID) {
        try {
            if (database.fieldExists(appointmentID, "Request")) {
                try {
                    database.delete(appointmentID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Request does not exist");
        }
    }


}
