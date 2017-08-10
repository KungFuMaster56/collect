package com.htdf.pi.collect.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProducer {
	// 默认连接用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// 默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// 默认连接地址
	private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

	public static void main(String[] args) {
		try {
			//连接工厂
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
			//连接
			Connection connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			//创建session
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//消息目的地
			Destination destination = session.createTopic("topic1");
			//消息生产者
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//发送消息
			//for(int i=0 ;i<10 ;i++){
				//创建一条文本消息
				TextMessage message = session.createTextMessage("cron");
				//生产者发送消息
				producer.send(message);
			//}
			session.commit();
			session.close();
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
