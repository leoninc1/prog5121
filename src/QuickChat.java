import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to LeonChat");
        System.out.println("-------------------");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // check if login is valid
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Login failed. Please try again.");
        } else {
            System.out.println("Login successful! Welcome " + username);
            System.out.println("Welcome to QuickChat.");

            // ask how many messages to send
            System.out.print("How many messages do you want to send? ");
            int numMessages = Integer.parseInt(scanner.nextLine());

            int choice = 0;

            // while loop to keep the menu running until user quits
            while (choice != 3) {

                System.out.println("\n1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose: ");
                choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {

                    // for loop to send the number of messages the user chose
                    for (int i = 0; i < numMessages; i++) {

                        System.out.println("\nMessage " + (i + 1) + " of " + numMessages);

                        // get recipient number
                        String recipient = "";
                        boolean validNumber = false;

                        // while loop to keep asking until number is correct
                        while (validNumber == false) {
                            System.out.print("Enter recipient number (with + and country code): ");
                            recipient = scanner.nextLine();

                            Message tempMsg = new Message(recipient, "temp", i + 1);
                            String check = tempMsg.checkRecipientCell();

                            if (check.equals("Cell phone number successfully captured.")) {
                                System.out.println(check);
                                validNumber = true;
                            } else {
                                System.out.println(check);
                            }
                        }

                        // get message text
                        String messageText = "";
                        boolean validMessage = false;

                        // while loop to keep asking until message is short enough
                        while (validMessage == false) {
                            System.out.print("Enter your message (max 250 characters): ");
                            messageText = scanner.nextLine();

                            if (messageText.length() > 250) {
                                int over = messageText.length() - 250;
                                System.out.println("Please enter a message of less than 250 characters.");
                                System.out.println("Message exceeds 250 characters by " + over + "; please reduce the size.");
                            } else {
                                System.out.println("Message ready to send.");
                                validMessage = true;
                            }
                        }

                        // create the message
                        Message message = new Message(recipient, messageText, i + 1);
                        System.out.println("Message Hash: " + message.messageHash);

                        // ask what to do with the message
                        System.out.println("1) Send Message");
                        System.out.println("0) Disregard Message");
                        System.out.println("2) Store Message");
                        System.out.print("Choose: ");
                        int sendChoice = Integer.parseInt(scanner.nextLine());

                        String result = message.SentMessage(sendChoice);
                        System.out.println(result);

                        // show message details if sent
                        if (sendChoice == 1) {
                            System.out.println("Message ID: " + message.messageID);
                            System.out.println("Message Hash: " + message.messageHash);
                            System.out.println("Recipient: " + message.recipient);
                            System.out.println("Message: " + message.messageText);
                        }
                    }

                    // show total messages after the for loop
                    System.out.println("Total messages sent: " + Message.returnTotalMessagess());

                } else if (choice == 2) {
                    System.out.println("Coming Soon.");

                } else if (choice == 3) {
                    System.out.println("Goodbye " + username + "!");
                }
            }
        }

        scanner.close();
    }
}
//Add QuickChat main app with menu and loops
