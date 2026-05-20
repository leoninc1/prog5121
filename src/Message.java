import java.util.ArrayList;
import java.util.Random;

public class Message {

    // variables to store message info
    String messageID;
    int numMessagesSent;
    String recipient;
    String messageText;
    String messageHash;

    // list to keep all sent messages
    static ArrayList<Message> sentMessages = new ArrayList<>();
    static int totalMessagesSent = 0;
    // Add message class with variables and constructor
    

    // constructor
    public Message(String recipient, String messageText, int numMessagesSent) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.numMessagesSent = numMessagesSent;

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
        if (recipient.length() <= 10 && recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // create the message hash
    // example: 00:1:HITONIGHT
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // remove any punctuation from last word
        lastWord = lastWord.replace("?", "").replace(".", "").replace("!", "").replace(",", "");

        String hash = firstTwo + ":" + numMessagesSent + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // send, disregard or store the message
    public String SentMessage(int choice) {
        if (choice == 1) {
            sentMessages.add(this);
            totalMessagesSent++;
            return "Message successfully sent.";
        } else if (choice == 0) {
            return "Press 0 to delete the message.";
        } else if (choice == 2) {
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

        // for loop to go through all messages
        for (int i = 0; i < sentMessages.size(); i++) {
            Message m = sentMessages.get(i);
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
            java.io.FileWriter file = new java.io.FileWriter("storedMessages.txt", true);
            file.write("messageID: " + messageID + "\n");
            file.write("messageHash: " + messageHash + "\n");
            file.write("recipient: " + recipient + "\n");
            file.write("message: " + messageText + "\n");
            file.write("---\n");
            file.close();
        } catch (java.io.IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}