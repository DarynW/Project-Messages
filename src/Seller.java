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

    public ArrayList<String> getBuyerRequests(String time) {
        if (cal == null) {
            System.out.println("No current calendar for this seller");
        }
        for (Appointment app : cal.getAppointments()) {
            if (time.equals(app.getTime())) {
                return app.getBuyerRequests();
            }
        }
        return null;

    }

    public String confirmAppointmentRequests(String name, String time) {
        if (cal == null) {
            System.out.println("No current calendar for this seller");
        }
        //searches through appointments looking for one that matches the time.
        for (Appointment app : cal.getAppointments()) {
            if (time.equals(app.getTime())) {
                //searches through buyer for one that matches name
                for (Buyer buyer : app.getBuyerRequests()) {
                    if (buyer.getName().equals(name)) {
                        //sets the confirmed buyer to the one with the right name
                        app.setConfirmedBuyer(buyer);
                        //clears buyer requests
                        app.clearBuyerRequests();
                        //returns the name of the confirmed buyer;
                        return buyer.getName();
                    }
                }
            }
        }
        return "";
    }

    public ArrayList<Appointment> getUnconfirmedAppointments() {
        ArrayList<Appointment> apps = new ArrayList<Appointment>();
        for (Appointment app : cal.getAppointments()) {
            if (app.getConfirmedBuyer() == null) {
                apps.add(app);
            }
        }
        return apps;
    }

    public ArrayList<Appointment> getConfirmedAppointments() {
        ArrayList<Appointment> apps = new ArrayList<Appointment>();
        for (Appointment app : cal.getAppointments()) {
            if (app.getConfirmedBuyer() != null) {
                apps.add(app);
            }
        }
        return apps;
    }

}
