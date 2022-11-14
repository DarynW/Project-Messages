import javax.swing.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class that gets username and password at startup to user and verifies correct information
 * to create account for user
 *
 * @author yashjha
 * @version 11-13-2022
 */
public class Menu extends Login {
    private Database database;
    public Menu(String databaseName, String userName, String password) {
        super(databaseName, userName, password);
        database = new Database(databaseName);
    }

    public void getUserInformation() {
        Scanner scanner = new Scanner(System.in);
        //prompt user for username and password
        System.out.println("Enter your username");
        String username = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();

        //look if username exists in database
        if (!userNameExists(username)) {
            createAccount();
        }
    }

    public boolean userNameExists(String checkUsername) {
        //get documentID if username exists
        //get documentID number from database
        ArrayList<String> temp;
        try {
            temp = super.searchAllByField("dataType: User, " +
                    "username: " + checkUsername);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (temp.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void editAccount(String documentID, String tag, String value) {
        try {
            if (database.fieldExists(documentID, "User")) {
                try {
                    database.write(documentID, tag, value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Account doesn't exist");
        }
    }

    public void deleteAccount(String documentID) {
        try {
            if (database.fieldExists(documentID, "User")) {
                try {
                    database.delete(documentID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Account does not exist");
        }
    }
}
