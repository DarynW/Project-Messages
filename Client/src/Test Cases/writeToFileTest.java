import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class writeToFileTest {
    static String uuid;
    public static void main(String[] args) {
        Start start = new Start();
        String expected = "I like corndogs\nAnd pizzas\nBUT NOT ASPARAGUS\n";
//        String uuid = start.writeToFile("src/Test Cases/", expected);
        if (!Files.exists(Path.of("src/Test Cases/uuid"))) {
            writing(start, expected);
        } else {
            tester(expected);
        }
//        uuid += "_Messages.txt";
//        File f = new File(uuid);
//        try {
//            String output = "";
//            BufferedReader br = new BufferedReader(new FileReader(f));
//            while (br.ready()){
//                output = br.readLine() + "\n";
//            }
//            if (output.equals(expected)) {
//                System.out.println("Test Passed!");
//            } else
//                System.out.println("Test Failed.");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    public static void writing(Start start, String expected) {
        File f = new File("src/Test Cases/uuid");
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(start.writeToFile("src/Test Cases/", expected));
            fw.close();
            System.out.println("Run again for test outcome.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void tester(String expected) {
        File f = new File("src/Test Cases/uuid");

        try {
            String output = "";
            BufferedReader br = new BufferedReader(new FileReader(f));
            String uuid = br.readLine() + "_Messages.txt";
            File fActual = new File("src/Test Cases/" + uuid);
            BufferedReader brActual = new BufferedReader(new FileReader(fActual));
            while (brActual.ready()){
                if (!output.equals("")) {
                    output += "\n";
                }
                output += brActual.readLine();
            }
            if (output.equals(expected)) {
                System.out.println("Test Passed!");
            } else
                System.out.println("Test Failed.");
            f.delete();
            fActual.delete();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
