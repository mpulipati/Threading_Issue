package runner;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import managers.FileReaderManager;
import managers.WebDriverManager;

@Test
@CucumberOptions(features = "src/test/resources/Features", glue = "stepDefinitions", plugin = {
		"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" }, tags = { "@Smoke" })
public class TestRunner_TestNG extends AbstractTestNGCucumberTests {

	@AfterSuite
	public static void writeExtentReport() {
		System.out.println("testINg Extent");
		Reporter.loadXMLConfig(new File(FileReaderManager.getInstance().getCofig_Reader().getReportConfigPath()));
	}

	@AfterClass
	public void terminate() {
		// Remove the ThreadLocalMap element
		WebDriverManager.terminateDriver();

	}

}
