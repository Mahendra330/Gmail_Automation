# Gmail_Automation
Automating Gmail Account.

Step1:
1. Open Gmail 
2. Enter email 
3. Enter password 
4. Verify that the login is successful 
5. Verify that the email entered in the first screen is the same as the one shown in the user profile.

Step2:Write a separate test which checks the inbox: 
1. Login in Gmail 
2. Click on Inbox folder 
3. Check the number of new emails in the Inbox folder 
4. If there are new emails, mark the first one as read 5. Make sure that ‘Mark as read’ works as it should 



package com.LandT.pageObjects;

import static io.appium.java_client.touch.offset.PointOption.point;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.LandT.Utilities.PropertiesHelper;
import com.LandT.Utilities.SoftAssert;
import com.LandT.Utilities.Utilities;
import com.LandT.Utilities.xmlReader;
import com.LandT.constants.Constants_DE;
import com.LandT.constants.Constants_UK;
import com.LandT.driverBase.DriverBase;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BasePage extends DriverBase {

	public BasePage(AppiumDriver<MobileElement> driver) {
		super();
	}

	protected static final Logger logger = LogManager.getLogger(BasePage.class.getName());
	protected PropertiesHelper propertiesHelper = new PropertiesHelper();
	Utilities utilities = new Utilities();

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public By getLocatorType(String locatorType, String locatorValue) {
		try {
			if (locatorType.toString().equalsIgnoreCase("xpath")) {
				return By.xpath(locatorValue);
			}
			if (locatorType.toString().equalsIgnoreCase("ID")) {
				return By.id(locatorValue);
			}
			if (locatorType.toString().equalsIgnoreCase("class Name")) {
				return By.className(locatorValue);
			}
			if (locatorType.toString().equalsIgnoreCase("name")) {
				return By.name(locatorValue);
			}
			if (locatorType.toString().equalsIgnoreCase("iOS-Predicate")) {
				// "type=='XCUIElementTypeButton' AND value BEGINWITH[c]
				// 'bla' AND visible==1";
				return MobileBy.iOSNsPredicateString(locatorValue);
			}
			if (locatorType.toString().equalsIgnoreCase("-ios class chain")) {
				return MobileBy.iOSClassChain(locatorValue);
			}

		} catch (Exception e) {
			logger.debug("failed to get locator type");
			onAutomationFailure(e);
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * public void onAutomationFailure(Throwable cause) { try { String file =
	 * getScreenshot(); SoftAssert.testAutom.log(Status.ERROR,
	 * "<pre><b><font colour='orange'>"+"UNKNOWN FAILURE :: "+cause.toString()+"."+
	 * "</font></b></pre>",MediaEntityBuilder.createScreenCaptureFromPath(file).
	 * build()); } catch (IOException e) {
	 * 
	 * e.printStackTrace(); }
	 * logger.warn("Testcase Failure due to unknown issue before end of test");
	 * Assert.fail("UNKNOWN FAILURE"); }
	 */

	public String getScreenshot() {
		try {
			File srcFile = getDriver().getScreenshotAs(OutputType.FILE);
			String filename = UUID.randomUUID().toString();
			String fullSnapShotFileName = System.getProperty("user.dir") + "//Output//ScreenShots//" + filename
					+ ".jpg";
			File targetFile = new File(fullSnapShotFileName);
			FileUtils.copyFile(srcFile, targetFile);
			return fullSnapShotFileName;
		} catch (Exception e) {
			logger.debug("Failed to get Screenshot");
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public By getLocator(String page, String identifier, String... parameters) {
		try {
			String fileName = "/src/main/resources/ObjectRepository/";
			String platform = DriverBase.PLATFORM_NAME;
			String locatorType = null;
			String locatorValue = null;
			if (platform.equalsIgnoreCase("ANDROID_APP")) {
				fileName = fileName + "androidApp_repository.xml";
			} else if (platform.equalsIgnoreCase("IOS_APP")) {
				fileName = fileName + "iOSApp_repository.xml";
			}
			HashMap<String, String> hash = xmlReader.readRepositoryXml(fileName, page, identifier);
			for (Map.Entry<String, String> entry : hash.entrySet()) {
				locatorType = entry.getKey().trim();
				locatorValue = entry.getValue().trim();
				System.out.println("Key= " + entry.getKey() + ", Value = " + entry.getValue());
			}
			for (int i = 0; i < parameters.length; i++) {
				locatorValue = locatorValue.replaceAll("#ELEMENT_" + i, parameters[i]);
			}
			return getLocatorType(locatorType, locatorValue);
		} catch (Exception e) {
			logger.debug("Failed to get Locator");
			onAutomationFailure(e);
			e.printStackTrace();
		}

		return null;

	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public MobileElement findElement(By locator) {
		try {
			return getDriver().findElement(locator);

		} catch (Exception e) {
			logger.debug("Failed to Find Element");
			onAutomationFailure(e);
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public List<MobileElement> findElements(By locator) {
		try {
			return getDriver().findElements(locator);

		} catch (Exception e) {
			logger.debug("Failed to Find Element");
			onAutomationFailure(e);
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public ArrayList<String> getTextOfElements(By locator) {
		ArrayList<String> values = new ArrayList<String>();
		try {
			List<MobileElement> ele = getDriver().findElements(locator);
			for (int i = 0; i < ele.size(); i++) {
				values.add(ele.get(i).getText());
			}
			return values;

		} catch (Exception e) {
			logger.debug("Failed to Find Element");
			onAutomationFailure(e);
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public String getTextOfElement(By locator) {
		try {
			return findElement(locator).getText();

		} catch (Exception e) {
			logger.debug("Failed to Find Element");
			onAutomationFailure(e);
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public String getAttributeOfElement(By locator, String attribute) {
		try {
			return findElement(locator).getAttribute(attribute);

		} catch (Exception e) {
			logger.debug("Failed to Find Element");
			onAutomationFailure(e);
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public void tapElementBasedOnText(By locator, String text) {
		try {
			List<MobileElement> ele = getDriver().findElements(locator);
			for (int i = 0; i < ele.size(); i++) {
				if (ele.get(i).getText().equalsIgnoreCase(text)) {
					ele.get(i).click();
					logger.info("User Successfully tapped on => '" + locator + "' with parameters => " + text);
					Thread.sleep(5000);
					break;
				}
			}

		} catch (Exception e) {
			logger.debug("Failed to Find Element");
			onAutomationFailure(e);
			e.printStackTrace();
		}

	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public boolean isElementPresent(By locator) {
		try {
			boolean flag = false;
			flag = getDriver().findElements(locator).size() >= 0;
			logger.info("element => " + locator + "is present " + flag);
			return flag;

		} catch (Exception e) {
			logger.debug("Failed to Find Element");
			return false;
		}
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public boolean isElementDisplayed(By locator) {
		try {
			boolean flag = false;
			flag = getDriver().findElements(locator).size() > 0;
			logger.info("element => " + locator + "is Displayed " + flag);
			return flag;

		} catch (Exception e) {
			logger.debug("Failed to Find Element");
			return false;
		}
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public void tapAndWait(String page, String locator, String... params) {
		try {
			findElement(getLocator(page, locator, params)).click();
			if (params.length > 0) {
				String finalParams = "";
				for (String parameters : params) {
					finalParams = finalParams + "+" + parameters;
				}
				logger.info("User Successfully tapped on => '" + locator + "' with parameters => " + finalParams);
			} else {
				logger.info("User Successfully tapped on => '" + locator + "'");
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			logger.debug("Failed to tapped on => '" + locator + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}
	}

	/*Added by Mahendra extra
	 * Description: PARAMETERS: RETURNS
	 */

	public List<WebElement> getWebElements(By locator) {
		try {
			return (List<WebElement>) findElement(locator);
			//logger.info("User Successfully tapped on => '" + locator + "'");
			//Thread.sleep(2000);
		} catch (Exception e) {
			logger.debug("Failed to tapped on => '" + locator + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}
		return null;
	}
	
	/*Added by Mahendra extra
	 * Description: PARAMETERS: RETURNS
	 */
	public WebElement getWebElement(By locator) {
		try {
			return findElement(locator);
			//logger.info("User Successfully tapped on => '" + locator + "'");
			//Thread.sleep(2000);
		} catch (Exception e) {
			logger.debug("Failed to tapped on => '" + locator + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public void tapAndWait(By locator) {
		try {
			findElement(locator).click();
			logger.info("User Successfully tapped on => '" + locator + "'");
			Thread.sleep(3000);
		} catch (Exception e) {
			logger.debug("Failed to tapped on => '" + locator + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public void tap(String page, String locator, String... params) {
		try {
			findElement(getLocator(page, locator, params)).click();
			if (params.length > 0) {
				String finalParams = "";
				for (String parameters : params) {
					finalParams = finalParams + parameters;
				}
				logger.info("User Successfully tapped on => '" + locator + "' with parameters => " + finalParams);
			} else {
				logger.info("User Successfully tapped on => '" + locator + "'");
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			logger.debug("Failed to tapped on => '" + locator + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public void tap(By locator) {
		try {
			findElement(locator).click();
			logger.info("User Successfully tapped on => '" + locator + "'");
			Thread.sleep(3000);
		} catch (Exception e) {
			logger.debug("Failed to tapped on => '" + locator + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public void tapUsingActionClass(String page, String locator, String... params) {
		try {
			MobileElement element = findElement(getLocator(page, locator, params));
			int xPoint = element.getLocation().getX();
			int yPoint = element.getLocation().getY();
			TouchAction touchAction = new TouchAction(getDriver());
			touchAction.tap(point(xPoint, yPoint)).perform();
			// touchAction.tap(point(xPoint,yPoint)).perform();
			logger.info("User Successfully tapped on => '" + locator + "'");
		} catch (Exception e) {
			logger.debug("Failed to tapped on => '" + locator + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}
	}

	/*
	 * Description: PARAMETERS: RETURNS
	 */

	public void enterText(String page, String locator, String text, String... params) {
		try {
			findElement(getLocator(page, locator, params)).sendKeys(text);
			logger.info("User Successfully entered => '" + text + "' in '" + locator + "'");
			Thread.sleep(3000);
		} catch (Exception e) {
			logger.debug("Failed to entere text => '" + text + "' in '" + locator + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}
	}

	/*
	 * Description: to wait explicitly for a particular element PARAMETERS:By
	 * locator,int time RETURN:boolean
	 */

	public void waitExplicitly(By locator, int time) {
		try {

			for (int i = 0; i < 5; i++) {
				List<MobileElement> element = findElements(locator);
				if (!(element.size() == 0)) {
					logger.debug(locator + "found in explicit wait");
					break;
				} else {
					Thread.sleep(5000);
				}
			}

		} catch (Exception e) {
			logger.debug("Error occured while clicking element '" + locator.toString() + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}

	}

	public void waitForElementPresent(int secs, String page, String locator, String... params) {
		try {
			System.out.println("waiting for locator " + locator);
			waitExplicitly(getLocator(page, locator, params), secs);
		} catch (NoSuchElementException e) {
			System.out.println("Element -'" + locator.toString() + "' is not found");
			onAutomationFailure(e);
		}
	}

	/**
	 * DESCRIPTION: PARAMETERS: RETURN:
	 */

	public void onAutomationFailure(Throwable cause) {
		try {
			String file = getScreenshot();
			SoftAssert.testAutom
					.log(Status.ERROR,
							"<pre><b><font color='orange'>" + "UNKNOWN FAILURE :: " + cause.toString() + "."
									+ "</font></b></pre>",
							MediaEntityBuilder.createScreenCaptureFromPath(file).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.warn("Test Case failure due to unkown issue before end of test");
		Assert.fail("UNKNOWN FAILURE");
	}

	/**
	 * DESCRIPTION: PARAMETERS: RETURN:
	 * 
	 * @throws IOException
	 */

	public void installAppFromTestFlight(String env) throws IOException {
		getDriver().removeApp(SHELLIOSAPPBUNDLEID);
		getDriver().terminateApp("com.apple.TestFlight");
		getDriver().activateApp("com.apple.TestFlight");
		if (getDriver().toString().contains(SHELLIOSAPPBUNDLEID)) {
			reInitiateiOSDriver("com.apple.TestFlight", EXECUTION_CONFIG_FILE, "en", "en-GB");
		}
		tapAndWait("TestFlight", "TARGET_APP", env.toUpperCase());
		tapAndWait("TestFlight", "INSTALL_BUTTON", getTestData("Install_text"));
		completeTestFlightAppDownloadProcess();
	}

	/**
	 * DESCRIPTION: PARAMETERS: RETURN:
	 */

	public String getTestData(String key) {
		String dataValue = null;
		String country = TARGET_APP_REGION;
		try {
			/*
			 * PropertiesHelper prop = new PropertiesHelper(); String fileName =
			 * System.getProperty("user.dir") + "/src/main/resources/Config/TestData/" +
			 * ENVIRONMENT_NAME + "/testData_" + TARGET_APP_REGION + ".properties";
			 * dataValue = prop.getPropertyValue(fileName, key); //
			 * System.out.println("test data Key => '" + key + "', and test data Value => '"
			 * + dataValue+ "'");
			 */
			if (country.equalsIgnoreCase("UK")) {
				dataValue = readConstantFile(Constants_UK.class.getFields(), key.toUpperCase());
			} else if (country.equalsIgnoreCase("DE")) {
				dataValue = readConstantFile(Constants_DE.class.getFields(), key.toUpperCase());

			}
			return dataValue.trim();
		} catch (Exception e) {
			logger.debug("failed to get testData => '" + key + "', and test data Value => '" + dataValue + "'");
			onAutomationFailure(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DESCRIPTION: PARAMETERS: RETURN:
	 * 
	 * @throws IOException
	 * @throws ConfigurationException
	 */

	public String newUser(String firstName) throws ConfigurationException, IOException {
		Date date = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		cal.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("hms");
		String formattedDate = sdf.format(date);
		String email = firstName.toLowerCase() + "+" + TARGET_APP_REGION + "_" + ENVIRONMENT_NAME + "_" + formattedDate
				+ "@gmail.com";
		propertiesHelper.updateRuntimeDataPropertyFile(TARGET_APP_REGION + "_" + "dynamicEmailId", email);
		return email;
	}

	/**
	 * DESCRIPTION: PARAMETERS: RETURN:
	 * 
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public String newUser(String firstName, String typeOfUser) throws ConfigurationException, IOException {
		String user = "";
		if (!typeOfUser.equalsIgnoreCase("SME")) {
			user = typeOfUser + ENVIRONMENT_NAME;
		} else {
			user = TARGET_APP_REGION + ENVIRONMENT_NAME;
		}
		Date date = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		cal.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("hms");
		String formattedDate = sdf.format(date);
		String email = firstName.toLowerCase() + "+" + user + "_" + formattedDate + "@gmail.com";
		propertiesHelper.updateRuntimeDataPropertyFile(user + formattedDate + "_" + "dynamicEmailId", email);
		return email;
	}

	/**
	 * DESCRIPTION:To Scroll Page up n number of times PARAMETERS: -- int num
	 * RETURN:
	 * 
	 * @throws Exception
	 */
	public void scrollPageUPOrDown(String direction, int num, double start, double end, int waitTimeinMS)
			throws Exception {
		try {
			Dimension dimensions = getDriver().manage().window().getSize();
			Double screenHeightStart = dimensions.getHeight() * start;
			int scrollStart = screenHeightStart.intValue();
			Double screenHeightEnd = dimensions.getHeight() * end;
			int scrollEnd = screenHeightEnd.intValue();
			TouchAction swipe = new TouchAction(getDriver());
			for (int i = 0; i < num; i++) {
				System.out
						.println("Swiping page in " + direction + " direction for '" + (i + 1) + "' times from offset '"
								+ Integer.toString(scrollStart) + "' to offset '" + Integer.toString(scrollEnd) + "'");
				swipe.press(PointOption.point(0, scrollStart))
						.waitAction(new WaitOptions().withDuration(Duration.ofMillis(waitTimeinMS)))
						.moveTo(PointOption.point(0, scrollEnd)).release().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public void ScrollIntoView(String containedText) {
	//driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Brazil\"));");
	   driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\"" + containedText + "\").instance(0))"));  
		
}
	/**
	 * 
	 * DESCRIPTION:To Scroll Page up n number of times PARAMETERS: -- int num
	 * RETURN:
	 * 
	 * @throws Exception
	 */
	public void scrollPageLeftOrRight(String direction, int num, double start, double end, int waitTimeinMS, double y)
			throws Exception {
		try {
			Dimension dimensions = getDriver().manage().window().getSize();
			Double screenHeightStart = dimensions.getWidth() * start;
			int scrollStart = screenHeightStart.intValue();
			Double screenHeightEnd = dimensions.getWidth() * end;
			int scrollEnd = screenHeightEnd.intValue();
			Double screenY = dimensions.getHeight() * start;
			int scrollY = screenY.intValue();
			TouchAction swipe = new TouchAction(getDriver());
			for (int i = 0; i < num; i++) {
				System.out
						.println("Swiping page in " + direction + " direction for '" + (i + 1) + "' times from offset '"
								+ Integer.toString(scrollStart) + "' to offset '" + Integer.toString(scrollEnd) + "'");
				swipe.press(PointOption.point(scrollStart, scrollY))
						.waitAction(new WaitOptions().withDuration(Duration.ofMillis(waitTimeinMS)))
						.moveTo(PointOption.point(scrollEnd, scrollY)).release().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DESCRIPTION:To Scroll Page up n number of times using x co-ordinates of
	 * locator PARAMETERS: -- int num RETURN:
	 * 
	 * @throws Exception
	 */
	public void scrollPageUPOrDownUsingXCoordinatesOfLocator(String direction, int num, double start, double end,
			By locator) throws Exception {
		try {
			String p = Integer.toString(getDriver().findElement(locator).getLocation().getX());
			Dimension dimensions = getDriver().manage().window().getSize();
			Double screenHeightStart = dimensions.getHeight() * start;
			Double screenWidthStart = Double.parseDouble(p);
			int scrollWidthStart = screenWidthStart.intValue();
			int scrollStart = screenHeightStart.intValue();
			Double screenHeightEnd = dimensions.getHeight() * end;
			int scrollEnd = screenHeightEnd.intValue();

			TouchAction swipe = new TouchAction(getDriver());
			for (int i = 0; i < num; i++) {
				System.out
						.println("Swiping page in " + direction + " direction for '" + (i + 1) + "' times from offset '"
								+ Integer.toString(scrollStart) + "' to offset '" + Integer.toString(scrollEnd) + "'");
				swipe.press(PointOption.point(scrollWidthStart, scrollStart))
						.waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
						.moveTo(PointOption.point(scrollWidthStart, scrollEnd)).release().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DESCRIPTION:To Scroll Page up n number of times PARAMETERS: -- int num
	 * RETURN:
	 * 
	 * @throws Exception
	 */
	public void scrollPageUPOrDownUsingXCoordinates(String direction, int num, double start, double end, double width)
			throws Exception {
		try {
			Dimension dimensions = getDriver().manage().window().getSize();
			Double screenHeightStart = dimensions.getHeight() * start;
			Double screenWidthStart = dimensions.getWidth() * width;
			int scrollWidthStart = screenWidthStart.intValue();
			int scrollStart = screenHeightStart.intValue();
			Double screenHeightEnd = dimensions.getHeight() * end;
			int scrollEnd = screenHeightEnd.intValue();
			TouchAction swipe = new TouchAction(getDriver());
			for (int i = 0; i < num; i++) {
				System.out
						.println("Swiping page in " + direction + " direction for '" + (i + 1) + "' times from offset '"
								+ Integer.toString(scrollStart) + "' to offset '" + Integer.toString(scrollEnd) + "'");
				swipe.press(PointOption.point(scrollWidthStart, scrollStart))
						.waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
						.moveTo(PointOption.point(scrollWidthStart, scrollEnd)).release().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DESCRIPTION:To Scroll Page up n number of times PARAMETERS: -- int num
	 * RETURN:
	 * 
	 * @throws Exception
	 */
	public void scrollPageUsingXAndYCoordinates(String direction, int num, double startx, double starty, double endx,
			double endy) throws Exception {
		try {
			Dimension dimensions = getDriver().manage().window().getSize();
			Double xs = dimensions.getWidth() * startx;
			int xstart = xs.intValue();
			Double ys = dimensions.getHeight() * starty;
			int ystart = ys.intValue();
			Double xe = dimensions.getWidth() * endx;
			int xend = xe.intValue();
			Double ye = dimensions.getHeight() * endy;
			int yend = ye.intValue();
			TouchAction swipe = new TouchAction(getDriver());
			for (int i = 0; i < num; i++) {
				System.out.println("Swiping page in " + direction + " direction for '" + (i + 1)
						+ "' times from offset '" + ("start x => '" + startx + "' start y => " + starty + "'")
						+ "' to offset '" + ("start x => '" + endx + "' start y => " + endy + "'") + "'");
				swipe.press(PointOption.point(xstart, ystart))
						.waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
						.moveTo(PointOption.point(xend, yend)).release().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author vikas.prasad DESCRIPTION:To Scroll Page up n number of times
	 *         PARAMETERS: -- int num RETURN:
	 * @throws Exception
	 */
	public void tapUsingXAndYCoordinates(double x, double y) throws Exception {
		try {
			Dimension dimensions = getDriver().manage().window().getSize();
			Double xs = dimensions.getWidth() * x;
			int xPoint = xs.intValue();
			Double ys = dimensions.getHeight() * y;
			int yPoint = ys.intValue();
			TouchAction touchAction = new TouchAction(getDriver());
			touchAction.tap(point(xPoint, yPoint)).perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DESCRIPTION:To Tap device back button PARAMETERS: -- RETURN: void
	 * 
	 * @throws Exception
	 */
	public void tapNativeBackButtonOnDevice() throws Exception {
		try {
			getDriver().navigate().back();
			logger.info("Native back button on Device is clicked");
		} catch (Exception e) {
			logger.debug("error occurred while tapping the native back button on device");
			e.printStackTrace();
			onAutomationFailure(e);
		}
	}

	/**
	 * DESCRIPTION: To switch to Native/Web View PARAMETERS: RETURN:
	 * 
	 
	 */
	public void switchToView(String view) {
		Set<String> contextNames = getDriver().getContextHandles();
		System.out.println(contextNames);
		for (String context : contextNames) {
			if (context.contains(view)) {
				getDriver().context(context);
				logger.info("switched to ->'" + context + "'");
				break;
			}
		}
	}

	/**
	 * DESCRIPTION: To press Search button on soft keyboard (OEM Keyboard)
	 * PARAMETERS: -- RETURN:
	 * 
	 * @throws Exception
	 */

	public void androidKeyboardOperationSearch() throws Exception {
		try {
			String command = "adb -s input keyevent KEYCODE_SEARCH";
			runCommand(command);
			logger.info("Search button on Soft Keyboard is clicked");
		} catch (Exception e) {
			onAutomationFailure(e);
		}
	}

	/**
	 * DESCRIPTION: To press Done button on soft keyboard (OEM Keyboard) PARAMETERS:
	 * RETURN:
	 * 
	 * @throws Exception
	 */

	public void keyboardOperationEnter() throws Exception {
		try {
			getDriver().executeScript("mobile: performEditorAction", ImmutableMap.of("action", "Go"));
			Thread.sleep(5000);
			logger.info("Done button on Soft Keyboard is clicked");
		} catch (Exception e) {
			onAutomationFailure(e);
		}
	}

	/**
	 * DESCRIPTION: To Pause execution PARAMETERS: RETURN:
	 * 
	 * @throws Exception
	 */

	public void pauseExecutionInSec(int sec) throws Exception {
		try {
			logger.info("Pausing execution for =>" + sec + " seconds");
			Thread.sleep(sec * 1000);
		} catch (Exception e) {
			onAutomationFailure(e);
		}
	}

	public void completeTestFlightAppDownloadProcess() throws MalformedURLException, IOException {
		if (isElementPresent(getLocator("TestFlight", "CANCEL_DOWNLOAD", getTestData("CancelDownload_text")))) {
			for (int i = 0; i < 15; i++) {
				if (isElementDisplayed(getLocator("TestFlight", "OPEN_APP", getTestData("OpenApp_Text")))) {
					tapAndWait("TestFlight", "OPEN_APP", getTestData("OpenApp_Text"));
					if (isElementDisplayed(getLocator("TestFlight", "NOTIFICATION_ALLOW_OR_DENY_BUTTON",
							getTestData("Notification_Allow_Button")))) {
						tapAndWait("TestFlight", "NOTIFICATION_ALLOW_OR_DENY_BUTTON",
								getTestData("Notification_Allow_Button"));
					}
					reInitiateiOSDriver(SHELLIOSAPPBUNDLEID, EXECUTION_CONFIG_FILE, language, locale);
					break;
				} else {
					try {
						logger.info("Waiting for 5 seconds");
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void relaunchApplication() throws Exception {
		if (PLATFORM_NAME.contains("ANDROID_APP")) {
			forceStopAndroidApp(SHELLANDROIDPACKAGE);
			launchAndroidApp(SHELLANDROIDPACKAGE, SHELLANDROIDACTIVITY);
		} else if (PLATFORM_NAME.contains("IOS_APP")) {
			getDriver().terminateApp(SHELLIOSAPPBUNDLEID);
			getDriver().activateApp(SHELLIOSAPPBUNDLEID);
		}
	}

	public String readConstantFile(Field[] fields, String key) throws IllegalArgumentException, IllegalAccessException {
		for (Field f : fields) {
			Object obj = f.getName();
			String keyOne = String.valueOf(obj);
			if (keyOne.equalsIgnoreCase(key)) {
				Object objValue = f.get(keyOne);
				String value = String.valueOf(objValue);
				return value;
			}
		}
		return null;

	}

}





<?xml version="1.0" encoding="UTF-8" ?>
<pages>
	<page pageName="LaunchPage">
		<uiElements>
			<element locatorName="LOGIN_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="SIGNUP_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="LANDING_TEXT_SLIDE_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,"#ELEMENT_0")]</locator>
			</element>
			<element locatorName="LANDING_TEXT_SLIDE_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,"#ELEMENT_0")]</locator>
			</element>
			<element locatorName="LANDING_SHELL_ICON" locatorType="xpath">
				<locator>//android.widget.ScrollView/preceding-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.ImageView
				</locator>
			</element>
			<element locatorName="COUNTRY_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="COUNTRY_ICON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/preceding-sibling::android.view.ViewGroup/android.widget.ImageView
				</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="LanguageSelectionPage">
		<uiElements>
			<element locatorName="LANGUAGE_HEADER" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="LANGUAGE_SELECT_DESCRIPTION"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CHANGE_LANGUAGE_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]
				</locator>
			</element>

			<element locatorName="LANGUAGE_RADIO_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/following-sibling::android.view.ViewGroup
				</locator>
			</element>
			<element locatorName="DYNAMIC_LOCATOR_ON_LANGUAGE_SELECTION_PAGE"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="LoginEmailPage">
		<uiElements>
			<element locatorName="LOGIN_EMAIL_HEADER" locatorType="xpath">
				<locator>//android.widget.TextView[@text=''#ELEMENT_0']//ancestor::android.widget.HorizontalScrollView//ancestor::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']
				</locator>
			</element>
			<element locatorName="EMAIL_TEXT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']
				</locator>
			</element>
			<element locatorName="NEXT_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="DYNAMIC_LOCATOR_ON_LOGIN_EMAIL_PAGE"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="LoginPasswordPage">
		<uiElements>
			<element locatorName="LOGIN_PASSWORD_HEADER" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']//ancestor::android.widget.HorizontalScrollView//ancestor::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']
				</locator>
			</element>
			<element locatorName="PASSWORD_TEXT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']
				</locator>
			</element>
			<element locatorName="LOGIN_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/preceding-sibling::android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
			<element locatorName="DYNAMIC_LOCATOR_ON_LOGIN_PASSWORD_PAGE"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="HomePage">
		<uiElements>
			<element locatorName="LOCATION_PERSMISSION_ALLOW_BUTTON" locatorType="id">
				<locator>com.android.permissioncontroller:id/permission_allow_foreground_only_button</locator>
			</element>
			<element locatorName="LOCATION_PERSMISSION_DENY_BUTTON" locatorType="id">
				<locator>com.android.permissioncontroller:id/permission_deny_button</locator>
			</element>
			<element locatorName="LOCATION_PERSMISSION_ALLOW_ONCE" locatorType="id">
				<locator>com.android.permissioncontroller:id/permission_allow_always_button</locator>
			</element>
			<element locatorName="LOCATION_PERSMISSION_OVERLAY_TITLE" locatorType="id">
				<locator>com.android.permissioncontroller:id/permission_message</locator>
			</element>
			<element locatorName="TOTAL_SPENDING_HEADER_TEXT"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="DYNAMIC_LOCATOR_ON_HOME_PAGE"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="TOP_RIGHT_NAVIGATION_ICON" locatorType="xpath">
				<locator>//android.widget.ImageView/ancestor::android.view.ViewGroup/following-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
			<element locatorName="TOP_LEFT_SHELL_CARD_ICON" locatorType="xpath">
				<locator>//android.widget.ImageView/ancestor::android.view.ViewGroup/preceding-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="MainMenuPage">
		<uiElements>
			<element locatorName="LOGOUT_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="TOP_RIGHT_X_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='']</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="LogoutConfirmationPage">
		<uiElements>
			<element locatorName="YES_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="NO_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="LOGOUT_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="X_BUTTON_ON_TOP_RIGHT" locatorType="xpath">
				<locator>//android.widget.ImageView/preceding-sibling::android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup
				</locator>
			</element>
			<element locatorName="LOGOUT_ICON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/preceding-sibling::android.widget.ImageView
				</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="ShellFleetAppCardPage">
		<uiElements>
			<element locatorName="SHELL_FLEET_APP_CARD_HEADER"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="ACTIVATE_YOUR_CARD_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="ALLOWANCE_AVAILABLE_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]
				</locator>
			</element>
			<element locatorName="NAME_ON_CARD" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/preceding-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']
				</locator>
			</element>
			<element locatorName="STATION_LOCATOR" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="NEAREST_SHELL_STATION" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="NEAREST_STATION_MESSAGE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="SEND_CARD_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="FREEZE_CARD_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="UNFREEZE_CARD_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="REPLACE_CARD_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="LOCATION_ICON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/preceding-sibling::android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
			<element locatorName="INACTIVE_CARD_ICON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/preceding-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
			<element locatorName="TOP_RIGHT_NAVIGATION_ICON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/following-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
			<element locatorName="TOP_LEFT_NAVIGATION_ICON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/preceding-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="BeforeYouBeginPage">
		<uiElements>
			<element locatorName="BEFORE_YOU_BEGIN_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="BEFORE_YOU_BEGIN_DESCRIPTION_TEXT"
				locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]
				</locator>
			</element>
			<element locatorName="VALID_ID_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="VALID_ID_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="REGISTERED_BUSINESS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="REGISTERED_BUSINESS_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="MOBILE_NUMBER_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="SIGNUP_IN_5_MINUTES_BUTTON"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="YourConsentPage">
		<uiElements>
			<element locatorName="YOUR_CONSENT_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="OUR_APP_TNC_LINK" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="OUR_APP_TNC_RADIOBUTTON_UNCHECKED"
				locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.view.ViewGroup
				</locator>
			</element>
			<element locatorName="OUR_APP_TNC_RADIOBUTTON_CHECKED"
				locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
			<element locatorName="OUR_PRIVACY_STATEMENT_LINK"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="OUR_PRIVACY_STATEMENT_RADIOBUTTON_UNCHECKED"
				locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/following-sibling::android.view.ViewGroup/following-sibling::android.view.ViewGroup/android.view.ViewGroup
				</locator>
			</element>
			<element locatorName="OUR_PRIVACY_STATEMENT_RADIOBUTTON_CHECKED"
				locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/following-sibling::android.view.ViewGroup/following-sibling::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
			<element locatorName="YES_ALL_DONE_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="EmailVerificationPage">
		<uiElements>
			<element locatorName="OPEN_EMAIL_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="GMAIL_TRAY_ICON" locatorType="xpath">
			<locator>//android.widget.TextView[@text='Gmail']</locator>
<!-- 				<locator>//android.widget.TextView[@text='Gmail']/preceding-sibling::android.widget.FrameLayout/android.widget.ImageView</locator> -->
			</element>
			<element locatorName="SELECTED_EMAIL" locatorType="id">
				<locator>com.google.android.gm:id/selected_account_disc_gmail</locator>
			</element>
			
			<element locatorName="ACCOUNT_NAME" locatorType="id">
				<locator>com.google.android.gm:id/account_name</locator>
			</element>
			<element locatorName="EMAIL_SEARCH_BAR" locatorType="id">
				<locator>com.google.android.gm:id/open_search_bar_text_view</locator>
			</element>
			<element locatorName="EMAIL_SEARCH_INPUT" locatorType="id">
				<locator>com.google.android.gm:id/open_search_view_edit_text</locator>
			</element>
			<element locatorName="EMAIL_SEARCH_RESULT" locatorType="id">
				<locator>com.google.android.gm:id/viewified_conversation_item_view</locator>
			</element>
			<element locatorName="EMAIL_RECEPIENT_ID" locatorType="id">
				<locator>com.google.android.gm:id/recipient_summary</locator>
			</element>
			<element locatorName="CONFIRM_EMAIL_BUTTON" locatorType="xpath">
				<locator>//android.view.View[@text='#ELEMENT_0']</locator>
			</element>

		</uiElements>
	</page>

	<page pageName="CreateAccountPasswordPage">
		<uiElements>
			<element locatorName="CREATE_ACCOUNT_PASSWORD_HEADER"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']//ancestor::android.widget.HorizontalScrollView//ancestor::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']
				</locator>
			</element>
			<element locatorName="PASSWORD_TEXT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CREATE_ACCOUNT_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup</locator>
			</element>
			<element locatorName="DYNAMIC_LOCATOR_ON_CREATE_ACCOUNT_PASSWORD_PAGE"
				locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="YourPlanPage">
		<uiElements>
			<element locatorName="YOUR_PLAN_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="STANDARD_TITLE" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="DISCOUNT_ON_HEADER" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/following-sibling::android.widget.TextView
				</locator>
			</element>
			<element locatorName="YOUR_PLAN_BULLET_POINTS" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/following-sibling::android.view.ViewGroup/android.widget.TextView
				</locator>
			</element>
			<element locatorName="MORE_DETAILS_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="YES_I_WANT_THIS_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="PRODUCT_DETAILS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="MAIN_FEATURES_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="WHAT_CAN_I_BUY_SECTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.widget.TextView[contains(@text,'#ELEMENT_1')]
				</locator>
			</element>
			<element locatorName="WHERE_CAN_I_USE_SECTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.widget.TextView[contains(@text,'#ELEMENT_1')]
				</locator>
			</element>
			<element locatorName="DEPOSIT_REQUIRED_SECTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="HOW_MANY_CARDS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="HOW_MANY_CARDS_DESCRIPTION"
				locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="CONTINUE_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="MAX_CARD_TEXT_AND_INPUT" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>


	<page pageName="ProductSummaryPage">
		<uiElements>
			<element locatorName="YES_I_AM_HAPPY_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]
				</locator>
			</element>
			<element locatorName="YES_I_ACCEPT_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="EUROSHELL_FUELCARD_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="PRODUCT_SUMMARY_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="YourBusinessPage">
		<uiElements>
			<element locatorName="YOUR_BUSINESS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="YOUR_BUSINESS_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CONTINUE_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']</locator>
			</element>
			<element locatorName="CONTINUE_BUTTON_POST_BUSINNESS_SELECTION" locatorType="xpath">
				<locator>//android.widget.EditText[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']</locator>
			</element>
			<element locatorName="BUSINESS_NAME_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="VAT_NUMBER_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="BUSINESS_NAME_SEARCH_INPUT_FIELD"
				locatorType="xpath">
				<locator>//android.widget.EditText[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="BUSINESS_NAME_VALUE" locatorType="xpath">
				<locator>//android.view.ViewGroup/android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="DIRECTOR_NAME" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView</locator>
			</element>
			<element locatorName="DIRECTOR_NAME_CHECKBOX" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/following-sibling::android.view.ViewGroup</locator>
			</element>
			<element locatorName="CONTINUE_BUTTON_POST_DIRECTOR_SELECTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/ancestor::android.view.ViewGroup/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']</locator>
			</element>
			<element locatorName="CONTINUE_TO_YOUR_ADDRESS_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="EMAIL_US_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="YourAddressPage">
		<uiElements>
			<element locatorName="YOUR_ADDRESS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="YOUR_ADDRESS_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="CONTINUE_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']
				</locator>
			</element>
			<element locatorName="COUNTRY_NAME" locatorType="xpath">
				<locator>//android.widget.EditText[contains(@text,'#ELEMENT_0')]
				</locator>
			</element>
			<element locatorName="POST_CODE_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="ADDRESS_LINE_ONE_INPUT_FIELD"
				locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CITY_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CONTINUE_BUTTON_ON_ADDRESS_FORM_SCREEN"
				locatorType="xpath">
				<locator>//android.widget.EditText[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']
				</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="IdCheckPage">
		<uiElements>
			<element locatorName="ID_CHECK_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="ID_CHECK_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="OK_IAM_READY_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="IDENTITY_LINK" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CAMERA_PERSMISSION_ALLOW_BUTTON" locatorType="id">
				<locator>com.android.permissioncontroller:id/permission_allow_button</locator>
			</element>
			<element locatorName="CAMERA_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup</locator>
			</element>
			<element locatorName="HAPPY_WITH_THIS_PIC_CHECK_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/following-sibling::android.view.ViewGroup
				</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="ConfirmYourDetailsPage">
		<uiElements>
			<element locatorName="CONFIRM_YOUR_DETAILS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CONFIRM_YOUR_DETAILS_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="LEGAL_FIRST_NAME" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="LEGAL_MIDDLE_NAME" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="LEGAL_LAST_NAME" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="DATE_OF_BIRTH_FIELD" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="YEAR_HEADER_CALENDAR_BUTTON"
				locatorType="id">
				<locator>android:id/date_picker_header_year</locator>
			</element>
			<element locatorName="YEAR_VALUE" locatorType="id">
				<locator>android:id/text1</locator>
			</element>
			<element locatorName="OK_BUTTON_ON_CALENDAR" locatorType="xpath">
				<locator>//android.widget.Button[@text='OK']</locator>
			</element>
			<element locatorName="START_ID_CHECK_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="ID_CHECK_VERIFICATION_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="CONTINUE_BUTTON_ON_ID_CHECK_VERIFICATION_SCREEN" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']</locator>
			</element>
			<element locatorName="NO_SELFIE_PIC_ERROR" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,"#ELEMENT_0")]</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="VerifyYourMobilePage">
		<uiElements>
			<element locatorName="VERIFY_YOUR_MOBILE_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="VERIFY_YOUR_MOBILE_DESCRIPTION"
				locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="CONTINUE_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']
				</locator>
			</element>
			<element locatorName="MOBILE_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="NEXT_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/ancestor::android.view.ViewGroup/android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']</locator>
			</element>
			<element locatorName="CONFIRM_YOUR_NUMBER_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="SMS_SENT_CONFIRMATION_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CODE_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/preceding-sibling::android.widget.EditText/preceding-sibling::android.widget.EditText
				</locator>
			</element>
			<element locatorName="CODE_INPUT_FIELD_ONE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/preceding-sibling::android.widget.EditText
				</locator>
			</element>
			<element locatorName="CODE_INPUT_FIELD_TWO" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/following-sibling::android.widget.EditText
				</locator>
			</element>
			<element locatorName="CODE_INPUT_FIELD_THREE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/following-sibling::android.widget.EditText/following-sibling::android.widget.EditText
				</locator>
			</element>
			<element locatorName="CODE_INPUT_FIELD_FOUR" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']/ancestor::android.view.ViewGroup/following-sibling::android.widget.EditText/following-sibling::android.widget.EditText/following-sibling::android.widget.EditText
				</locator>
			</element>
			<element locatorName="RESEND_CODE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="VERIFY_YOUR_MOBILE_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="ThanksPage">
		<uiElements>
			<element locatorName="THANKS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="THANKS_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]
				</locator>
			</element>
		</uiElements>
	</page>

	<page pageName="PaymentDetailsPage">
		<uiElements>
			<element locatorName="PAYMENT_DETAILS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="PAYMENT_DETAILS_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]
				</locator>
			</element>
			<element locatorName="CONTINUE_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']
				</locator>
			</element>
			<element locatorName="PAYMENT_CARD_INFO_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]
				</locator>
			</element>
			<element locatorName="CARD_NUMBER_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="NAME_ON_CARD_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="EXPIRY_DATE_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="CVV_INPUT_FIELD" locatorType="xpath">
				<locator>//android.widget.EditText[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="ADD_PAYMENT_DETAILS_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>
	
	<page pageName="WelcomeToShellFleetAppPage">
		<uiElements>
			<element locatorName="WELCOME_TO_SHELL_FLEET_APP_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="WELCOME_TO_SHELL_FLEET_APP_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="SETUPACCOUNT_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
		</uiElements>
	</page>
	
	<page pageName="SetUpYourAccountPage">
		<uiElements>
			<element locatorName="SETUPYOURACCOUNT_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="SETUPYOURACCOUNT_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="CREATE_CARDS_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>
	
	<page pageName="SetUpDriversPage">
		<uiElements>
			<element locatorName="SETUPDRIVERS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="SETUPDRIVERS_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="YES_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']</locator>
			</element>
			<element locatorName="NO_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']</locator>
			</element>
			<element locatorName="USERNAME" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="EMAILID" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="ADD_DRIVER_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="ADD_DRIVER_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="SEND_CARDS_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="TOP_BACK_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>
	
	<page pageName="SendCardsPage">
		<uiElements>
			<element locatorName="SEND_CARDS_TITLE" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="SEND_CARDS_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="ADDRESS_LINE_ONE_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="ADDRESS_LINE_TWO_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CITY_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="POSTAL_TEXT" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="SEND_CARDS_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
			<element locatorName="CHANGE_DELIVERY_ADDRESS_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[@text='#ELEMENT_0']</locator>
			</element>
		</uiElements>
	</page>
	
	<page pageName="ShowTheAppPage">
		<uiElements>
			<element locatorName="SHOW_ME_THE_APP_DESCRIPTION" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]</locator>
			</element>
			<element locatorName="SHOW_ME_THE_APP_BUTTON" locatorType="xpath">
				<locator>//android.widget.TextView[contains(@text,'#ELEMENT_0')]/following-sibling::android.view.ViewGroup/android.widget.TextView[@text='#ELEMENT_1']</locator>
			</element>
		</uiElements>
	</page>

</pages>
