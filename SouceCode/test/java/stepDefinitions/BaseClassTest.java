package stepDefinitions;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import PageObjects.Inbox;
import PageObjects.LoginPage;

//import pageObjects.LoginPage;

public class BaseClassTest {

	public WebDriver driver;
	public static Logger logger;
	public Properties configProp;
	public LoginPage lp;
	public Inbox Ibox;
}
