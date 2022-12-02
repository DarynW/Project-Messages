import java.lang.reflect.Array;
import java.util.ArrayList;

public class getMostCommonWordsTest {
    public static void main(String[] args) {
        ArrayList<String> messages = new ArrayList<>() {
            {
                add("Daryn");
                add("science");
                add("is");
                add("is");
                add("computer");
                add("science");
                add("is");
                add("computer");
                add("cool");
                add("is");
                add("is");
                add("Yash");
                add("computer");
                add("Daryn");
                add("loves");
                add("is");
                add("Andy");
                add("Daryn");
                add("is");
                add("and");
                add("loves");
                add("Fischer");
                add("cool");
                add("science");
                add("computer");
                add("computer");
                add("cool");
                add("Daryn");
                add("Fischer");
                add("science");
                add("is");
            }
        };



        Start start = new Start();
        ArrayList<String> output = new ArrayList<>();
        output = start.getMostCommonWords(messages);
        System.out.println(output.toString());


        //check if arraylists are equal
        ArrayList<String> expected = new ArrayList<>() {
            {
                add("is");
                add("computer");
                add("Daryn");
                add("science");
                add("cool");
                add("loves");
                add("Fischer");
                add("Yash");
                add("Andy");
                add("and");
            }
        };
        if (output.toString().equals(expected.toString())){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed!");
        }
    }

}