package com.qa.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.ExtentReportListener.ExtentManager;
import com.qa.base.TestBase;

public class WebEventListener extends TestBase implements ITestListener{

	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	public static ThreadLocal<ExtentTest> classLevelReport = new ThreadLocal<ExtentTest>();
	
	public static ThreadLocal<ExtentTest> test() {
		return test;
	}

	

	


	

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = classLevelReport.get().createNode(result.getMethod().getMethodName()); 
		extentTest.assignCategory("Test_Case_Level_Run_Result");
		logger.info("STARTING TEST :"+result.getMethod().getMethodName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info(result.getMethod().getMethodName()+" --- Test Passed");
		test.get().pass("Test passed");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.info(result.getMethod().getMethodName()+" --- Test Failed");

		String exceptionMessage = result.getThrowable().getClass().toString();
		test.get()
		.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>"
				+ "Exception Occured: </summary>" + "</font>" + "</b >"
				+ exceptionMessage.replaceAll(",", "<br>")+ "</details>"+result.getThrowable().getMessage());
		test.get().log(Status.INFO, result.getMethod().getMethodName() + " Execution Ended");
		test.get().log(Status.FAIL, "FAILED");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.info(result.getMethod().getMethodName()+" --- Test Skipped");
		//test.get().skip(result.getThrowable());
		String exceptionMessage = result.getThrowable().getClass().toString();
		test.get()
		.skip("<details>" + "<summary>" + "<b>" + "<font color=" + "yellow>"
				+ "Exception Occured: Click to see. </summary>" + "</font>" + "</b >"
				+ exceptionMessage.replaceAll(",", "<br>"));
		test.get().log(Status.INFO, result.getMethod().getMethodName() + " Execution Skipped");
		test.get().log(Status.SKIP, "SKIPPED");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		logger.info("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName());
	}

	@Override
	public void onStart(ITestContext context) {
		ExtentTest parent = extent.createTest(context.getName().toString());
		parent.assignCategory("Epic_Level_Run_Result");
		classLevelReport.set(parent);
	   

	}
	

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		
	}

	

	



}
