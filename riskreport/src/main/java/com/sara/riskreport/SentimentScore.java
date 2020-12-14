package com.sara.riskreport;

import org.json.JSONArray;
import org.json.JSONObject;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import java.util.List;
import java.util.ArrayList;

public class SentimentScore {
	
	private double social[];
	private double news[];
	private double notes[];
	private int socialScore;
	private int newsScore;
	private int notesScore;
	private int sentimentScore;
	
	/**
	 * constructor for sentiment score
	 * @param json json file string
	 */
	public SentimentScore(String json) {
		JSONObject data = new JSONObject(json);
		parseSentimentScores(data);
		socialScore = calcSocialScore();
		newsScore = calcNewsScore();
		notesScore = calcNotesScore();
		sentimentScore = calcSentimentScore();
	}
	
	/**
	 * parse the social media scores, news scores, and meeting notes scores
	 * @param obj json data
	 */
	private void parseSentimentScores(JSONObject obj) {
		JSONArray socialData = obj.getJSONArray("social-analysis");
		JSONArray newsData = obj.getJSONArray("news-analysis");
		JSONArray notesData = obj.getJSONArray("notes-analysis");
		social = new double[socialData.length()];
		news = new double[newsData.length()];
		notes = new double[notesData.length()];
		for (int i = 0; i < socialData.length(); i++) {
			social[i] = socialData.getJSONObject(i).getDouble("value");
		}
		for (int i = 0; i <  newsData.length(); i++) {
			news[i] = newsData.getJSONObject(i).getDouble("value");
		}
		for (int i = 0; i < notesData.length(); i++) {
			notes[i] = notesData.getJSONObject(i).getDouble("value");
		}
	}
	
	/**
	 * calculate a score based on social media analysis results
	 * @return a score from 1 to 10
	 */
	private int calcSocialScore() {
		double avg = 0.0;
		for (int i = 0; i < social.length; i++) {
			avg += social[i];
		}
		avg = avg / social.length;
		if (avg == 0) {
			return 1;
		}
		return (int) Math.ceil(avg * 10);
	}
	
	/**
	 * calculate a score based on news analysis results
	 * @return a score from 1 to 10
	 */
	private int calcNewsScore() {
		double avg = 0.0;
		for (int i = 0; i < news.length; i++) {
			avg += news[i];
		}
		avg = avg / news.length;
		if (avg == 0) {
			return 1;
		}
		return (int) Math.ceil(avg * 10);
	}
	
	/**
	 * calculate a notes score based on notes analysis
	 * @return a score from 1 to 10
	 */
	private int calcNotesScore() {
		double avg = 0.0;
		for (int i = 0; i < notes.length; i++) {
			avg += notes[i];
		}
		avg = avg / notes.length;
		if (avg == 0) {
			return 1;
		}
		return (int) Math.ceil(avg * 10);
	}
	
	/**
	 * calculate the overall sentiment score 
	 * @return a score 1 to 10
	 */
	private int calcSentimentScore() {
		return (socialScore + newsScore + notesScore) / 3;
	}
	
	/**
	 * getter for social score
	 * @return social score
	 */
	public int getSocialScore() {
		return socialScore;
	}
	
	/**
	 * getter for news score
	 * @return news score
	 */
	public int getNewsScore() {
		return newsScore;
	}
	
	/**
	 * getter for notes score
	 * @return notes score
	 */
	public int getNotesScore() {
		return notesScore;
	}
	
	/**
	 * getter for sentiment score
	 * @return sentiment score
	 */
	public int getSentimentScore() {
		return sentimentScore;
	}
	
	//------------------------------- Graphing methods to create histograms of scores -------------------------------
	
	public CategoryChart plotSocialScores() {
		List<Double> xData = new ArrayList<Double>();
		for (int i = 0; i < social.length; i++) {
			xData.add(social[i]);
		}
		Histogram hist = new Histogram(xData, 20, 0, 1);
		CategoryChart chart = new CategoryChartBuilder().width(1000).height(600).title("Social Scores Histogram").xAxisTitle("Sentiment Score").yAxisTitle("Count").build();
		chart.addSeries("Scores", hist.getxAxisData(), hist.getyAxisData());
		return chart;
	}
	
	public CategoryChart plotNewsScores() {
		List<Double> xData = new ArrayList<Double>();
		for (int i = 0; i < news.length; i++) {
			xData.add(news[i]);
		}
		Histogram hist = new Histogram(xData, 20, 0, 1);
		CategoryChart chart = new CategoryChartBuilder().width(1000).height(600).title("News Scores Histogram").xAxisTitle("Sentiment Score").yAxisTitle("Count").build();
		chart.addSeries("Scores", hist.getxAxisData(), hist.getyAxisData());
		return chart;
	}
	
	public CategoryChart plotNotesScores() {
		List<Double> xData = new ArrayList<Double>();
		for (int i = 0; i < notes.length; i++) {
			xData.add(notes[i]);
		}
		Histogram hist = new Histogram(xData, 20, 0, 1);
		CategoryChart chart = new CategoryChartBuilder().width(1000).height(600).title("Notes Scores Histogram").xAxisTitle("Sentiment Score").yAxisTitle("Count").build();
		chart.addSeries("Scores", hist.getxAxisData(), hist.getyAxisData());
		return chart;
	}
	
	public CategoryChart plotAllScores() {
		List<Double> xData = new ArrayList<Double>();
		for (int i = 0; i < social.length; i++) {
			xData.add(social[i]);
		}
		for (int i = 0; i < news.length; i++) {
			xData.add(news[i]);
		}
		for (int i = 0; i < notes.length; i++) {
			xData.add(notes[i]);
		}
		Histogram hist = new Histogram(xData, 20, 0, 1);
		CategoryChart chart = new CategoryChartBuilder().width(1000).height(600).title("All Sentiment Scores Histogram").xAxisTitle("Sentiment Score").yAxisTitle("Count").build();
		chart.addSeries("Scores", hist.getxAxisData(), hist.getyAxisData());
		return chart;
	}
	
	//------------------------- Getter methods to test parsing ---------------------------------
	public double[] getSocialSentimentScores() {
		return social;
	}
	
	public double[] getNewsSentimentScores() {
		return news;
	}
	
	public double[] getNotesSentimentScores() {
		return notes;
	}
}
