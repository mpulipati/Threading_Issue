package pageObjects;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import superHelper.MAF;

public class Login_Page extends MAF {

	private static WebDriver driver;

	// protected static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<>();

	@FindBy(xpath = "//a[text()='Log In']")
	public WebElement logIn;

	@FindBy(xpath = "//input[@id='userID']")
	public WebElement userID;

	@FindBy(xpath = "//input[@id='password']")
	public WebElement password;

	@FindBy(xpath = "//input[@value='Log in']")
	public WebElement LogIn;

	@FindBy(xpath = "//input[@value='Switch']")
	public WebElement btnSwitch;

	@FindBy(xpath = "//span[text()='Submissions']")
	public WebElement lblSubmissions;

	@FindBy(xpath = "//input[contains(@value,'Bulk File Upload')]")
	public WebElement btnBulkFileUpload;

	@FindBy(xpath = "//input[contains(@value,'Manual Data Entry')]")
	public WebElement btnManualDataEntry;

	@FindBy(xpath = "//a[text()='View']")
	public WebElement viewRecords;

	@FindBy(xpath = "//span[text()='Total Amount of Payment: ']/following::span[contains(text(),'$')]")
	public WebElement totAmt;

	@FindBy(xpath = "//input[@value='Back']")
	public WebElement Back;

	@FindBy(xpath = "//input[@class='selectAllbox']")
	public WebElement selectAll;

	@FindBy(xpath = "//input[@value='Delete Selected']")
	public WebElement deleteSelected;

	@FindBy(xpath = "//a[text()='Yes']")
	public WebElement YesBtn;

	public Login_Page(WebDriver driver) throws MalformedURLException {

		this.driver = getDriver();
		PageFactory.initElements(getDriver(), this);
	}

	public void login(String uname, String pswd) throws InterruptedException, MalformedURLException {

		clickElement(logIn);
		// click(logIn);
		setText(userID, uname);
		setText(password, pswd);
		clickElement(LogIn);

	}

	public void navigateToHomePage(String env, String ver) throws InterruptedException, MalformedURLException {

		WebElement wEnv = getDriver().findElement(By.xpath("//a[contains(text(),'" + env + "')]"));
		clickElement(wEnv);
		WebElement wVer = getDriver().findElement(By.xpath("//a[contains(text(),'" + ver + "')]"));
		clickElement(wVer);
	}

	public void selectUserType(String uType) throws InterruptedException, MalformedURLException {

		WebElement wUserType = getDriver().findElement(
				By.xpath("//label[contains(text(),'" + uType + "')]/preceding-sibling::input[@type='radio']"));
		clickElement(wUserType);
		System.out.println("The thread ID foor " + Thread.currentThread().getId());
		clickElement(btnSwitch);
		System.out.println("The thread ID foor " + Thread.currentThread().getId());

	}

	public void navigateToBulkFileUploadPage() throws InterruptedException, MalformedURLException {

		clickElement(lblSubmissions);
		clickElement(btnBulkFileUpload);
	}

	public void navigateToManualDataEntrySubmissionPage() throws InterruptedException, MalformedURLException {

		clickElement(lblSubmissions);
		clickElement(btnManualDataEntry);
	}

	public void verifySubmission(String pType) throws InterruptedException, MalformedURLException {

		WebElement viewAllElement = getDriver()
				.findElement(By.xpath("//span[text()='View All for " + pType + "']/preceding-sibling::input"));
		clickElement(viewAllElement);
		clickElement(viewRecords);
		Assert.assertEquals(getText(totAmt), "$43,543.00");
		clickElement(Back);

	}

	public void deleteAllSubmissions() throws InterruptedException, MalformedURLException {

		clickElement(selectAll);
		clickElement(deleteSelected);
		clickElement(YesBtn);

	}

}
