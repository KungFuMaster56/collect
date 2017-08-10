package com.htdf.pi.collect.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.quartz.SchedulerException;

import com.htdf.pi.collect.CronTriggerExample;

public class JMSConsumer {
	
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	
	private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	public static void main(String[] args) {
		
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKER_URL);
			Connection connection = connectionFactory.createConnection(USERNAME, PASSWORD);
			connection.start();
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("topic1");
			MessageConsumer consumer = session.createConsumer(destination);
			while(true){
				TextMessage message = (TextMessage) consumer.receive();
				if(message != null){
					if(message.getText().equals("cron")){
						CronTriggerExample cron = new CronTriggerExample();
						try {
							cron.run();
						} catch (SchedulerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						System.out.println("接收到消息："+message.getText());
					}
				}else{
					break;
				}
			}
			session.close();
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
