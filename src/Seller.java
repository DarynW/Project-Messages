
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
            ArrayList<String> storesTemp = new ArrayList<>();
            for (String each : temp) {
                storesTemp.add(database.get(each, "storeName"));
            }
            for (String each : storesTemp) {
                if (!stores.contains(each)) {
                    stores.add(each);
                }
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
        updateStores();
        return stores;
    }

    public void setStores(ArrayList<String> stores) {
        this.stores = stores;
    }

    public void editStoreName(String storeName, String newStoreName) {
        ArrayList<String> storeIDs = new ArrayList<>();
        //Edits the stores arraylist which contains the names of the stores.
        //Replaces the old storeName with the new storeName
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).equals(storeName)) {
                stores.set(i, newStoreName);
            }
        }
        //Find all appointmentsIDs with the old store name
        try {
            storeIDs = database.searchAllByField("storeName: " + storeName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Replace/Write over the old storeName for those appointments
        //with the new storeName
        for (String each : storeIDs) {
            try {
                database.write(each, "storeName", newStoreName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteStore(String storeName) {
        ArrayList<String> storeIDs = new ArrayList<>();

        stores.remove(storeName);

        try {
            storeIDs = database.searchAllByField("storeName: " + storeName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Replace/Write over the old storeName for those appointments
        //with the new storeName
        for (String each : storeIDs) {
            try {
                database.delete(each);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void updateStores() {
        stores.clear();
        try {
            ArrayList<String> temp = database.searchAllByField("dataType: Appointment, sellerName: " + sellerName);
            ArrayList<String> storesTemp = new ArrayList<>();
            for (String each : temp) {
                storesTemp.add(database.get(each, "storeName"));
            }
            for (String each : storesTemp) {
                if (!stores.contains(each)) {
                    stores.add(each);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
