package com.sara.testing.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProduceMessages {
	private Producer<String, String> producer;
	
    public ProduceMessages() throws IOException {
        Properties props = loadProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(props);
	}
	
	/**
	 * parses properties information for kafka topic from config file
	 * @return properties files
	 * @throws IOException
	 */
	private Properties loadProperties() throws IOException {
	InputStream props = ProduceMessages.class.getClassLoader().getResourceAsStream("java.config");
	Properties cfg = new Properties();
	cfg.load(props);
	return cfg;
	}
	
	public void sendOneMessage(String msg) {
		producer.send(new ProducerRecord<String, String>("risk-requests", "request", msg));
		producer.flush();
	}
	
	public void sendMultipleMessages(ArrayList<String> msgs) {
		for (int i = 0; i < msgs.size(); i++) {
			producer.send(new ProducerRecord<String, String>("risk-requests", "request", msgs.get(i)));
		}
		producer.flush();
	}
	
	public void close() {
		producer.close();
	}
}
