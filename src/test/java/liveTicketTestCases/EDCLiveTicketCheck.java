/**
 * 
 */
package liveTicketTestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
import org.openqa.selenium.edge.EdgeDriver;
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

public class EDCLiveTicketCheck {
	public static ExtentHtmlReporter htmlReporter;
	public WebDriver driver;
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest logger;
	Action action = new Action();
	int newticket, closeticket, reopenticket, cancelledticket, clientreviewticket, pendingticket;
	int openticket;
	String URL;
	String Client;

	@BeforeTest
	public void startReport() {
		// Create an object of Extent Reports
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "/Test-Output/ExtentReport/" + "EDC_Tickets.html");
		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extentconfig.xml");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
//		spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/Test-Output/extentreport/Live Tickets Report.html");
//		extent.attachReporter(spark);
		extent.setSystemInfo("Host Name", "Octalsoft-86");
		extent.setSystemInfo("Environment", "Production(LIVE)");
		extent.setSystemInfo("User Name", "Anil Kasture");
//		spark.config().setDocumentTitle("CTMS Live New Ticket Cheking ");
//		// Name of the report
//		spark.config().setReportName("ALL CTMS Live Ticket Check Report ");
//		// Dark Theme
//		spark.config().setTheme(Theme.STANDARD);
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
//		WebDriverManager.edgedriver().setup();
//		driver = new EdgeDriver();
		driver.manage().window().maximize();
//driver.get("https://www.google.com/");
	}

	@Test(dataProvider = "edcCredentials", dataProviderClass = DataProviders.class)
	public void findCTMSLiveTickets(String Url, String edc, CharSequence username, CharSequence password)
			throws InterruptedException {
		System.out.println("Client Name:"+edc);
		driver.get(Url);
		URL=Url;
		Thread.sleep(2000);
		logger = extent.createTest("->" + edc);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("#P101_USERNAME")).sendKeys(username);
		driver.findElement(By.cssSelector("#P101_PASSWORD")).sendKeys(password);
		driver.findElement(By.xpath("//span[normalize-space()='Login']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[normalize-space()='View Ticket']")).click();
		Thread.sleep(2000);
		// --------------------------------------------------------------------------------------------------
		newticket = Integer
				.parseInt(driver.findElement(By.xpath("//input[@id='P78_NEW_TICKETS']")).getAttribute("value"));
		 Thread.sleep(1000);
		openticket = Integer
				.parseInt(driver.findElement(By.xpath("//input[@id='P78_OPEN_TICKETS']")).getAttribute("value"));
		 Thread.sleep(1000);
		closeticket = Integer
				.parseInt(driver.findElement(By.xpath("//input[@id='P78_CLOSED_TICKETS']")).getAttribute("value"));
		Thread.sleep(1000);

		reopenticket = Integer
				.parseInt(driver.findElement(By.xpath("//input[@id='P78_REOPENED_TICKETS']")).getAttribute("value"));
		 Thread.sleep(1000);

		cancelledticket = Integer
				.parseInt(driver.findElement(By.xpath("//input[@id='P78_CANCELLED_TICKETS']")).getAttribute("value"));
		 Thread.sleep(1000);

		clientreviewticket = Integer.parseInt(
				driver.findElement(By.xpath("//input[@id='P78_CLIENT_REVIEW_TICKETS']")).getAttribute("value"));
		 Thread.sleep(1000);

		pendingticket = Integer
				.parseInt(driver.findElement(By.xpath("//input[@id='P78_PENDING_TICKETS']")).getAttribute("value"));
		 Thread.sleep(1000);
		// -------------------------------------------------------------------------------------------------

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		// if (result.getStatus() == ITestResult.SUCCESS)
		
			// MarkupHelper is used to display the output in different colors
			logger.log(Status.PASS, MarkupHelper.createLabel(" New Tickets:" + newticket, ExtentColor.RED));
			logger.log(Status.PASS, MarkupHelper.createLabel(" Open Tickets:" + openticket, ExtentColor.RED));
			logger.log(Status.PASS, MarkupHelper.createLabel(" Closed Tickets:" + closeticket, ExtentColor.GREEN));
			logger.log(Status.PASS, MarkupHelper.createLabel(" Reopened Tickets:" + reopenticket, ExtentColor.RED));
//			logger.log(Status.PASS,
//					MarkupHelper.createLabel(" Cancelled Tickets:" + cancelledticket, ExtentColor.WHITE));
//			logger.log(Status.PASS,
//					MarkupHelper.createLabel(" Client Review Tickets:" + clientreviewticket, ExtentColor.WHITE));
			logger.log(Status.PASS, MarkupHelper.createLabel(" Pending Tickets:" + pendingticket, ExtentColor.BLUE));
			//logger.log(Status.PASS, MarkupHelper.createLabel(Client +" Live Url: "+ URL, ExtentColor.WHITE));
			logger.pass( URL);
			//logger.log(Status.PASS, Client +" Live Url: "+ URL);
//			logger.log(Status.PASS,
//					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Passed", ExtentColor.GREEN));
			// To capture screenshot path and store the path of the screenshot in the string
			// "screenshotPath"
			// We do pass the path captured by this method in to the extent reports using
			// "logger.addScreenCapture" method.
			// String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
			if(newticket != 0||openticket != 0||reopenticket!=0||pendingticket!=0)
			{
			if (newticket != 0) {
				driver.findElement(By.xpath("//a[normalize-space()='Status']")).click();
				Thread.sleep(2000);
				driver.findElement(By.cssSelector("a[data-return-value='New']")).click();
				Thread.sleep(2000);
				String screenshotPath = getScreenShot(driver, result.getName());

				byte[] file = FileUtils.readFileToByteArray(new File(screenshotPath));
				String base64Img = Base64.encodeBase64String(file);
				//logger.pass("New Ticket,  Snapshot is below " + logger.addScreenCaptureFromBase64String(base64Img));
				logger.addScreenCaptureFromBase64String(base64Img);
			
				driver.findElement(By.cssSelector(".a-Icon.icon-remove")).click();
				Thread.sleep(2000);
			}
			if (openticket != 0) {
				driver.findElement(By.xpath("//a[normalize-space()='Status']")).click();
				Thread.sleep(2000);
				driver.findElement(By.cssSelector("a[data-return-value='Open']")).click();
				Thread.sleep(2000);
				String screenshotPath = getScreenShot(driver, result.getName());

				byte[] file = FileUtils.readFileToByteArray(new File(screenshotPath));
				String base64Img = Base64.encodeBase64String(file);
				//logger.pass("Open Ticket,  Snapshot is below " + logger.addScreenCaptureFromBase64String(base64Img));
				logger.addScreenCaptureFromBase64String(base64Img);
				Thread.sleep(2000);
				driver.findElement(By.cssSelector(".a-Icon.icon-remove")).click();
				Thread.sleep(2000);
			}
			if (reopenticket != 0) {
				driver.findElement(By.xpath("//a[normalize-space()='Status']")).click();
				Thread.sleep(2000);
				driver.findElement(By.cssSelector("a[data-return-value='Reopen']")).click();
				Thread.sleep(2000);
				String screenshotPath = getScreenShot(driver, result.getName());

				byte[] file = FileUtils.readFileToByteArray(new File(screenshotPath));
				String base64Img = Base64.encodeBase64String(file);
				//logger.pass("ReOpen Ticket,  Snapshot is below " + logger.addScreenCaptureFromBase64String(base64Img));
				logger.addScreenCaptureFromBase64String(base64Img);
				driver.findElement(By.cssSelector(".a-Icon.icon-remove")).click();
				Thread.sleep(2000);
			}
			//if (closeticket != 0) {
			if (pendingticket != 0) {
				driver.findElement(By.xpath("//a[normalize-space()='Status']")).click();
				Thread.sleep(2000);
				//driver.findElement(By.cssSelector("a[data-return-value='Close']")).click();
				driver.findElement(By.cssSelector("a[data-return-value='Pending']")).click();
				Thread.sleep(2000);
				String screenshotPath = getScreenShot(driver, result.getName());

				byte[] file = FileUtils.readFileToByteArray(new File(screenshotPath));
				String base64Img = Base64.encodeBase64String(file);
				//logger.pass("Close Ticket,  Snapshot is below " + logger.addScreenCaptureFromBase64String(base64Img));
				logger.addScreenCaptureFromBase64String(base64Img);
			}
		}
			else if (newticket <= 0 && openticket <= 0 && reopenticket <= 0) {
//MarkupHelper is used to display the output in different colors
			logger.fail("No Ticket ");
//			logger.log(Status.PASS, MarkupHelper.createLabel(" Open Tickets:" + openticket, ExtentColor.RED));
//			logger.log(Status.PASS, MarkupHelper.createLabel(" Closed Tickets:" + closeticket, ExtentColor.GREEN));
//			logger.log(Status.PASS, MarkupHelper.createLabel(" Reopened Tickets:" + reopenticket, ExtentColor.RED));
//			logger.log(Status.PASS,
//					MarkupHelper.createLabel(" Cancelled Tickets:" + cancelledticket, ExtentColor.WHITE));
//			logger.log(Status.PASS,
//					MarkupHelper.createLabel(" Client Review Tickets:" + clientreviewticket, ExtentColor.WHITE));
//			logger.log(Status.PASS, MarkupHelper.createLabel(" Pending Tickets:" + pendingticket, ExtentColor.BLUE));
//			// logger.log(Status.FAIL,
//			// MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed",
//			// ExtentColor.RED));
//
//			String screenshotPath = getScreenShot(driver, result.getName());
////			File f=new File(screenshotPath);
////			FileInputStream fis=new FileInputStream(f);
////			byte[] bytes=new byte[(int)f.length()];
////			fis.read(bytes);
////			String base64Img= new String(Base64.encodeBase64(bytes),StandardCharsets.UTF_8);
//			byte[] file = FileUtils.readFileToByteArray(new File(screenshotPath));
//			String base64Img = Base64.encodeBase64String(file);
//			logger.fail("No any New ticket, Snapshot is below " + logger.addScreenCaptureFromBase64String(base64Img));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		}
//		else if (result.getStatus() == ITestResult.SUCCESS) {
//			logger.log(Status.PASS,
//					MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
//		}
		driver.quit();
	}

	@AfterTest
	public void endReport() throws EmailException, MalformedURLException {
		extent.flush();
		//SendEmail();

	}

	public static void SendEmail() throws EmailException {
		System.out.println("before report sending");
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(
				"C:\\Users\\Octalsoft-86\\git\\repository\\CtmsLiveTicketCheck\\Test-Output\\ExtentReport\\EDC_Tickets.html");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Report QA automation report");
		// attachment.setName("Report QA");
		MultiPartEmail email1 = new MultiPartEmail();
		// Email email = new SimpleEmail();
		email1.setHostName("smtp.gmail.com");
		email1.setSmtpPort(465);
		email1.setAuthenticator(new DefaultAuthenticator("anil.kasture@octalsoft.com", "anil.9403182922."));
		email1.setSSLOnConnect(true);
		email1.setFrom("anil.kasture@octalsoft.com");
		String dateName1 = new SimpleDateFormat("dd/MMM/yyyy").format(new Date());
		email1.setSubject("Octalsoft EDC - Open Tickets Tracker (Urgent) - " + dateName1);
		email1.setMsg("Hi All, Please coordinate with QA and developers to close open tickets ASAP");
		email1.addTo("anil.kasture@octalsoft.com");
//		email1.addTo("ishita.desai@octalsoft.com");
//		email1.addTo("kushal.gohil@octalsoft.com");
//		email1.addCc("arun.janardhanan@octalsoft.com");
//		email1.addTo("vishal.mavani@octalsoft.com");
//		email1.addTo("mayank.parmar@octalsoft.com");
		email1.attach(attachment);
		System.out.println("Report sent");
		email1.send();
	}

}