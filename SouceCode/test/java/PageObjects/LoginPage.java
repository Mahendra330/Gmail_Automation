package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.WaitHelper;

public class LoginPage {

	public WebDriver ldriver;
	WaitHelper waitHelper;
	public LoginPage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
		waitHelper = new WaitHelper(ldriver);
	}
	
	@FindBy(xpath = "//input[contains(@type,'email')]")
	WebElement Email;
	
	@FindBy(xpath = "//span[text()='Weiter']")
	WebElement ClickNext;
	
	@FindBy(xpath = "//input[contains(@type,'password')]")
	WebElement Password;
	
	
	
	@FindBy(xpath = "(//span[contains(@aria-hidden,'true')])[2]")
	WebElement ClickonProfile;
	
	
	public WebElement getWebElementOfProfile(String value) { 
		WebElement ele = ldriver.findElement(By.xpath("(//div[contains(.,'\"+value+\"')])[10]"));
		String actualXpath=ele.toString();
		System.out.println("Actaul Xpath is :" +actualXpath);
	  return ele;
		
	}
	
	public String getValueofProfile(String value) {
		return getWebElementOfProfile(value).getText();
	}
	
	public void clickEmailId(String email) {
		Email.sendKeys(email);
	}
	public void clickOnNext() {
		ClickNext.click();
	}
	public void clickPassword(String psw) {
		Password.clear();
		Password.sendKeys(psw);
		
	}
	public void clickOnProfile() {
		ClickonProfile.click();
	}
}
