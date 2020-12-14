package com.sara.riskreport;

import java.util.Queue;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

public class RiskResponse {
	private Producer<String, String> producer;
	private Queue<String> sendQueue;
	
	public RiskResponse() throws IOException {
		sendQueue = new LinkedList<String>();
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
    	InputStream props = RiskRequest.class.getClassLoader().getResourceAsStream("java.config");	
    	Properties cfg = new Properties();
    	cfg.load(props);
    	return cfg;
    }
    
    public void addResponseToQueue(String response) {
    	sendQueue.add(response);
    }
    
    public void sendAvailableResponses() {
    	if (!sendQueue.isEmpty()) {
    		for (int i = 0; i < sendQueue.size(); i++) {
    			producer.send(new ProducerRecord<String, String>("risk-responses", "response", sendQueue.remove()));
    		}
    		producer.flush();
    	}
    }
    
    public void closeProducer() {
    	producer.close();
    }
}
