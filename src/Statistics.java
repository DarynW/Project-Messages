import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Does the statistical calculations for both
 * sellers and buyers
 *
 * @author Andy Niu, lab sec 13
 * @version 11/13/2022
 */
public class Statistics {
    private String databaseName;
    private Database database;
    private String userName;

    /**
     * Constructor for statistics class. Creates new database
     * using the databaseName and takes the userName of the user
     *
     * @param databaseName Name of database
     * @param userName Name of user
     */
    public Statistics(String databaseName, String userName) {
        this.databaseName = databaseName;
        database = new Database(databaseName);
        this.userName = userName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public ArrayList<String> getCustomerAppointmentCounts(boolean descending) {
        ArrayList<String> text = new ArrayList<String>();
        ArrayList<String> temp;
        ArrayList<String> buyers = new ArrayList<String>();
        ArrayList<String> buyersFinal = new ArrayList<String>();

        ArrayList<LabeledData> data = new ArrayList<>();

        try {
            temp = database.searchAllByField("dataType: Appointment, " +
                    "sellerName: " + userName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Get all the buyerNames for each of the appointments
        for (String each : temp) {
            try {
                buyers.add(database.get(each, "buyerName"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //Get rid of the duplicate buyerNames that were stored in the stores arraylist
        //And store them in the storesFinal arraylist
        for (String each : buyers) {
            if (!buyersFinal.contains(each)) {
                buyersFinal.add(each);
            }
        }
        //Count how many buyers in the buyer arraylist and then use that data to create
        //LabeledData objects in a list so that we can sort the buyers later.
        for (String each : buyersFinal) {
            int count = 0;
            for (String element : buyers) {
                if (each.equals(element)) {
                    count++;
                }
            }
            data.add(new LabeledData(each, count));
        }

        //Sort the LabeledData by the counts by either descending or ascending
        //depending on the boolean value. Then convert the LabeledData into String Arraylists,
        //and return the arraylist
        if (descending) {
            data.sort(Comparator.comparing(LabeledData::getData));
            for (LabeledData each : data) {
                text.add(each.getLabel() + "'s appointments: " + each.getData());
            }
        } else {
            data.sort(Comparator.comparing(LabeledData::getData).reversed());
            for (LabeledData each : data) {
                text.add(each.getLabel() + "'s appointments: " + each.getData());
            }
        }
        return text;
    }

    public ArrayList<String> getPopularAppointmentsByStore(boolean descending) {
        ArrayList<String> text = new ArrayList<>();
        ArrayList<String> stores = new ArrayList<String>();
        ArrayList<String> storesFinal = new ArrayList<String>();
        ArrayList<String> temp;
        ArrayList<LabeledData> data = new ArrayList<>();

        try {
            temp = database.searchAllByField("dataType: Appointment");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (String each : temp) {
            try {
                stores.add(database.get(each, "storeName"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        for (String each : stores) {
            if (!storesFinal.contains(each)) {
                storesFinal.add(each);
            }
        }

        //Find the most popular appointment for each store
        for (String storeName : storesFinal) {
            ArrayList<String> dateAndTime = new ArrayList<String>();
            ArrayList<String> dateAndTimeFinal = new ArrayList<>();
            ArrayList<LabeledData> timeData = new ArrayList<>();
            ArrayList<String> storeIDs;
            //Find all the appointments that have the same storeName
            try {
                storeIDs = database.searchAllByField("dataType: Appointment, " +
                        "storeName: " + storeName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //Get the date and time of these storeIDs appointments
            for (String each : storeIDs) {
                try {
                    dateAndTime.add(database.get(each, "date") + " " + database.get(each, "hour"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            for (String each : dateAndTime) {
                if (!dateAndTimeFinal.contains(each)) {
                    dateAndTimeFinal.add(each);
                }
            }
            for (String each : dateAndTimeFinal) {
                int count = 0;
                for (String element : dateAndTime) {
                    if (each.equals(element)) {
                        count++;
                    }
                }
                timeData.add(new LabeledData(each, count));
            }
            timeData.sort(Comparator.comparing(LabeledData::getData));
            data.add(new LabeledData(storeName, timeData.get(0).getLabel()));
        }

        if (descending) {
            data.sort(Comparator.comparing(LabeledData::getData));
            for (LabeledData each : data) {
                text.add(each.getLabel() + " customers: " + each.getData());
            }
        } else {
            data.sort(Comparator.comparing(LabeledData::getData).reversed());
            for (LabeledData each : data) {
                text.add(each.getLabel() + " customers: " + each.getData());
            }
        }
        return text;
    }

    public ArrayList<String> getCustomersPerStore(boolean descending) {
        ArrayList<String> text = new ArrayList<String>();
        ArrayList<String> temp;
        ArrayList<String> stores = new ArrayList<String>();
        ArrayList<String> storesFinal = new ArrayList<String>();

        ArrayList<LabeledData> data = new ArrayList<>();

        try {
            temp = database.searchAllByField("dataType: Appointment");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Get all the storeNames for each of the appointments
        for (String each : temp) {
            try {
                stores.add(database.get(each, "storeName"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //Get rid of the duplicate storeNames that were stored in the stores arraylist
        for (String each : stores) {
            if (!storesFinal.contains(each)) {
                storesFinal.add(each);
            }
        }
        //Count how many stores there are in the stores arraylist and then use that data to create
        //LabeledData objects in a list so that we can sort the stores later.
        for (String each : storesFinal) {
            int count = 0;
            for (String element : stores) {
                if (each.equals(element)) {
                    count++;
                }
            }
            data.add(new LabeledData(each, count));
        }

        //Sort the LabeledData by the counts by either descending or ascending
        //depending on the boolean value. Then convert the LabeledData into String Arraylists,
        //and return the arraylist
        if (descending) {
            data.sort(Comparator.comparing(LabeledData::getStringData));
            for (LabeledData each : data) {
                text.add(each.getLabel() + "'s popular appointment: " + each.getStringData());
            }
        } else {
            data.sort(Comparator.comparing(LabeledData::getStringData).reversed());
            for (LabeledData each : data) {
                text.add(each.getLabel() + "'s popular appointment: " + each.getStringData());
            }
        }
        return text;
    }

}
