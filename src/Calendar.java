import javax.xml.crypto.Data;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Calendar class that extends Store.
 * Contains the appointments that go with the specific calendar for one tutor.
 *
 * @author Andy Niu
 * @version 11/10/2022 databaseFocus branch
 */
public class Calendar extends Store {
    private String tutorName;
    /**
     * Contains the appointment IDs for this specific tutor.
     */
    private ArrayList<String> appointments;

    /**
     * Instantiates the Calendar object. Throws Exception because of searchAllbyField. TODO fix this please.
     * Finds all appointments that are under sellerName. TODO add more tag searches for this?
     * Then sorts out all that are for the specific tutor.
     *
     * @param databaseName
     * @param sellerName
     * @param storeName
     * @param tutorName the tutor's name. NOT THE SAME AS THE SELLER.
     * @throws Exception
     */
    public Calendar(String databaseName, String sellerName, String storeName, String tutorName) throws Exception {
        super(databaseName, sellerName, storeName);

        ArrayList<String> temp =
                super.getDatabase().searchAllByField("dataType: Appointment, sellerName: " + sellerName);
        for (String each : temp) {
            appointments.add(super.getDatabase().get(each, "tutorName"));
        }
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public ArrayList<String> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<String> appointments) {
        this.appointments = appointments;
    }

    /**
     * Adds an appointment to this specific calendar for this tutor.
     * This makes an appointment datatype with all the necessary tags.
     * Also checks if there is already an appointment with the exact same info.
     * TODO Still needs to give error if duplicate appointment is found.
     *
     * @param date the date of the appointment in m/d/y. Ex. 01/01/2022
     * @param hour the hour of the appointment in 24 hour time. Ex. 15:00
     */
    public void addAppointment(String date, String hour) {
        ArrayList<String> temp;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String modTime = dtf.format(now);
        try {
             temp = super.getDatabase().searchAllByField("dataType: Appointment, " +
                     "sellerName: " + super.getSellerName() + ", " +
                     "storeName: " + super.getStoreName() +
                     ", tutorName: " + tutorName +
                     ", buyerName: " + null +
                     ", date: " + date +
                     ", hour: " + hour);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (temp.isEmpty()) {
            String documentID = "";
            try {
                documentID = super.getDatabase().createDocument();
            } catch (Exception e) {
                System.out.println("Document ID duplicate");
            }
            try {
                super.getDatabase().add(documentID, "dataType", "Appointment");
                super.getDatabase().add(documentID, "sellerName", super.getSellerName());
                super.getDatabase().add(documentID, "storeName", super.getStoreName());
                super.getDatabase().add(documentID, "tutorName", tutorName);
                super.getDatabase().add(documentID, "buyerName", null);
                super.getDatabase().add(documentID, "date", date);
                super.getDatabase().add(documentID, "hour", hour);
                super.getDatabase().add(documentID, "modTime", modTime);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Appointment already exists!");
        }
    }

    public void editAppointment(String documentID, String tag, String value) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String modTime = dtf.format(now);
        try {
            if (super.getDatabase().fieldExists(documentID, "Appointment")) {
                try {
                    super.getDatabase().write(documentID, tag, value);
                    super.getDatabase().write(documentID, "modTime", modTime);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Appointment does not exist");
        }
    }

    public void deleteAppointment(String documentID) {
        try {
            if (super.getDatabase().fieldExists(documentID, "Appointment")) {
                try {
                    super.getDatabase().delete(documentID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Appointment does not exist");
        }
    }
}
