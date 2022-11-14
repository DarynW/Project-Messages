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
        ArrayList<String> temp = new ArrayList<String>();
        ArrayList<String> buyer = new ArrayList<String>();
        ArrayList<Integer> count = new ArrayList<Integer>();
        try {
            temp = database.searchAllByField("dataType: Appointment, " +
                    "sellerName: " + userName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < temp.size(); i++) {
            boolean buyerNameDuplicate = false;
            for (int j = 0; j < buyer.size(); j++) {
                try {
                    if (database.get(temp.get(i), "buyerName").equals(buyer.get(j))) {
                        count.set(j, count.get(j) + 1);
                        buyerNameDuplicate = true;
                    }
                } catch (Exception e) {
                    buyerNameDuplicate = true;
                    throw new RuntimeException(e);
                }
            }
            if (!buyerNameDuplicate) {
                try {
                    buyer.add(database.get(temp.get(i), "buyerName"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        ArrayList<LabeledData> data = new ArrayList<>();
        for (int i = 0; i < buyer.size(); i++) {
            data.add(new LabeledData(buyer.get(i), count.get(i)));
        }

        if (descending) {
            data.sort(Comparator.comparing(LabeledData::getData));
            for (LabeledData each : data) {
                text.add(each.getLabel() + ": " + each.getData());
            }
        } else {
            data.sort(Comparator.comparing(LabeledData::getData).reversed());
            for (LabeledData each : data) {
                text.add(each.getLabel() + ": " + each.getData());
            }
        }
        return text;
    }

    public ArrayList<String> getPopularAppointmentsByStore(boolean descending) {

    }

    public ArrayList<String> getCustomersPerStore(boolean descending) {

    }

}
