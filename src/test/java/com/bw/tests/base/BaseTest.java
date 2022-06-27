package com.bw.tests.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.bw.accelerators.ReportManager;
import com.bw.utilities.ConfigManager;
import com.bw.utilities.Constants;
import com.bw.utilities.FileUtils;
import com.bw.utilities.Log;
import com.bw.utilities.RandomGeneratorUtil;
import com.bw.utilities.TestRailAPIClient;

public class BaseTest {
	public static TestRailAPIClient client = new TestRailAPIClient(ConfigManager.getProperty("TestRailAPIUrl"));
	public static JSONObject testRun;
	public static boolean testRailUpdate = false;
	public static int EXPLICIT_WAIT_TIME = Constants.explicitWaitTime;
	@BeforeSuite
	public void beforeSuite(ITestContext testContext) throws Exception {
		try {
			Log.info("Before Suite");
			Log.info("Loading Project Configutations");
			String suiteName = testContext.getSuite().getName();
			ConfigManager.loadConfig();
			Log.info("Loaded Project Configutations Successfully");
			String currentDirectory = System.getProperty("user.dir");
			String resultsDir = currentDirectory.concat(File.separator).concat("Results");
			FileUtils.deleteAndCreateADirectory(resultsDir);
			ReportManager.createReport(suiteName);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Falied to setup the suite " + e.getMessage());
		}
	}

	@AfterSuite
	public void afterSuite() {
		Log.info("After Suite");
		ReportManager.publishReport();
	}

	@BeforeMethod
	public void baseBeforeMethod(Method method) throws InterruptedException {
		try {
			String methodName = method.getName().trim();
			Log.startTestCase(methodName);
			ReportManager.createTest(methodName);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@AfterMethod
	public void afterMethod(Method method) throws IOException, Exception {
		String methodName = method.getName().trim();
		Log.info("After Method");
		Log.endTestCase(methodName);
		ReportManager.publishReport();
	}

	/*
	 * ------------------------ Test Rail Update // ------------------------
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@BeforeSuite
	public void createTestRun(ITestContext testContext) throws Exception {
		String strTestRailUpdate = System.getProperty("testRailUpdate");
		if (strTestRailUpdate != null) {
			testRailUpdate = Boolean.parseBoolean(strTestRailUpdate);
		}
		if (testRailUpdate) { 
			try {
				LinkedList testIds = new LinkedList();
				Iterator testMethods = testContext.getSuite().getAllMethods().iterator();

				while (testMethods.hasNext()) {
					ITestNGMethod runName = (ITestNGMethod) testMethods.next();
					String details = runName.getMethodName();
					String[] tokens = details.split("_");
					try {
						if(tokens.length>1) {
						testIds.add(Integer.valueOf(Integer.parseInt(tokens[tokens.length - 1])));
						}else {
							Log.info("WARNING: Test Rail integration is enabled but test method " + details+ "found without test id. Test case will not be added to test rail test run");
						}
					} catch (NumberFormatException var14) {
						Log.info("WARNING: Test Rail integration is enabled but test method found without test id ["
								+ details + "]");
						testRailUpdate=false;
					}
				}
				if (testIds.size() > 0) {
					Map<String, java.io.Serializable> data = new HashMap<>();
					data.put("name", "Cigniti Temp test run-" + RandomGeneratorUtil.getRandomNumber(9999));
					data.put("include_all", false);
					data.put("case_ids", testIds);
					testRun = (JSONObject) client.sendPost("add_run/1", data);
				}else {
					testRailUpdate=false;
				}

			} catch (Exception e) {
				Log.info("WARNING: Failed to create test run in Test Rail. Results will not be posted. Caused by: ["
						+ e.getClass().getName() + ": " + e.getMessage() + "]");
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@AfterMethod
	public void updateTestResults(Method method, ITestResult result) throws IOException, Exception {
		if (testRailUpdate) {
			System.out.println("I am here");
			try {
				Map<String, Comparable> data = new HashMap<>();
				String methodName = method.getName().trim();
				String tokens[] = (methodName.split("_"));
				if(tokens.length>1) {
					System.out.println("I am here");
					String testCaseId = tokens[tokens.length - 1];
					Log.info(result.getStatus() + "");
					switch (result.getStatus()) {
					case 1:
						data.put("status_id", "1");
						data.put("comment", "Passed using automation");
						break;
					case 2:
						data.put("status_id", "5");
						data.put("comment", "Failed using automation");
						break;
					default:
						data.put("status_id", "3");
						data.put("comment", "Unable to complete test using automation");
					}
					JSONObject output = (JSONObject) client
							.sendPost("add_result_for_case/" + testRun.get("id") + "/" + testCaseId, data);
				}else {
					Log.info("WARNING: Skipping test results update for test " + method.getName() + " Caused by: [" + "test method found without test id]");
				}


			} catch (Exception e) {
				Log.info("WARNING: Failed to update results for test " + method.getName() + " Caused by: ["
						+ e.getClass().getName() + ": " + e.getMessage() + "]");
			}
		}

	}

}
