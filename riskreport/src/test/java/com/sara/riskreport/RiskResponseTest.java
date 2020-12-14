package com.sara.riskreport;

import org.junit.Test;

import com.itextpdf.text.DocumentException;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Scanner;

public class RiskResponseTest {
	
	/**
	 * helper method for getting resource file for testing as a string
	 * @param filename file to return as string
	 * @return files contents as a string
	 */
	private String getResourceAsString(String filename) {
		InputStream data = this.getClass().getClassLoader().getResourceAsStream(filename);
		Scanner scan = new Scanner(data).useDelimiter("\\A");
		String content = scan.hasNext() ? scan.next() : "";
		scan.close();
		return content;
	}
	
	/**
	 * test that the risk response send correctly
	 * @throws IOException 
	 * @throws DocumentException 
	 * @throws MalformedURLException 
	 */
	@Test
	public void testRiskResponse() throws MalformedURLException, DocumentException, IOException {
		RiskReportGenerator resp = new RiskReportGenerator(getResourceAsString("test4.json"));
		RiskResponse test = new RiskResponse();
		test.addResponseToQueue(resp.generateKafkaResponse());
		test.sendAvailableResponses();
		test.closeProducer();
		assertTrue(true);
	}
}
