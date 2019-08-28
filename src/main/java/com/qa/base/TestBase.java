package com.qa.base;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.codoid.products.exception.FilloException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.qa.utils.TestUtils;
import com.qa.utils.WebEventListener;
import com.qa.utils.jsonReader;
import com.qa.utils.readAndWriteData;

import io.restassured.RestAssured;



public class TestBase {

	public static Properties config;
	public Properties prop;
	public static EventFiringWebDriver edriver;
	public static WebEventListener eventlistener;
	public readAndWriteData readNwrite=new readAndWriteData();
	public static String TEST;
	public ITestResult result;
	public static Logger logger;
	public static String className;
	public static SoftAssert softAssert;
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public TestBase() {

		try {
			if(config==null) {
				config=new Properties();
				FileInputStream fs=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/config/config.properties");
				config.load(fs);

				prop=new Properties();
				FileInputStream ip=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/config/"+config.getProperty("Environment")+"Env.properties");
				prop.load(ip);
				System.out.println(prop.getProperty("env"));


			}}catch(FileNotFoundException e) {
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}



	}

	public void initial_test_tasks(Hashtable<String,String> data) {
		readNwrite.runmodeCheck(data);

	}

	@BeforeClass
	public void init(){
		className = this.getClass().getSimpleName().toString();
		logger=Logger.getLogger(className);
		PropertyConfigurator.configure(TestUtils.LOG4J_PROPERTYFILE);
	    logger.info("STARTING TESTS FOR CLASS --- "+className);
		RestAssured.baseURI = "http://localhost:8080/student";
	}

	@BeforeMethod
	public void setUp(Method m) {
		TEST=m.getName().toString();
		className = m.getDeclaringClass().getSimpleName().toString();
		logger=Logger.getLogger(className+"----"+TEST);
		PropertyConfigurator.configure(TestUtils.LOG4J_PROPERTYFILE);

	}

	@AfterClass
	public void tearDown() {

		logger.info("ENDING TESTS FOR CLASS --- "+className);
	}

	//********************************Data Provider*****************************************//


	@DataProvider
	public Object[][] dataProvider(Method m) throws FilloException, JsonIOException, JsonSyntaxException, FileNotFoundException{
		Object[][] dataSet=null;
		if(config.getProperty("dataReadConfiguration").equalsIgnoreCase("excel")) {
			System.out.println("data provider is excel");
			dataSet= readAndWriteData.dataSet(m);
		}else if(config.getProperty("dataReadConfiguration").equalsIgnoreCase("json"))

		{
			System.out.println("data provider is json");
			dataSet= jsonReader.jsonTestDataSet(m);
		}

		return dataSet;

	}

	//******************************** Reporting Functions ***************************************//

	public void reportPass(String message) {
		test.get().log(Status.PASS, message);
	}

	public void reportFail(String message) {
		test.get().log(Status.FAIL, message);
	}

	public void reportInfo(String message) {
		test.get().log(Status.INFO, message);
	}

	public void reportSkip(String message) {
		test.get().log(Status.SKIP, message);
	}

}
