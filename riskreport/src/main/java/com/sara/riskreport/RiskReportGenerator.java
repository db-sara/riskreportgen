package com.sara.riskreport;

//byte stream for pdf bytes
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Base64;

//pdf creation
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

//chart manipulation
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.CategoryChart;

//import for json object
import org.json.JSONObject;

public class RiskReportGenerator {
	private FinancialAnalysis fin;
	private SentimentScore sen;
	private int overallScore;
	byte[] pdfBytes;
	byte[] pdfBytesBase64;
	String companyName;
	String reportDate;
	
	public RiskReportGenerator(String data) throws DocumentException, MalformedURLException, IOException {
		fin = new FinancialAnalysis(data);
		sen = new SentimentScore(data);
		overallScore = (fin.getFinancialScore() + sen.getSentimentScore()) / 2;
		JSONObject obj = new JSONObject(data);
		companyName = obj.getString("company-name");
		reportDate = obj.getString("report-date");
		generatePDF();
	}
	
	/**
	 * method to create the pdf bytes for base64 encoding
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private void generatePDF() throws DocumentException, MalformedURLException, IOException {
		ByteArrayOutputStream pdf = new ByteArrayOutputStream();
		Document output = new Document();
		PdfWriter.getInstance(output, pdf);
		output.open();
		Font title = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD ,BaseColor.BLACK);
		Font body = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK);
		Font header = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.UNDERLINE, BaseColor.BLACK);
		Paragraph titleParagraph = new Paragraph();
		Chunk titleChunk = new Chunk("Risk Report Generated for " + companyName + "for date: " +  reportDate + "\n", title);
		Phrase titlePhrase =  new Phrase();
		titlePhrase.add(titleChunk);
		titleParagraph.add(titlePhrase);
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		output.add(titleParagraph);
		Chunk overallAnalysis = new Chunk("Overall Analysis Results:\n", header);
		Phrase overallPhrase = new Phrase();
		overallPhrase.add(overallAnalysis);
		Phrase overallResult = new Phrase("The overall risk score is " + overallScore + "\n", body);
		Phrase overallResult2 = new Phrase("This score is derived from a Financial Analysis score of " + fin.getFinancialScore(), body);
		Phrase overallResult3 = new Phrase("and a Sentiment Analysis score of " + sen.getSentimentScore() + "\n", body);
		Phrase scoreNotice = new Phrase("All scores in this report range from 1 to 10 with 1 being the worst score.\n", body);
		Paragraph overallParagraph = new Paragraph();
		overallParagraph.add(overallPhrase);
		overallParagraph.add(scoreNotice);
		overallParagraph.add(overallResult);
		overallParagraph.add(overallResult2);
		overallParagraph.add(overallResult3);
		output.add(overallParagraph);
		Chunk financialAnalysis = new Chunk("Financial Analysis Results:\n", header);
		Phrase financialPhrase = new Phrase();
		financialPhrase.add(financialAnalysis);
		Phrase finIntro = new Phrase("The Financial score of " + fin.getFinancialScore() + " was derived from the following metrics.\n", body);
		Phrase crPhrase = new Phrase("Current Ratio score - " + fin.getCrScore() + "\n", body);
		Phrase dprPhrase = new Phrase("Dividend Payout Ratio - " + fin.getDprScore() + "\n", body);
		Phrase omPhrase = new Phrase("Operating Margin Score - " + fin.getOmScore() + "\n", body);
		Phrase niPhrase = new Phrase("Net Income Score - " + fin.getNiScore() + "\n", body);
		Paragraph financialParagraph = new Paragraph();
		financialParagraph.add(financialPhrase);
		financialParagraph.add(finIntro);
		financialParagraph.add(crPhrase);
		financialParagraph.add(dprPhrase);
		financialParagraph.add(omPhrase);
		financialParagraph.add(niPhrase);
		output.add(financialParagraph);
		Chunk sentimentAnalysis = new Chunk("Sentiment Analysis Results:\n", header);
		Phrase sentimentPhrase = new Phrase();
		sentimentPhrase.add(sentimentAnalysis);
		Phrase sentimentIntro = new Phrase("The Sentiment Score of " + sen.getSentimentScore() + " was derived from the following metrics.\n", body);
		Phrase socialPhrase = new Phrase("Social Media Score - " + sen.getSocialScore() + "\n", body);
		Phrase newsPhrase = new Phrase("News Score - " + sen.getNewsScore() + "\n", body);
		Phrase notesPhrase = new Phrase("Meeting Notes Score - " + sen.getNotesScore() + "\n", body);
		Paragraph sentimentParagraph = new Paragraph();
		sentimentParagraph.add(sentimentPhrase);
		sentimentParagraph.add(sentimentIntro);
		sentimentParagraph.add(socialPhrase);
		sentimentParagraph.add(newsPhrase);
		sentimentParagraph.add(notesPhrase);
		output.add(sentimentParagraph);
		XYChart crGraph = fin.graphCrData();
		XYChart dprGraph = fin.graphDprData();
		XYChart omGraph = fin.graphOmData();
		XYChart niGraph = fin.graphNiData();
		Image crImage = Image.getInstance(BitmapEncoder.getBitmapBytes(crGraph, BitmapFormat.PNG));
		Image dprImage = Image.getInstance(BitmapEncoder.getBitmapBytes(dprGraph, BitmapFormat.PNG));
		Image omImage = Image.getInstance(BitmapEncoder.getBitmapBytes(omGraph, BitmapFormat.PNG));
		Image niImage = Image.getInstance(BitmapEncoder.getBitmapBytes(niGraph, BitmapFormat.PNG));
		Phrase financialVisualization = new Phrase("Financial Analysis Visualizations:\n", header);
		Paragraph finViz = new Paragraph();
		finViz.add(financialVisualization);
		output.add(finViz);
		output.add(crImage);
		Paragraph newLine = new Paragraph(" ");
		output.add(newLine);
		output.add(dprImage);
		output.add(newLine);
		output.add(omImage);
		output.add(newLine);
		output.add(niImage);
		CategoryChart overallHist = sen.plotAllScores();
		CategoryChart socialHist = sen.plotSocialScores();
		CategoryChart newsHist = sen.plotNewsScores();
		CategoryChart notesHist = sen.plotNotesScores();
		Image senImage = Image.getInstance(BitmapEncoder.getBitmapBytes(overallHist, BitmapFormat.PNG));
		Image socialImage = Image.getInstance(BitmapEncoder.getBitmapBytes(socialHist, BitmapFormat.PNG));
		Image newsImage = Image.getInstance(BitmapEncoder.getBitmapBytes(newsHist, BitmapFormat.PNG));
		Image notesImage = Image.getInstance(BitmapEncoder.getBitmapBytes(notesHist, BitmapFormat.PNG));
		Phrase sentimentVisualization = new Phrase("Sentiment Analysis Visualizations:\n", header);
		Paragraph senViz = new Paragraph();
		senViz.add(sentimentVisualization);
		output.add(senViz);
		output.add(senImage);
		output.add(newLine);
		output.add(socialImage);
		output.add(newLine);
		output.add(newsImage);
		output.add(newLine);
		output.add(notesImage);
		output.close();
		pdfBytes = pdf.toByteArray();
	}
	
	/**
	 * method for testing
	 * @return the bytes of the pdf
	 */
	public byte[] getPdfBytes() {
		return pdfBytes;
	}
	
	/**
	 * returns the base64 encoded bytes of the pdf
	 * @return base64 encoded bytes
	 */
	public byte[] getPdfBytesBase64() {
		return Base64.getEncoder().encode(pdfBytes);
	}
	
	public String generateKafkaResponse() {
		String start = "{";
		String name = "\"company-name\":" + "\"" + companyName + "\",";
		String date = "\"report-date\":" + "\"" + reportDate + "\",";
		String base64String = new String(getPdfBytesBase64());
		String pdf = "\"pdf-bytes\":" + "\"" + base64String + "\"";
		String end = "}";
		return start + name + date + pdf + end;
	}
}