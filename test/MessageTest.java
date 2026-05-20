import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MessageTest {

    Message message1;
    Message message2;

    @Before
    public void setUp() {
        // test message 1
        message1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);

        // test message 2
        message2 = new Message("08575975889", "Hi Keegan, did you receive the payment?", 2);
    }

    // test that message is not more than 250 characters - success
    @Test
    public void testMessageLengthSuccess() {
        String msg = message1.messageText;
        assertTrue("Message ready to send.", msg.length() <= 250);
    }

    // test that message is not more than 250 characters - failure
    @Test
    public void testMessageLengthFailure() {
        String longMessage = "This is a very long message that is way too long and should not be allowed "
                + "by the application because it goes over the two hundred and fifty character limit "
                + "that has been set. This extra text makes sure it is over the limit for testing.";

        int over = longMessage.length() - 250;
        assertTrue("Message exceeds 250 characters by " + over + "; please reduce the size.",
                longMessage.length() > 250);
    }

    // test recipient number is correct - success
    @Test
    public void testRecipientCellSuccess() {
        String result = message1.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }

    // test recipient number is wrong - failure
    @Test
    public void testRecipientCellFailure() {
        String result = message2.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    // test message hash for message 1
    @Test
    public void testMessageHash() {
        String hash = message1.createMessageHash();
        assertTrue("Hash should end with HITONIGHT", hash.endsWith("HITONIGHT"));
        System.out.println("Message 1 Hash: " + hash);
    }

    // test message hash for all messages using a loop
    @Test
    public void testMessageHashLoop() {
        Message[] messages = { message1, message2 };
        String[] endings = { "HITONIGHT", "HIPAYMENT" };

        // for loop to test each message hash
        for (int i = 0; i < messages.length; i++) {
            String hash = messages[i].createMessageHash();
            assertTrue("Hash should end with " + endings[i], hash.endsWith(endings[i]));
            System.out.println("Hash " + (i + 1) + ": " + hash);
        }
    }

    // test that message ID is created
    @Test
    public void testMessageID() {
        String id = message1.messageID;
        assertNotNull("Message ID should not be null", id);
        System.out.println("Message ID generated: " + id);
    }

    // test check message ID method
    @Test
    public void testCheckMessageID() {
        boolean result = message1.checkMessageID();
        assertTrue("Message ID should be 10 characters or less", result);
    }

    // test send message
    @Test
    public void testSendMessage() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        String result = msg.SentMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    // test disregard message
    @Test
    public void testDisregardMessage() {
        Message msg = new Message("08575975889", "Hi Keegan, did you receive the payment?", 2);
        String result = msg.SentMessage(0);
        assertEquals("Press 0 to delete the message.", result);
    }

    // test store message
    @Test
    public void testStoreMessage() {
        Message msg = new Message("+27718693002", "Test message.", 3);
        String result = msg.SentMessage(2);
        assertEquals("Message successfully stored.", result);
    }

    // test total messages sent
    @Test
    public void testTotalMessages() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        int before = Message.returnTotalMessagess();
        msg.SentMessage(1);
        int after = Message.returnTotalMessagess();
        assertTrue("Total should go up after sending", after > before);
        System.out.println("Total messages sent: " + after);
    }
}
//Add unit tests for Message class