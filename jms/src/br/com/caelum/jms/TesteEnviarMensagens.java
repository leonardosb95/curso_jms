package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteEnviarMensagens {

	public static void main(String[] args) throws Exception {
		
		InitialContext context= new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection=factory.createConnection();
		connection.start();
		
		Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Destination fila=(Destination) context.lookup("loja");
		
		MessageProducer producer=session.createProducer(fila);
		Message message=null;
		
		for (int i = 0; i < 1000; i++) {
			message=session.createTextMessage("<pedido><id>"+i+"</id></pedido>");
			producer.send(message);
		}
		
		session.close();
		connection.close();
		context.close();
		//fecha conexões
		
		
	}

}
