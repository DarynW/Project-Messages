import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Handles the login and account creation for the user
 * 
 * @author yashjha
 * @version 11-13-2022
 */
public class Login {
    private String userName;
    private String password;
    private String email;
    
    public Login(String userName, String password) {
        this.userName = userName; 
        this.password = password;
        this.email = "";
    }
    
    public boolean checkUsername() {
        return false;
    }
    
    public boolean checkPassword() {
        return false;
    }
    
    public void createAccount() {
        
    }
    
    public String getUserID() {
        if (checkUsername() && checkPassword()) {
            ArrayList<String> temp;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String modTime = dtf.format(now);
            try {
                temp = super.getDatabase().searchAllByField("dataType: Appointment, " +
                        "sellerName: " + super.getSellerName() + ", " +
                        "storeName: " + super.getStoreName() +
                        ", tutorName: " + tutorName +
                        ", buyerName: " + null +
                        ", date: " + date +
                        ", hour: " + hour);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
