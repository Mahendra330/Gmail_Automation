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

package com.LandT.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xmlReader {

	protected static final Logger logger = LogManager.getLogger(xmlReader.class.getName());

	public static HashMap<String, String> readRepositoryXml(String fileName, String page, String identifier)
			throws SAXException, IOException {
		try {
			HashMap<String, String> map = new HashMap<>();
			map.clear();
			String locatorType;
			String locatorValue;
			File xmlFile = new File(System.getProperty("user.dir") + fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;

			dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("page");
			outerloop: for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					if (eElement.getAttribute("pageName").equalsIgnoreCase(page)) {
						NodeList nListNew = eElement.getElementsByTagName("element");
						for (int tempNew = 0; tempNew < nListNew.getLength(); tempNew++) {
							Node nNodeNew = nListNew.item(tempNew);
							if (nNodeNew.getNodeType() == Node.ELEMENT_NODE) {
								Element eElementNew = (Element) nNodeNew;
								if (eElementNew.getAttribute("locatorName").equalsIgnoreCase(identifier)) {
									locatorType = eElementNew.getAttribute("locatorType");
									locatorValue = eElementNew.getElementsByTagName("locator").item(0).getTextContent();
									map.put(locatorType, locatorValue);
									break outerloop;
								}
							}
						}
					}
				}
			}
			return map;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HashMap<String, String> readTestDataXml(String fileName, String key)
			throws SAXException, IOException {
		try {
			HashMap<String, String> map = new HashMap<>();
			map.clear();
			String dataValue;
			File xmlFile = new File(System.getProperty("user.dir") + fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("data");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					if (eElement.getAttribute("key").equalsIgnoreCase(key)) {
						dataValue = eElement.getElementsByTagName("value").item(0).getTextContent();
						map.put(key, dataValue);
						break;
					}
				}
			}
			return map;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
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
