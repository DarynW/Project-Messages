import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    
    public Login(String databaseName, String userName, String password) {
        super(databaseName);
        this.databaseName = databaseName;
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
