package com.sara.testing.test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {
        ProduceMessages produce = new ProduceMessages();
        ReceiveMessages recieve = new ReceiveMessages();
        MessageGenerator gen = new MessageGenerator();
        //provide control over which tests should be run
        boolean test10diffSize = true;
        boolean test1kMessages = true;
        boolean test100diffSize = true;
        boolean test100Messages = true;
        long sum = 0;
        //prime the connection as it was taking a large amount of time for first message
        produce.sendOneMessage(gen.generateNDataPts(100));
        int n = recieve.notifyOne();
        assert (n == 1);
        if (test10diffSize) {
	        //generate fixed size data points for testing
	        String dataPt100 = gen.generateNDataPts(100);
	        String dataPt500 = gen.generateNDataPts(500);
	        String dataPt1000 = gen.generateNDataPts(1000);
	        String dataPt5000 = gen.generateNDataPts(5000);
	        String dataPt10000 = gen.generateNDataPts(10000);
	        //generate arrays to store results of 10 tests
	        long[] dataPt100Test = new long[10];
	        long[] dataPt500Test = new long[10];
	        long[] dataPt1000Test = new long[10];
	        long[] dataPt5000Test = new long[10];
	        long[] dataPt10000Test = new long[10];
	        for (int i = 0; i < 10; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt100);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt100Test[i] = endTime - startTime;
	        	System.out.println("100 Data Points Test - Run " + i + " took " + dataPt100Test[i] + " milliseconds");
	        	sum += dataPt100Test[i];
	        }
	        System.out.println("100 Data Points Test Average was " + sum / 10 + " milliseconds\n");
	        sum = 0;
	        for (int i = 0; i < 10; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt500);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt500Test[i] = endTime - startTime;
	        	System.out.println("500 Data Points Test - Run " + i + " took " + dataPt500Test[i] + " milliseconds");
	        	sum += dataPt500Test[i];
	        }
	        System.out.println("500 Data Points Test Average was " + sum / 10 + " milliseconds\n");
	        sum = 0;
	        for (int i = 0; i < 10; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt1000);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt1000Test[i] = endTime - startTime;
	        	System.out.println("1000 Data Points Test - Run " + i + " took " + dataPt1000Test[i] + " milliseconds");
	        	sum += dataPt1000Test[i];
	        }
	        System.out.println("1000 Data Points Test Average was " + sum / 10 + " milliseconds\n");
	        sum = 0;
	        for (int i = 0; i < 10; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt5000);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt5000Test[i] = endTime - startTime;
	        	System.out.println("5000 Data Points Test - Run " + i + " took " + dataPt5000Test[i] + " milliseconds");
	        	sum += dataPt5000Test[i];
	        }
	        System.out.println("5000 Data Points Test Average was " + sum / 10 + " milliseconds\n");
	        sum = 0;
	        for (int i = 0; i < 10; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt10000);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt10000Test[i] = endTime - startTime;
	        	System.out.println("10000 Data Points Test - Run " + i + " took " + dataPt10000Test[i] + " milliseconds");
	        	sum += dataPt10000Test[i];
	        }
	        System.out.println("10000 Data Points Test Average was " + sum / 10 + " milliseconds\n");
        }
        if (test1kMessages) {
	        //generate 1k messages for 1k msg test
	        ArrayList<String> data1kMsg = gen.get1kRequests();
	        //create array to store test results
	        long[] data1kMsgTest = new long[10];
	        sum = 0;
	        for (int i = 0; i < 10; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendMultipleMessages(data1kMsg);
	        	int num = recieve.notify1K();
	        	assert(num == 999);
	        	final long endTime = System.currentTimeMillis();
	        	data1kMsgTest[i] = endTime - startTime;
	        	System.out.println("1K Messages Test - Run " + i + " took " + data1kMsgTest[i] + " milliseconds");
	        	sum += data1kMsgTest[i];
	        }
	        System.out.println("1K Messages Test Average was " + sum / 10 + " milliseconds");
        }
        if (test100diffSize) {
        	//generate fixed size data points for testing
	        String dataPt100 = gen.generateNDataPts(100);
	        String dataPt500 = gen.generateNDataPts(500);
	        String dataPt1000 = gen.generateNDataPts(1000);
	        String dataPt5000 = gen.generateNDataPts(5000);
	        String dataPt10000 = gen.generateNDataPts(10000);
	        //generate arrays to store results of 10 tests
	        long[] dataPt100Test = new long[100];
	        long[] dataPt500Test = new long[100];
	        long[] dataPt1000Test = new long[100];
	        long[] dataPt5000Test = new long[100];
	        long[] dataPt10000Test = new long[100];
	        for (int i = 0; i < 100; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt100);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt100Test[i] = endTime - startTime;
	        	System.out.println("100 Data Points Test - Run " + i + " took " + dataPt100Test[i] + " milliseconds");
	        	sum += dataPt100Test[i];
	        }
	        System.out.println("100 Data Points Test Average was " + sum / 100 + " milliseconds\n");
	        sum = 0;
	        for (int i = 0; i < 100; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt500);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt500Test[i] = endTime - startTime;
	        	System.out.println("500 Data Points Test - Run " + i + " took " + dataPt500Test[i] + " milliseconds");
	        	sum += dataPt500Test[i];
	        }
	        System.out.println("500 Data Points Test Average was " + sum / 100 + " milliseconds\n");
	        sum = 0;
	        for (int i = 0; i < 100; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt1000);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt1000Test[i] = endTime - startTime;
	        	System.out.println("1000 Data Points Test - Run " + i + " took " + dataPt1000Test[i] + " milliseconds");
	        	sum += dataPt1000Test[i];
	        }
	        System.out.println("1000 Data Points Test Average was " + sum / 100 + " milliseconds\n");
	        sum = 0;
	        for (int i = 0; i < 100; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt5000);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt5000Test[i] = endTime - startTime;
	        	System.out.println("5000 Data Points Test - Run " + i + " took " + dataPt5000Test[i] + " milliseconds");
	        	sum += dataPt5000Test[i];
	        }
	        System.out.println("5000 Data Points Test Average was " + sum / 100 + " milliseconds\n");
	        sum = 0;
	        for (int i = 0; i < 100; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendOneMessage(dataPt10000);
	        	int num = recieve.notifyOne();
	        	assert(num == 1);
	        	final long endTime = System.currentTimeMillis();
	        	dataPt10000Test[i] = endTime - startTime;
	        	System.out.println("10000 Data Points Test - Run " + i + " took " + dataPt10000Test[i] + " milliseconds");
	        	sum += dataPt10000Test[i];
	        }
	        System.out.println("10000 Data Points Test Average was " + sum / 100 + " milliseconds\n");
        }
        if (test100Messages) {
	        //generate 1k messages for 1k msg test
	        ArrayList<String> data100Msg = gen.get100Requests();
	        //create array to store test results
	        long[] data1kMsgTest = new long[10];
	        sum = 0;
	        for (int i = 0; i < 10; i++) {
	        	final long startTime = System.currentTimeMillis();
	        	produce.sendMultipleMessages(data100Msg);
	        	int num = recieve.notify100();
	        	assert(num == 99);
	        	final long endTime = System.currentTimeMillis();
	        	data1kMsgTest[i] = endTime - startTime;
	        	System.out.println("100 Messages Test - Run " + i + " took " + data1kMsgTest[i] + " milliseconds");
	        	sum += data1kMsgTest[i];
	        }
	        System.out.println("100 Messages Test Average was " + sum / 10 + " milliseconds");
        }
        produce.close();
        recieve.close();
    }
}
