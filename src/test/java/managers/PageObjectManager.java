package managers;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import pageObjects.BulkFileUpload_Subm_page;
import pageObjects.Login_Page;
import pageObjects.Manual_Entry_Submission_Page;

public class PageObjectManager {

	private WebDriver driver;

	private Login_Page loginPage;
	private Manual_Entry_Submission_Page manual_entry_submission_page;
	private BulkFileUpload_Subm_page bulkFileUpload_Subm_page;

	public PageObjectManager(WebDriver driver) {

		this.driver = driver;

	}

	public Login_Page getLoginPage() throws MalformedURLException {

		return (loginPage == null) ? loginPage = new Login_Page(driver) : loginPage;

	}

	public Manual_Entry_Submission_Page getManualEntrySubmissionPage() {

		return (manual_entry_submission_page == null)
				? manual_entry_submission_page = new Manual_Entry_Submission_Page(driver)
				: manual_entry_submission_page;

	}

	public BulkFileUpload_Subm_page getBulkFileUploadPage() {

		return (bulkFileUpload_Subm_page == null) ? bulkFileUpload_Subm_page = new BulkFileUpload_Subm_page(driver)
				: bulkFileUpload_Subm_page;

	}

}
