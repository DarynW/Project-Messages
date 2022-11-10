import java.util.*;
/**
 *  Buyer class that contains a buyer.
 *  with a list of appointments.
 *  TODO When calling buyer, first instantiate Database class.
 *
 * @author Andy Niu, lab sec 13
 * @version 11/6/2022
 */
public class Buyer {
    private String name;
    private ArrayList<Appointment> ConfirmedAppointments;
    private ArrayList<Appointment> requestedAppointments;

    /**
     * Instantiates buyer name with param name. ConfirmedAppointments set to empty.
     * RequestedAppointments set to empty.
     *
     * @param name  name of buyer
     */
    public Buyer(String name) {
        this.name = name;
        ConfirmedAppointments = new ArrayList<Appointment>();
        requestedAppointments = new ArrayList<Appointment>();
    }

    /**
     * Instantiates a Buyer object.
     *
     * @param name name of buyer
     * @param bCA Confirmed Appointments
     * @param bRA Requested Appointments
     */
    public Buyer(String name, ArrayList<Appointment> bCA, ArrayList<Appointment> bRA) {
        this.name = name;
        ConfirmedAppointments = bCA;
        requestedAppointments = bRA;
    }

    /**
     * Instantiates a Buyer object.
     *
     * @param name name of Buyer
     * @param bCA Confirmed Appointments
     */
    public Buyer(String name, ArrayList<Appointment> bCA) {
        this.name = name;
        ConfirmedAppointments = bCA;
        requestedAppointments = new ArrayList<Appointment>();
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

    public ArrayList<Appointment> getConfirmedAppointments() {
        return ConfirmedAppointments;
    }

    public void setConfirmedAppointments(ArrayList<Appointment> ConfirmedAppointments) {
        this.ConfirmedAppointments = ConfirmedAppointments;
    }

    public ArrayList<Appointment> getRequestedAppointments() {
        return requestedAppointments;
    }

    public void setRequestedAppointments(ArrayList<Appointment> requestedAppointments) {
        this.requestedAppointments = requestedAppointments;
    }
}
