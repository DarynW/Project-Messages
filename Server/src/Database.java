
//import scanner and arraylist

import java.util.Scanner;
//import file
import java.io.File;
//import printwriter
import java.io.PrintWriter;

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

        int count = 0;

        while (scanner.hasNextLine()) {
            fileContents += (count == 0 ? "" : "\n") + scanner.nextLine();
            count++;
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
