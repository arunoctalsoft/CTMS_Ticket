/**
 * 
 */
package liveTicketTestCases;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import liveTicketActionDrivers.Action;
import liveTicketDataProviders.DataProviders;

/**
 * @author Octalsoft-86
 *
 */

public class CTMSLiveTicketCheckwithemail {
	public static ExtentHtmlReporter htmlReporter;
	public WebDriver driver;
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest logger;
	Action action=new Action();
	String newticket;

	@BeforeTest
	public void startReport() {
		// Create an object of Extent Reports
		
				htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir")+"/Test-Output/ExtentReport/"+"CTMS_Tickets.html");
				htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extentconfig.xml");
				extent = new ExtentReports();
				extent.attachReporter(htmlReporter);
//				spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/Test-Output/extentreport/Live Tickets Report.html");
//				extent.attachReporter(spark);
				extent.setSystemInfo("Host Name", "Octalsoft-86");
				extent.setSystemInfo("Environment", "Production(LIVE)");
				extent.setSystemInfo("User Name", "Anil Kasture");
//				spark.config().setDocumentTitle("CTMS Live New Ticket Cheking ");
//				// Name of the report
//				spark.config().setReportName("ALL CTMS Live Ticket Check Report ");
//				// Dark Theme
//				spark.config().setTheme(Theme.STANDARD);
	}

//This method is to capture the screenshot and return the path of the screenshot.
	public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
// after execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = System.getProperty("user.dir") + "/Screenshots1/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
//driver.get("https://www.google.com/");
	}

	@Test(dataProvider = "email", dataProviderClass = DataProviders.class)
	public void findCTMSLiveTickets(String Url, String ctms, CharSequence username, CharSequence password) throws InterruptedException {
		driver.get(Url);
		logger = extent.createTest("To find new ticket in "+ctms);
		driver.findElement(By.id("P101_USERNAME")).sendKeys(username);
		driver.findElement(By.id("P101_PASSWORD")).sendKeys(password);
		driver.findElement(By.xpath("//span[normalize-space()='Login']")).click();
		driver.findElement(By.xpath("//a[normalize-space()='View Ticket']")).click();
		
		WebElement v=driver.findElement(By.xpath("//td[normalize-space()='355']"));
		newticket=v.getText();
		driver.findElement(By.xpath("//a[normalize-space()='Status']")).click();
				Thread.sleep(2000);
				driver.findElement(By.cssSelector("a[data-return-value='New']")).click();
				//driver.findElement(By.cssSelector("a[data-return-value='Reopen']")).click();
				Thread.sleep(2000);
		
		
//		driver.findElement(By.xpath("(//button[normalize-space()='Actions'])[1]")).click();
//		Thread.sleep(2000);
//		driver.findElement(By.xpath("(//button[normalize-space()='Filter'])[1]")).click();
//		//Thread.sleep(3000);
//		action.switchToFrameByIndex(driver, 0);
//		//driver.switchTo().frame(0);
//		//Thread.sleep(15000);
//		driver.findElement(By.xpath("(//select[@id='R7302934638920559439_column_name'])[1]")).click();
//		Thread.sleep(15000);
//		Select select = new Select(driver.findElement(By.xpath("(//select[@id='R7302934638920559439_column_name'])[1]"))); 
//		select.selectByVisibleText("Status");
//		Thread.sleep(2000);
		
		
	}


	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.SUCCESS) {
			// MarkupHelper is used to display the output in different colors
			logger.log(Status.PASS,
					MarkupHelper.createLabel(" New:"+newticket, ExtentColor.GREEN));
			logger.log(Status.PASS,
					MarkupHelper.createLabel(" Open:"+newticket, ExtentColor.ORANGE));
			logger.log(Status.PASS,
					MarkupHelper.createLabel(" Reopen:"+newticket, ExtentColor.YELLOW));
			logger.log(Status.PASS,
					MarkupHelper.createLabel(" Internal Review:"+newticket, ExtentColor.BLUE));
			logger.log(Status.PASS,
					MarkupHelper.createLabel(" Under Review:"+newticket, ExtentColor.BLUE));
//			logger.log(Status.PASS,
//					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Passed", ExtentColor.GREEN));
			// To capture screenshot path and store the path of the screenshot in the string
			// "screenshotPath"
			// We do pass the path captured by this method in to the extent reports using
			// "logger.addScreenCapture" method.
			// String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
			String screenshotPath = getScreenShot(driver, result.getName());
			byte[] file= FileUtils.readFileToByteArray(new File(screenshotPath));
			String base64Img= Base64.encodeBase64String(file);
			logger.pass("Test Case Passed Snapshot is below " + logger.addScreenCaptureFromBase64String(base64Img));
		}
		else if (result.getStatus() == ITestResult.FAILURE) {
//MarkupHelper is used to display the output in different colors
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			logger.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
//To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
//We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method.
//String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
			String screenshotPath = getScreenShot(driver, result.getName());
			byte[] file= FileUtils.readFileToByteArray(new File(screenshotPath));
			String base64Img= Base64.encodeBase64String(file);
//To add it in the extent report
			logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(base64Img));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
		}
		driver.quit();
	}

	@AfterTest
	public void endReport() throws EmailException, MalformedURLException {
		extent.flush();
		SendEmail() ;
		  	
	}
	public static void SendEmail() throws EmailException {
		   // Create the attachment
//		   EmailAttachment attachment = new EmailAttachment();
//
//		              attachment.setPath(System.getProperty("user.dir")+"\\Test-Output\\extentreport\\index.html");
//		   
//
//		              attachment.setDisposition(EmailAttachment.ATTACHMENT);
//		                  attachment.setDescription(" Test Execution Report");
//		                  attachment.setName("Automation Test Execution Report");
//		         
//		                  // Create the email message
//		                  MultiPartEmail email = new MultiPartEmail();
//		                  email.setHostName("smtp.gmail.com");
//		                  email.setSSLOnConnect(true);
//		                  email.setSmtpPort(587);
//		                  email.setAuthenticator(new DefaultAuthenticator("anil.kasture@octalsoft.com", "anil.9403182922."));
//		                  email.addTo("anil.kasture@octalsoft.com", "Test");
//		                  email.setFrom("anil.kasture@octalsoft.com", "Me");
//		                  email.setSubject("Automation Test Execution Report");
//		                  email.setMsg("Automation Test Execution Report");
//		         
//		                  // add the attachment
//		                  email.attach(attachment);
//		         
//		                  // send the email
//		                  email.send();
		
		//----------------------------------------------------------------------------------------
		System.out.println("before report sending");
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(("user.dir")+"/Test-Output/ExtentReport/"+"CTMS_Tickets.html");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Report QA automation report");
		//attachment.setName("Report QA");
		MultiPartEmail email1 = new MultiPartEmail();
		//Email email = new SimpleEmail();
		email1.setHostName("smtp.gmail.com");
		email1.setSmtpPort(465);
		email1.setAuthenticator(new DefaultAuthenticator("anil.kasture@octalsoft.com", "anil.9403182922."));
		email1.setSSLOnConnect(true);

		email1.setFrom("anil.kasture@octalsoft.com");
		String dateName1 = new SimpleDateFormat("dd/MMM/yyyy").format(new Date());
		email1.setSubject("Octalsoft CTMS - Open Tickets Tracker (Urgent) - "+dateName1);	
		email1.setMsg("Hi All, Please coordinate with QA and developers to close open tickets ASAP");
		email1.addTo("anil.kasture@octalsoft.com" );
		//email1.addTo("parth.suthar@octalsoft.com");
		email1.attach(attachment);
		System.out.println("Report sent");
		email1.send();
		//-------------------------------------------------------------------------------------
//		Email email = new SimpleEmail();
//		email.setHostName("smtp.gmail.com");
//		email.setSmtpPort(465);
//		email.setAuthentication("anil.kasture@octalsoft.com", "anil.9403182922.");
//		email.setSSLOnConnect(true);
//		try {
//		    email.setSubject("TestMail");
//		    email.setFrom("anil.kasture@octalsoft.com", "Automated Message Sender");
//		    email.setMsg("This is a message sent by a computer program built by: ");
//		    email.addTo("parth.suthar@octalsoft.com", "Someone's Name");
//		    email.send();
//		} catch (EmailException ex) {
//		    ex.printStackTrace();
//		}
		}
		            
}