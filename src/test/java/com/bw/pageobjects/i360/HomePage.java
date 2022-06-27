package com.bw.pageobjects.i360;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.bw.accelerators.ReportManager;
import com.bw.pageobjects.base.WebBasePage;
import com.bw.utilities.Constants;
import com.bw.utilities.validations.Assert;
import com.bw.utilities.validations.Validations;
import com.thoughtworks.selenium.webdriven.JavascriptLibrary;
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

public class HomePage extends WebBasePage {

	public HomePage() throws MalformedURLException, InterruptedException {
		super();
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}

	/*******************************************
	 *
	 * ELEMENTS ON THE HOME PAGE
	 *
	 *******************************************/
	@FindBy(id = "img-nav-logo")
	private WebElement imgHomePageLogo;
	@FindBy(id = "destination-input")
	private WebElement txtDestinationInput;
	@FindBy(id = "checkin")
	private WebElement ddCheckin;
	@FindBy(id = "checkout")
	private WebElement ddCheckout;
	@FindBy(id = "add-room")
	private WebElement btnAddRoom;
	@FindBy(id = "checkin")
	private WebElement ddcheckin;
	@FindBy(xpath = "//button[text()='Find My Hotel']")
	private WebElement btnFindMyHotel;
	@FindBy(xpath = "//div[@class='loaderText hidden']")
	private WebElement loader;
	@FindBy(xpath = "//button[text()='Accept']")
	private WebElement btnAccept;
	@FindBy(xpath = "//a[text()='COLLAPSE']")
	private WebElement lnkCollapse;
	
	
	private WebElement setDate(String dateWithMonth) throws Exception {
		return driver.findElement(By.xpath("//a[contains(@aria-label,'"+dateWithMonth+"')]"));
	}
	
	private WebElement addChild(int childNo) throws Exception {
		return driver.findElement(By.xpath("//button[@class='increaseChildren btn' and contains(@onclick,'"+childNo+"')]"));
	}
	
	private WebElement scrollToHotel(String indexHotelNumber) throws Exception {
		return driver.findElement(By.xpath("(//a[@tabindex='0' and text()='VIEW ROOMS'])["+indexHotelNumber+"]"));
	}
	
	
	
	public void searchByDestinationAndDate() throws Exception {
		try {
			/*
			 * webActionEngine.mousehover(menubarDrivers, "Drivers");
			 * webActionEngine.click(submenuDriverManager, "Driver Manager");
			 * wait.invisibilityOf(driverManagerLoadmask);
			 */
		wait.visibilityOf(btnAccept);
		Thread.sleep(5000);
		webActionEngine.click(btnAccept, "Accept");
		Thread.sleep(5000);
		wait.visibilityOf(imgHomePageLogo);
		Assert.that(Validations.elementIsVisible(imgHomePageLogo, "Home Page is not Loaded"));
		//webActionEngine.refreshPage();
		//webActionEngine.jsClick(btnFindMyHotel, "Find My Hotel");
		
		
//		JavascriptLibrary jsl = new JavascriptLibrary();
//        jsl.executeScript(driver,btnFindMyHotel.getAttribute("onclick"), "");
		
		
		/*
		 * webActionEngine.setText(txtDestinationInput, "Phoenix, AZ, USA",
		 * "Destination"); webActionEngine.click(ddCheckin, "Checkin");
		 * webActionEngine.click(setDate("June 20"), "June 20");
		 * webActionEngine.click(ddCheckout, "Checkout");
		 * webActionEngine.click(setDate("June 23"), "June 23");
		 */
		
		//ReportManager.pass("Entered search data successfully", webActionEngine.getAshotScreenshot());
		}catch (Exception e) {
			ReportManager.fail("Unable to  enter search data " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("Unable to enter search data. \n" + e.getMessage());
		}
	}
	
	public void searchByDestinationAndDate2() throws Exception {
		try {
			/*
			 * webActionEngine.mousehover(menubarDrivers, "Drivers");
			 * webActionEngine.click(submenuDriverManager, "Driver Manager");
			 * wait.invisibilityOf(driverManagerLoadmask);
			 */
		wait.visibilityOf(btnAccept);
		Thread.sleep(5000);
		webActionEngine.click(btnAccept, "Accept");
		Thread.sleep(5000);
		wait.visibilityOf(imgHomePageLogo);
		Assert.that(Validations.elementIsVisible(imgHomePageLogo, "Home Page is not Loaded"));
		//webActionEngine.refreshPage();
		//webActionEngine.jsClick(btnFindMyHotel, "Find My Hotel");
		
		
//		JavascriptLibrary jsl = new JavascriptLibrary();
//        jsl.executeScript(driver,btnFindMyHotel.getAttribute("onclick"), "");
		
		
		webActionEngine.setText(txtDestinationInput, "Phoenix, AZ, USA", "Destination");
		Thread.sleep(1000);
		webActionEngine.hitKeyboardKey(Keys.ARROW_DOWN, "");
		Thread.sleep(1000);
		webActionEngine.hitKeyboardKey(Keys.TAB, "");
		Thread.sleep(5000);
		webActionEngine.click(ddCheckin, "Checkin");
		webActionEngine.click(setDate("June 28"), "June 28");
		webActionEngine.click(ddCheckout, "Checkout");
		webActionEngine.click(setDate("June 30"), "June 30");
		
		ReportManager.pass("Entered search data successfully", webActionEngine.getAshotScreenshot(), true);
		}catch (Exception e) {
			ReportManager.fail("Unable to  enter search data " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("Unable to enter search data. \n" + e.getMessage());
		}
	}

	
	public void addRooms(int noOfRooms) throws Exception {
		try {
			for(int i=1;i<=noOfRooms-1;i++) {
				webActionEngine.click(btnAddRoom, "Add Room");
			} 
		
		ReportManager.pass("Successfully added "+noOfRooms+ " rooms", webActionEngine.getAshotScreenshot());
		}catch (Exception e) {
			ReportManager.fail("Unable to add \"+noOfRooms+ \" rooms " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("Successfully added \"+noOfRooms+ \" rooms" + e.getMessage());
		}
	}

	public void addSingleChild(int noOfRooms) throws Exception {
		try {
			for(int i=0;i<=noOfRooms-1;i++) {
				webActionEngine.click(addChild(i), "Add Children");
			}
		
		ReportManager.pass("Successfully added "+noOfRooms+ " children", webActionEngine.getAshotScreenshot(), true);
		}catch (Exception e) {
			ReportManager.fail("Unable to add Children " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("Unable to add Children" + e.getMessage());
		}
	}

	public void clickFindMyHotel() throws Exception {
		try {
			//webActionEngine.scrollToWebElement(btnFindMyHotel, "Find My Hotel");
			//webActionEngine.wait(5000);
			
			//webActionEngine.click(lnkCollapse, "");
			Thread.sleep(5000);
			webActionEngine.jsFUnctionCall(btnFindMyHotel, "");
			//webActionEngine.jsClick(btnFindMyHotel, "Find My Hotel");
			//JavascriptLibrary jsl = new JavascriptLibrary();
	        //jsl.executeScript(driver,btnFindMyHotel.getAttribute("onclick"), "");
			//waitForPageToLoad();
			webActionEngine.jsClick(scrollToHotel("3"), "View Rooms");
			ReportManager.pass("Successfully clicked on View Rooms", webActionEngine.getAshotScreenshot(), true);
		} catch (Exception e) {
			throw new Exception("Unable to click on Find My Hotel " + e.getMessage());
		}
	}
	
	public void waitForPageToLoad() throws Exception {
		try {
		wait.invisibilityOf(loader);
		ReportManager.pass("Page loaded successfully.<br>", webActionEngine.getAshotScreenshot());
		}catch (Exception e) {
			ReportManager.fail("Page not loaded " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("Page is not loaded. \n" + e.getMessage());
		}
	}
	
	
	
	@FindBy(id = "driversTopLink")
	private WebElement menubarDrivers;
	@FindBy(id = "vehiclesTopLink")
	private WebElement menubarVehicles;
	@FindBy(xpath = "//a[text()='Driver Manager']")
	private WebElement submenuDriverManager;
	@FindBy(xpath = "//a[text()='Vehicle Manager']")
	private WebElement submenuVehicleManager;
	@FindBy(xpath = "//title[text()='Driver Manager']")
	private WebElement pageTitleDriverMgr;
	@FindBy(xpath = "//title[text()='Vehicle Manager']")
	private WebElement pageTitleVehicleMgr;
	@FindBy(xpath = "//a[text()='Driver Hours/ELD']")
	private WebElement submenuDriverHours;
	@FindBy(xpath = "//a[text()='Driver Logs']")
	private WebElement submenuDriverLogs;
	@FindBy(xpath = "//title[text()='DriverHours']")
	private WebElement pageTitleDriverHours;
	@FindBy(xpath = "//a[text()='MESSAGE CENTER']")
	private WebElement menuBarMessageCenter;
	@FindBy(xpath = "//title[text()='messages']")
	private WebElement pageTitleMessageCenter;
	@FindBy(id = "mnuAdmin")
	private WebElement menubarAdmin;
	@FindBy(xpath = "//a[(text()='Device Manager')]")
	private WebElement tabDeviceManager;
	@FindBy(id = "loadmask-1023-msgEl")
	private WebElement DeviceManagerLoadmask;
	@FindBy(xpath = "//a[text()='Fleet Manager']")
	private WebElement submenuFleetManager;
	@FindBy(xpath = "//title[text()='Fleet Manager']")
	private WebElement pageTitleFleetMgr;
	@FindBy(xpath = "//a[text()='Unidentified Driving']")
	private WebElement submenuUnIdentifieDriving;
	@FindBy(xpath = "//span[text()='Vehicles with Unidentified Driver Records']")
	private WebElement titleWindowUnIdentifieDriving;
	@FindBy(xpath = "//a[text()='REPORTS']")
	private WebElement menubarReports;
	@FindBy(xpath = "//a[text()='Create']")
	private WebElement submenuCreate;
	@FindBy(xpath = "//title[text()='Create Reports']")
	private WebElement pageTitleCreateReport;
	@FindBy(xpath = "//div[text()='Loading unidentified driver records, please wait...']")
	private WebElement icnLoadingUnidentified;
	@FindBy(xpath = "//a[text()='Data Transfer Report']")
	private WebElement submenuDataTransferReport;
	@FindBy(xpath = "//span[text()='Data Transfer Request']")
	private WebElement pageDataTransferReport;
	@FindBy(xpath = "//div[text()='Loading...']")
	private WebElement icnLoading;
	@FindBy(xpath = "//img[contains(@src,'driver_manager.png')]")
	private WebElement pageIconDriverMgr;
	@FindBy(xpath = "//img[contains(@src,'vehicle_manager.png')]")
	private WebElement pageIconVehicleMgr;
	@FindBy(xpath = "//img[contains(@src,'driverhours_log2.png')]")
	private WebElement pageIconDriverHours;
	@FindBy(xpath = "//img[contains(@src,'admin-module-message.png')]")
	private WebElement pageIconMessageCenter;
	@FindBy(xpath = "//img[contains(@src,'fleet_manager.png')]")
	private WebElement pageIconFleetMgr;
	@FindBy(xpath = "//img[contains(@src,'admin-report.png')]")
	private WebElement pageIconCreateReport;
	@FindBy(xpath = "//a[text()='Company Settings']")
	private WebElement submenuCompanySettings;
	@FindBy(xpath = "//span[text()='Company Settings']")
	private WebElement pageTxtCompanySettings;
	@FindBy(xpath = "//a[text()='Compliance ']")
	private WebElement menuComplaince;
	@FindBy(xpath = "//a[text()='DVIR Inspections']")
	private WebElement submenuDvirInspections;
	@FindBy(xpath = "//a[text()='Account Settings']")
	private WebElement accountSettings;
	@FindBy(xpath = "//span[text()='Settings']")
	private WebElement pageTxtSettings;
	@FindBy(xpath = "//a[text()='Compliance ']")
	private WebElement pageTxtDrivePortal;
	@FindBy(id = "lblTotal")
	private WebElement driversCount;
	@FindBy(xpath = "//a[text()='View Scheduled']")
	private WebElement subMenuViewScheduled;
	@FindBy(xpath = "//div[contains(text(),'Loading...') and contains(@id,'loadmask')]")
	private WebElement driverManagerLoadmask;
	@FindBy(xpath = "//img[contains(@src,'reefer-icon.svg')]")
	private WebElement pageIconReeferMgr;
	@FindBy(xpath = "//a[text()='Route Manager']")
	private WebElement subMenuRoutManager;
	@FindBy(xpath = "//img[contains(@src,'route_manager.png')]")
	private WebElement pageIconRouteManager;
	@FindBy(xpath = "//a[text()='Product Manager']")
	private WebElement subMenuProductManager;
	@FindBy(xpath = "//img[contains(@src,'product_manager.png')]")
	private WebElement pageIconProductManager;
	@FindBy(xpath = "//a[text()='Checklist Manager']")
	private WebElement subMenuChecklistManager;
	@FindBy(xpath = "//img[contains(@src,'checklist_manager.png')]")
	private WebElement pageIconChecklistManager;
	@FindBy(xpath = "//img[contains(@src,'service.png')]")
	private WebElement pageIconServiceManager;
	
	
	
	private WebElement getPageTitle(String pagetitle) throws Exception {
		return driver.findElement(By.xpath("//title[text()='" + pagetitle + "']"));
	}

	private WebElement getSubmenuOption(String submenu) throws Exception {
		return driver.findElement(By.xpath("//a[text()='" + submenu + "']"));
	}

	public void clickDriverManager() throws Exception {
		try {
		webActionEngine.mousehover(menubarDrivers, "Drivers");
		webActionEngine.click(submenuDriverManager, "Driver Manager");
		wait.invisibilityOf(driverManagerLoadmask);
		wait.visibilityOf(pageIconDriverMgr);
		Assert.that(Validations.elementIsVisible(pageIconDriverMgr, "Driver Manager Page not loaded"));
		ReportManager.pass("Driver Manager Page loaded successfully.<br>", webActionEngine.getAshotScreenshot());
		}catch (Exception e) {
			ReportManager.fail("Driver Manager page not loaded " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("It seems Driver Manager page is not loaded. \n" + e.getMessage());
		}
	}

	public void clickVehicleManager() throws Exception {
		try {
			webActionEngine.mousehover(menubarVehicles, "Vehicles");
			webActionEngine.click(submenuVehicleManager, "Vehicle Manager");
			wait.invisibilityOf(icnLoading);
			wait.visibilityOf(pageIconVehicleMgr);
			Assert.that(Validations.elementIsVisible(pageIconVehicleMgr, "Vehicle Manager Page not loaded"));
			ReportManager.pass("Vehicle Manager Page loaded successfully.<br>", webActionEngine.getAshotScreenshot());
		} catch (Exception e) {
			ReportManager.fail("Vehicle Manager page not loaded " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("It seems Vehicle Manager page is not loaded. \n" + e.getMessage());
		}
	}

	public void clickDriverHours() throws Exception {
		webActionEngine.mousehover(menubarDrivers, "Drivers");
		webActionEngine.click(submenuDriverHours, "Driver Hours/ELD");
		webActionEngine.verifyText(pageIconDriverHours, "Driver Hours/ELD", "Driver Hours/ELD");
	}

	public void clickDriverLogs() throws Exception {
		webActionEngine.mousehover(menubarDrivers, "Drivers");
		webActionEngine.mousehover(submenuDriverHours, "Driver Hours/ELD");
		webActionEngine.click(submenuDriverLogs, "Driver Logs");
		webActionEngine.verifyText(pageIconDriverHours, "Driver Hours/ELD", "Driver Hours/ELD");
	}

	public void clickMessageCenter() throws Exception {
		webActionEngine.click(menuBarMessageCenter, "MESSAGE CENTER");
		webActionEngine.waitForPageLoad();
		webActionEngine.verifyText(pageIconMessageCenter, "Message Center Icon", "Message Center Icon");
	}

	public void navigateToDeviceManager() throws Exception {
		try {
			driver.manage().window().setSize(new Dimension(1100, 1024));
			webActionEngine.waitForPageLoad(Constants.shortWait);
			webActionEngine.mousehover(menubarAdmin, "Menubar ADMIN");
			wait.visibilityOf(tabDeviceManager);
			webActionEngine.click(tabDeviceManager, "Device Manager Tab");
			wait.invisibilityOf(DeviceManagerLoadmask);
			driver.manage().window().maximize();
			ReportManager.pass("Device Manager Page loaded successfully.<br>", webActionEngine.getAshotScreenshot());
		} catch (Exception e) {
			ReportManager.fail("Device Manager page not loaded " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("It seems Device Manager page is not loaded. \n" + e.getMessage());
		}
	}

	public void clickFleetManager() throws Exception {
		try {
			webActionEngine.mousehover(menubarVehicles, "Vehicles");
			webActionEngine.click(submenuFleetManager, "Fleet Manager");
			wait.invisibilityOf(icnLoading);
			wait.visibilityOf(pageIconFleetMgr);
			Assert.that(Validations.elementIsVisible(pageIconFleetMgr, "Fleet Manager Page not loaded"));
			ReportManager.pass("Fleet Manager Page loaded successfully.<br>", webActionEngine.getAshotScreenshot());
		} catch (Exception e) {
			ReportManager.fail("Fleet Manager page not loaded " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("It seems Fleet Manager page is not loaded. \n" + e.getMessage());
		}
	}

	public void clickServiceManager() throws Exception {
		try {
			webActionEngine.mousehover(menubarVehicles, "Vehicles");
			webActionEngine.click(getSubmenuOption("Service Manager"), "Sub menu");
			webActionEngine.waitForPageLoad();
			wait.visibilityOf(pageIconServiceManager);
			//-webActionEngine.verifyText(getPageTitle("Service Manager"), "Service Manager", "Page title");
			Assert.that(Validations.elementIsVisible(pageIconServiceManager, "Service Manager Page not loaded"));
			ReportManager.pass("Service Manager Page loaded successfully.<br>", webActionEngine.getAshotScreenshot());
		} catch (Exception e) {
			ReportManager.fail("Service Manager page not loaded " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("It seems Service Manager page is not loaded. \n" + e.getMessage());
		}
	}

	public void clickUnidentifiedDriving() throws Exception {
		try {
			webActionEngine.mousehover(menubarDrivers, "Drivers");
			webActionEngine.mousehover(submenuDriverHours, "Driver Hours/ELD");
			webActionEngine.click(submenuUnIdentifieDriving, "Unidentified Driving");
			wait.invisibilityOf(icnLoadingUnidentified);
			webActionEngine.verifyText(titleWindowUnIdentifieDriving, "Unidentified Driving", "Unidentified Driving");
		} catch (Exception e) {
			throw new Exception("It Seems Unidentified Driving Page is not loaded successfully... " + e.getMessage());
		}
	}

	public void clickCreateReport() throws Exception {
		try {
			webActionEngine.mousehover(menubarReports, "Reports");
			webActionEngine.click(submenuCreate, "Create");
			webActionEngine.waitForPageLoad();
			webActionEngine.verifyText(pageIconCreateReport, "Create Reports", "Create Reports");
		} catch (Exception e) {
			throw new Exception("It Seems Create Reports Page is not loaded successfully... " + e.getMessage());
		}
	}

	public void clickDataTransfterReport() throws Exception {
		try {
			webActionEngine.mousehover(menubarDrivers, "Drivers");
			webActionEngine.mousehover(submenuDriverHours, "Driver Hours/ELD");
			webActionEngine.click(submenuDataTransferReport, "Data Transfer Report");
			wait.invisibilityOf(icnLoading);
			webActionEngine.waitForPageLoad();
			wait.visibilityOf(pageDataTransferReport);
			Assert.that(Validations.elementTextEquals(pageDataTransferReport,Constants.DataTranferRequest_Window_Title, "Data Transfer Request Not visible"));			
		} catch (Exception e) {
			throw new Exception("It Seems Unidentified Driving Page is not loaded successfully... " + e.getMessage());
		}
	}

	public void clickReeferManager() throws Exception {
		try {
		webActionEngine.mousehover(menubarVehicles, "Vehicles");
		webActionEngine.click(getSubmenuOption("Reefer Manager"), "Reefer Manager");
		wait.invisibilityOf(icnLoading);
		webActionEngine.waitForPageLoad();
		wait.visibilityOf(pageIconReeferMgr);
		Assert.that(Validations.elementIsVisible(pageIconReeferMgr, "Refer Manager Page not loaded"));
		}catch (Exception e) {
			ReportManager.fail("Reefer Manager page not loaded " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("It seems Reefer Manager page is not loaded. \n" + e.getMessage());
		}
		}

	public void clickCompanySettings() throws Exception {
		try {
			driver.manage().window().setSize(new Dimension(1100, 1024));
			webActionEngine.mousehover(menubarAdmin, "Menubar ADMIN");
			wait.visibilityOf(tabDeviceManager);
			webActionEngine.click(submenuCompanySettings, "Company Settings Tab");
			webActionEngine.waitForPageLoad();
			wait.visibilityOf(pageTxtCompanySettings);
			driver.manage().window().maximize();
		} catch (Exception e) {
			throw new Exception("It Seems Device Manager Page is not loaded successfully... " + e.getMessage());
		}
	}

	public void ClickDvirAccountSettings() throws Exception {
		try {

			webActionEngine.click(menuComplaince, "Complaince Tab");
			webActionEngine.click(submenuDvirInspections, "DVIR Inspections");
			webActionEngine.click(accountSettings, "Account Settings");
			webActionEngine.waitForPageLoad();
			wait.visibilityOf(pageTxtSettings);
			driver.manage().window().maximize();
		} catch (Exception e) {
			throw new Exception("It Seems Device Manager Page is not loaded successfully... " + e.getMessage());
		}
	}

	public void clickViewScheduled() throws Exception {
		try {
			webActionEngine.mousehover(menubarReports, "Reports");
			webActionEngine.click(subMenuViewScheduled, "View Scheduled");
			webActionEngine.waitForPageLoad();
			// webActionEngine.verifyText(pageIconCreateReport, "Create Reports", "Create
			// Reports");
		} catch (Exception e) {
			throw new Exception("It Seems Viiew Scheduled Page is not loaded successfully... " + e.getMessage());
		}
	}
	
	
	public void clickRouteManager() throws Exception {
		try {
			driver.manage().window().setSize(new Dimension(1100, 1024));
			webActionEngine.mousehover(menubarAdmin, "Menubar ADMIN");
			wait.visibilityOf(tabDeviceManager);
			webActionEngine.click(subMenuRoutManager, "Route Manager Tab");
			webActionEngine.waitForPageLoad();
			wait.visibilityOf(pageIconRouteManager);
			driver.manage().window().maximize();
		} catch (Exception e) {
			throw new Exception("It Seems Route Manager Page is not loaded successfully... " + e.getMessage());
		}
	}
	
	public void clickProdutManager() throws Exception {
		try {
			driver.manage().window().setSize(new Dimension(1100, 1024));
			webActionEngine.mousehover(menubarAdmin, "Menubar ADMIN");
			wait.visibilityOf(tabDeviceManager);
			webActionEngine.click(subMenuProductManager, "Product Manager Tab");
			webActionEngine.waitForPageLoad();
			wait.visibilityOf(pageIconProductManager);
			driver.manage().window().maximize();
			Assert.that(Validations.elementIsVisible(pageIconProductManager, "Product Manager Page not loaded"));
			ReportManager.pass("Product Manager Page loaded successfully.<br>", webActionEngine.getAshotScreenshot());
		} catch (Exception e) {
			ReportManager.fail("Product Manager page not loaded " + e.getMessage() + ".<br>",
					webActionEngine.getAshotScreenshot());
			throw new Exception("It seems Product Manager page is not loaded. \n" + e.getMessage());
		}
	}
	
	public void clickChecklistManager() throws Exception {
		try {
			//driver.manage().window().setSize(new Dimension(1100, 1024));
			webActionEngine.mousehover(menubarAdmin, "Menubar ADMIN");
			wait.visibilityOf(tabDeviceManager);
			webActionEngine.click(subMenuChecklistManager, "Checklist Manager Tab");
			webActionEngine.waitForPageLoad();
			wait.visibilityOf(pageIconChecklistManager);
			driver.manage().window().maximize();
		} catch (Exception e) {
			throw new Exception("It Seems Checklist Manager Page is not loaded successfully... " + e.getMessage());
		}
	}
}
