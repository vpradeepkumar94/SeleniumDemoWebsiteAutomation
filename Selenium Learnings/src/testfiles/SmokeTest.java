package testfiles;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import com.constantss.java.Constants;
import com.pageobjects1.java.BrowserFactory;
import com.utilss.java.Page;

public class SmokeTest {
 
  @BeforeClass
  public void beforeClass() {
	  BrowserFactory.initBrowser("chrome");
	  BrowserFactory.Driver.manage().window().maximize();
  }

  @AfterClass
  public void afterClass() {
	  BrowserFactory.CloseAllDrivers();
  }

  @Test (description ="Opens up selenium demo website and asserts the page title")
  public void TC001_launchDemoWebste() {
	  // open the application URL  
	  Page.demoHomePage.openInputFormPage();
	  // assert
	  Assert.assertEquals(Page.demoHomePage.pageTitle(), "sample","Expected page title is different");
  }
  
  @Test (description ="enters message in the message box and asserts the entered message")
  public void TC002_enterMessage() {
	  // enter text message
	  Page.inputFormPage.enterMessage(Constants.message);
	  // assert
	  Assert.assertEquals(Page.inputFormPage.getMessageDisplayed(),Constants.message,"Someother text is present");
  }
}
