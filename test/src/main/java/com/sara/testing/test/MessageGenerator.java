package com.sara.testing.test;

import java.util.ArrayList;

public class MessageGenerator {
	private String initial;
	private static final int initialDataPts = 42;
	
	//set up the initial string of data
	public MessageGenerator() {
		initial = "{\n	\"company-name\": \"Tesla\",\n"
				+ "    \"report-date\": \"20201209123312\",\n"
				+ "    \"net-income\": [\n"
				+ "      {\n"
				+ "	\"amount\": 300000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 104000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 16000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 113000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 143000000\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"operating-costs\": [\n"
				+ "      {\n"
				+ "	\"amount\": 7962000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 5709000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 5702000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 7025000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 6042000000\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"net-assets\": [\n"
				+ "      {\n"
				+ "	\"amount\": 45691000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 38135000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 37205000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 34309000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 32795000000\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"stock-prices\": [\n"
				+ "        {\n"
				+ "          \"open\": 118,\n"
				+ "          \"close\": 599.04,\n"
				+ "          \"high\": 122,\n"
				+ "          \"low\": 117,\n"
				+ "          \"date\": \"2019-08-24\"\n"
				+ "        },\n"
				+ "        {\n"
				+ "          \"open\": 121,\n"
				+ "          \"close\": 388.04,\n"
				+ "          \"high\": 122,\n"
				+ "          \"low\": 118,\n"
				+ "          \"date\": \"2019-08-24\"\n"
				+ "        },\n"
				+ "        {\n"
				+ "          \"open\": 118,\n"
				+ "          \"close\": 145.04,\n"
				+ "          \"high\": 122,\n"
				+ "          \"low\": 117,\n"
				+ "          \"date\": \"2019-08-24\"\n"
				+ "        },\n"
				+ "        {\n"
				+ "          \"open\": 121,\n"
				+ "          \"close\": 95.63,\n"
				+ "          \"high\": 122,\n"
				+ "          \"low\": 118,\n"
				+ "          \"date\": \"2019-08-24\"\n"
				+ "        },\n"
				+ "        {\n"
				+ "          \"open\": 121,\n"
				+ "          \"close\": 45.63,\n"
				+ "          \"high\": 122,\n"
				+ "          \"low\": 118,\n"
				+ "          \"date\": \"2019-08-24\"\n"
				+ "        }\n"
				+ "    ],\n"
				+ "    \"dividends\": [\n"
				+ "      {\n"
				+ "	\"amount\": 0\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 0\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 0\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 0\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 0\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"net-liability\": [\n"
				+ "      {\n"
				+ "	\"amount\": 28800000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 27410000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 27210000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 26840000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 25913000000\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"num-shares\": [\n"
				+ "      {\n"
				+ "	\"amount\": 1105000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 1035000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 995000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 885000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 922000000\n"
				+ "      }\n"
				+ "    ],\n"
				+ "    \"total-revenue\": [\n"
				+ "      {\n"
				+ "	\"amount\": 8771000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 6036000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 5985000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 7384000000\n"
				+ "      },\n"
				+ "      {\n"
				+ "	\"amount\": 6303000000\n"
				+ "      }\n"
				+ "    ],\n";
	}
	
	public String generateNDataPts(int n) {
		int toGenerate = n - initialDataPts;
		String result = initial;
		int remain = toGenerate % 3;
		int amt = toGenerate/3;
		result += "\"social-analysis\":[";
		for (int i = 0; i < amt - 1; i++) {
			double rand = Math.random();
			result += "{\"value\":" + Double.toString(rand) + "},";
		}
		result += "{\"value\":" + Double.toString(Math.random()) + "}],";
		result += "\"news-analysis\":[";
		for (int i = 0; i < amt - 1; i++) {
			double rand = Math.random();
			result += "{\"value\":" + Double.toString(rand) + "},";
		}
		result += "{\"value\":" + Double.toString(Math.random()) + "}],";
		result += "\"notes-analysis\":[";
		for (int i = 0; i < amt - 1 + remain; i++) {
			double rand = Math.random();
			result += "{\"value\":" + Double.toString(rand) + "},";
		}
		result += "{\"value\":" + Double.toString(Math.random()) + "}]}";
		return result;
	}
	
	public ArrayList<String> get1kRequests() {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) {
			//we'll use 500 data points for this
			String data = generateNDataPts(500);
			ret.add(data);
		}
		return ret;
	}
	
	public ArrayList<String> get100Requests() {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			//we'll use 500 data points for this
			String data = generateNDataPts(500);
			ret.add(data);
		}
		return ret;
	}
}
