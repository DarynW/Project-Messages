
// import required packages for network communication

import java.io.*;
import java.net.*;

public class Start {

    static int PORT = 5000;

    public static void main(String[] args) {
        // initiate database
        Database db = new Database("database.csv");

        ServerSocket serverSocket = null;
        try {
            // afaik we can ignore the memory leak warning
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }

        while (true) {
            try {

                System.out.println("Waiting for the client to connect...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                // create a new thread for each client
                Thread t = new ClientHandler(socket, db);
                t.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
