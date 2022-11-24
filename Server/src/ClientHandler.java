import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {

    private Socket socket;
    private Database db;

    // consrtuctor that intakes a socket
    public ClientHandler(Socket socket, Database db) {
        this.socket = socket;
    }

    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        while (true) {

            String message = null;
            try {
                message = reader.readLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            String response = "";

            if (message.startsWith("write")) {
                // write to the database
                String data = message.substring(5);
                db.writeFile(data.replace("{$%^&}", "\n"));
                response = "OK";
            } else if (message.startsWith("read")) {
                // read from the database
                try {
                    response = db.readFile();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (message.startsWith("exit")) {
                // exit the program
                System.exit(0);
            } else {
                response = "Invalid command";
            }

            writer.println(response.replace("\n", "{$%^&}"));

        }
    }

}
