/**
 * 
 */
package liveTicketTestCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import liveTicketBase.BaseLiveTickets;
import liveTicketDataProviders.DataProviders;
import liveTicketPageObjects.LambdaLiveTicketPage;
import liveTicketUtilities.Log;

/**
 * @author Octalsoft-86
 *
 */
public class LambdaLiveTicketPageTest extends BaseLiveTickets{
	private LambdaLiveTicketPage lambdaLiveTicketPage ;
	@Parameters("browser")
	@BeforeMethod (groups = {"Smoke","Sanity","Regression"})
	public void setup(String browser) throws InterruptedException {
		launchApp(); 
		Thread.sleep(3000);;
	}
	
	@AfterMethod  (groups = {"Smoke","Sanity","Regression"})
	public void tearDown() {
		getDriver().quit();
	}
	@Test(priority=1,groups = {"Smoke","Sanity"},dataProvider = "credentials", dataProviderClass = DataProviders.class)
	public void loginTest(String uname1, String pswd1) throws Throwable {
		Log.startTestCase("login Test");
		Log.info("Login as Admin, Enter Valid User Name and Password");
	    //loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		lambdaLiveTicketPage=new LambdaLiveTicketPage();
		lambdaLiveTicketPage.lambdaLogin(uname1, pswd1);
	    String actualTitle=lambdaLiveTicketPage.getCurrTitle(getDriver());
	    Thread.sleep(1000);
	    String expectedTitle="Home";
	    Log.info("The system should open Home page.");
	    Assert.assertEquals(actualTitle, expectedTitle);
	    Log.info("Login is Successful,The system opens Home page.");
	    Log.endTestCase("login Test");
	    Thread.sleep(5000);  
	}
	@Test(priority=2,groups = {"Smoke","Sanity"},dataProvider = "credentials", dataProviderClass = DataProviders.class)
	public void lambdaViewTicketTest(String uname1, String pswd1) throws Throwable {
		Log.startTestCase("View Tickets");
		//Log.info("Login as Admin, Enter Valid User Name and Password");
		lambdaLiveTicketPage=new LambdaLiveTicketPage();
		lambdaLiveTicketPage.lambdaLogin(uname1, pswd1);
		lambdaLiveTicketPage.lambdaViewTicket();
	    Thread.sleep(5000);
	    Log.endTestCase("View Tickets");
	}
}
