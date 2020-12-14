package com.sara.riskreport;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import com.itextpdf.text.DocumentException;

public class WorkerThread implements Runnable {
	
	private String json;
	private RiskResponse result;
	private Semaphore sem;
	
	/**
	 * constructor for worker thread
	 * @param data json data
	 */
	public WorkerThread(String data, RiskResponse req, Semaphore sema) {
		json = data;
		result = req;
		sem = sema;
	}
	
	/**
	 * run method for the worker thread
	 */
	@Override
	public void run() {
		try {
			RiskReportGenerator gen = new RiskReportGenerator(this.json);
			String kafkaResp = gen.generateKafkaResponse();
			sem.acquire();
			result.addResponseToQueue(kafkaResp);
			sem.release();
		}
		catch (InterruptedException | DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
}
