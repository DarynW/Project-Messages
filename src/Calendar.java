import java.util.ArrayList;

public class Calendar {
    private int appointmentCount;
    private ArrayList<Appointment> appointments;

    public Calendar () {
        
    }

    public Calendar (Calendar[] calendars) {

    }

    public int getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(int appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    public boolean addAppointment(Seller seller, String time) {
        Appointment newAppointment = new Appointment(seller, null, null);
        newAppointment.setTime(time);
        appointments.add(newAppointment);
        return true;
    }

    public boolean deleteAppointment(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointment == appointments.get(i)) {
                appointments.remove(i);
                return true;
            }
        }
        return false;
    }

    public Appointment modifyAppointment(Appointment appointment) {
        //modify time, confirmedBuyers, buyerRequests, or seller name
        return appointment;
    }
}
