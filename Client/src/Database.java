
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

    public String arrayListToString(ArrayList<String> arrayList) {
        String string = "";
        for (int i = 0; i < arrayList.size(); i++) {
            string += arrayList.get(i) + "{$#}";
        }
        return "{$#}" + string + "{$#}";
    }

    public ArrayList<String> stringToArrayList(String string) {
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

    public void createDocument(String documentID) throws Exception {
        if (this.documentExists(documentID))
            throw new Exception("Document already exists");

        ArrayList<String[][]> documents = readFile();
        String[][] newDocument = new String[1][2];
        newDocument[0][0] = "documentID";
        newDocument[0][1] = documentID;
        documents.add(newDocument);
        writeFile(documents);
    }

    public String createDocument() throws Exception {
        String documentID = UUID.randomUUID().toString();
        this.createDocument(documentID);
        return documentID;
    }

    public void delete(String documentID) throws Exception {
        if (!this.documentExists(documentID))
            return;

        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // iterate through each key value pair
            if (document[0][1].equals(documentID)) {
                // remove that document by adding all other documents to a new arraylist
                ArrayList<String[][]> newDocuments = new ArrayList<String[][]>();
                for (String[][] doc : documents) {
                    if (doc != document) {
                        newDocuments.add(doc);
                    }
                }

                writeFile(newDocuments);
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

    public String searchByField(String tags) throws Exception {

        String[] tagArray = tags.split(", ");

        // create arraylist
        ArrayList<String> searchTags = new ArrayList<String>();

        // loop through each tag
        for (int i = 0; i < tagArray.length; i++) {
            String tag = tagArray[i];
            String[] tagParts = tag.split(": ");
            searchTags.add(tagParts[0]);
        }

        ArrayList<String[][]> documents = readFile();
        for (String[][] document : documents) {
            // iterate through each key value pair
            for (String[] fieldPair : document) {

                // check if all tags match
                boolean allTagsMatch = true;
                for (String tag : searchTags) {
                    if (!fieldPair[0].equals(tag)) {
                        allTagsMatch = false;
                    }
                }

                if (allTagsMatch) {
                    return document[0][1];
                }
            }
        }

        return null;
    }

    /**
     * Searches with multiple tag criteria and values and returns an array of unique
     * document ID's
     * 
     * @param tags - String of tags and values to search for Example: "tag1: value1,
     *             tag2: value2, tag3: value3, etc."
     *             </p>
     *             Tags (Things in {} are the tag names. Things in () are what the
     *             exact values have to be.
     *             </p>
     *             Then in [] there can be different types of stuff, doesn't need to
     *             be the exact wording):
     *             </p>
     *             </p>
     *             There are three types of data in this database: User,
     *             Appointment, Request. Then, these 3 types
     *             of data each have their own tags. When you create a datatype,
     *             MAKE SURE THAT IT HAS ALL THE TAGS
     *             FOR THAT DATATYPE!
     *             </p>
     *             USER DATA TYPE TAGS:
     *             </p>
     *             dataType (User) *User for User dataType
     *             </p>
     *             userName ([String name]) *Used for User dataType
     *             </p>
     *             password ([String password]) *Used for User dataType
     *             </p>
     *             email ([String email]) *Used for User dataType
     *             </p>
     *             userType ([String Seller or Buyer]) *Used for User dataType
     *             </p>
     *             </p>
     *             APPOINTMENTS DATA TYPE TAGS:
     *             </p>
     *             dataType (Appointment) *User for Appointment dataType
     *             </p>
     *             sellerName ([String sellerName]) *Used for Appointment dataType
     *             </p>
     *             storeName ([String storeName]) *Used for Appointment dataType
     *             </p>
     *             tutorName ([String tutorName]) *Used for Appointment dataType
     *             </p>
     *             buyerName ([String buyerName]) *Used for Appointment dataType.
     *             </p>
     *             date ([String m/d/y. Ex. 01/01/2022]) *Used for Appointment
     *             dataType
     *             </p>
     *             hour ([String hr:min. Ex. 14:44. USE 24 HOUR TIME]) *Used for
     *             Appointment dataType
     *             modTime ([String m/d/y hr:min:sec]) *Used for Appointment
     *             dataType
     *             </p>
     *             REQUEST DATA TYPE TAGS:
     *             </p>
     *             dataType (Request) *User for Request dataType
     *             </p>
     *             appointmentID ([String documentID]) *Used for Request dataType
     *             </p>
     *             requestName ([String name of buyer]) *Used for Request dataType
     *             </p>
     *
     * @return ArrayList/<Strings> - Array of document ID's
     * @throws Exception
     */
    public ArrayList<String> searchAllByField(String tags) throws Exception {

        String[] tagArray = tags.split(", ");

        // create arraylist
        ArrayList<String[]> searchTags = new ArrayList<String[]>();

        // loop through each tag
        for (int i = 0; i < tagArray.length; i++) {
            String tag = tagArray[i];
            String[] tagParts = tag.split(": ");
            searchTags.add(tagParts);
        }

        ArrayList<String[][]> documents = readFile();
        ArrayList<String> documentIDs = new ArrayList<String>();
        for (String[][] document : documents) {
            // iterate through each key value pair
            boolean allTagsMatch = true;
            ArrayList<String> keys = new ArrayList<String>();
            // create boolean arraylist
            ArrayList<Boolean> tagMatches = new ArrayList<Boolean>();

            for (String[] fieldPair : document) {

                // check if all tags match
                // loop through tag parts
                for (String[] tagParts : searchTags) {
                    if (fieldPair[0].equals(tagParts[0])) {

                        keys.add(fieldPair[0]);

                        if (fieldPair[1].equals(tagParts[1])) {
                            tagMatches.add(true);
                        } else {
                            tagMatches.add(false);
                        }
                    }
                }

            }
            // loop through search tags
            for (String[] tagParts : searchTags) {

                if (!keys.contains(tagParts[0])) {
                    allTagsMatch = false;
                }
            }

            if (allTagsMatch && !tagMatches.contains(false)) {
                // print hello World
                documentIDs.add(document[0][1]);
            }
        }

        return documentIDs;

    }
}
