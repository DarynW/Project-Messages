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
            for (String each : temp) {
                calendars.add(super.getDatabase().get(each, "tutorName"));
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
}
