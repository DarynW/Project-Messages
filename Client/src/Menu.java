import javax.swing.*;
import java.awt.*;

public class Menu {

    JTextField text;
    JButton button;
    boolean clicked = false;
    JFrame frame;
    JPanel north;

    String currentText = "";

    // create a gui
    public void go() {
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        // make background black
        frame.getContentPane().setBackground(Color.BLACK);

        // create a button and textbox next to eachother and put it south
        JPanel south = new JPanel();
        south.setLayout(new FlowLayout());
        button = new JButton("Enter");
        text = new JTextField(10);
        south.add(text);
        south.add(button);
        frame.add(south, BorderLayout.SOUTH);

        // add a textfield to the north
        north = new JPanel();
        north.setLayout(new FlowLayout());
        north.add(new JLabel("Hello World"));
        frame.add(north, BorderLayout.NORTH);
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.setForeground(Color.WHITE);
        frame.setVisible(true);

        // add action listener to button
        button.addActionListener(e -> {
            currentText = "";
            printText();
            clicked = true;
            // clear the textfield
            text.setText("");
        });

        // add listener to check if the enter key is pressed
        text.addActionListener(e -> {
            currentText = "";
            printText();
            clicked = true;
        });

    }

    private String getInput() {
        // wait for button to be clicked
        while (!clicked) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        clicked = false;
        return text.getText();
    }

    // method that removes all text from the north panel
    private void clearNorth() {
        north.removeAll();
        north.revalidate();
        north.repaint();
    }

    // method that prints current text to the north panel
    private void printText() {

        // if there are more than 12 lines, remove any extra lines

        clearNorth();

        // split at newlines
        String[] lines = currentText.split("\\r?\\n");

        // add each line to the north panel
        for (String line : lines) {
            north.add(new JLabel(line));
        }
        north.revalidate();
        north.repaint();
    }

    public void print(String s) {
        // add a new line to the north panel
        // add to current text
        currentText += s;
        printText();
    }

    public void println(String s) {
        // add a new line to the north panel
        // add to current text
        currentText += s + "\n";
        printText();
    }

    public int getIntInput(String prompt) {
        // print prompt
        println(prompt);

        int output;
        while (true) {
            try {
                output = Integer.parseInt(getInput());
                break;
            } catch (NumberFormatException e) {
                println("Please enter a valid number");
                continue;
            }
        }

        return output;
    }

    public String getStringInput(String prompt) {
        // print prompt
        println(prompt);

        return getInput();
    }
}