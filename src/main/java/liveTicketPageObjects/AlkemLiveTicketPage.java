/**
 * 
 */
package liveTicketPageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import liveTicketActionDrivers.Action;

import liveTicketBase.BaseLiveTickets;

/**
 * @author Octalsoft-86
 *
 */
public class AlkemLiveTicketPage extends BaseLiveTickets {
	Action action = new Action();
	@FindBy(id = "P101_USERNAME")
	private WebElement username;
	@FindBy(id = "P101_PASSWORD")
	private WebElement password;
	@FindBy(id = "P101_LOGIN")
	private WebElement login;
	@FindBy(xpath = "//a[normalize-space()='View Ticket']")
	private WebElement viewticket;

	public AlkemLiveTicketPage() {
		PageFactory.initElements(getDriver(), this);
		//System.out.println("Current working directory in Java : " + username);
	}

	public void alkemLogin(String uname, String pswd) {
		action.type(username, uname);
		action.type(password, pswd);
		action.click(getDriver(), login);
	}

	public void alkemViewTicket() {
		action.click(getDriver(), viewticket);
	}

	public String getCurrTitle(WebDriver driver) {
		String homePageTitle = action.getTitle(driver);
		return homePageTitle;
	}
}
