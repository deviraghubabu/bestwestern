package com.bw.tests.i360;

import org.testng.annotations.Test;

import com.bw.pageobjects.i360.HomePage;
import com.bw.pageobjects.i360.LoginPage;
import com.bw.pageobjects.i360.SelectRoomPage;
import com.bw.tests.base.UIBaseTest;
import com.bw.utilities.ConfigManager;
import com.bw.utilities.Constants;

public class BestWesternWF2 extends UIBaseTest {

	@Test
    public void TC_BestWesternWF2() throws Exception {
        	LoginPage loginPage = new LoginPage();
        	HomePage homePage = new HomePage();
        	SelectRoomPage selectRoomPage = new SelectRoomPage();
			//loginPage.login(ConfigManager.getProperty("Web_username"),ConfigManager.getProperty("Web_password"));		
			//homePage.searchByDestinationAndDate();	
			loginPage.login(ConfigManager.getProperty("Web_username"),ConfigManager.getProperty("Web_password"));		
			homePage.searchByDestinationAndDate2();
			homePage.addRooms(3);
			homePage.addSingleChild(3);
			homePage.clickFindMyHotel();
			selectRoomPage.verifyPage();
			selectRoomPage.selectRoomType();
			selectRoomPage.verifySummaryPage1();
			System.out.println();

    }
}
