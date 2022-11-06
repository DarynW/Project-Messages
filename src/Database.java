
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

    public static String arrayListToString(ArrayList<String> arrayList) {
        String string = "";
        for (int i = 0; i < arrayList.size(); i++) {
            string += arrayList.get(i) + "{$#}";
        }
        return string;
    }

    public static ArrayList<String> stringToArrayList(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] stringArray = string.split("\\{\\$\\#\\}");
        for (int i = 0; i < stringArray.length; i++) {
            arrayList.add(stringArray[i]);
        }
        return arrayList;
    }

    private ArrayList<String[][]> readFile() throws Exception {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null)
                    lines.add(line);
            }
            scanner.close();
        } catch (Exception e) {
            throw new Exception("Document couldnt be parsed");
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

    // writeFile - write the contents of an ArrayList<String[][]> to the db file
    private void writeFile(ArrayList<String[][]> documents) {

        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            for (String[][] document : documents) {
                for (String[] field : document) {
                    writer.print(field[0] + "{$@}" + field[1] + "{$@}");
                }
                writer.println();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String get(String documentID, String key) throws Exception {
        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // System.out.println(arrayToString(document));
            // iterate through each key value pair
            if (document[0][1].equals(documentID)) {
                for (String[] field : document) {
                    if (field[0].equals(key)) {
                        return field[1];
                    }
                }
            }
        }
        return null;
    }

    public void write(String documentID, String key, String value) throws Exception {
        if (!this.documentExists(documentID)) {
            throw new Exception("Document does not exist");
        }
        if (!this.fieldExists(documentID, key)) {
            throw new Exception("Key does not exist.");
        }

        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // iterate through each key value pair
            if (document[0][1].equals(documentID)) {
                for (int i = 1; i < document.length; i++) {
                    if (document[i][0].equals(key)) {
                        document[i][1] = value;
                        writeFile(documents);
                        return;
                    }
                }
            }
        }
    }

    public void add(String documentID, String key, String value) throws Exception {
        if (!this.documentExists(documentID)) {
            throw new Exception("Document does not exist");
        }
        if (this.fieldExists(documentID, key)) {
            throw new Exception("Key already exists.");
        }

        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // iterate through each key value pair
            if (document[0][1].equals(documentID)) {
                String[][] newDocument = new String[document.length + 1][2];
                for (int i = 0; i < document.length; i++) {
                    newDocument[i][0] = document[i][0];
                    newDocument[i][1] = document[i][1];
                }
                newDocument[document.length][0] = key;
                newDocument[document.length][1] = value;
                documents.add(newDocument);

                documents.remove(document);

                writeFile(documents);
                return;
            }
        }
    }

    public void delete(String documentID, String key) throws Exception {
        if (!this.documentExists(documentID)) {
            throw new Exception("Document does not exist");
        }

        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // iterate through each key value pair
            if (document[0][1].equals(documentID)) {
                String[][] newDocument = new String[document.length - 1][2];
                int j = 0;
                for (int i = 0; i < document.length; i++) {
                    if (document[i][0] != key) {
                        newDocument[j][0] = document[i][0];
                        newDocument[j][1] = document[i][1];
                        j++;
                    }
                }
                documents.add(newDocument);
                writeFile(documents);
            }
        }
    }

    public void create(String documentID) throws Exception {
        if (this.documentExists(documentID))
            throw new Exception("Document already exists");

        ArrayList<String[][]> documents = readFile();
        String[][] newDocument = new String[1][2];
        newDocument[0][0] = "documentID";
        newDocument[0][1] = documentID;
        documents.add(newDocument);
        writeFile(documents);
    }

    public void delete(String documentID) throws Exception {
        if (!this.documentExists(documentID))
            return;

        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // iterate through each key value pair
            if (document[0][1].equals(documentID)) {
                documents.remove(document);
                writeFile(documents);
            }
        }
    }

    public boolean fieldExists(String documentID, String field) {
        try {
            String value = this.get(documentID, field);
            if (value != null)
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean documentExists(String documentID) throws Exception {
        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // iterate through each key value pair
            if (document[0][1].equals(documentID)) {
                return true;
            }
        }
        return false;
    }

    public String searchByField(String field, String value) throws Exception {
        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // iterate through each key value pair
            for (String[] fieldPair : document) {
                if (fieldPair[0].equals(field) && fieldPair[1].equals(value)) {
                    return document[0][1];
                }
            }
        }
        return null;
    }
}
