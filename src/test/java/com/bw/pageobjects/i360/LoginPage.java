package com.bw.pageobjects.i360;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bw.accelerators.ReportManager;
import com.bw.pageobjects.base.WebBasePage;
import com.bw.utilities.ConfigManager;
import com.bw.utilities.validations.Assert;
import com.bw.utilities.validations.Validations;

public class LoginPage extends WebBasePage {
	public LoginPage() throws MalformedURLException, InterruptedException {
		super();
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "userid")
	private WebElement txtUsername;
	@FindBy(id = "password")
	private WebElement txtPassword;
	@FindBy(id = "btnLogin")
	private WebElement btnLogin;

	private WebElement getLoggedInUser(String userName) {
		return driver.findElement(By.xpath("//strong[text()='" + userName + "']"));

	}

	private void launch() throws Exception {
		String url = ConfigManager.getProperty("i360_url");
		webActionEngine.launchUrl(url);
	}

	private void enterUsername(String username) throws Exception {
		wait.visibilityOf(txtUsername);
		webActionEngine.setText(txtUsername, username, "Username");
	}

	private void enterPassword(String password) throws Exception {
		webActionEngine.setText(txtPassword, password, "Password");
	}

	private void clickLogin() throws Exception {
		webActionEngine.click(btnLogin, "LoginPage Button");
	}

	private void verifyLoggedInUser(String username) throws Exception {

		WebElement loggedInUserLocator = getLoggedInUser(username);
		wait.visibilityOf(loggedInUserLocator);
		Assert.that(Validations.elementTextEquals(getLoggedInUser(username), username,
				"Logged in username not matched with actual username"));
	}

	public void login(String username, String password) throws Exception {
		try {
			launch();
			/*
			 * enterUsername(username); enterPassword(password); clickLogin();
			 * verifyLoggedInUser(username);
			 */
			
			
//			  ReportManager.
//			  pass("Successfylly logged in into BestWestern application with the username " +
//			  username + ".<br>", webActionEngine.getScreenshot(), true);
			 
			
		} catch (Exception e) {
			ReportManager.fail(
					"Unable  to login into BestWestern application with the username " + username + ". \n" + e.getMessage(),
					webActionEngine.getScreenshot());
			throw new Exception(
					"Unable to login into BestWestern application with the username " + username + ". \n" + e.getMessage());
		}
	}

}
