package com.sara.riskreport;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;;


public class SentimentScoreTest {
	
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
	 * tests the json parsing of sentiment scores
	 */
	@Test
	public void testFileParsing() {
		SentimentScore test = new SentimentScore(getResourceAsString("test3.json"));
		double[] social = test.getSocialSentimentScores();
		double[] news = test.getNewsSentimentScores();
		double[] notes = test.getNotesSentimentScores();
		assertTrue(social[0] == 0.32);
		assertTrue(news[0] == 0.12);
		assertTrue(notes[0] == 0.86);
	}
	
	/**
	 * tests the calculation of sentiment scores
	 */
	@Test
	public void testScores() {
		SentimentScore test = new SentimentScore(getResourceAsString("test3.json"));
		System.out.println("The social score is " + test.getSocialScore());
		System.out.println("The news score is " + test.getNewsScore());
		System.out.println("The notes score is " + test.getNotesScore());
		System.out.println("The overall score is " + test.getSentimentScore());
		assertTrue(test.getSocialScore() >= 1 && test.getSocialScore() <= 10);
		assertTrue(test.getNewsScore() >= 1 && test.getNewsScore() <= 10);
		assertTrue(test.getNotesScore() >= 1 && test.getNotesScore() <= 10);
		assertTrue(test.getSentimentScore() >= 1 && test.getSentimentScore() <= 10);
	}
	
	/**
	 * tests the creation of histograms
	 * @throws IOException 
	 */
	@Test
	public void testGraphing() throws IOException {
		SentimentScore test = new SentimentScore(getResourceAsString("test3.json"));
		CategoryChart socialHist = test.plotSocialScores();
		CategoryChart newsHist = test.plotNewsScores();
		CategoryChart notesHist = test.plotNotesScores();
		CategoryChart overallHist = test.plotAllScores();
		BitmapEncoder.saveBitmap(socialHist, "./socialHistogram", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(newsHist, "./newsHistogram", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(notesHist, "./notesHistogram", BitmapFormat.PNG);
		BitmapEncoder.saveBitmap(overallHist, "./overallHistogram", BitmapFormat.PNG);
	}
}
