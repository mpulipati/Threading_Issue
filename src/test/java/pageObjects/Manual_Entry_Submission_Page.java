package pageObjects;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import managers.FileReaderManager;
import superHelper.MAF;

public class Manual_Entry_Submission_Page extends MAF {

	WebDriver driver;

	@FindBy(xpath = "//select[@id ='form1:paymentType']")
	public WebElement paymentType;

	@FindBy(xpath = "//select[@id ='form1:paymentreportentity']")
	public WebElement reportentity;

	@FindBy(xpath = "//select[@id ='form1:programYear']")
	public WebElement pgrmYear;

	@FindBy(xpath = "//input[@id ='form1:continueButton']")
	public WebElement continueButton;

	@FindBy(xpath = "//input[@id ='form1:continueBtn']")
	public WebElement continueBtn;

	@FindBy(xpath = "//select[@id ='form1:coveredRecipientType']")
	public WebElement coveredRecipientType;

	@FindBy(xpath = "//input[contains(@id,'First')]")
	public WebElement PhysicianFirstName;

	@FindBy(xpath = "//input[contains(@id,'Last')]")
	public WebElement PhysicianLastName;

	// @FindBy(xpath = "//select[@id ='form1:nonresearchCountryPhy']")
	@FindBy(xpath = "//select[contains(@id ,'Country')]")
	public WebElement Country;

	@FindBy(xpath = "//input[contains(@id,'Address1')]")
	// @FindBy(xpath = "//input[@id ='form1:nrAddress1']")
	public WebElement Address1;

	@FindBy(xpath = "//input[contains(@id,'City')]")
	// @FindBy(xpath = "//input[@id ='form1:nrCity']")
	public WebElement City;

	@FindBy(xpath = "//select[contains(@id,'State')]")
	// @FindBy(xpath = "//select[@id ='form1:nrState']")
	public WebElement State;

	@FindBy(xpath = "//input[contains(@id,'ZipCode')]")
	// @FindBy(xpath = "//input[@id ='form1:recipientZipCodeGnrl']")
	public WebElement zipCode;

	// @FindBy(xpath = "//select[contains(@id,'Type')]")
	@FindBy(xpath = "//select[@id ='form1:nrPrimaryType']")
	public WebElement primaryType1;

	// @FindBy(xpath = "//select[contains(@id,'Type')]")
	@FindBy(xpath = "//select[@id ='form1:nrPrimaryType'] | //select[contains(@id,'form1:oPhysicianType')]")
	public WebElement primaryType;

	// @FindBy(xpath = "//input[contains(@id,'_input')]")
	@FindBy(xpath = "//input[@id ='form1:phySpecGnrl_input'] | //input[@id ='form1:ownershipTaxonomy_input']")
	public WebElement taxonomyCode;

	@FindBy(xpath = "//select[@id ='form1:stLicGnrl'] | //select[@id ='form1:stLicOwner']")
	public WebElement stLicGnrl;

	@FindBy(xpath = "//input[@id ='form1:nrlicense1'] | //input[@id ='form1:ownershipPhysicianLicenseNo2']")
	public WebElement nrlicense1;

	@FindBy(xpath = "//input[@id ='form1:roleButton']")
	public WebElement roleButton;

	@FindBy(xpath = "//select[contains(@id,'InterestHeldby')]")
	public WebElement intHeldBy;

	@FindBy(xpath = "//input[contains(@id,'ownershipInvestment')]")
	public WebElement DollarAmount;
	@FindBy(xpath = "//input[contains(@id,'ownershipInterest')]")
	public WebElement ValueOfInt;
	@FindBy(xpath = "//textarea[contains(@id,'termsOfInterest')]")
	public WebElement termsOfInt;

	@FindBy(xpath = "//select[@id ='form1:productInd']")
	public WebElement productInd;

	@FindBy(xpath = "//select[@id ='form1:coveredInd']")
	public WebElement coveredInd;

	@FindBy(xpath = "//select[@id ='form1:prodType']")
	public WebElement prodType;

	@FindBy(xpath = "//input[@id ='form1:prodCat']")
	public WebElement prodCat;

	@FindBy(xpath = "//textarea[@id ='form1:prodName']")
	public WebElement prodName;

	@FindBy(xpath = "//input[@id ='form1:prodNDC1']")
	public WebElement prodNDC1;

	@FindBy(xpath = "//input[@id ='form1:addProdButton']")
	public WebElement addProdButton;

	@FindBy(xpath = "//input[@value ='Yes']")
	public WebElement Yes;

	@FindBy(xpath = "//input[@id ='form1:nrTotalAmount']")
	public WebElement TotalAmount;

	@FindBy(xpath = "//input[@id ='form1:paymentDate']")
	public WebElement paymentDate;

	@FindBy(xpath = "//input[@id ='form1:paymentCount']")
	public WebElement paymentCount;

	@FindBy(xpath = "//select[@id ='form1:nrFormofPaymentorTransferofValue2016']")
	public WebElement FormofPayment;

	@FindBy(xpath = "//select[@id ='form1:nrNonResearchNatureofPaymentorTransferofValueb']")
	public WebElement ResearchNature;

	@FindBy(xpath = "//input[@id ='form1:nrPhysicianOwnership:0']")
	public WebElement PhysicianOwnership;
	@FindBy(xpath = "//select[@id ='form1:nrThirdPartyPaymentRecipientIndicator']")
	public WebElement ThirdPartyPayment;
	@FindBy(xpath = "//select[@id ='form1:charityIndicator']")
	public WebElement charityIndicator;
	@FindBy(xpath = "//select[@id ='form1:nrResearchDelayinPublicationofResearchPaymentIndicator']")
	public WebElement ResearchDelay;
	@FindBy(xpath = "//textarea[@id ='form1:contextualInformation']")
	public WebElement contextualInformation;
	@FindBy(xpath = "//input[@value ='Save Record']")
	public WebElement saveRecord;

	@FindBy(xpath = "//input[@id ='form1:homeSystemPaymentId']")
	public WebElement PaymentId;

	@FindBy(xpath = "//input[@value ='Go to Review Records']")
	public WebElement goToReviewRecords;

	public Manual_Entry_Submission_Page(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void submitManualSubmission(String pType, String sScenario)
			throws InterruptedException, MalformedURLException {

		// String sScenario =
		// testContext.scenarioContext.getContext(Context.Scenario_Name).toString();
		int rNum = FileReaderManager.getInstance().getXls_Reader().getCellRowNum("Scenarios", "Scenario_Name",
				sScenario);
		String ReportEntity = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ReportEntity",
				rNum);
		String ProgramYear = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ProgramYear",
				rNum);
		String HomeSystemPaymentID = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"HomeSystemPaymentID", rNum);
		String CoveredRecipientType = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"CoveredRecipientType", rNum);
		String PhysicianFName = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"PhysicianFirstName", rNum);
		String PhysicianLName = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"PhysicianLastName", rNum);
		String Cntry = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "Country", rNum);
		String Adr1 = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "Address1", rNum);
		String Cty = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "City", rNum);
		String Stat = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "State", rNum);
		String Zip = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ZipCode", rNum);
		String PrmryType = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "PrimaryType",
				rNum);
		String TaxCode = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "TaxonomyCode", rNum);
		String strLic = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "StLicGnrl", rNum);
		String Nrlicense1 = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "Nrlicense1",
				rNum);
		String prdInd = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ProductInd", rNum);
		String CoveredInd = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "CoveredInd",
				rNum);
		String ProdType = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ProdType", rNum);
		String ProdCat = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ProdCat", rNum);
		String ProdName = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ProdName", rNum);
		String ProdNDC1 = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ProdNDC1", rNum);
		String TotalAmt = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "TotalAmount", rNum);
		String PaymentDate = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "PaymentDate",
				rNum);
		String PaymentCount = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "PaymentCount",
				rNum);
		String FormofPaymt = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "FormofPayment",
				rNum);
		String researchNature = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"ResearchNature", rNum);
		String thirdPartyPayment = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"ThirdPartyPayment", rNum);
		String CharityIndicator = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"CharityIndicator", rNum);
		String researchDelay = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios", "ResearchDelay",
				rNum);
		String ContextualInformation = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"ContextualInformation", rNum);
		String InterestHeldBy = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"InterestHeldBy", rNum);
		String DollarAmountInvested = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"DollarAmountInvested", rNum);
		String ValueOfInterest = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"ValueOfInterest", rNum);
		String TermsOfInterest = FileReaderManager.getInstance().getXls_Reader().getCellData("Scenarios",
				"TermsOfInterest", rNum);

		selectFromDropdown(paymentType, pType);
		selectFromDropdown(reportentity, ReportEntity);
		selectFromDropdown(pgrmYear, ProgramYear);
		setText(PaymentId, HomeSystemPaymentID);
		clickElement(continueButton);
		if (pType.equalsIgnoreCase("General Payments")) {
			selectFromDropdown(coveredRecipientType, CoveredRecipientType);
		}
		setText(PhysicianFirstName, PhysicianFName);
		setText(PhysicianLastName, PhysicianLName);
		selectFromDropdown(Country, Cntry);
		setText(Address1, Adr1);
		setText(City, Cty);
		selectFromDropdown(State, Stat);
		setText(zipCode, Zip);
		selectFromDropdown(primaryType, PrmryType);
		setText(taxonomyCode, TaxCode);
		selectFromDropdown(stLicGnrl, strLic);
		setText(nrlicense1, Nrlicense1);
		clickElement(roleButton);
		clickElement(continueButton);

		if (pType.equalsIgnoreCase("Ownership or Investment Interest")) {
			selectFromDropdown(intHeldBy, InterestHeldBy);
			// setText(intHeldBy, InterestHeldBy);
			setText(DollarAmount, DollarAmountInvested);
			setText(ValueOfInt, ValueOfInterest);
			setText(termsOfInt, TermsOfInterest);

		} else {

			selectFromDropdown(productInd, prdInd);
			selectFromDropdown(coveredInd, CoveredInd);
			selectFromDropdown(prodType, ProdType);
			setText(prodCat, ProdCat);
			setText(prodName, ProdName);
			setText(prodNDC1, ProdNDC1);
			clickElement(addProdButton);
			clickElement(continueBtn);
			scrollToElement(Yes);
			clickElement(Yes);
			setText(TotalAmount, TotalAmt);
			setText(paymentDate, PaymentDate);
			setText(paymentCount, PaymentCount);
			selectFromDropdown(FormofPayment, FormofPaymt);
			selectFromDropdown(ResearchNature, researchNature);
			clickElement(continueButton);
			clickElement(PhysicianOwnership);
			selectFromDropdown(ThirdPartyPayment, thirdPartyPayment);
			selectFromDropdown(charityIndicator, CharityIndicator);
			selectFromDropdown(ResearchDelay, researchDelay);
			setText(contextualInformation, ContextualInformation);
		}
		clickElement(continueButton);
		clickElement(saveRecord);
		clickElement(goToReviewRecords);

	}

}
