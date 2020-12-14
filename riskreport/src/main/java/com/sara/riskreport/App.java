package com.sara.riskreport;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class App 
{	
    public static void main( String[] args ) throws IOException, DocumentException
    {
    	final RiskRequest req = new RiskRequest();
    	final RiskResponse resp = new RiskResponse();
    	Semaphore sem = new Semaphore(1);
    	final ExecutorService exe = Executors.newFixedThreadPool(8);
    	final ResponseThread response = new ResponseThread(resp, sem);
    	Thread t = new Thread(response);
    	t.start();
    	//implement a shutdown hook so in case of shutdown it's grateful
    	Runtime.getRuntime().addShutdownHook(new Thread() {
    		@Override
    		public void run() {
    			exe.shutdown();
    	    	req.closeConsumer();
    	    	resp.closeProducer();
    	    	response.endThread();
    		}
    	});
    	while (true) {
    		req.pollConsumer();
    		int size = req.getQueueSize();
    		for (int i = 0; i < size; i++) {
    			Runnable worker = new WorkerThread(req.getNextRequest(), resp, sem);
    			exe.execute(worker);
    		}
    	}
    }
}
