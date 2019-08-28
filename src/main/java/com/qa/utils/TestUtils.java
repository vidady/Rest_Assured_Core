package com.qa.utils;

import java.util.Random;

public class TestUtils {
	public static final String LOG4J_PROPERTYFILE=System.getProperty("user.dir")+"/src/main/java/com/qa/config/log4j.properties";
	public static String getRandomValue(){
		Random random = new Random();
		int radomInt = random.nextInt(100000);
		return Integer.toString(radomInt);
	}
	

}
