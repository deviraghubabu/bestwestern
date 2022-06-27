package com.bw.utilities.validations;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Assert.ThrowingRunnable;

public class Validations {
	private static WebDriver driver;;
	private static final String EQ = "equals";
	private static final String NEQ = "not equals";

	public Validations(WebDriver driver) {
		Validations.driver = driver;
	}

	private static WebDriver driver() {
		return (WebDriver) driver;
	}

	public static Validation not(Validation validation) {
		return Validation.of(() -> {
			try {
				validation.getStep().perform();
				throw new Validations.NegatedAssertionError(
						"Validation is expected to fail when negated with not, but the following validation passed: ["
								+ validation.getName() + "]");
			} catch (Validations.NegatedAssertionError var2) {
				throw var2;
			} catch (Error | Exception var3) {
				;
			}
		}, validation.getTarget(), validation.getName(), validation.getMessage());
	}

	public static Validation elementExists(By locator, String failureMessage) {
		return Validation.of(() -> {
			driver().findElement(locator);
		}, locator, "Element exists", failureMessage);
	}

	public static Validation elementIsVisible(By locator, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertTrue(driver().findElement(locator).isDisplayed(), "Not visible: " + locator);
		}, locator, "Element is visible", failureMessage);
	}

	public static Validation elementIsVisible(WebElement element, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertTrue(element.isDisplayed(), "Not displayed: " + element);
		}, element, "Element is visible", failureMessage);
	}

	public static Validation elementIsEnabled(By locator, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertTrue(driver().findElement(locator).isEnabled(), "Not enabled: " + locator);
		}, locator, "Element is enabled", failureMessage);
	}

	public static Validation elementIsEnabled(WebElement element, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertTrue(element.isEnabled(), "Not enabled: " + element);
		}, element, "Element is enabled", failureMessage);
	}

	public static Validation elementTextEquals(By locator, String expectedText, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(driver().findElement(locator).getText(), expectedText,
					"Text not equal for: " + locator);
		}, locator, "Element text equals", failureMessage);
	}

	public static Validation elementTextEquals(WebElement element, String expectedText, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(element.getText(), expectedText, "Text not equal for: " + element);
		}, element, "Element text equals", failureMessage);
	}

	public static Validation elementTextContains(By locator, String expectedText, String failureMessage) {
		return Validation.of(() -> {
			String text = driver().findElement(locator).getText();
			Assert.assertTrue(text.contains(expectedText), "Text not contained for: " + locator
					+ ". Expected text containing [" + expectedText + "] but found [" + text + "]");
		}, locator, "Element text equals", failureMessage);
	}

	public static Validation elementTextContains(WebElement element, String expectedText, String failureMessage) {
		return Validation.of(() -> {
			String text = element.getText();
			Assert.assertTrue(text.contains(expectedText), "Text not contained for: " + element
					+ ". Expected text containing [" + expectedText + "] but found [" + text + "]");
		}, element, "Element text equals", failureMessage);
	}

	public static Validation elementTextMatches(By locator, String pattern, String failureMessage) {
		return Validation.of(() -> {
			String text = driver().findElement(locator).getText();
			Assert.assertTrue(Pattern.matches(pattern, text),
					"Expected element text [" + text + "] to match [" + pattern + "] for: " + locator);
		}, locator, "element text matches", failureMessage);
	}

	public static Validation elementTextMatches(WebElement element, String pattern, String failureMessage) {
		return Validation.of(() -> {
			String text = element.getText();
			Assert.assertTrue(Pattern.matches(pattern, text),
					"Expected element text [" + text + "] to match [" + pattern + "] for: " + element);
		}, element, "element text matches", failureMessage);
	}

	public static Validation elementIsSelected(By locator, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertTrue(driver().findElement(locator).isSelected(), "Not selected: " + locator);
		}, locator, "Element is selected", failureMessage);
	}

	public static Validation elementIsSelected(WebElement element, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertTrue(element.isSelected(), "Not selected: " + element);
		}, element, "Element is selected", failureMessage);
	}

	public static Validation elementAttributeEquals(By locator, String attributeName, String expectedValue,
			String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(driver().findElement(locator).getAttribute(attributeName), expectedValue,
					"Attribute not equal for: " + locator);
		}, locator, "Element attribute equals", failureMessage);
	}

	public static Validation elementAttributeEquals(WebElement element, String attributeName, String expectedValue,
			String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(element.getAttribute(attributeName), expectedValue,
					"Attribute not equal for: " + element);
		}, element, "Element attribute equals", failureMessage);
	}

	public static Validation elementAttributeContains(By locator, String attributeName, String expectedValue,
			String failureMessage) {
		return Validation.of(() -> {
			String actualValue = driver().findElement(locator).getAttribute(attributeName);
			Assert.assertTrue(actualValue.contains(expectedValue), "Expected attribute [" + attributeName
					+ "] to contain [" + expectedValue + "] but found [" + actualValue + "] for: " + locator);
		}, locator, "Element attribute contains", failureMessage);
	}

	public static Validation elementAttributeContains(WebElement element, String attributeName, String expectedValue,
			String failureMessage) {
		return Validation.of(() -> {
			String actualValue = element.getAttribute(attributeName);
			Assert.assertTrue(actualValue.contains(expectedValue), "Expected attribute [" + attributeName
					+ "] to contain [" + expectedValue + "] but found [" + actualValue + "] for: " + element);
		}, element, "Element attribute contains", failureMessage);
	}

	public static Validation elementAttributeMatches(By locator, String attributeName, String pattern,
			String failureMessage) {
		return Validation.of(() -> {
			String actualValue = driver().findElement(locator).getAttribute(attributeName);
			boolean matches = Pattern.compile(pattern).matcher(actualValue).matches();
			Assert.assertTrue(matches, "Expected attribute [" + attributeName + "] to match [" + pattern
					+ "] but found [" + actualValue + "] for: " + locator);
		}, locator, "Element attribute matches", failureMessage);
	}

	public static Validation elementAttributeMatches(WebElement element, String attributeName, String pattern,
			String failureMessage) {
		return Validation.of(() -> {
			String actualValue = element.getAttribute(attributeName);
			boolean matches = Pattern.compile(pattern).matcher(actualValue).matches();
			Assert.assertTrue(matches, "Expected attribute [" + attributeName + "] to match [" + pattern
					+ "] but found [" + actualValue + "] for: " + element);
		}, element, "Element attribute matches", failureMessage);
	}

	public static Validation numberOfElementsIs(By locator, int expectedCount, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(driver().findElements(locator).size(), expectedCount);
		}, locator, "Number of elements", failureMessage);
	}

	public static Validation numberOfElementsIsLessThan(By locator, int expectedCount, String failureMessage) {
		return Validation.of(() -> {
			int actualCount = driver().findElements(locator).size();
			Assert.assertTrue(actualCount < expectedCount, "Expected less than " + expectedCount
					+ " elements but found " + actualCount + " elements for: " + locator);
		}, locator, "Number of elements less than", failureMessage);
	}

	public static Validation numberOfElementsIsGreaterThan(By locator, int expectedCount, String failureMessage) {
		return Validation.of(() -> {
			int actualCount = driver().findElements(locator).size();
			Assert.assertTrue(actualCount > expectedCount, "Expected greater than " + expectedCount
					+ " elements but found " + actualCount + " elements for: " + locator);
		}, locator, "Number of elements greater than", failureMessage);
	}

	public static Validation equals(boolean actual, boolean expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(byte actual, byte expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(char actual, char expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(double actual, double expected, double delta, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected, delta);
		}, "equals", failureMessage);
	}

	public static Validation equals(float actual, float expected, float delta, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected, delta);
		}, "equals", failureMessage);
	}

	public static Validation equals(int actual, int expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(long actual, long expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(short actual, short expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(String actual, String expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(Object actual, Object expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(Collection<?> actual, Collection<?> expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(Iterable<?> actual, Iterable<?> expected, String failureMessage) {
		return equals(actual.iterator(), expected.iterator(), failureMessage);
	}

	public static Validation equals(Set<?> actual, Set<?> expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(Object[] actual, Object[] expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equals(Map<?, ?> actual, Map<?, ?> expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEquals(actual, expected);
		}, "equals", failureMessage);
	}

	public static Validation equalsNoOrder(Object[] actual, Object[] expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertEqualsNoOrder(actual, expected);
		}, "equals no order", failureMessage);
	}

	public static Validation notEquals(double actual, double expected, double delta, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertNotEquals(actual, expected, delta);
		}, "not equals", failureMessage);
	}

	public static Validation notEquals(float actual, float expected, float delta, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertNotEquals(actual, expected, delta);
		}, "not equals", failureMessage);
	}

	public static Validation notEquals(Map<?, ?> actual, Map<?, ?> expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertNotEquals(actual, expected);
		}, "not equals", failureMessage);
	}

	public static Validation notEquals(Set<?> actual, Set<?> expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertNotEquals(actual, expected);
		}, "not equals", failureMessage);
	}

	public static Validation notEquals(Object actual, Object expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertNotEquals(actual, expected);
		}, "not equals", failureMessage);
	}

	public static Validation contains(Collection<?> collection, Object expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertTrue(collection.contains(expected),
					"Object [" + expected + "] is not contained in the collection. " + collection.toArray());
		}, collection, "contains", failureMessage);
	}

	public static Validation notNull(Object object, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertNotNull(object);
		}, "not null", failureMessage);
	}

	public static Validation notSame(Object actual, Object expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertNotSame(actual, expected);
		}, "not null", failureMessage);
	}

	public static Validation isNull(Object object, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertNull(object);
		}, "not null", failureMessage);
	}

	public static Validation same(Object actual, Object expected, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertSame(actual, expected);
		}, "not null", failureMessage);
	}

	public static Validation throwsException(ThrowingRunnable runnable, String failureMessage) {
		return Validation.of(() -> {
			Assert.assertThrows(runnable);
		}, "throws", failureMessage);
	}

	public static <T extends Throwable> Validation throwsException(Class<T> throwableClass, ThrowingRunnable runnable,
			String failureMessage) {
		return Validation.of(() -> {
			Assert.assertThrows(throwableClass, runnable);
		}, "throws", failureMessage);
	}

	public static boolean isElementPresent(By element) {
		boolean isPresent = driver().findElements(element).size() > 0;
		return isPresent;
	}

	public static Validation elementIsNotVisible(By locator, String failureMessage) {
		return Validation.of(() -> {
			try {
				Assert.assertFalse(driver().findElement(locator).isDisplayed(), "Visible: " + locator);
			} catch (NoSuchElementException var2) {
				;
			}

		}, locator, "Element is not visible", failureMessage);
	}

	public static Validation elementIsNotVisible(WebElement element, String failureMessage) {
		return Validation.of(() -> {
			try {
				Assert.assertFalse(element.isDisplayed(), "displayed: " + element);
			} catch (NoSuchElementException var2) {
				;
			}

		}, element, "Element is not visible", failureMessage);
	}

	private static class NegatedAssertionError extends AssertionError {
		private static final long serialVersionUID = 1531815929920593830L;

		public NegatedAssertionError(String message) {
			super(message);
		}
	}
}
