package pageObjects;

import java.io.File;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import managers.FileReaderManager;
import superHelper.MAF;

public class BulkFileUpload_Subm_page extends MAF {

	WebDriver driver;

	@FindBy(xpath = "//input[@id='fileUploadInput']")
	public WebElement lnkBrowse;

	@FindBy(xpath = "//input[@id='submit']")
	public WebElement btnSubmit;

	@FindBy(xpath = "//select[@name='paymentTypeId']")
	public WebElement paymentCategory;

	@FindBy(xpath = "//select[@name='organizationId']")
	public WebElement reportingEntity;

	@FindBy(xpath = "//select[@name='programCycleId']")
	public WebElement programYear;

	@FindBy(xpath = "//select[@name='reSubFileInd']")
	public WebElement resubmissionFileIndicator;

	@FindBy(xpath = "//li[@class='confirmSuccess']")
	public WebElement confirmSuccessMsg;

	@FindBy(xpath = "//h2[@class='confirmSuccess']")
	public WebElement ConfirmTxt;

	public BulkFileUpload_Subm_page(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void enterPaymentDetails(String sScenario) throws InterruptedException, MalformedURLException {

		int rNum = FileReaderManager.getInstance().getXls_Reader().getCellRowNum("Scenarios", "Scenario_Name",
				sScenario);
		String PaymentCategory = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"PaymentCategory", rNum);
		String ReportEntity = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ReportEntity",
				rNum);
		String ProgramYear = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ProgramYear",
				rNum);
		String ResubmissionFileIndicator = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"ResubmissionFileIndicator", rNum);

		selectFromDropdown(paymentCategory, PaymentCategory);
		selectFromDropdown(reportingEntity, ReportEntity);
		selectFromDropdown(programYear, ProgramYear);
		selectFromDropdown(resubmissionFileIndicator, ResubmissionFileIndicator);
		// click(lnkBrowse);
		Thread.sleep(1000);
		String sFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "java" + File.separator + "Attachments" + File.separator + "OWNVAL0regression.csv";
		System.out.println(sFile);
		lnkBrowse.sendKeys(sFile);
//				"C:\\Users\\mpulipat\\eclipse-workspace\\OPS\\src\\test\\java\\Attachments\\OWNVAL0regression.csv");
		// Thread.sleep(3000);
		clickElement(btnSubmit);
		Thread.sleep(5000);

	}

	public void validateConfirmMessage() throws InterruptedException {
		System.out.println(getText(confirmSuccessMsg));
		Assert.assertEquals(getText(confirmSuccessMsg).trim(),
				"Your file has been received and will undergo validations. You will receive emails notifying you of the results of these validations.",
				"File has been uploaded");
		Assert.assertEquals(getText(ConfirmTxt), "Confirmation:");

	}

}
