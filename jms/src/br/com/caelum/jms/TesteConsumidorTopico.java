package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class TesteConsumidorTopico {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		InitialContext context= new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
	
		Connection connection=factory.createConnection();
		connection.setClientID("estoque");//Vai identificar a conexão
		connection.start();
		Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		
		Topic topico=(Topic) context.lookup("loja");
		MessageConsumer consumer=session.createDurableSubscriber(topico,"assinatura","ebook=false",false);
		
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
					//Recebe a mensgem e faz algum tratamento com ela
				TextMessage textMessage=(TextMessage) message;
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		new Scanner(System.in).nextLine();//Segura o código
		
		session.close();
		connection.close();
		context.close();
	}

}
