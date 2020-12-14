package com.sara.testing.test;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Arrays;
import java.io.IOException;

//kafka imports
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ReceiveMessages {
	private Consumer<String, String> consumer;
	
	public ReceiveMessages() throws IOException {
		Properties props = loadProperties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "risk-report-requests");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("risk-responses"));
	}
	
    /**
     * parses properties information for kafka topic from config file
     * @return properties files
     * @throws IOException
     */
	private Properties loadProperties() throws IOException {
	    InputStream props = ReceiveMessages.class.getClassLoader().getResourceAsStream("java.config");
	    Properties cfg = new Properties();
	    cfg.load(props);
	    return cfg;
	}
	
	/**
	 * use this method so we can calculate time to recieve one response on risk responses
	 * essentially we just wait until this 
	 * @return number of messages collected, should be one in this case
	 */
	public int notifyOne() {
		ConsumerRecords<String, String> record = consumer.poll(Duration.ofMillis(100));
		while (record.isEmpty()) {
			record = consumer.poll(Duration.ofMillis(100));
		}
		return record.count();
	}
	
	/**
	 * wait until we've recieved 1k responses, used for calculating throughput
	 */
	public int notify1K() {
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
		int count = 0;
		while (count < 999) {
			count += records.count();
			records = consumer.poll(Duration.ofMillis(100));
		}
		return count;
	}
	
	public int notify100() {
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
		int count = 0;
		while (count < 99) {
			count += records.count();
			records = consumer.poll(Duration.ofMillis(100));
		}
		return count;
	}
	
	public void close() {
		consumer.close();
	}
}
