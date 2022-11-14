import java.util.ArrayList;

/**
 * Store class that stores calendars for a store.
 * Created by the Seller class.
 *
 * @author Andy Niu
 * @version 11/10/2022 databaseFocus branch
 */
public class Store extends Seller {
    /**
     * contains the store name
     */
    private String storeName;
    /**
     * A list of calendars in the store.
     */
    private ArrayList<String> calendars;

    /**
     * Instantiates the Store object.
     *
     * @param databaseName name of database
     * @param sellerName name of the seller
     * @param storeName name of the store
     */
    public Store(String databaseName, String sellerName, String storeName) {
        super(databaseName, sellerName);
        this.storeName = storeName;
        try {
            ArrayList<String> temp = super.getDatabase().searchAllByField("storeName: " + storeName);
            ArrayList<String> calendarsTemp = new ArrayList<>();
            for (String each : temp) {
                calendarsTemp.add(super.getDatabase().get(each, "tutorName"));
            }
            for (String each : calendarsTemp) {
                if (!calendars.contains(each)) {
                    calendars.add(each);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public ArrayList<String> getCalendars() {
        return calendars;
    }

    public void setCalendars(ArrayList<String> calendars) {
        this.calendars = calendars;
    }

    public void updateCalendars() {
        calendars.clear();
        try {
            ArrayList<String> temp = super.getDatabase().searchAllByField("storeName: " + storeName);
            ArrayList<String> calendarsTemp = new ArrayList<>();
            for (String each : temp) {
                calendarsTemp.add(super.getDatabase().get(each, "tutorName"));
            }
            for (String each : calendarsTemp) {
                if (!calendars.contains(each)) {
                    calendars.add(each);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editCalendarName(String calendarName, String newCalendarName) {
        ArrayList<String> calendarIDs = new ArrayList<>();
        //Edits the calendar arraylist which contains the names of the stores.
        //Replaces the old calendarName with the new calendarName
        for (int i = 0; i < calendars.size(); i++) {
            if (calendars.get(i).equals(calendarName)) {
                calendars.set(i, newCalendarName);
            }
        }
        //Find all appointmentsIDs with the old calendar name
        try {
            calendarIDs = super.getDatabase().searchAllByField("tutorName: " + calendarName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Replace/Write over the old calendarName for those appointments
        //with the new calendarName
        for (String each : calendarIDs) {
            try {
                super.getDatabase().write(each, "tutorName", newCalendarName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteCalendar(String calendarName) {
        ArrayList<String> calendarIDs = new ArrayList<>();

        calendars.remove(storeName);

        try {
            calendarIDs = super.getDatabase().searchAllByField("tutorName: " + calendarName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Delete all appointments with the name of calendarName
        for (String each : calendarIDs) {
            try {
                super.getDatabase().delete(each);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}

