package com.bw.pageobjects.base;

import java.net.MalformedURLException;

import org.openqa.selenium.WebElement;

import com.bw.accelerators.DriverFactory;
import com.bw.accelerators.MobileActionEngine;
import com.bw.tests.base.BaseTest;
import com.bw.utilities.validations.Validations;
import com.bw.utilities.waits.Wait;

import io.appium.java_client.AppiumDriver;

public class MobileBasePage {
	protected MobileActionEngine mobileActionEngine;
	protected AppiumDriver<WebElement> driver;
	protected Wait wait;
	protected Validations validations;

	@SuppressWarnings("unchecked")
	public MobileBasePage() throws MalformedURLException, InterruptedException {
		driver = DriverFactory.getAppiumDriver();
		mobileActionEngine = new MobileActionEngine();
		wait = new Wait(driver,BaseTest.EXPLICIT_WAIT_TIME);
		validations = new Validations(driver);
	}

}
