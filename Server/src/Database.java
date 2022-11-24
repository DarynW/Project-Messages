
//import scanner and arraylist
import java.util.Scanner;
import java.util.ArrayList;
//import file
import java.io.File;
//import printwriter
import java.io.PrintWriter;
// import uuid.uuid.UUID
import java.util.UUID;

/**
 * This class is used for database stuff.
 * 
 * @author Fischer Lab-13
 * @version 1.0
 */
public class Database {
    private String fileName;

    public Database(String fileName) {
        this.fileName = fileName;
    }

    public synchronized String readFile() throws Exception {
        // return a string containing the contents of the entire file
        String fileContents = "";
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            fileContents += scanner.nextLine();
        }
        scanner.close();
        return fileContents;
    }

    // writeFile - write the contents of an ArrayList<String[][]> to the db file
    public synchronized void writeFile(String data) {

        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.print(data);

            writer.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
