package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.WaitHelper;

public class Inbox {
	public WebDriver ldriver;
	WaitHelper waitHelper;
	public Inbox(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
		waitHelper = new WaitHelper(ldriver);
	}
	
	@FindBy(xpath = "//a[contains(@title,'Inbox')]")
	WebElement Inbox;
	
	@FindBy(xpath = "(//span[contains(@class,'ts')])[3]")
	WebElement TotalEmail;
	
	@FindBy(xpath = "//div[contains(@class,'bsU')]")
	WebElement TotalNewEmail;
	
	//@FindBy(xpath = "//div[contains(@class,'yW')]")
	//WebElement bothReadandunreadEmails;
	
	
	@FindBy(xpath = ("(//td[div[contains(text(),'unread')]]/preceding-sibling::td//div[@role='checkbox'])[1]"))
	WebElement FirstunreadEmail;
	
	@FindBy(xpath = "(//div[contains(@class,'asa')])[4]")
	WebElement markedRead;
	
	
	@FindBy(xpath = "//svg[contains(@focusable,'false')]")
	WebElement Search;
	
	public void clickOnSearch() {
		Search.click();
	}
	
	
	public void clickOnmarkedRead() {
		markedRead.click();
	}
	public void clickOnFirstunreadEmail() {
		FirstunreadEmail.click();
	}
	public void clickOnInbox() {
		Inbox.click();
	}
	
	public String getTotalEmail() {
		return TotalEmail.getText();
	}
	
	
	public String getTotalNewEmail() {
		return TotalNewEmail.getText();
	}
}
