package com.bw.accelerators;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.bw.utilities.ConfigManager;
import com.bw.utilities.Constants;
import com.bw.utilities.Log;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * Driver Factory
 *
 */
public class DriverFactory {
	private static WebDriver webDriver = null;
	private static AppiumDriver<WebElement> appiumDriver = null;

	/**
	 * 
	 * @param browser
	 * @return
	 * @throws InterruptedException
	 * @throws MalformedURLException
	 */
	public static synchronized WebDriver setWebDriver(String browser)
			throws InterruptedException, MalformedURLException {

		if (Constants.FF.equalsIgnoreCase(browser)) {
			WebDriverManager.firefoxdriver().setup();
			webDriver = new FirefoxDriver();
		} else if (Constants.CHROME.equalsIgnoreCase(browser)) {
			WebDriverManager.chromedriver().setup();
			//System.setProperty("webdriver.chrome.driver", "C:\\Users\\E005830\\Documents\\My Docs\\chromedriver.exe");
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--user-data-dir=C:\\Users\\E005830\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 4");
			//chromeOptions.addArguments("--test-type");
			chromeOptions.addArguments("--start-maximized");
			//chromeOptions.addArguments("--disable-infobars");
			chromeOptions.addArguments("--incognito");
			//capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			//capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			
			//ChromeOptions options = new ChromeOptions();
			//options.addArguments("headless");
			//options.setExperimentalOption("useAutomationExtension", false);
			//options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
			//options.addArguments("user-data-dir=C:\\Users\\E005830\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 2");
			//options.addArguments("disable-infobars");
			//options.addArguments("--start-maximized");
			//ChromeDriver chromeDriver = new ChromeDriver(options);

			webDriver = new ChromeDriver(chromeOptions);
		}else {
			System.setProperty("webdriver.edge.driver", "C:\\Users\\E005830\\Downloads\\edgedriver_win64\\msedgedriver.exe");
			webDriver = new EdgeDriver();
		}

		return webDriver;
	}

	/**
	 * 
	 * @param browser
	 * @return
	 * @throws InterruptedException
	 * @throws MalformedURLException
	 */
	public static synchronized AppiumDriver setAppiumDriver(String browser)
			throws InterruptedException, MalformedURLException {
		if (Constants.ANDROID.equalsIgnoreCase(browser)) {
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("noReset", true);
			caps.setCapability("noSign", false);
			caps.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY,
					ConfigManager.getProperty("App_Wait_Activity"));
			caps.setCapability("autoGrantPermissions", "true");
			caps.setCapability("deviceName", ConfigManager.getProperty("deviceName"));
			caps.setCapability("udid", ConfigManager.getProperty("udid"));
			caps.setCapability("automationName", ConfigManager.getProperty("automationName"));
			caps.setCapability("platformName", ConfigManager.getProperty("platformName"));
			caps.setCapability("platformVersion", ConfigManager.getProperty("platformVersion"));
			caps.setCapability("app", System.getProperty("user.dir") + "//Apps//coretex.apk");
			caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 900);

			appiumDriver = new AndroidDriver<WebElement>(new URL(ConfigManager.getProperty("appiumUrl")), caps);
			Log.info("App Launched Successfully");
		}
		return appiumDriver;
	}

	/**
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	public static synchronized WebDriver getWebDriver() throws MalformedURLException, InterruptedException {
		return webDriver;
	}

	/**
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */

	public static synchronized AppiumDriver getAppiumDriver() throws MalformedURLException, InterruptedException {
		if (appiumDriver == null) {
			setAppiumDriver(Constants.ANDROID);
		}
		return appiumDriver;
	}
}
