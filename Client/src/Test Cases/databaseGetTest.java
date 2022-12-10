import java.net.Socket;
import java.util.Objects;

/**
 * This class is used to test the database Get function
 *
 * @author yashjha and Daryn
 * @version 12-10-2022
 */
public class databaseGetTest {
    //TODO: Yash, for this test to work, I think you need to run the server side as well otherwise
    // there is no database file to test with.
    Database database = new Database();

    public void writeTest(String userID) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        database.createDocument(userID);
        System.out.println("Before write statement");
        database.add(userID, "name", "testUserName");
        System.out.println("Past write statement");

        String getTest = database.get(userID, "name");
        System.out.println("Past get statement");

        if (Objects.equals(getTest, "testUserName")) {
            System.out.println("Test passed!");
        } else {
            System.out.println("Test failed!");
        }
        database.delete(userID);
    }

    public static void main(String[] args) throws Exception {
        databaseGetTest db = new databaseGetTest();
        db.writeTest("abcd");
    }

}