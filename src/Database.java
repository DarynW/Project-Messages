
//import scanner and arraylist
import java.util.Scanner;
import java.util.ArrayList;
//import file
import java.io.File;
//import printwriter
import java.io.PrintWriter;

class Database {
    private String fileName;

    public Database(String fileName) {
        this.fileName = fileName;
    }

    private ArrayList<String[][]> readFile() {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // new arraylist
        ArrayList<String[][]> parsedDocuments = new ArrayList<String[][]>();

        // go through each line and split them by {$@}
        for (String line : lines) {
            String[] parts = line.split("\\{\\$\\@\\}");

            // create an arraylist of arrays
            ArrayList<String[]> parsedDocument = new ArrayList<String[]>();

            for (int i = 0; i < parts.length; i += 2) {
                String[] field = new String[2];
                field[0] = parts[i];
                field[1] = parts[i + 1];
                parsedDocument.add(field);
            }

            // parse arraylist to array and add to parsedDocuments
            parsedDocuments.add(parsedDocument.toArray(new String[0][0]));
        }

        return parsedDocuments;
    }

    public String get(String documentID, String key) {
        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // iterate through each key value pair
            if (document[0][1] == documentID) {
                for (int i = 1; i < document.length; i++) {
                    if (document[i][0] == key) {
                        return document[i][1];
                    }
                }
            }
        }
        return null;
    }

}
