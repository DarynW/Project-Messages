import java.util.Objects;

public class databaseGetTest {
    Database database = new Database();

    public void writeTest(String userID) throws Exception {
        database.write(userID, "name", "testUserName");

        String getTest = database.get(userID, "name");

        assert Objects.equals(getTest, "testUserName");
    }

}
