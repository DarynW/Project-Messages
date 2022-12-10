import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {

    private Socket socket;
    private Database db;

    // consrtuctor that intakes a socket
    public ClientHandler(Socket socket, Database db) {
        this.socket = socket;
        this.db = db;
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

            // read until we get a message
            String message = null;
            try {
                message = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            String response = "";

            // print message
            System.out.println(message);

            if (message.startsWith("write")) {
                // write to the database
                String data = message.substring(5);
                db.writeFile(data.replaceAll("\\{\\$\\%\\^\\&\\}", "\n"));

                response = "OK";
            } else if (message.startsWith("read")) {
                // remove read
                message = message.replace("read", "");
                // read from the database
                try {
                    response = db.readFile().replaceAll("\n", "\\{\\$\\%\\^\\&\\}");

                    if (response.equals("")) {
                        response = "No data";
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (message.startsWith("exit")) {
                // exit the program
                System.exit(0);
            } else {
                response = "Invalid command";
            }

            writer.println(response);
            // print response
            System.out.println(response);
            // output a new line
            writer.flush();
        }
    }
}
