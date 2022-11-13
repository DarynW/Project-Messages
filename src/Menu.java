//menu that connects to Calendar for the user to interface and login with username and password

import javax.swing.*;
import java.util.Locale;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //ask for username
        System.out.println("What is your username?");
        String username = scanner.nextLine();

        //ask for password
        System.out.println("What is your password?");
        String password = scanner.nextLine();

        //ask if they are tutor or customer
        System.out.println("Are you a tutor or customer?");
        String userObject = scanner.nextLine();
        if (userObject.toLowerCase(Locale.ROOT).equals("tutor")) {
            Seller newSeller = new Seller(username);
        } else if (userObject.toLowerCase(Locale.ROOT).equals("customer")) {
            Buyer newBuyer = new Buyer(username);
        }
    }
}
