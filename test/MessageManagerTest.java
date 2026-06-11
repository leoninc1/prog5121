import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


public class MessageManagerTest {

    MessageManager message1;
    MessageManager message2;
    MessageManager message3;
    MessageManager message4;
    MessageManager message5;

    @Before
    public void setUp() {
        // clear all arrays before each test
        MessageManager.sentMessages.clear();
        MessageManager.disregardedMessages.clear();
        MessageManager.storedMessages.clear();
        MessageManager.messageHashes.clear();
        MessageManager.messageIDs.clear();
        MessageManager.totalMessagesSent = 0;

        // test data from assignment
        message1 = new MessageManager("+27834557896", "Did you get the cake?", 1);
        message2 = new MessageManager("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2);
        message3 = new MessageManager("+27834484567", "Yohoooo, I am at your gate.", 3);
        message4 = new MessageManager("0838884567", "It is dinner time !", 4);
        message5 = new MessageManager("+27838884567", "Ok, I am leaving without you.", 5);

        // set flags as per assignment
        message1.SentMessage(1);   // sent
        message2.SentMessage(2);   // stored
        message3.SentMessage(0);   // disregarded
        message4.SentMessage(1);   // sent
        message5.SentMessage(2);   // stored
    }

    // test that sent messages array is correctly populated
    @Test
    public void testSentMessagesArray() {
        assertEquals(2, MessageManager.sentMessages.size());
        assertEquals("Did you get the cake?", MessageManager.sentMessages.get(0).messageText);
        assertEquals("It is dinner time !", MessageManager.sentMessages.get(1).messageText);
    }

    // test display longest message
    @Test
    public void testLongestMessage() {
        ArrayList<MessageManager> allMessages = new ArrayList<>();
        allMessages.addAll(MessageManager.sentMessages);
        allMessages.addAll(MessageManager.storedMessages);

        MessageManager longest = allMessages.get(0);
        for (int i = 1; i < allMessages.size(); i++) {
            if (allMessages.get(i).messageText.length() > longest.messageText.length()) {
                longest = allMessages.get(i);
            }
        }

        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.messageText);
    }

    // test search by message ID
    @Test
    public void testSearchByMessageID() {
        String id = message4.messageID;
        boolean found = false;

        ArrayList<MessageManager> allMessages = new ArrayList<>();
        allMessages.addAll(MessageManager.sentMessages);
        allMessages.addAll(MessageManager.storedMessages);

        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).messageID.equals(id)) {
                assertEquals("It is dinner time !", allMessages.get(i).messageText);
                found = true;
            }
        }

        assertTrue("Message should be found by ID", found);
    }

    // test search by recipient
    @Test
    public void testSearchByRecipient() {
        String recipient = "+27838884567";

        ArrayList<MessageManager> allMessages = new ArrayList<>();
        allMessages.addAll(MessageManager.sentMessages);
        allMessages.addAll(MessageManager.storedMessages);

        ArrayList<String> results = new ArrayList<>();

        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).recipient.equals(recipient)) {
                results.add(allMessages.get(i).messageText);
            }
        }

        assertTrue("Should find messages for recipient", results.size() >= 1);
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(results.contains("Ok, I am leaving without you."));
    }

    // test delete by message hash
    @Test
    public void testDeleteByHash() {
        String hash = message2.messageHash;
        int sizeBefore = MessageManager.storedMessages.size();

        for (int i = 0; i < MessageManager.storedMessages.size(); i++) {
            if (MessageManager.storedMessages.get(i).messageHash.equals(hash)) {
                MessageManager.storedMessages.remove(i);
                break;
            }
        }

        int sizeAfter = MessageManager.storedMessages.size();
        assertTrue("Stored messages should decrease after delete", sizeAfter < sizeBefore);
    }

    // test display report
    @Test
    public void testDisplayReport() {
        ArrayList<MessageManager> allMessages = new ArrayList<>();
        allMessages.addAll(MessageManager.sentMessages);
        allMessages.addAll(MessageManager.storedMessages);

        assertTrue("Should have messages in report", allMessages.size() > 0);
    }

    // test recipient cell success
    @Test
    public void testRecipientCellSuccess() {
        String result = message1.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }

    // test recipient cell failure
    @Test
    public void testRecipientCellFailure() {
        String result = message4.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    // test message hash
    @Test
    public void testMessageHash() {
        String hash = message1.createMessageHash();
        assertTrue("Hash should end with DIDCAKE", hash.endsWith("DIDCAKE"));
        System.out.println("Message 1 Hash: " + hash);
    }

    // test message hash loop
    @Test
    public void testMessageHashLoop() {
        MessageManager[] messages = { message1, message2 };
        String[] endings = { "DIDCAKE", "WHERETIME" };

        for (int i = 0; i < messages.length; i++) {
            String hash = messages[i].createMessageHash();
            assertEquals(hash.toUpperCase(), hash);
            assertTrue("Hash should end with " + endings[i], hash.endsWith(endings[i]));
            System.out.println("Hash " + (i + 1) + ": " + hash);
        }
    }
}
//Add unit tests for MessageManager class