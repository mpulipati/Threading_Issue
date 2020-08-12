package stepDefinitions;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import cucumber.TestContext;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.Context;
import managers.FileReaderManager;
import managers.PageObjectManager;
import managers.WebDriverManager;
import pageObjects.BulkFileUpload_Subm_page;
import pageObjects.Login_Page;
import pageObjects.Manual_Entry_Submission_Page;

public class LoginSteps {

	public static WebDriver driver;
	public static Login_Page loginPg;
	public static Manual_Entry_Submission_Page manual_entry_submission_page;
	public static BulkFileUpload_Subm_page getBulkFileUploadPage;
	public static PageObjectManager POM;
	public static FileReaderManager FRM;
	public WebDriverManager webDriverManager;
	TestContext testContext;
	// public WebDriver driver;

	public LoginSteps(TestContext context) throws MalformedURLException {
		testContext = context;
		loginPg = testContext.getPageObjectManager().getLoginPage();
		manual_entry_submission_page = testContext.getPageObjectManager().getManualEntrySubmissionPage();
		getBulkFileUploadPage = testContext.getPageObjectManager().getBulkFileUploadPage();

	}

	@Given("^User login to OPP$")
	public void User_Login_OPP() throws Throwable {

		String sScenario = testContext.scenarioContext.getContext(Context.Scenario_Name).toString();
		int rNum = FileReaderManager.getInstance().getXls_Reader().getCellRowNum("Scenarios", "Scenario_Name",
				sScenario);
		String uName = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "uName", rNum);
		String pwd = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "pwd", rNum);
		loginPg.login(uName, pwd);

	}

	@Given("^user navigates to \"(.*)\" and \"(.*)\" homepage")
	public void User_Navigates_HomePage(String envr, String version) throws Throwable {

		loginPg.navigateToHomePage(envr, version);
	}

	@When("^user selects user type as \"(.*)\"")
	public void selectUserType(String userType) throws Throwable {

		loginPg.selectUserType(userType);
	}

	@When("^user navigates to Bulk File Upload Submissions Page$")
	public void navigateToBulkFileUpload() throws Throwable {

		loginPg.navigateToBulkFileUploadPage();
	}

	@When("^user navigates to Manual Data Entry Submissions Page$")
	public void manualDataEntry() throws Throwable {

		loginPg.navigateToManualDataEntrySubmissionPage();
	}

	@When("^user submits the \"(.*)\" submission$")
	public void manualSubmission(String paymentType) throws Throwable {

		String sScen = testContext.scenarioContext.getContext(Context.Scenario_Name).toString();

		manual_entry_submission_page.submitManualSubmission(paymentType, sScen);
	}

	@Then("^user verifies the submission in \"(.*)\" submissions$")
	public void verifySubmission(String paymentType) throws Throwable {

		loginPg.verifySubmission(paymentType);
		loginPg.deleteAllSubmissions();
	}

	@When("^user enters payment details$")
	public void user_enters_payment_details() throws Throwable {

		String sScen = testContext.scenarioContext.getContext(Context.Scenario_Name).toString();

		getBulkFileUploadPage.enterPaymentDetails(sScen);
	}

	@Then("^user sees the confirmation message$")
	public void user_sees_confirm_msg() throws Throwable {

		getBulkFileUploadPage.validateConfirmMessage();
	}

}
