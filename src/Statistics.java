import java.util.ArrayList;

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

        temp =



    }
}
