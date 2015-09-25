import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicSubscriber {

	private static String topicName = "topic";
	private static String url = "tcp://localhost:61616";
	
    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(topicName);

        MessageConsumer consumer = session.createConsumer(topic);

        MessageListener listner = new MessageListener() {
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                    	
                        TextMessage textMessage = (TextMessage) message;
                        
                        System.out.println("Received message : " + textMessage.getText() + "'");
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        };
        consumer.setMessageListener(listner);

        try {
              System.in.read();
         } catch (IOException e) {
             e.printStackTrace();
         }
    connection.close();

}
}    