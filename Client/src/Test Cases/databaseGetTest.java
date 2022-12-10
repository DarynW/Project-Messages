import java.util.Objects;

public class databaseGetTest {
    //TODO: Yash, for this test to work, I think you need to run the server side as well otherwise
    // there is no database file to test with.
    Database database = new Database();
    
    public void writeTest(String userID) throws Exception {
        database.write(userID, "name", "testUserName");

        String getTest = database.get(userID, "name");

        if (Objects.equals(getTest, "testUserName")) {
            System.out.println("Test passed!");
        } else {
            System.out.println("Test failed!");
        }
    }

    public static void main(String[] args) throws Exception {
        databaseGetTest db = new databaseGetTest();
        db.writeTest("abcd");
    }

}
