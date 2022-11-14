import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the login and account creation for the user
 * 
 * @author yashjha
 * @version 11-13-2022
 */
public class Login extends Database {
    private String userName;
    private String password;
    private String email;
    private String databaseName;
    private Database database;
    
    public Login(String databaseName, String userName, String password) {
        super(databaseName);
        this.databaseName = databaseName;
        database = new Database(this.databaseName);
        this.userName = userName;
        this.password = password;
        this.email = "";
    }
    
    public boolean checkUsername() {
        ArrayList<String> temp;
        String documentID;
        //get documentID
        try {
            temp = super.searchAllByField("dataType: User, " + " username: " + this.userName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (temp.isEmpty()) {
            documentID = "";
            try {
                documentID = database.createDocument();
            } catch (Exception e) {
                System.out.println("Document ID duplicate");
            }
        } else {
            documentID = temp.get(0);
        }

        //search documentID for username
        String userName = null;
        try {
            userName = super.get(documentID, "username");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (userName.equals(this.userName)) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean checkPassword() {
        ArrayList<String> temp;
        String documentID;
        //get documentID
        try {
            temp = super.searchAllByField("dataType: User, " + " username: " + this.userName +
                    " password: " + this.password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (temp.isEmpty()) {
            documentID = "";
        } else {
            documentID = temp.get(0);
        }

        //search documentID for username
        String password = null;
        try {
            password = super.get(documentID, "password");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (password.equals(this.password)) {
            return true;
        } else {
            return false;
        }

    }
    
    public void createAccount() {
        Scanner scanner = new Scanner(System.in);
        String newUserName;
        String newUserPassword;
        String newUserEmail;
        String newUserType = "";
        String documentID = "";
        ArrayList<String> temp;

        //get username, password, email, and userType
        System.out.println("Enter your new username");
        newUserName = scanner.nextLine();
        System.out.println("Enter a password");
        newUserPassword = scanner.nextLine();
        System.out.println("Enter your email");
        newUserEmail = scanner.nextLine();
        System.out.println("Select Buyer or Seller\n1. Buyer\n2. Seller");
        int userType = scanner.nextInt();
        scanner.nextLine();
        if (userType == 1) {
            newUserType = "Buyer";
        } else if (userType == 2) {
            newUserType = "Seller";
        }

        //get documentID number from database
        try {
            temp = super.searchAllByField("dataType: User, " +
                    "username: " + this.userName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (temp.isEmpty()) {
            documentID = "";

            //write to file in database format
            try {
                database.add(documentID, "dataType", "User");
                database.add(documentID, "userName", newUserName);
                database.add(documentID, "password", newUserPassword);
                database.add(documentID, "email", newUserEmail);
                database.add(documentID, "userType", newUserType);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Username already exists!");
        }
    }
    
    public String getUserID() {
        if (checkUsername() && checkPassword()) {
            ArrayList<String> temp;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String modTime = dtf.format(now);
            String documentID = "";
            //get documentID number from database
            try {
                temp = super.searchAllByField("dataType: User, " +
                        "username: " + this.userName + ", " +
                        "password: " + this.password);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (temp.isEmpty()) {
                documentID = "";
            } else {
                documentID = temp.get(0);
            }

            //look for userType to get their UserID
            String userType = null;
            try {
                userType = super.get(documentID, "userType");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return userType;
        }
        return "Error!";
    }
}
