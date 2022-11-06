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

    public String getName() {
        return name;
    }

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
