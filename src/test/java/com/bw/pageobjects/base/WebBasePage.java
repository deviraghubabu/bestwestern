package com.bw.pageobjects.base;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import com.bw.accelerators.DriverFactory;
import com.bw.accelerators.WebActionEngine;
import com.bw.tests.base.BaseTest;
import com.bw.utilities.validations.Validations;
import com.bw.utilities.waits.Wait;

public class WebBasePage {
	protected WebActionEngine webActionEngine;
	protected WebDriver driver;
	protected Wait wait;
	protected Validations validations;

	public WebBasePage() throws MalformedURLException, InterruptedException {
		driver = DriverFactory.getWebDriver();
		webActionEngine = new WebActionEngine(driver);
		wait = new Wait(driver,BaseTest.EXPLICIT_WAIT_TIME);
		validations = new Validations(driver);
	}
}
