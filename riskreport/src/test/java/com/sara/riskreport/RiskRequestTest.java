package com.sara.riskreport;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.json.JSONObject;
import org.junit.Test;

public class RiskRequestTest {
	/**
	 * test the risk request class
	 * Pre: you have to produce an example message for the consumer to consume otherwise this test will hang
	 * @throws IOException 
	 */
	@Test
	public void testRiskRequest() throws IOException {
		RiskRequest test = new RiskRequest();
		test.pollConsumer();
		String result = test.getNextRequest();
		while (result == null) {
			test.pollConsumer();
			result = test.getNextRequest();
		}
		JSONObject obj = new JSONObject(result);
		test.closeConsumer();
		assertTrue(obj.getString("company-name").equals("Tesla"));
	}
}
