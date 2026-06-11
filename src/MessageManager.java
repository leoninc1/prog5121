import java.util.ArrayList;
import java.util.Random;

public class MessageManager {
    //Add MessageManager class with variables and constructor
    // variables to store message info
    String messageID;
    int numMessagesSent;
    String recipient;
    String messageText;
    String messageHash;
    String status;

    // arrays to store messages
    static ArrayList<MessageManager> sentMessages = new ArrayList<>();
    static ArrayList<MessageManager> disregardedMessages = new ArrayList<>();
    static ArrayList<MessageManager> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs = new ArrayList<>();

    static int totalMessagesSent = 0;

    // constructor
    public MessageManager(String recipient, String messageText, int numMessagesSent) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.numMessagesSent = numMessagesSent;
        this.status = "";

        // make a random 10 digit ID
        Random random = new Random();
        long id = (long)(random.nextDouble() * 9000000000L) + 1000000000L;
        this.messageID = String.valueOf(id);

        // create the hash
        this.messageHash = createMessageHash();
    }

    // check if message ID is not more than 10 characters
    public boolean checkMessageID() {
        if (messageID.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }

    // check if recipient number is correct
    public String checkRecipientCell() {
        //Add checkMessageID and checkRecipientCell methods
        if (recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // create the message hash
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // remove punctuation from last word
        lastWord = lastWord.replace("?", "").replace(".", "").replace("!", "").replace(",", "");

        String hash = firstTwo + ":" + numMessagesSent + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // send, disregard or store the message
    public String SentMessage(int choice) {
        //Add createMessageHash and SentMessage methods
        if (choice == 1) {
            this.status = "sent";
            sentMessages.add(this);
            messageHashes.add(this.messageHash);
            messageIDs.add(this.messageID);
            totalMessagesSent++;
            return "Message successfully sent.";
        } else if (choice == 0) {
            this.status = "disregarded";
            disregardedMessages.add(this);
            return "Press 0 to delete the message.";
        } else if (choice == 2) {
            this.status = "stored";
            storedMessages.add(this);
            messageHashes.add(this.messageHash);
            messageIDs.add(this.messageID);
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid option.";
        }
    }

    // print all messages that were sent
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }

        String result = "";

        for (int i = 0; i < sentMessages.size(); i++) {
            MessageManager m = sentMessages.get(i);
            result = result + "Message ID: " + m.messageID + "\n";
            result = result + "Message Hash: " + m.messageHash + "\n";
            result = result + "Recipient: " + m.recipient + "\n";
            result = result + "Message: " + m.messageText + "\n";
            result = result + "---\n";
        }
        return result;
    }

    // return total number of messages sent
    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // store message to a text file
    public void storeMessage() {
        try {
            java.io.FileWriter file = new java.io.FileWriter("messages.json", true);
            file.write("{\n");
            file.write("  \"messageID\": \"" + messageID + "\",\n");
            file.write("  \"messageHash\": \"" + messageHash + "\",\n");
            file.write("  \"recipient\": \"" + recipient + "\",\n");
            file.write("  \"message\": \"" + messageText + "\"\n");
            file.write("}\n");
            file.close();
        } catch (java.io.IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // display sender and recipient of all stored messages
    public static void displayStoredMessages() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages.");
            return;
        }
        for (int i = 0; i < storedMessages.size(); i++) {
            MessageManager m = storedMessages.get(i);
            System.out.println("Recipient: " + m.recipient);
            System.out.println("Message: " + m.messageText);
            System.out.println("---");
        }
    }

    // display the longest message
    public static void displayLongestMessage() {
        ArrayList<MessageManager> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        if (allMessages.isEmpty()) {
            System.out.println("No messages found.");
            return;
        }

        MessageManager longest = allMessages.get(0);
        for (int i = 1; i < allMessages.size(); i++) {
            if (allMessages.get(i).messageText.length() > longest.messageText.length()) {
                longest = allMessages.get(i);
            }
        }
        System.out.println("Longest message: " + longest.messageText);
    }

    // search for a message by message ID
    public static void searchByMessageID(String id) {
        //Add displayStoredMessages, displayLongestMessage and searchByMessageID methods
        ArrayList<MessageManager> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        boolean found = false;

        for (int i = 0; i < allMessages.size(); i++) {
            MessageManager m = allMessages.get(i);
            if (m.messageID.equals(id)) {
                System.out.println("Recipient: " + m.recipient);
                System.out.println("Message: " + m.messageText);
                found = true;
            }
        }

        if (found == false) {
            System.out.println("Message ID not found.");
        }
    }

    // search all messages for a particular recipient
    public static void searchByRecipient(String recipient) {
        ArrayList<MessageManager> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        boolean found = false;

        for (int i = 0; i < allMessages.size(); i++) {
            MessageManager m = allMessages.get(i);
            if (m.recipient.equals(recipient)) {
                System.out.println("Message: " + m.messageText);
                found = true;
            }
        }

        if (found == false) {
            System.out.println("No messages found for that recipient.");
        }
    }

    // delete a message using message hash
    public static void deleteByHash(String hash) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).messageHash.equals(hash)) {
                System.out.println("Message: \"" + sentMessages.get(i).messageText + "\" successfully deleted.");
                sentMessages.remove(i);
                return;
            }
        }

        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).messageHash.equals(hash)) {
                System.out.println("Message: \"" + storedMessages.get(i).messageText + "\" successfully deleted.");
                storedMessages.remove(i);
                return;
            }
        }

        System.out.println("Message hash not found.");
    }

    // display full report of all messages
    public static void displayReport() {
        ArrayList<MessageManager> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(storedMessages);

        if (allMessages.isEmpty()) {
            System.out.println("No messages to display.");
            return;
        }

        System.out.println("--- FULL REPORT ---");

        for (int i = 0; i < allMessages.size(); i++) {
            MessageManager m = allMessages.get(i);
            System.out.println("Message Hash: " + m.messageHash);
            System.out.println("Recipient: " + m.recipient);
            System.out.println("Message: " + m.messageText);
            System.out.println("---");
        }
    }
}