package com.sara.riskreport;

import org.junit.Test;
import java.util.Base64;

import com.itextpdf.text.DocumentException;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.io.FileOutputStream;

public class RiskReportGeneratorTest {
	
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
	 * testing how the pdf that is generated looks
	 * @throws IOException 
	 * @throws DocumentException 
	 * @throws MalformedURLException 
	 */
	@Test
	public void testRiskReportGeneration() throws MalformedURLException, DocumentException, IOException {
		RiskReportGenerator test = new RiskReportGenerator(getResourceAsString("test4.json"));
		FileOutputStream output = new FileOutputStream("./testOutput.pdf");
		output.write(test.getPdfBytes());
		output.close();
		assertTrue(true);
	}
	
	/**
	 * testing that the pdf can properly decoded from base64
	 * @throws IOException 
	 * @throws DocumentException 
	 * @throws MalformedURLException 
	 */
	@Test
	public void testBase64Encoding() throws MalformedURLException, DocumentException, IOException {
		RiskReportGenerator test = new RiskReportGenerator(getResourceAsString("test4.json"));
		byte[] decoded = Base64.getDecoder().decode(test.getPdfBytesBase64());
		for (int i = 0; i < decoded.length; i++) {
			assertTrue(decoded[i] == test.getPdfBytes()[i]);
		}
	}
	
	/**
	 * test the kafka response
	 * @throws IOException 
	 * @throws DocumentException 
	 * @throws MalformedURLException 
	 */
	@Test
	public void testKafkaResponse() throws MalformedURLException, DocumentException, IOException {
		RiskReportGenerator test = new RiskReportGenerator(getResourceAsString("test4.json"));
		String result = test.generateKafkaResponse();
		System.out.println(result);
		assertTrue(true);
	}
}
