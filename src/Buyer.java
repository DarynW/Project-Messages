import java.util.*;
/**
 *  Buyer class that contains a buyer
 *  with a list of appointments
 *
 * @author Andy Niu, lab sec 13
 * @version 11/6/2022
 */
public class Buyer {
    private String name;
    private ArrayList<Appointment> ConfirmedAppointments;
    private ArrayList<Appointment> bRAppointments;

    /**
     * Instantiates buyer name with param name. ConfirmedAppointments set to empty
     * RequestedAppointments set to empty.
     *
     * @param name  the string to display.  If the text is null,
     *              the tool tip is turned off for this component.
     */
    public Buyer(String name) {
        this.name = name;
        ConfirmedAppointments = new ArrayList<Appointment>();
        bRAppointments = new ArrayList<Appointment>();
    }

    public Buyer(String name, ArrayList<Appointment> bCA, ArrayList<Appointment> bRA) {
        this.name = name;
        ConfirmedAppointments = bCA;
        bRAppointments = bRA;
    }

    public Buyer(String name, ArrayList<Appointment> bCA) {
        this.name = name;
        ConfirmedAppointments = bCA;
        bRAppointments = new ArrayList<Appointment>();
    }

    /**
     * gets the buyer's name
     *
     * @return returns name variable
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name using a string param
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

    public ArrayList<Appointment> getbRAppointments() {
        return bRAppointments;
    }

    public void setbRAppointments(ArrayList<Appointment> bRAppointments) {
        this.bRAppointments = bRAppointments;
    }
}
