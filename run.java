import javax.jms.*;
import javax.naming.InitialContext;

public class JMSSample {
    public static void main(String[] args) {
        try {
            // Obtain a JNDI connection
            InitialContext ctx = new InitialContext();

            // Look up a JMS connection factory
            ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");

            // Create a JMS connection
            Connection connection = factory.createConnection();

            // Create a JMS session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Look up a JMS destination
            Destination destination = (Destination) ctx.lookup("queue/testQueue");

            // Create a message producer
            MessageProducer producer = session.createProducer(destination);

            // Create a text message
            TextMessage message = session.createTextMessage("Hello, JMS!");

            // Send the message
            producer.send(message);

            // Create a message consumer
            MessageConsumer consumer = session.createConsumer(destination);

            // Start the connection to receive messages
            connection.start();

            // Receive a message
            TextMessage receivedMessage = (TextMessage) consumer.receive();

            // Print the received message
            System.out.println("Received message: " + receivedMessage.getText());

            // Clean up
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
