package com.bw.tests.base;

import com.bw.accelerators.DriverFactory;
import com.bw.utilities.ConfigManager;
import com.bw.utilities.Log;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class UIBaseTest extends BaseTest {
	protected WebDriver driver;
	protected AppiumDriver appiumDriver;

	@AfterSuite
	public void uiAfterSuite() {
		if (driver != null)
			driver.quit();
		if (appiumDriver != null)
			appiumDriver.quit();
	}

	@Parameters({ "browser", "device" })
	@BeforeMethod
	public void uiBeforeMethod(Method method, @Optional String browser, @Optional String device)
			throws InterruptedException, MalformedURLException {

		System.out.println("Before Method");

		try {
			browser = browser == null ? ConfigManager.getProperty("browser") : browser;
			if (!(browser == null || browser.isEmpty())) {
				Log.info("Initializing Driver");
				driver = DriverFactory.setWebDriver(browser);
				Log.info("Driver initialized successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@AfterMethod
	public void uiAfterMethod() {
		if (driver != null)
			driver.quit();
		if (appiumDriver != null)
			appiumDriver.quit();
	}
}
