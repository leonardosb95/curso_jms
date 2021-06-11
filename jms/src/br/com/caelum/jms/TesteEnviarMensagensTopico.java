package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteEnviarMensagensTopico {

	public static void main(String[] args) throws Exception {
		
		InitialContext context= new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection=factory.createConnection();
		connection.start();
		
		Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Destination fila=(Destination) context.lookup("loja");
		
		MessageProducer producer=session.createProducer(fila);
		Message message=null;
		
		message=session.createTextMessage("<pedido><id>teste</id></pedido>");
		message.setBooleanProperty("ebook", false);
		producer.send(message);
		
		
		session.close();
		connection.close();
		context.close();
		//fecha conexões
		
		
	}

}
