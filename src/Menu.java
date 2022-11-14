import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
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
    private String databaseName;
    private String userName;
    public Menu(String databaseName, String userName, String password) {
        super(databaseName, userName, password);
        this.databaseName = databaseName;
        this.userName = userName;
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

    public void sellerMenu() {
        Scanner scanner = new Scanner(System.in);
        //arraylist to hold the stores that user owns
        ArrayList<String> stores = new ArrayList<>();

        //make seller object for user who is of Seller userType
        Seller userSeller = new Seller(databaseName, userName);

        //get the arraylist of stores that seller has
        stores = userSeller.getStores();

        //print out the stores that user owns
        System.out.println("Stores");
        for (int i = 0; i < stores.size(); i++) {
            System.out.print(stores.get(i) + "\t");
        }
        System.out.println();

        //give user options to do with stores
        System.out.println("\n1. Create store" +
                "\n2. Delete store" +
                "\n3. Select store" +
                "\n4. Calculate statistics" +
                "\n5. Review requests");
        int userChoice = scanner.nextInt();

        //give user different options based on what they press
        switch (userChoice) {
            case 1:
                stores.add(createStore(userSeller).getStoreName());
                userSeller.setStores(stores);
                break;
            case 2:
                //print out all the stores
                for (int i = 0; i < stores.size(); i++) {
                    System.out.println((i + 1) + ". " + stores.get(i));
                }
                //get the number of the store user wants to delete and remove from stores arraylist
                int deletedStore = scanner.nextInt();
                stores.remove(deletedStore);
                userSeller.setStores(stores);
                break;
            case 3:
                selectStore(stores);
        }
    }

    public Store createStore(Seller userSeller) {
        Scanner scanner = new Scanner(System.in);
        //get new name of store
        System.out.println("Enter the name of the new store");
        String newStoreName = scanner.nextLine();
        //create new store and add to stores arraylist
        Store newStore = new Store(databaseName, userName, newStoreName);
        return newStore;
    }

    public void selectStore(ArrayList<String> stores) {
        Scanner scanner = new Scanner(System.in);
        //print out all the stores
        for (int i = 0; i < stores.size(); i++) {
            System.out.println((i + 1) + ". " + stores.get(i));
        }
        int chosenStore = scanner.nextInt();

        System.out.println();
    }
}
