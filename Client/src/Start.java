
//import arraylist
import java.util.ArrayList;
// hashmap
import java.util.HashMap;
// import File
import java.io.File;
//import scanner
import java.util.Scanner;

public class Start {

    Menu menu = new Menu();

    // method that gets int input form console and throws error if its not an
    // integer
    public int getIntInput(String prompt) {
        return menu.getIntInput(prompt);
    }

    Database database;

    // method that gets string input form console
    public String getStringInput(String prompt) {
        return menu.getStringInput(prompt);
    }

    // method that writes to a new text file with the given path
    public void writeToFile(String path, String text) {
        // check that the path is valid
        if (path == null || path.equals("")) {
            menu.println("Invalid path");
            return;
        }

        // write to the file
        try {
            // create a new uuid
            String uuid = java.util.UUID.randomUUID().toString();

            path = path + uuid + "_Messages.txt";

            // create a new file
            File file = new File(path);

            // if the file doesn't exist, create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // create a new file writer
            java.io.PrintWriter writer = new java.io.PrintWriter(path, "UTF-8");
            writer.println(text);
            writer.close();
        } catch (java.io.IOException e) {
            // print the error message
            menu.println(e.getMessage());
        }
    }

    // method that finds and sorts the most common list of words from an arraylist
    // of messsage strings
    public ArrayList<String> getMostCommonWords(ArrayList<String> messages) {

        // if the size is 0 return an empty list
        if (messages.size() == 0) {
            return new ArrayList<String>();
        }
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Integer> wordCount = new ArrayList<Integer>();
        for (String message : messages) {
            String[] messageWords = message.split(" ");
            for (String word : messageWords) {
                if (words.contains(word)) {
                    int index = words.indexOf(word);
                    int count = wordCount.get(index);
                    wordCount.set(index, count + 1);
                } else {
                    words.add(word);
                    wordCount.add(1);
                }
            }
        }

        int maxInt = words.size();
        if (maxInt > 10) {
            maxInt = 10;
        }

        ArrayList<String> mostCommonWords = new ArrayList<String>();
        for (int i = 0; i < maxInt; i++) {
            int max = 0;
            int maxIndex = 0;
            for (int j = 0; j < wordCount.size(); j++) {
                if (wordCount.get(j) > max) {
                    max = wordCount.get(j);
                    maxIndex = j;
                }
            }
            mostCommonWords.add(words.get(maxIndex));
            wordCount.set(maxIndex, 0);
        }
        return mostCommonWords;
    }

    public void displayMessageInteraction(String ourUserId, String otherUserId, String userType, String otherUserType)
            throws Exception {

        while (true) {
            // get all messages between the two users
            ArrayList<String> messages = database
                    .searchAllByField(userType + ": " + ourUserId + ", " + otherUserType + ": " + otherUserId);
            /*
             * // sort the messages by timestamp
             * for (int i = 0; i < messages.size(); i++) {
             * String timestamp = database.get(messages.get(i), "timestamp");
             * for (int j = 0; j < messages.size(); j++) {
             * String timestamp2 = database.get(messages.get(j), "timestamp");
             * if (timestamp.compareTo(timestamp2) < 0) {
             * String temp = messages.get(i);
             * messages.set(i, messages.get(j));
             * messages.set(j, temp);
             * }
             * }
             * }
             */

            // display messages
            for (int i = 0; i < messages.size(); i++) {
                menu.println(i + ". Message: " + database.get(messages.get(i), "message") + ", ID: " + messages.get(i)
                        + ", Author: " + database.get(database.get(messages.get(i), "author"), "email"));
            }

            // get message input
            String message = getStringInput(
                    "Enter message or type \nExit to Exit \nBlock to Block User\nEdit to Edit Message\nDelete to Delete Message\nDownload to Download Messages\nUpload to Upload Messages");

            // if message is exit, exit the method
            if (message.toLowerCase().equals("exit")) {
                break;
            } else if (message.toLowerCase().equals("block")) {
                String blocked = database.get(ourUserId, "blocked");

                // create array with size 0
                String[] blockedArray = new String[0];

                if (blocked != null)
                    blockedArray = blocked.split(",");

                // add TO THE FUCKING ARRAy
                ArrayList<String> blockedList = new ArrayList<String>();
                for (String block : blockedArray) {
                    blockedList.add(block);
                }
                blockedList.add(otherUserId);
                String blockedString = "";
                for (String block : blockedList) {
                    blockedString += block + ",";
                }
                database.write(ourUserId, "blockedUsers", blockedString);
            } else if (message.toLowerCase().equals("edit")) {
                // take in message id and edit it in the database
                // taker in message id and delete it from database
                String messageNumber = getStringInput("Enter message number to delete");
                // get message id from messages
                String messageId = messages.get(Integer.parseInt(messageNumber));
                String newMessage = getStringInput("Enter new message");
                database.write(messageId, "message", newMessage);

            } else if (message.toLowerCase().equals("delete")) {
                // taker in message id and delete it from database
                String messageNumber = getStringInput("Enter message number to delete");
                // get message id from messages
                String messageId = messages.get(Integer.parseInt(messageNumber));
                database.delete(messageId);
            } else if (message.toLowerCase().equals("download")) {
                // download all messages to a text file
                String text = "";
                for (int i = 0; i < messages.size(); i++) {
                    text += "Message: " + database.get(messages.get(i), "message") + ", ID: " + messages.get(i)
                            + ", Author: " + database.get(database.get(messages.get(i), "author"), "email");
                }

                this.writeToFile(".", text);
            }

            else if (message.toLowerCase().equals("upload")) {
                // prompt for txt file path
                String path = getStringInput("Enter path to file");
                // read file with scanner
                String text = "";

                try {
                    File file = new File(path);
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        text += scanner.nextLine();
                    }
                    scanner.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // check if the user has blocked this user from messaging them
                String[] blocked = database.get(otherUserId, "blockedUsers").split(",");

                // turn it into an arraylist
                ArrayList<String> blockedList = new ArrayList<String>();

                for (String block : blocked) {
                    blockedList.add(block);
                }

                // if they have blocked them, inform the user and return
                if (blockedList.contains(ourUserId)) {
                    menu.println("You have been blocked from messaging this user");
                    return;
                }

                // if message is not exit, add the message to the database
                String newMessageId = database.createDocument();
                database.add(newMessageId, "message", text);
                // author
                database.add(newMessageId, "author", ourUserId);
                database.add(newMessageId, userType, ourUserId);
                database.add(newMessageId, otherUserType, otherUserId);
                // add timestamps
                database.add(newMessageId, "timestamp", System.currentTimeMillis() + "");

            } else {
                // check if the user has blocked this user from messaging them
                String[] blocked = database.get(otherUserId, "blockedUsers").split(",");

                // turn it into an arraylist
                ArrayList<String> blockedList = new ArrayList<String>();

                for (String block : blocked) {
                    blockedList.add(block);
                }

                // if they have blocked them, inform the user and return
                if (blockedList.contains(ourUserId)) {
                    menu.println("You have been blocked from messaging this user");
                    return;
                }

                // if message is not exit, add the message to the database
                String newMessageId = database.createDocument();
                database.add(newMessageId, "message", message);
                // author
                database.add(newMessageId, "author", ourUserId);
                database.add(newMessageId, userType, ourUserId);
                database.add(newMessageId, otherUserType, otherUserId);
                // add timestamps
                database.add(newMessageId, "timestamp", System.currentTimeMillis() + "");
            }
        }
    }

    // method that takes buyer or seller input, and user id and displays their
    // messages while taking input for messaging with other users
    public void messages(String userType, String id) throws Exception {
        String otherUserType;
        if (userType.equals("buyer")) {
            otherUserType = "seller";
        } else {
            otherUserType = "buyer";
        }

        while (true) {
            // present all users they have messaged
            ArrayList<String> data = database.searchAllByField(userType + ": " + id);

            // loop through arraylist create an arraylist of users that they are messaging
            // and display them
            ArrayList<String> users = new ArrayList<String>();

            for (int i = 0; i < data.size(); i++) {
                // get other user type id from data
                String otherUserId = database.get(data.get(i), otherUserType);

                // if the user is not already in the arraylist, add them
                if (!users.contains(otherUserId)) {
                    users.add(otherUserId);
                }
            }

            String prompt = "";

            // display users
            for (int i = 0; i < users.size(); i++) {
                prompt += (i + 1) + ". " + database.get(users.get(i), "email") + "\n";
            }

            // add exit option at the end of the prompt
            prompt += (users.size() + 1) + ". Exit";

            // get user input
            int input = getIntInput(prompt);

            // if user input is exit, exit the method
            if (input == users.size() + 1) {
                break;
            } else {
                // if user input is not exit, get the user id of the user they want to message
                String user = users.get(input - 1);
                displayMessageInteraction(id, user, userType, otherUserType);
            }

        }
    }

    public void main() {

        // init menu
        menu.go();

        try {
            // intatinatiate the database
            database = new Database();

            while (true) {
                int choice = getIntInput("1. Login\n2. Register\n3. Exit");
                // give a menu to the user with login and register options
                if (choice == 1) {
                    // ask for email and password
                    String email = getStringInput("Enter your email");
                    String password = getStringInput("Enter your password");

                    // check for user in database
                    String userId = database.searchByField("email: " + email);

                    // check if the user exists
                    if (userId == null) {
                        menu.println("User does not exist");
                        return;
                    }

                    // check if password matches, if it doesnt, throw error and end program
                    if (!database.get(userId, "password").equals(password)) {
                        menu.println("Incorrect password");
                        return;
                    }

                    // ask if theyre buying or selling
                    int optionPicked = getIntInput("1. Buyer\n2. Seller\n3. Delete Account");

                    switch (optionPicked) {
                        case 1:
                            // if theyre buying, display the buying menu
                            // loop infinitely until option 1 is selected
                            while (true) {
                                // list menu of options for viewing messages, viewing stores, deleting account
                                // and exiting
                                int input = getIntInput(
                                        "1. Search Sellers and Message\n2. View stores\n3. Delete account\n4. Exit");

                                if (input == 4) {
                                    return;
                                }

                                // switch statement for the input
                                switch (input) {
                                    // if input is 1, view messages
                                    case 1:
                                        // prompt for seller email and start message with them
                                        String sellerEmail = getStringInput("Enter seller email");
                                        String sellerId = database.searchByField("email: " + sellerEmail);
                                        if (sellerId == null) {
                                            menu.println("Seller does not exist");
                                            break;
                                        }

                                        // if the seller exists, display messages
                                        displayMessageInteraction(userId, sellerId, "buyer", "seller");

                                        break;
                                    // if input is 2, view stores
                                    case 2:
                                        // get all stores
                                        ArrayList<String> stores = database.searchAllByField("type: store");
                                        ArrayList<String> invalidStores = new ArrayList<String>();

                                        // loop through stores and display them
                                        for (int i = 0; i < stores.size(); i++) {
                                            // if the seller has our user blocked

                                            // unparsed blockedlist
                                            String blockedList = database.get(database.get(stores.get(i), "owner"),
                                                    "blockedList");

                                            // if the blocked list is not empty
                                            if (!blockedList.equals("")) {
                                                // split the blocked list by commas
                                                String[] blocked = blockedList.split(",");

                                                // convert to arraylist
                                                ArrayList<String> blockedListArray = new ArrayList<String>();

                                                for (String block : blocked) {
                                                    blockedListArray.add(block);
                                                }

                                                // if the user is in the blocked list, skip this store
                                                if (blockedListArray.contains(userId)) {
                                                    invalidStores.add(stores.get(i));
                                                    menu.println("(Invisible)");
                                                    continue;
                                                }
                                            }

                                            menu.println((i + 1) + ". " + database.get(stores.get(i), "name")
                                                    + " | Messages Sent: "
                                                    + database
                                                            .searchAllByField(
                                                                    "seller: " + database.get(stores.get(i), "owner"))
                                                            .size()
                                                    + " | " + database.get(stores.get(i), "description"));
                                        }

                                        // get input for store
                                        int storeInput = getIntInput("Enter store number or 0 to exit");

                                        // if input is 0, exit the method
                                        if (storeInput == 0) {
                                            break;
                                        } else {

                                            // check if the number is within the range of the stores
                                            if (storeInput > stores.size() || storeInput < 1
                                                    || invalidStores.contains(stores.get(storeInput - 1))) {
                                                menu.println("Invalid store number");
                                                break;
                                            }

                                            // if input is not 0, get the store id
                                            String storeId = stores.get(storeInput - 1);

                                            // get owner id from store
                                            String ownerId = database.get(storeId, "owner");

                                            // get all messages between buyer and seller
                                            displayMessageInteraction(userId, ownerId, "buyer", "seller");
                                        }
                                        break;
                                    // if input is 3, delete account
                                    case 3:
                                        // delete buyer
                                        database.delete(userId);
                                        return;
                                }

                            }
                        case 2:
                            // if theyre selling, display the selling menu
                            // loop infinitely until option 1 is selected
                            while (true) {
                                // here we list all of the stores
                                // present menu where they can view their stores, create a store, view their
                                // messages or delete their account or exit the programs
                                int option = getIntInput(
                                        "1. View stores\n2. Create store\n3. View messages\n4. Delete account\n5. Search User To Message\n6. Exit");

                                // switch statement for the menu
                                switch (option) {
                                    case 1:
                                        // view stores
                                        // get all stores that the user owns
                                        ArrayList<String> stores = database.searchAllByField("owner: " + userId);

                                        String dub = "";

                                        // loop through the stores and display them
                                        for (int i = 0; i < stores.size(); i++) {
                                            dub += (i + 1) + ". " + database.get(stores.get(i), "name");
                                        }

                                        // print choose a store to modify
                                        dub += "\nChoose a store";

                                        // get user input
                                        int input = getIntInput(dub);

                                        // if the input is not within the range of the stores, throw error
                                        if (input > stores.size() || input < 1) {
                                            menu.println("Invalid store number");
                                            break;
                                        }

                                        // get the store id
                                        String storeId = stores.get(input - 1);

                                        // list the most common words used in this users messages
                                        ArrayList<String> messages = database.searchAllByField("seller: " + userId);

                                        // create a hashmap to store the words and their frequency
                                        HashMap<String, Integer> words = new HashMap<String, Integer>();

                                        // loop through the messages
                                        for (int i = 0; i < messages.size(); i++) {
                                            // get the message
                                            String message = database.get(messages.get(i), "message");

                                            // split the message into words
                                            String[] messageWords = message.split(" ");

                                            // loop through the words
                                            for (int j = 0; j < messageWords.length; j++) {
                                                // if the word is already in the hashmap, increment the value
                                                if (words.containsKey(messageWords[j])) {
                                                    words.put(messageWords[j], words.get(messageWords[j]) + 1);
                                                } else {
                                                    // if the word is not in the hashmap, add it with a value of 1
                                                    words.put(messageWords[j], 1);
                                                }
                                            }
                                        }

                                        // create a list of the words
                                        ArrayList<String> wordList = new ArrayList<String>(words.keySet());

                                        // sort the list by the frequency of the words
                                        ArrayList<String> commonWords = getMostCommonWords(wordList);

                                        // print the most common words
                                        menu.println("Most common words: ");
                                        for (int i = 0; i < commonWords.size(); i++) {
                                            menu.println(commonWords.get(i));
                                        }

                                        // ask if they want to edit the description, delete the store or edit the name
                                        int option2 = getIntInput(
                                                "1. Edit description\n2. Delete store\n3. Edit name \n4. Exit");

                                        if (option2 == 4) {
                                            break;
                                        }

                                        // switch statement for the menu
                                        switch (option2) {
                                            case 1:
                                                // edit description
                                                // get new description
                                                String description = getStringInput("Enter new description");

                                                // set the description
                                                database.write(storeId, "description", description);
                                                break;
                                            case 2:
                                                // delete store
                                                // delete the store
                                                database.delete(storeId);
                                                break;
                                            case 3:
                                                // edit name
                                                // get new name
                                                String name = getStringInput("Enter new name");

                                                // set the name
                                                database.write(storeId, "name", name);
                                                break;
                                        }

                                        break;
                                    case 2:
                                        // create store

                                        // create database entry
                                        String storeId2 = database.createDocument();
                                        // add type
                                        database.add(storeId2, "type", "store");
                                        // add owner
                                        database.add(storeId2, "owner", userId);
                                        // add name
                                        database.add(storeId2, "name", getStringInput("Enter store name"));
                                        // add description
                                        database.add(storeId2, "description",
                                                getStringInput("Enter store description"));

                                        // say the store has been created
                                        menu.println("Store created");
                                        break;
                                    case 3:
                                        // view messages
                                        messages("seller", userId);
                                        break;
                                    case 4:
                                        // delete account
                                        database.delete(userId);
                                        break;
                                    case 5:
                                        // prompt for seller email and start message with them
                                        String buyer = getStringInput("Enter user email");
                                        String buyerId = database.searchByField("email: " + buyer);
                                        if (buyerId == null) {
                                            menu.println("Buyer does not exist");
                                            break;
                                        }

                                        // if the seller exists, display messages
                                        displayMessageInteraction(userId, buyerId, "seller", "buyer");

                                        break;
                                    case 6:

                                        // exit program
                                        System.exit(0);
                                    default:
                                        // if the user enters an invalid option, throw error and end program
                                        menu.println("Please enter a valid option");
                                        return;
                                }
                            }
                        case 3:
                            // if theyre deleting their account, delete their account
                            database.delete(userId);
                            return;
                        default:
                            // if theyre doing something else, throw an error
                            menu.println("Invalid input");
                            return;
                    }

                } else if (choice == 2) {

                    // register
                    // ask for email and password
                    String email = getStringInput("Enter your email");
                    String password = getStringInput("Enter your password");

                    String result = database.searchByField("email: " + email);

                    // check if email is already in use
                    if (result != null) {
                        menu.println("Email already in use");
                        return;
                    }

                    // give them an entry in the database
                    String id = database.createDocument();
                    // add email and password fields along with number of messages they have
                    database.add(id, "password", password);
                    database.add(id, "email", email);
                    database.add(id, "messages", "0");
                    database.add(id, "type", "user");
                    database.add(id, "blockedUsers", ",");

                    // notify that they have registered and end program
                    menu.println("You have registered.");
                } else {
                    // if theyre doing something else
                    return;
                }
            }
        } catch (Exception e) {
            // print stacktrace and error
            e.printStackTrace();
            menu.println("Error: " + e.getMessage());
        }

    }
}
