package com.bw.accelerators;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bw.exceptions.ControlStateException;
import com.bw.exceptions.UnexpectedValueException;
import com.bw.tests.base.BaseTest;
import com.bw.utilities.Constants;
import com.bw.utilities.Log;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class WebActionEngine {
	WebDriverWait wait;
	WebDriver driver;

	public WebActionEngine(WebDriver driver) {
		this.driver = driver;
	}

	public void setText(WebElement element, String value, String controlName) throws Exception {
		Log.info("Setting control " + controlName + "\'s text to " + value);
		this.clear(element, controlName);
		element.sendKeys(new CharSequence[] { value });
		if (!element.getAttribute("value").equals(value)) {
			ReportManager.fail("Unable to set " + controlName + "\'s value to " + value, getAshotScreenshot());
			throw new UnexpectedValueException("Unable to set " + controlName + "\'s value to " + value);
		} else {
			ReportManager.pass("Set control " + controlName + "\'s text to " + value);
		}
	}

	public void setTextWithoutClearTextBox(WebElement element, String value, String controlName) throws Exception {
		Log.info("Setting control " + controlName + "\'s text to " + value);
		element.sendKeys(new CharSequence[] { value });
		if (!element.getAttribute("value").equals(value)) {
			ReportManager.fail("Unable to set " + controlName + "\'s value to " + value, getAshotScreenshot());
			throw new UnexpectedValueException("Unable to set " + controlName + "\'s value to " + value);
		} else {
			ReportManager.pass("Set control " + controlName + "\'s text to " + value);
		}
	}

	public boolean verifyText(WebElement element, String value, String controlName) throws Exception {
		try {
			return this.getText(element, controlName).equals(value);
		} catch (Exception var3) {
			ReportManager.fail("Unable to verify " + controlName + "\'s value.", getAshotScreenshot());
			throw new Exception("Unable to verify " + controlName + "\'s value.");
		}
	}

	public void clear(WebElement element, String controlName) throws Exception {
		try {
			Log.info("Clearing text box " + controlName);
			element.clear();
			ReportManager.pass("Cleared text box " + controlName);
		} catch (Exception var2) {
			ReportManager.fail("Unable to clear text box " + controlName, getAshotScreenshot());
			throw new Exception("Unable to clear text box " + controlName);
		}
	}

	public void click(WebElement element, String controlName) throws Exception {
		try {
			this.waitForClickable(element, controlName);
			element.click();
		} catch (Exception var2) {
			ReportManager.fail("unable to click on " + controlName, getAshotScreenshot());
		}
	}

	public void waitForClickable(WebElement element, String controlName) throws Exception {
		try {
			Log.info("Waiting for element " + controlName + " to become clickable.");
			(new WebDriverWait(driver, (long) BaseTest.EXPLICIT_WAIT_TIME))
					.until(ExpectedConditions.elementToBeClickable(element));
			ReportManager.pass("Element " + controlName + " is now clickable.");
		} catch (TimeoutException var2) {
			ReportManager.fail("Timed out waiting for element " + controlName + " to become clickable.",
					getAshotScreenshot());
			throw var2;
		} catch (NoSuchElementException var3) {
			ReportManager.fail("Unable to locate element " + controlName, getAshotScreenshot());
			throw new Exception("Unable to locate element " + controlName);
		} catch (Exception var4) {
			ReportManager.fail("An exception occurred waiting for control " + controlName + " to become clickable.",
					getAshotScreenshot());
			throw var4;
		}
	}

	public String getText(WebElement element, String controlName) throws Exception{
		if (this.exits(element, BaseTest.EXPLICIT_WAIT_TIME)) {
			return element.getText();
		} else {
			throw new Exception("Unable to retrieve the text for control " + controlName);
		}
	}

	public String getAttribute(WebElement element, String attribute, String controlName) throws Exception {
		if (this.exits(element, BaseTest.EXPLICIT_WAIT_TIME)) {
			return element.getAttribute(attribute);
		} else {
			throw new Exception("Unable to retrieve the text for control " + controlName);
		}
	}
	
	protected boolean exits(WebElement element, int seconds) {
		try {
			(new WebDriverWait(this.driver, (long) seconds)).until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception var3) {
			return false;
		}
	}

	// Check Box

	public void check(WebElement element, String controlName) throws Exception {
		Log.info("Checking " + controlName);
		if (this.isChecked(element)) {
			ReportManager.pass("Checkbox " + controlName + " is already checked");
		} else {
			element.click();
			if (!this.isChecked(element)) {
				throw new ControlStateException("Clicking " + controlName + " did not check the checkbox");
			}

			ReportManager.pass("Checked " + controlName);
		}

	}

	public void unCheck(WebElement element, String controlName) throws Exception {
		Log.info("Un-checking " + controlName);
		if (!this.isChecked(element)) {
			ReportManager.pass("Checkbox " + controlName + " is already un-checked");
		} else {
			element.click();
			if (this.isChecked(element)) {
				throw new ControlStateException("Clicking " + controlName + " did not un-check the checkbox");
			}

			ReportManager.pass("un-checked " + controlName);
		}

	}

	public boolean isChecked(WebElement element) {
		return element.isSelected();
	}

	// Select Box

	public void select(WebElement element, List<WebElement> options, String value, String controlName)
			throws Exception {
		Log.info("Selecting option " + value + " from select box " + controlName);
		this.exposeOptionsList(element, controlName);
		this.selectOption(options, value);
		ReportManager.pass("Selected option " + value + " from drop-down box " + controlName);
	}

	private void exposeOptionsList(WebElement element, String controlName) throws Exception {
		this.click(element, controlName);
	}

	public void selectOption(List<WebElement> options, String value) throws Exception {
		for (int i = 0; i < options.size(); ++i) {
			WebElement control = (WebElement) options.get(i);
			if (control.getText().trim().equalsIgnoreCase(value)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", control);
				ReportManager.pass("Clicking option " + control.getText());
				control.click();
				return;
			}
		}

	}

	public String selectRandomOption(WebElement element, List<WebElement> options, String value, String controlName)
			throws Exception {
		Log.info("Selecting random option from control " + controlName);
		this.exposeOptionsList(element, controlName);
		WebElement option = (WebElement) options.get(getRandomNumberInRange(0, options.size() - 1));
		this.selectOption(options, option.getText());
		Log.info("Selecting random option " + option);
		return option.getText();
	}

	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		} else {
			Random r = new Random();
			return r.nextInt(max - min + 1) + min;
		}
	}

	public String getSelectedText(WebElement element) throws Exception {
		Select select = new Select(element);
		List<WebElement> options = select.getOptions();
		Iterator<WebElement> var3 = options.iterator();

		WebElement option;
		do {
			if (!var3.hasNext()) {
				return null;
			}

			option = (WebElement) var3.next();
		} while (!option.isSelected());

		return option.getText();
	}

	public String getValue(WebElement element, int index) throws Exception {
		Select select = new Select(element);
		return ((WebElement) select.getOptions().get(index)).getAttribute("value");
	}
	
	public boolean isElementDisplayed(WebElement element, String controlName) throws Exception {
			if (this.exits(element, BaseTest.EXPLICIT_WAIT_TIME)) {
					element.isDisplayed();
					ReportManager.pass("Element " + controlName + " is Displayed on UI");
					return true;
			}else{
				ReportManager.fail("Element " + controlName + " is not Displayed on UI");
				throw new Exception ("Element " + controlName + " is not Displayed on UI");
		}
	}
	
	public void launchUrl(String url) throws Exception {
		String Url1 = "";
		try {
			driver.manage().window().maximize();
			driver.navigate().to(url);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			if (url.length() >= 70)
				Url1 = url.substring(0, 70) + "...";
			else
				Url1 = url;
			//ReportManager.pass("Successfully launched " + Url1);
		} catch (Exception e) {
			ReportManager.fail("Failed to launch " + Url1, getAshotScreenshot());
			throw new Exception("Failed to launch " + Url1+". \n" + e.getMessage());
		}
	}

	public String getScreenshot() throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		byte[] imageBytes = FileUtils.readFileToByteArray(source);
		String base64Content = Base64.encodeBase64String(imageBytes);
		return base64Content;
	}

	public String getAshotScreenshot() throws Exception {
		try {
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
					.takeScreenshot(driver);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(screenshot.getImage(), "PNG", os);
			return java.util.Base64.getEncoder().encodeToString(os.toByteArray());
		} catch (Exception e) {
			Log.info(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public void switchToDefaultFrame() throws Exception {
			driver.switchTo().defaultContent();
			Log.info("Switched to default context");
	}
	
	public void waitForPageLoad() throws Exception {
        Log.info("Waiting for page to load.");
        String s = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState", new Object[0]);

        while(!s.equals("complete")) {
            s = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState", new Object[0]);

            try {
                Thread.sleep(500L);
            } catch (InterruptedException var3) {
                Log.error("Timed out waiting for page to load.");
                throw var3;
            }
        }

        Log.info("Page loaded.");
    }
	
	public void mousehover(WebElement element, String controlName) throws Exception {
		try {
			new Actions(driver).moveToElement(element).build().perform();
			ReportManager.pass("Able to mouse hover on " + controlName);
		} catch (Exception e) {
			ReportManager.fail("Unable to mouse hover on " + controlName, getAshotScreenshot());
			throw new Exception("Unable to mouse hover on " + controlName);
		}
	}

	public void dragAndDrop(WebElement source, WebElement dest, String srcControlName, String desControlName) throws Exception {
		try {
			new Actions(driver).clickAndHold(source).moveToElement(dest).release(dest).build().perform();
			ReportManager.pass("Able to drag from " + srcControlName + " and Drop on " + desControlName);
		} catch (Exception e) {
			ReportManager.fail("Unable to drag from " + srcControlName + " and Drop on " + desControlName, getScreenshot());
			throw new Exception("Unable to drag from " + srcControlName + " and Drop on " + desControlName);
		}
	}

	public void typeAndSelect(WebElement element, String value, WebElement locatorSug, String controlName)
			throws Exception {
			this.setText(element, value, controlName);
			locatorSug.click();
	}
	
	public void acceptAlert() throws Exception {
		Alert alert = null;
		try {
			alert = driver.switchTo().alert();
			alert.accept();
			ReportManager.pass("Able to handle the alert");
		} catch (NoAlertPresentException ex) {
			ReportManager.fail("Unable to handle the alert ", getScreenshot());
			throw new Exception("Unable to handle the alert");
		}
	}
	
	public void doubleClick(WebElement element, String controlName) throws Exception {
		try {
			new Actions(driver).doubleClick(element).build().perform();
			ReportManager.pass("Able to Double click on " + controlName);
		} catch (Exception e) {
			ReportManager.fail("Unable to Double clicked on " + controlName, getAshotScreenshot());
			throw new Exception("Unable to Double clicked on " + controlName);
		}
	}

	public void clickEnter() throws Exception {
			new Actions(driver).sendKeys(Keys.ENTER).build().perform();
			ReportManager.pass("Pressed Enter Key on the keyboard");
	}
	
	public void hitKeyboardKey(Keys key, String keyname) throws Exception{
			Actions action = new Actions(driver);
			action.sendKeys(key).build().perform();
			Log.info("Pressed " + keyname+ " on Keyboard");
	}
	
	public void scrollToWebElement(WebElement element, String controlName) throws Exception {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			ReportManager.fail("Unable to perform ScrollIntoView on  " + controlName, getAshotScreenshot());
			throw new Exception("Unable to perform ScrollIntoView on  " + controlName);
		}
	}

	public void waitForPageLoad(int explicitWait) throws InterruptedException {
		Thread.sleep(explicitWait * 1000);
	}
	
	public void jsClick(WebElement element, String controlName) throws Exception {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			ReportManager.pass("Successfully clicked on " + controlName);
		} catch (Exception e) {
			ReportManager.fail("Unable to clicked on " + controlName, getAshotScreenshot());
			throw new Exception("Unable to clicked on " + controlName);
		}
	}
	
	
	public void jsFUnctionCall(WebElement element, String controlName) throws Exception {
		try {
			//((JavascriptExecutor) driver).executeScript("BestWestern.FindHotelUI.updateSearchParams();");
			((JavascriptExecutor) driver).executeScript("document.querySelector('div .submitButton > button').dispatchEvent(new Event('click'));");
			ReportManager.pass("Successfully clicked on " + controlName);
		} catch (Exception e) {
			ReportManager.fail("Unable to clicked on " + controlName, getAshotScreenshot());
			throw new Exception("Unable to clicked on " + controlName);
		}
	}
	
	public void jsSetText(WebElement element, String value, String controlName) throws Exception {
		try {
			Log.info("Setting control " + controlName + "\'s text to " + value);
			((JavascriptExecutor) driver).executeScript("arguments[0].value='"+value+"';", element);
			ReportManager.pass("Set control " + controlName + "\'s text to " + value);
		} catch (Exception e) {
			ReportManager.fail("Unable to Set control " + controlName + "\'s text to " + value, getAshotScreenshot());
			throw new Exception("Unable to Set control " + controlName + "\'s text to " + value);
		}
	}
	
	public void refreshPage() throws Exception{
		driver.navigate().refresh();
		Log.info("Page is refreshing");
	}
}
