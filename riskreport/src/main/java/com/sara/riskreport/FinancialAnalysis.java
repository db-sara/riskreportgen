package com.sara.riskreport;

import org.json.JSONArray;
import org.json.JSONObject;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

public class FinancialAnalysis {
	//variables to hold data for calculation
	private long netIncome[];
	private long totalRevenue[];
	private long operatingCosts[];
	private double stockHistory[];
	private long netAssets[];
	private double dividends[];
	private long netLiability[];
	private long numShares[];
	private double currentRatios[];
	private double dprs[];
	private double oms[];
	private int finScore;
	private int crScore;
	private int dprScore;
	private int omScore;
	private int niScore;
	
	/**
	 * empty constructor for testing purposes
	 */
	public FinancialAnalysis() {
		//intentionally blank
	}
	
	/**
	 * constructor for financial analysis class
	 */
	public FinancialAnalysis(String json) {
		JSONObject obj = new JSONObject(json);
		parseNetIncome(obj);
		parseTotalRevenue(obj);
		parseOperatingCosts(obj);
		parseStockPrices(obj);
		parseNetAssets(obj); 
		parseDividends(obj);
		parseNetLiability(obj);
		parseNumShares(obj);
		crScore = calcCRScore();
		dprScore = calcDprScore();
		omScore = calcOmScore();
		niScore = calcNiScore();
		finScore = calcFinScore();
	}

	/**
	 * parse the net income
	 * @param obj json object
	 * @VisibleForTesting
	 */
	public void parseNetIncome(JSONObject obj) {
		JSONArray arr = obj.getJSONArray("net-income");
		netIncome = new long[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			netIncome[i] = arr.getJSONObject(i).getLong("amount");
		}
	}

	/**
	 * parse the total revenue
	 * @param obj json object
	 * @VisibleForTesting
	 */
	public void parseTotalRevenue(JSONObject obj) {
		JSONArray arr = obj.getJSONArray("total-revenue");
		totalRevenue = new long[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			totalRevenue[i] = arr.getJSONObject(i).getLong("amount");
		}
	}

	/**
	 * parse the operating costs
	 * @param obj
	 * @VisibleForTesting
	 */
	public void parseOperatingCosts(JSONObject obj) {
		JSONArray arr = obj.getJSONArray("operating-costs");
		operatingCosts = new long[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			operatingCosts[i] = arr.getJSONObject(i).getLong("amount");
		}
	}

	/**
	 * parse the company stock prices
	 * @param obj
	 * @VisibleForTesting
	 */
	public void parseStockPrices(JSONObject obj) {
		JSONArray arr = obj.getJSONArray("stock-prices");
		stockHistory = new double[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			stockHistory[i] = arr.getJSONObject(i).getDouble("close");
		}
	}

	/**
	 * parse the company net assets
	 * @param obj json object
	 * @VisibleForTesting
	 */
	public void parseNetAssets(JSONObject obj) {
		JSONArray arr = obj.getJSONArray("net-assets");
		netAssets = new long[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			netAssets[i] = arr.getJSONObject(i).getLong("amount");
		}
	}

	/**
	 * parse the dividends paid
	 * @param obj json object
	 * @VisibleForTesting
	 */
	public void parseDividends(JSONObject obj) {
		JSONArray arr = obj.getJSONArray("dividends");
		dividends = new double[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			dividends[i] = arr.getJSONObject(i).getDouble("amount");
		}
	}

	/**
	 * parse the companies net liability
	 * @param obj json object
	 * @VisibleForTesting
	 */
	public void parseNetLiability(JSONObject obj) {
		JSONArray arr = obj.getJSONArray("net-liability");
		netLiability = new long[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			 netLiability[i] = arr.getJSONObject(i).getLong("amount");
		}
	}
	
	public void parseNumShares(JSONObject obj) {
		JSONArray arr = obj.getJSONArray("num-shares");
		numShares = new long[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			numShares[i] = arr.getJSONObject(i).getLong("amount");
		}
	}
	
	/**
	 * calculates the CR score from 1 to 10
	 * 1 being the worst, 10 being the best
	 * @return the CR score
	 */
	private int calcCRScore() {
		currentRatios = new double[netAssets.length];
		for (int i = 0; i < netAssets.length; i++) {
			currentRatios[i] = calcCR(netAssets[i], netLiability[i]);
		}
		//calculate the trend of CR over the past 4 quarters
		double q1slope = calcSlope(currentRatios[1], currentRatios[0], 0, 1);
		double q2slope = calcSlope(currentRatios[2], currentRatios[1], 1, 2);
		double q3slope = calcSlope(currentRatios[3], currentRatios[2], 2, 3);
		double q4slope = calcSlope(currentRatios[4], currentRatios[3], 3, 4);
		//calculate overall trend of CR over the past 4 quarters
		double overall = calcSlope(currentRatios[4], currentRatios[0], 0, 4);
		//calculate weighted avg
		double weightedAvg = (0.1875 * q1slope) + (0.1875 * q2slope) + (0.1875 * q3slope) + (0.1875 * q4slope) + (0.25 * overall);
		double maxChange = 10;
		double minChange = -10;
		double normalized = (weightedAvg - minChange) / (maxChange - minChange);
		return (int) Math.ceil(normalized * 10);
	}
	
	/**
	 * calculate a score based on DPR 
	 * in this case im assuming a high DPR to be good, but i guess this can
	 * be subjective depending on other factors such as how new the company is
	 * if they are investing in growth, etc.
	 * @return normalized score from 1 to 10
	 */
	private int calcDprScore() {
		dprs = new double[netIncome.length];
		for (int i = 0; i < netIncome.length; i++) {
			dprs[i] = calcDPR(netIncome[i], dividends[i], numShares[i]);
		}
		//calculate weighted avg of dpr over past 4 quarters with most recent quarter weighted highest
		double weightedavg = (0.15 * dprs[3]) + (0.2 * dprs[2]) + 
				(0.25 * dprs[1]) + (0.4 * dprs[0]);
		//handle case of dividend payout ratio is zero
		if (weightedavg == 0.0) {
			return 1;
		}
		return (int) Math.ceil(weightedavg * 10);
	}
	
	/**
	 * calculate a score based on operating margin
	 * @return score from 1 to 10
	 */
	private int calcOmScore() {
		oms = new double[totalRevenue.length];
		for (int i = 0; i < totalRevenue.length; i++) {
			oms[i] = calcOM(totalRevenue[i], operatingCosts[i]);
		}
		//calculate first derivative for last 4 quarters
		double q1slope = calcSlope(oms[1], oms[0], 0, 1);
		double q2slope = calcSlope(oms[2], oms[1], 1, 2);
		double q3slope = calcSlope(oms[3], oms[2], 2, 3);
		double q4slope = calcSlope(oms[4], oms[3], 3, 4);
		//calculate derivative over 4 quarters
		double overall = calcSlope(oms[4], oms[0], 0, 4);
		//calculate a weighted avg
		double weightedAvg = (0.1875 * q1slope) + (0.1875 * q2slope) + (0.1875 * q3slope) + (0.1875 * q4slope) + (0.25 * overall);
		double maxChange = 2;
		double minChange = -2;
		double normalized = (weightedAvg - minChange) / (maxChange - minChange);
		return (int) Math.ceil(normalized * 10);
	}
	
	/**
	 * calculate a score based on netincome
	 * @return score 1 to 10
	 */
	private int calcNiScore() {
		double[] slopes = new double[5];
		for (int i = 0; i < 4; i++) {
			slopes[i] = calcSlope(netIncome[i+1], netIncome[i], i, i + 1);
		}
		slopes[4] = calcSlope(netIncome[4], netIncome[0], 0, 4);
		int score = 0;
		//for each quarter that revenue increases add 2 to score
		for (int i = 0; i < slopes.length; i++) {
			if (slopes[i] > 0) {
				score += 2;
			}
		}
		//handle the case where score is 1x
		if (score == 0) {
			return 1;
		}
		return score;
	}
	
	/**
	 * helper function for computing CR score
	 * @param asset company assets
	 * @param liability company liability
	 * @return the current ratio
	 */
	private double calcCR(long asset, long liability) {
		return (double) liability / (double) asset;
	}
	
	/**
	 * calculate the first derivative of a linear function
	 * @param y1 first y value
	 * @param y2 second y value
	 * @param x1 first x value
	 * @param x2 second x value
	 * @return the value of first derivative
	 */
	private double calcSlope(double y1, double y2, double x1, double x2) {
		return (y2 - y1) / (x2 - x1);
	}
	
	/**
	 * calculate the dividend payout ratio
	 * @param income net income
	 * @param dividend net dividends paid
	 * @return
	 */
	private double calcDPR(long income, double dividend, long shares) {
		double totalDividends = dividend * (double) shares;
		return totalDividends / (double) income;
	}
	
	/**
	 * calculate the operating margin
	 * @param revenue total revenue
	 * @param operate total operating costs
	 * @return
	 */
	private double calcOM(long revenue, long operate) {
		return (((double) revenue - (double) operate) / (double) revenue);
	}

	/**
	 * calculate the overall financial score
	 * @return financial score 
	 */
	private int calcFinScore() {
		return (niScore + omScore + dprScore + crScore) / 4;
	}
	
	/**
	 * getter for cr score
	 * @return cr score
	 */
	public int getCrScore() {
		return crScore;
	}
	
	/**
	 * getter for dpr score
	 * @return dpr score
	 */
	public int getDprScore() {
		return dprScore;
	}
	
	/**
	 * getter for om score
	 * @return om score
	 */
	public int getOmScore() {
		return omScore;
	}
	
	/**
	 * getter for ni score
	 * @return ni score
	 */
	public int getNiScore() {
		return niScore;
	}
	
	/**
	 * getter for overall financial score
	 * @return financial score
	 */
	public int getFinancialScore() {
		return finScore;
	}
	
	/**
	 * build a graph of the cr data
	 * @return graph of the CR data
	 */
	public XYChart graphCrData() {
		double quarters[] = new double[currentRatios.length];
		for (int i = 1; i <= currentRatios.length; i++) {
			quarters[i - 1] = (double) i;
		}
		double data[] = reverse(currentRatios);
		XYChart crGraph = QuickChart.getChart("Current Ratio Trends", "Quarters", "Current Ratio", "CR", quarters, data);
		return crGraph;
	}
	
	/**
	 * build a graph of the dpr data
	 * @return
	 */
	public XYChart graphDprData() {
		double quarters[] = new double[dprs.length];
		for (int i = 1; i <= dprs.length; i++) {
			quarters[i - 1] = (double) i;
		}
		double[] data = reverse(dprs);
		XYChart dprGraph = QuickChart.getChart("Divided Payout Ratio Trends", "Quarters", "Dividend Payout Ratio", "DPR", quarters, data);
		return dprGraph;
	}
	
	/**
	 * build a graph of the om data
	 * @return a graph of om data
	 */
	public XYChart graphOmData() {
		double quarters[] = new double[oms.length];
		for (int i = 1; i <= oms.length; i++) {
			quarters[i - 1] = (double) i;
		}
		double[] data = reverse(oms);
		XYChart omGraph = QuickChart.getChart("Operating Margin Trends", "Quarters", "Operating Margin", "OM", quarters, data);
		return omGraph;
	}
	
	/**
	 * build a graph of the ni data
	 * @return graph of the ni data
	 */
	public XYChart graphNiData() {
		double quarters[] = new double[netIncome.length];
		double netIncomeDoubles[] = new double[netIncome.length];
		for (int i = 1; i <= netIncome.length; i++) {
			quarters[i - 1] = (double) i;
			netIncomeDoubles[i - 1] = (double) netIncome[i - 1];
		}
		netIncomeDoubles = reverse(netIncomeDoubles);
		XYChart niGraph = QuickChart.getChart("Net Income Trends", "Quarters", "Net Income", "NI", quarters, netIncomeDoubles);
		return niGraph;
	}
	
	private double[] reverse(double[] a) {
		for (int i = 0; i < a.length/2; i++) {
			double temp = a[i];
			a[i] = a[a.length - i - 1];
			a[a.length - i - 1] = temp;
		}
		return a;
	}
	
	// ------------------- Getter Methods for Testing ----------------------
	public double[] getDividends() {
		return dividends;
	}
	
	public long[] getNetIncome() {
		return netIncome;
	}
	
	public long[] getNetAssets() {
		return netAssets;
	}
	
	public long[] getLibaility() {
		return netLiability;
	}
	
	public long[] getRevenue() {
		return totalRevenue;
	}
	
	public long[] getOperatingCosts() {
		return operatingCosts;
	}
	
	public double[] getStockPrices() {
		return stockHistory;
	}
	
	public long[] getNumShares() {
		return numShares;
	}
}

