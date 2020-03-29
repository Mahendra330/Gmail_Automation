package stepDefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import PageObjects.Inbox;
import PageObjects.LoginPage;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import junit.framework.Assert;


public class StepTest extends BaseClassTest {

@Before
	
	public void setup() throws IOException {
		logger = Logger.getLogger("Automan");
		PropertyConfigurator.configure("log4j.properties");
		
		configProp= new Properties();
		FileInputStream configProfile = new FileInputStream("config.properties");
		configProp.load(configProfile);
		
		String br = configProp.getProperty("browser");
		if(br.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", configProp.getProperty("chromepath")); //Will take driver from current user
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			
		}
		
		else if(br.equals("ie")) {
			System.setProperty("webdriver.ie.driver", configProp.getProperty("iepath")); //Will take driver from current user
			driver=new InternetExplorerDriver();
			driver.manage().window().maximize();
		}
		else if(br.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", configProp.getProperty("firefoxpath")); //Will take driver from current user
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
		}
		
		logger.info("***********Launching Browser************");
		}
	
		@Given("^Open the Browser$")
		public void open_the_Browser() throws Throwable {
			lp = new LoginPage(driver);
			Ibox= new Inbox(driver);
		}
		
		@When("^Enter URL$")
		public void enter_URL() throws Throwable {
			logger.info("***********Launching URL************");
			driver.get(configProp.getProperty("URL"));
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
		
		@When("^Provide Email as and password$")
		public void provide_Email_as_and_password() throws Throwable {
			logger.info("***********Providing credential************");
		    lp.clickEmailId(configProp.getProperty("Email"));
		    lp.clickOnNext();
		    Thread.sleep(2000);
		    lp.clickPassword(configProp.getProperty("Password"));
		    
		}
		
		@When("^click on login$")
		public void click_on_login() throws Throwable {
			logger.info("***********Click on Login************");
			lp.clickOnNext();
			Thread.sleep(2000);
		}
		
			
		@Then("^Verify Login Successful$")
		public void verify_Login_Successful() throws Throwable {
			logger.info("***********Verifying Home Page************");
			WebElement checkInbox = driver.findElement(By.xpath("//a[contains(@title,'Inbox')]"));
			String actualTitleName=checkInbox.getText();
			Assert.assertEquals("Inbox",actualTitleName);
			System.out.println("Login page contains " +actualTitleName+ " and Login is successfully verified");
		}

		@When("^Click on Profile$")
		public void click_on_Profile() throws Throwable {
		logger.info("***********Selecting Email ID  shown in the user profile ************");
		    lp.clickOnProfile();
		}

		@Then("^Verify Email ID$")
		public void verify_Email_ID() throws Throwable {
			logger.info("***********Verifying email entered in the first screen is the same as the one shown in the user profile ************");
			Thread.sleep(2000);
			/*Assert.assertTrue(driver.findElement(By.xpath("(//div[contains(.,'mahendrabarik332@gmail.com')])[10]")).isDisplayed());*/
			WebElement checkProfile = driver.findElement(By.xpath("(//div[contains(.,'mahendrabarik332@gmail.com')])[10]"));
			String actualProfileName=checkProfile.getText();
			System.out.println("email shown in the user profile is" +actualProfileName);
			Assert.assertEquals(configProp.getProperty("Email"),actualProfileName);
			System.out.println("email entered in the first screen is the same as the one shown in the user profile");
		}
		@When("^click on Inbox$")
		public void click_on_Inbox() throws Throwable {
			logger.info("***********Click Inbox ************");
		    Ibox.clickOnInbox();
		}

		@When("^check Number of Email$")
		public void check_Number_of_Email() throws Throwable {
			logger.info("***********Checking Total Number of Emails present in Inbox ************");
		    String getTotalnumberofemails = Ibox.getTotalEmail();
			System.out.println("Total number of Emails in the Inbox is:" +getTotalnumberofemails);
			
			
		}
		
		@When("^check Number of New Emails$")
		public void check_Number_of_New_Emails() throws Throwable {
			logger.info("***********Checking Total Number of New Emails/Unread Emails present in Inbox ************");
			String inbox = driver.findElement (By.xpath ("// * [contains (@ title, 'Inbox')]")). getText ();
			List <WebElement> mailsObj = driver.findElements (By.xpath ("// * [@ class = 'zA zE']")); 
			System.out.println ("total no of unread mails are ===" + mailsObj.size ());  
			if(mailsObj.size ()==0)
			{
				System.out.println("There is no New Email to read");
			}
			else
			{
			String getTotalnumberofNewemails = Ibox.getTotalNewEmail(); 
			System.out.println("Total number of New Emails or unread Emails availble in the Inbox are:" +getTotalnumberofNewemails);
			logger.info("***********Click on the first unread Email present in Inbox ************");
			Ibox.clickOnFirstunreadEmail();
			logger.info("***********Mark first unread Email present in Inbox to Read and Verify Number of Unread Email Present in Inbox after Marked one unread Email to read ************");
			Ibox.clickOnmarkedRead();
			Thread.sleep(3000);
			String getTotalnumberofNewemailsafter = Ibox.getTotalNewEmail();
			System.out.println("Total number of New Emails available in the Inbox are:" +getTotalnumberofNewemailsafter);
					
			
			}
			}
		
		
		@When("^close Browser$")
		public void close_Browser() throws Throwable {
			logger.info("***********closing driver************");
			
			driver.close();	
		}
		}
