package com.sara.riskreport;

import static org.junit.Assert.assertTrue;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.BitmapEncoder; 

public class FinancialAnalysisTest {
	
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
	 * test the parsing
	 */
	@Test
	public void testFileParsing() {
		JSONObject obj = new JSONObject(getResourceAsString("test.json"));
		FinancialAnalysis test = new FinancialAnalysis();
		test.parseDividends(obj);
		test.parseNetAssets(obj);
		test.parseNetIncome(obj);
		test.parseNetLiability(obj);
		test.parseOperatingCosts(obj);
		test.parseStockPrices(obj);
		test.parseTotalRevenue(obj);
		test.parseNumShares(obj);
		double[] div = test.getDividends();
	    long[] asset = test.getNetAssets();
		long[] inc = test.getNetIncome();
		long[] lia = test.getLibaility();
		long[] op = test.getOperatingCosts();
		long[] rev = test.getRevenue();
		double[] stk = test.getStockPrices();
		long[] shr = test.getNumShares();
		assertTrue(asset[0] == 100000000000L);
		assertTrue(div[0] == 0.205);
		assertTrue(inc[0] == 57411000000L);
		assertTrue(lia[0] == 94000000000L);
		assertTrue(op[0] == 4860000000L);
		assertTrue(rev[0] == 30284884853492L);
		assertTrue(stk[0] == 120.0);
		assertTrue(shr[0] == 170000000000L);
	}
	
	/**
	 * test the score calculation
	 */
	@Test
	public void testScoreCalculation() {
		FinancialAnalysis test = new FinancialAnalysis(getResourceAsString("test2.json"));
		System.out.println("The CR Score is " + test.getCrScore());
		System.out.println("The DPR Score is " + test.getDprScore());
		System.out.println("The OM Score is " + test.getOmScore());
		System.out.println("The NI Score is " + test.getNiScore());
		System.out.println("The Fin Score is " + test.getFinancialScore());
		assertTrue(test.getCrScore() >= 1 && test.getCrScore() <= 10);
		assertTrue(test.getDprScore() >= 1 && test.getDprScore() <= 10);
		assertTrue(test.getOmScore() >= 1 && test.getOmScore() <= 10);
		assertTrue(test.getNiScore() >= 1 && test.getNiScore() <= 10);
		assertTrue(test.getFinancialScore() >= 1 && test.getFinancialScore() <= 10);
	}
	
	/**
	 * test the graph creation
	 * @throws IOException 
	 */
	@Test
	public void testGraphGeneration() throws IOException {
		FinancialAnalysis test = new FinancialAnalysis(getResourceAsString("test2.json"));
		XYChart crGraph = test.graphCrData();
		XYChart omGraph = test.graphOmData();
		XYChart dprGraph = test.graphDprData();
		XYChart niGraph = test.graphNiData();
		BitmapEncoder.saveBitmap(crGraph, "./crGraph", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(omGraph, "./omGraph", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(dprGraph, "./dprGraph", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(niGraph, "./niGraph", BitmapFormat.PNG);
		assertTrue(true);
	}
}
