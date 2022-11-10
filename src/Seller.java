
import java.util.ArrayList;

/**
 * Seller class. Used by the seller User.
 * Create Stores that contain calendars that then contain appointments.
 *
 * @author Andy Niu
 * @version 11/10/2022 databaseFocus branch
 */
public class Seller {
    /**
     * Name of the database.
     */
    private String databaseName;
    /**
     * The database object created using the databaseName.
     */
    private Database database;
    /**
     * The name of the seller account.
     */
    private String sellerName;
    /**
     * An ArrayList of the stores that the seller controls.
     */
    private ArrayList<String> stores;

    /**
     * Instantiates a seller with the database and the name of the seller (username?).
     *
     * @param databaseName The name of the database
     * @param sellerName The name of the seller account
     */
    public Seller(String databaseName, String sellerName) {
        this.databaseName = databaseName;
        this.sellerName = sellerName;
        database = new Database(databaseName);
        try {
            ArrayList<String> temp = database.searchAllByField("dataType: Appointment, sellerName: " + sellerName);
            for (String each : temp) {
                stores.add(database.get(each, "storeName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new store under the seller.
     *
     * @param storeName Name of the new store
     */
    public void newStore(String storeName) {
        stores.add(storeName);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public ArrayList<String> getStores() {
        return stores;
    }

    public void setStores(ArrayList<String> stores) {
        this.stores = stores;
    }
}
