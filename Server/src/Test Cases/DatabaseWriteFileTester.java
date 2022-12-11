/**
 * This class is used to test the datase's write function
 *
 * @author Daryn
 * @version 12-10-2022
 */
public class DatabaseWriteFileTester {
    public static void main(String[] args) throws Exception {
        Database db = new Database("src/Test Cases/databaseTestFile.csv");
        String original = db.readFile();
//        original = original.replace((char) 10, ',');
//        String original = db.readFile();
        db.writeFile(original + "\nHello I like cheese");

        System.out.println(db.readFile());
    }
}
