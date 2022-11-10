import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Database db = new Database("database.txt");
        Scanner keyboard = new Scanner(System.in);
        /*
        if (!db.documentExists("1")) {
            db.createDocument("1");
        }
        if (!db.fieldExists("1", "dataType")){
            db.add("1", "dataType", "Appointment");
        }
        if (!db.fieldExists("1", "sellerName")){
            db.add("1", "sellerName", "Andy Niu");
        }

         */
        for (int i = 1; i <= 10; i++) {
            db.createDocument("" + i);
            for (int i = 0; i )
        }
    }
}