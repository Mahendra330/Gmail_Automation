package testRunner;

import java.io.File;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		//features= ".//Features/Inbox.feature", 
		features= ".//Features/LoginGmail.feature",
		//features= ".//Features/", 
		glue= "stepDefinitions",
		dryRun=false, 
		plugin= {"pretty","html:test-output","json:json_output/cucumber.json","junit:junit_xml_output/cucumber.xml",
				"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:test-output/Report.html"},
		monochrome=true,
		tags= {} 
		)
public class TestRunnerTest {
@AfterClass
	
	public static void reportSetup() {
		Reporter.loadXMLConfig(new File("/Automan/Features/extent-config.xml"));
		Properties p = System.getProperties();
		p.list(System.out);
		
	}
}
