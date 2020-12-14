package com.sara.riskreport;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResponseThread implements Runnable {
	
	private RiskResponse result;
	private Semaphore sema;
	private AtomicBoolean flag;
	
	public ResponseThread(RiskResponse resp, Semaphore sem) {
		result = resp;
		sema = sem;
		flag = new AtomicBoolean();
		flag.set(true);
	}
	
	/**
	 * run method for response thread
	 */
	@Override
	public void run() {
		try {
			while (flag.get()) {
				sema.acquire();
				result.sendAvailableResponses();
				sema.release();
				Thread.sleep(500);
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void endThread() {
		flag.set(false);
	}
}
