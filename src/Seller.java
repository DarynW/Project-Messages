import java.util.ArrayList;

public class Seller {
    private String name;
    private Calendar cal;

    public Seller(String name) {
        this.name = name;
        cal = new Calendar(this);

    }

    public boolean createCalendar() {
        //check to make sure there is no calendar first
        if (cal == null) {
            cal = new Calendar(this);
            return true;
        } else return false;
    }

    public boolean deleteCalendar() {
        if (cal != null) {
            cal = null;
            return true;
        } else return false;
    }

    public ArrayList<String> getAppointmentRequests() {
        if (cal == null) {
            return null;
        }
        for (int i = 0; i < cal.getAppointmentCount(); i++) {

        }
    }

}
