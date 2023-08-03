/**
 * 
 */
package liveTicketUtilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

/**
 * @author Octalsoft-86
 *
 */
public class ExtentManager {
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public static void setExtent() {
		//htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/ExtentReport/"+"MyReport_"+BaseClass.getCurrentTime()+".html");
		htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir")+"/Test-Output/ExtentReport/"+"Live Ticket Check Report.html");
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extentconfig.xml");
		//htmlReporter.config().setDocumentTitle("Automation Test Report");
		//htmlReporter.config().setReportName("OrangeHRM Test Automation Report");
		//htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		extent.setSystemInfo("HostName", "octalsoft86");
		extent.setSystemInfo("ProjectName", "CTMS Live Ticket Check");
		extent.setSystemInfo("Quality Analyst", "Anil");
		extent.setSystemInfo("OS", "Win10");
		extent.setSystemInfo("Browser", "Chrome");
	}
	public static void endReport() {
		extent.flush();
	}

}
