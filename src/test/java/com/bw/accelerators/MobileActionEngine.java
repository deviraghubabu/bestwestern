package com.bw.accelerators;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bw.utilities.Constants;
import com.bw.utilities.Log;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class MobileActionEngine {
	AppiumDriver<WebElement> driver;

	@SuppressWarnings("unchecked")
	public MobileActionEngine() throws MalformedURLException, InterruptedException {
		driver = DriverFactory.getAppiumDriver();
	}

	public void click(By locator, String locatorName) throws Exception {
		ExplicitWaitOnElementToBeClickable(locator);
		try {
			driver.findElement(locator).click();
			ReportManager.pass("Successfully clicked on " + locatorName);
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.fail("Unable to clicked on " + locatorName, getScreenshot());
			throw e;
		}
	}

	public void click(WebElement element, String locatorName) throws Exception {
		try {
			element.click();
			ReportManager.pass("Successfully clicked on " + locatorName);
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.fail("Unable to clicked on " + locatorName, getScreenshot());
			throw e;
		}
	}

	public void type(By locator, String testData, String locatorName) throws Exception {
		type(locator, testData, locatorName, true);
	}

	public void type(WebElement element, String testData, String locatorName) throws Exception {
		type(element, testData, locatorName, true);
	}

	public void type(By locator, String testData, String locatorName, boolean shouldClear) throws Exception {
		ExplicitWaitOnElementToBeClickable(locator);
		try {
			if (shouldClear) {
				driver.findElement(locator).clear();
				Log.info(locatorName + " content cleared");
			}
			driver.findElement(locator).sendKeys(testData);
			ReportManager.pass(
					"Data typing action is performed on " + locatorName + " with data is " + testData.replace(":", ""));
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.fail("Data typing action is not perform on " + locatorName + " with data is "
					+ testData.replace(":", ""), getScreenshot());
			throw e;
		}
	}

	public void type(WebElement element, String testData, String locatorName, boolean shouldClear) throws Exception {
		ExplicitWaitOnElementToBeClickable(element);
		try {
			if (shouldClear) {
				element.clear();
				Log.info(locatorName + " content cleared");
			}
			element.sendKeys(testData);
			ReportManager.pass(
					"Data typing action is performed on " + locatorName + " with data is " + testData.replace(":", ""));
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.fail("Data typing action is not perform on " + locatorName + " with data is "
					+ testData.replace(":", ""), getScreenshot());
			throw e;
		}
	}

	public boolean waitForElementPresent(By by, String locator) throws Exception {
		try {
			for (int i = 0; i < 10 * 120; i++) {
				if (driver.findElement(by).isDisplayed()) {
					ReportManager.pass("Successfully located element " + locator);
					return true;
				}
			}
			ReportManager.fail("Failed to locate element " + locator, getScreenshot());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.fail("Failed to locate element " + locator, getScreenshot());
			throw e;
		}
	}

	public boolean waitForElementPresent(WebElement element, String locator) throws Exception {
		try {
			for (int i = 0; i < 10 * 120; i++) {
				if (element.isDisplayed()) {
					ReportManager.pass("Successfully located element " + locator);
					return true;
				}
			}
			ReportManager.fail("Failed to locate element " + locator, getScreenshot());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.fail("Failed to locate element " + locator, getScreenshot());
			throw e;
		}
	}

	public boolean selectByVisibleText(By locator, String visibletext, String locatorName) throws Exception {
		try {
			Select s = new Select(driver.findElement(locator));
			if (!s.getFirstSelectedOption().getText().equalsIgnoreCase(visibletext)) {
				s.selectByVisibleText(visibletext);
			}
			ReportManager.pass(visibletext + "  is Selected from the DropDown " + locatorName);
			return true;
		} catch (Exception e) {
			ReportManager.fail(visibletext + " is Not Select from the DropDown " + locatorName, getScreenshot());
			throw e;
		}
	}

	public boolean launchUrl(String url) throws Exception {
		String Url1 = "";
		try {
			driver.navigate().to(url);
			if (url.length() >= 70)
				Url1 = url.substring(0, 70) + "...";
			else
				Url1 = url;
			ReportManager.pass("Successfully launched" + Url1);
			return true;
		} catch (Exception e) {
			ReportManager.fail("Failed to launch " + Url1, getScreenshot());
			throw e;
		}
	}

	public boolean isEnabled(By locator, String locatorName) throws Exception {
		try {
			if (driver.findElement(locator).isEnabled()) {
				ReportManager.pass(locatorName + " is Enabled");
				return true;
			} else {
				ReportManager.fail(locatorName + " is not Enabled", getScreenshot());
				return false;
			}
		} catch (Exception e) {
			ReportManager.fail(locatorName + " is not Enabled", getScreenshot());
			throw e;
		}
	}

	public String getTitle() throws Exception {
		String text = "";
		try {
			text = driver.getTitle();
			ReportManager.pass("Title of the page is " + text);
		} catch (Exception e) {
			ReportManager.fail("Failed to get the browser title", getScreenshot());
			throw e;
		}
		return text;
	}

	public String getText(WebElement element, String locatorName) throws Exception {
		ExplicitWaitOnElementToBeClickable(element);
		String text = "";
		try {
			text = element.getText();
			ReportManager.pass("Able to get Text from " + locatorName);
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.fail("Unable to get Text from " + locatorName, getScreenshot());
			throw e;
		}
		return text;
	}

	public List<String> getListData(By locator, String locatorName) throws Exception {
		List<String> listData = new ArrayList<>();
		try {
			waitForVisibilityOfElement(locator, locatorName);
			List<WebElement> textElements = driver.findElements(locator);
			for (WebElement element : textElements) {
				listData.add(element.getText());
			}
			ReportManager.pass("Able to get Text from " + locatorName);
		} catch (Exception e) {
			e.printStackTrace();
			ReportManager.fail("Unable to get Text from " + locatorName);
		}
		return listData;
	}

	public String getScreenshot() throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		byte[] imageBytes = FileUtils.readFileToByteArray(source);
		String base64Content = Base64.encodeBase64String(imageBytes);
		return base64Content;
	}

	public boolean switchToDefaultFrame() throws Exception {
		boolean flag = false;
		try {
			driver.switchTo().defaultContent();
			Log.info("Switched to default frame");
			flag = true;
			return flag;
		} catch (Exception e) {
			Log.info("Unable to switch to default frame");
			e.printStackTrace();
			return false;
		}
	}

	public boolean waitForVisibilityOfElement(By by, String locator) throws Exception {
		return this.waitForVisibilityOfElement(by, locator, 30);
	}

	public boolean waitForVisibilityOfElement(WebElement element, String locator) throws Exception {
		return this.waitForVisibilityOfElement(element, locator, 30);
	}

	public boolean waitForVisibilityOfElement(WebElement element, String locator, Integer timeout) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			ReportManager.pass("Element " + locator + "  is visible");
			return true;
		} catch (Exception e) {
			ReportManager.fail("Element " + locator + " is not visible", getScreenshot());
			throw e;
		}
	}

	public boolean waitForVisibilityOfElement(By by, String locator, Integer timeout) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			ReportManager.pass("Element " + locator + "  is visible");
			return true;
		} catch (Exception e) {
			ReportManager.fail("Element " + locator + " is not visible", getScreenshot());
			throw e;
		}
	}

	/**
	 * This method wait driver until Invisibility of Elements on WebPage.
	 *
	 * @param by : Action to be performed on element (Get it from Object repository)
	 * @param by : Action to be performed on element (Get it from Object repository)
	 */
	public boolean waitForInVisibilityOfElement(By by, String locator) throws Exception {
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver, 12);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			Log.info(locator + " invisible");
			flag = true;
			return flag;
		} catch (Exception e) {
			Log.info(locator + " not visible");
			return false;
		}

	}

	public boolean waitForInVisibilityOfElement(WebElement element, String locator) throws Exception {
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver, 12);
		try {
			wait.until(ExpectedConditions.invisibilityOf(element));
			Log.info(locator + " invisible");
			flag = true;
			return flag;
		} catch (Exception e) {
			Log.info(locator + " not visible");
			return false;
		}

	}

	public void ExplicitWaitOnElementToBeClickable(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Constants.wait_time);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			Log.info(locator + " is clickable");
		} catch (Exception e) {
			Log.info(locator + " is not clickable");
		}
	}

	public void ExplicitWaitOnElementToBeClickable(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Constants.wait_time);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Log.info(element + " is clickable");
		} catch (Exception e) {
			Log.info(element + " is not clickable");
		}
	}

	public void WaitforScreenLoad() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void WaitforElementLoad() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void waitForPageLoad() throws InterruptedException {
		for (int counter = 1; counter <= 10 * 60; counter++) {
			try {
				String getPageLoadStatusJS = "return document.readyState";
				String readyState = (String) ((JavascriptExecutor) driver).executeScript(getPageLoadStatusJS);
				if (readyState.equalsIgnoreCase("complete"))
					Log.info("Page Loaded");
				return;

			} catch (Exception e) {
				Log.info("Page load failed");
				e.printStackTrace();
			}
		}

	}

	public void executeJS(String jsString, String locatorName) {
		((JavascriptExecutor) driver).executeScript(jsString);
	}

	public boolean isElementPresent(WebElement element, String locatorName) throws Exception {
		boolean flag = false;
		try {
			element.isDisplayed();
			ReportManager.pass(locatorName + " is present on the page");
		} catch (Exception e) {
			Log.info(e.getMessage());
			e.printStackTrace();
			ReportManager.fail(locatorName + " is not present on the page", getScreenshot());
			throw e;
		}
		return flag;
	}

	public void ScrollingToPositionofAPage(int x1, int y1, int x2, int y2) {
		try {
			TouchAction ta = new TouchAction(driver);
			ta.press(x1, y1).waitAction(5000).moveTo(x2, y2).release().perform();
		} catch (Exception e) {
			Log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void changeOreintation(String orientation) throws Exception {
		try {
			if (orientation.equalsIgnoreCase("Landscape")) {
				driver.rotate(ScreenOrientation.LANDSCAPE);
			} else {
				driver.rotate(ScreenOrientation.PORTRAIT);
			}
			Log.info("Orinetation changed to " + orientation);
		} catch (Exception e) {
			Log.info("Orinetation not changed to " + orientation);
			e.printStackTrace();
		}
	}

	public void PretripInspection() throws InterruptedException {
		driver.wait(Constants.wait_time);
	}

	public void Waitfor(By by, String locatorName) throws InterruptedException {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(Constants.wait_time))
				.pollingEvery(Duration.ofSeconds(Constants.pooling_interval)).ignoring(NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(by));

	}

}
