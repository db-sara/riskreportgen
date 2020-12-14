package com.sara.riskreport;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.io.IOException;
import java.util.Queue;
import java.util.Arrays;
import java.util.LinkedList;

//kafka imports
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

//for debugging
//import java.util.Scanner;

public class RiskRequest {
	//hold requests to be handled
	private Queue<String> requests;
	private Consumer<String, String> consumer;

	/**
	 * constructor for risk request
	 * will open kafka connection and then load request info to queue
	 * @throws IOException 
	 */
	public RiskRequest() throws IOException {
		//instantiate the queue which holds requests for risk reports
		requests = new LinkedList<String>();
		Properties props = loadProperties();
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "risk-report-requests");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList("risk-requests"));
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

    public String getNextRequest() {
    	if (requests.isEmpty()) {
    		return null;
    	}
    	return requests.remove();
    }
    
    public int getQueueSize() {
    	return requests.size();
    }
    
    public void pollConsumer() {
    	ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
    	for (ConsumerRecord<String, String> record : records) {
    		requests.add(record.value());
    	}
    }
    
    public void closeConsumer() {
    	consumer.close();
    }
}
