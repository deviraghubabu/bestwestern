package com.bw.utilities.waits;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bw.tests.base.BaseTest;

public class Wait {
	private WebDriver driver;
	private long timeoutInSeconds;

	public Wait(WebDriver driver) {
		this.driver = driver;
		this.timeoutInSeconds = (long) BaseTest.EXPLICIT_WAIT_TIME;
	}

	public Wait(WebDriver driver, long timeoutInSeconds) {
		this.driver = driver;
		this.timeoutInSeconds = timeoutInSeconds;
	}

	private WebDriverWait waits() {
		return new WebDriverWait(this.driver, this.timeoutInSeconds);
	}

	public Alert alertIsPresent() {
		return (Alert) this.waits().until(ExpectedConditions.alertIsPresent());
	}

	public void attributeContains(WebElement ele, String attribute, String value) {
		this.waits().until(ExpectedConditions.attributeContains(ele, attribute, value));
	}

	public void attributeContains(By locator, String attribute, String value) {
		this.waits().until(ExpectedConditions.attributeContains(locator, attribute, value));
	}

	public void attributeToBe(WebElement element, String attribute, String value) {
		this.waits().until(ExpectedConditions.attributeToBe(element, attribute, value));
	}

	public void attributeToBe(By locator, String attribute, String value) {
		this.waits().until(ExpectedConditions.attributeToBe(locator, attribute, value));
	}

	public void attributeToBeNotEmpty(WebElement element, String attribute) {
		this.waits().until(ExpectedConditions.attributeToBeNotEmpty(element, attribute));
	}

	public void elementSelectionStateToBe(WebElement element, boolean selected) {
		this.waits().until(ExpectedConditions.elementSelectionStateToBe(element, selected));
	}

	public void elementSelectionStateToBe(By locator, boolean selected) {
		this.waits().until(ExpectedConditions.elementSelectionStateToBe(locator, selected));
	}

	public <T extends WebElement> T elementToBeClickable(T element) {
		this.waits().until(ExpectedConditions.elementToBeClickable(element));
		return element;
	}

	public WebElement elementToBeClickable(By locator) {
		return (WebElement) this.waits().until(ExpectedConditions.elementToBeClickable(locator));
	}

	public <T extends WebElement> T elementToBeSelected(T element) {
		this.waits().until(ExpectedConditions.elementToBeSelected(element));
		return element;
	}

	public void elementToBeSelected(By locator) {
		this.waits().until(ExpectedConditions.elementToBeSelected(locator));
	}

	public WebDriver frameToBeAvailableAndSwitchToIt(By locator) {
		return (WebDriver) this.waits().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	public WebDriver frameToBeAvailableAndSwitchToIt(int frameLocator) {
		return (WebDriver) this.waits().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public WebDriver frameToBeAvailableAndSwitchToIt(String frameLocator) {
		return (WebDriver) this.waits().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public WebDriver frameToBeAvailableAndSwitchToIt(WebElement frameLocator) {
		return (WebDriver) this.waits().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void invisibilityOf(WebElement element) {
		this.waits().until(ExpectedConditions.invisibilityOf(element));
	}

	public void invisiblityOfAllElements(List<WebElement> elements) {
		this.waits().until(ExpectedConditions.invisibilityOfAllElements(elements));
	}

	public void invisibilityOfAllElements(WebElement... elements) {
		this.waits().until(ExpectedConditions.invisibilityOfAllElements(elements));
	}

	public void invisibilityOfElementLocated(By locator) {
		this.waits().until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public void invisibilityOfElementWithText(By locator, String text) {
		this.waits().until(ExpectedConditions.invisibilityOfElementWithText(locator, text));
	}

	public void javaScriptThrowsNoExceptions(String javaScript) {
		this.waits().until(ExpectedConditions.javaScriptThrowsNoExceptions(javaScript));
	}

	public Object jsReturnsValue(String javaScript) {
		return this.waits().until(ExpectedConditions.jsReturnsValue(javaScript));
	}

	public List<WebElement> numberOfElementsToBe(By locator, int number) {
		return (List) this.waits().until(ExpectedConditions.numberOfElementsToBe(locator, Integer.valueOf(number)));
	}

	public List<WebElement> numberOfElementsToBeLessThan(By locator, int number) {
		return (List) this.waits()
				.until(ExpectedConditions.numberOfElementsToBeLessThan(locator, Integer.valueOf(number)));
	}

	public List<WebElement> numberOfElementsToBeMoreThan(By locator, int number) {
		return (List) this.waits()
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, Integer.valueOf(number)));
	}

	public void numberOfWindowsToBe(int expectedNumberOfWindows) {
		this.waits().until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
	}

	public List<WebElement> presenceOfAllElementedLocatedBy(By locator) {
		return (List) this.waits().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public WebElement presenceOfElementLocated(By locator) {
		return (WebElement) this.waits().until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement presenceOfNestedElementLocatedBy(By locator, By childLocator) {
		return (WebElement) this.waits()
				.until(ExpectedConditions.presenceOfNestedElementLocatedBy(locator, childLocator));
	}

	public WebElement presenceOfNestedElementLocatedBy(WebElement element, By childLocator) {
		return (WebElement) this.waits()
				.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, childLocator));
	}

	public List<WebElement> presenceOfNestedElementsLocatedBy(By parentLocator, By childLocator) {
		return (List) this.waits()
				.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(parentLocator, childLocator));
	}

	public List<WebElement> presenceOfNestedElementsLocatedBy(WebElement parent, By childLocator) {
		return (List) this.waits().until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, childLocator));
	}

	public void stalenessOf(WebElement element) {
		this.waits().until(ExpectedConditions.stalenessOf(element));
	}

	public void textMatches(By locator, Pattern pattern) {
		this.waits().until(ExpectedConditions.textMatches(locator, pattern));
	}

	public void textToBe(By locator, String value) {
		this.waits().until(ExpectedConditions.textToBe(locator, value));
	}

	public void textToBePresentInElement(WebElement element, String text) {
		this.waits().until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	public void textToBePresentInElementLocated(By locator, String text) {
		this.waits().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}

	public void textToBePresentInElementValue(WebElement element, String text) {
		this.waits().until(ExpectedConditions.textToBePresentInElementValue(element, text));
	}

	public void textToBePresentInElementValue(By locator, String text) {
		this.waits().until(ExpectedConditions.textToBePresentInElementValue(locator, text));
	}

	public void titleContains(String title) {
		this.waits().until(ExpectedConditions.titleContains(title));
	}

	public void titleIs(String title) {
		this.waits().until(ExpectedConditions.titleIs(title));
	}

	public void urlContains(String urlFraction) {
		this.waits().until(ExpectedConditions.urlContains(urlFraction));
	}

	public void urlMatches(String url) {
		this.waits().until(ExpectedConditions.urlMatches(url));
	}

	public void urlToBe(String url) {
		this.waits().until(ExpectedConditions.urlToBe(url));
	}

	public WebElement visibilityOf(WebElement element) {
		return (WebElement) this.waits().until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement visibilityOfElementLocated(By locator) {
		return (WebElement) this.waits().until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public List<WebElement> visibilityOfAllElements(List<WebElement> elements) {
		return (List) this.waits().until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	public List<WebElement> visibilityOfAllElements(WebElement... elements) {
		return (List) this.waits().until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	public List<WebElement> visibilityOfAllElementsLocatedBy(By locator) {
		return (List) this.waits().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public List<WebElement> visibilityOfNestedElementsLocatedBy(By parent, By child) {
		return (List) this.waits().until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parent, child));
	}

	public List<WebElement> visibilityOfNestedElementsLocatedBy(WebElement parent, By child) {
		return (List) this.waits().until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parent, child));
	}
}
