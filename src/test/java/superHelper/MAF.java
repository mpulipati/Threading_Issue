package superHelper;

import static java.lang.Math.floor;
import static java.lang.Math.log;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

import cucumber.TestContext;
import enums.Context;
import managers.WebDriverManager;

public class MAF extends WebDriverManager {

	/*
	 * protected final String INIT_AR = rb.getString("AR"); protected final String
	 * INIT_AU = rb.getString("AU"); protected final String INIT_BR =
	 * rb.getString("BR"); protected final String INIT_CN = rb.getString("CN");
	 * protected final String INIT_DE = rb.getString("DE"); protected final String
	 * INIT_ES = rb.getString("ES"); protected final String INIT_FR =
	 * rb.getString("FR"); protected final String INIT_IT = rb.getString("IT");
	 * protected final String INIT_JP = rb.getString("JP"); protected final String
	 * INIT_KR = rb.getString("KR"); protected final String INIT_RU =
	 * rb.getString("RU"); protected final String INIT_UK = rb.getString("UK");
	 * protected final String INIT_US = rb.getString("US");
	 */

	protected static String screenshotLocation;
	protected static String testEnvironment;

	// protected static boolean screenshotsWanted =
	// Boolean.parseBoolean(rb.getString("take_screenshots"));

	// protected static String apiEnvironment = rb.getString("api_environment");

	protected static List<String> words, names;

	public static String brow;
	public Connection connection;

	@AfterMethod(alwaysRun = true)
	protected void closeBrowser() {

		try {
			getDriver().manage().deleteAllCookies();
			getDriver().close();
			report("Closed: Application & Browser");
		} catch (Exception exp) {
		}

	}

	protected void resetIEZoomLevel() throws AWTException {
		Robot keys = new Robot();
		keys.keyPress(KeyEvent.VK_CONTROL);
		keys.keyPress(KeyEvent.VK_0);
		keys.keyRelease(KeyEvent.VK_CONTROL);
		keys.keyRelease(KeyEvent.VK_0);
	}

	protected void pasteTextAndEnter() {
		try {
			Robot keys = new Robot();
			keys.keyPress(KeyEvent.VK_CONTROL);
			keys.keyPress(KeyEvent.VK_V);
			keys.keyRelease(KeyEvent.VK_CONTROL);
			keys.keyRelease(KeyEvent.VK_V);
			keys.keyPress(KeyEvent.VK_ENTER);
			keys.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	protected static List<Class<?>> sortList(List<Class<?>> objectList) {
		Collections.sort(objectList, new Comparator<Class<?>>() {
			public int compare(Class<?> o1, Class<?> o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return objectList;
	}

	public void click(WebElement element) throws InterruptedException, MalformedURLException {
		String elementText = element.getText();

		try {
			element.click();
			if (!elementText.equals("")) {
				report("Clicked: " + elementText);
			}
		} catch (Exception enve) {
			// checkForseePopup();
			element.click();
			if (!elementText.equals("")) {
				report("Clicked: " + elementText);
			}
			// checkForseePopup();
		}
		waitForPageLoaded(getDriver());

	}

	public void clickDropdown(String selector, int row) throws InterruptedException, MalformedURLException {
		if (row <= getElementSize(selector)) {
			List<WebElement> element = getDriver().findElements(By.cssSelector(selector));
			click(element.get(row));
		} else {
			report("Dropdown row is out of bounds");
		}
	}

	public void clickDropdown(String selector, String val) throws InterruptedException, MalformedURLException {
		for (WebElement element : getDriver().findElements(By.cssSelector(selector))) {
			if (element.getText().equals(val)) {
				click(element);
				break;
			} else {
				report("The dropdown value is unavailable");
			}
		}
	}

	public void clickLink(String linktext) throws InterruptedException, MalformedURLException {
		if (getDriver().findElements(By.linkText(linktext)).size() > 0) {
			click(getDriver().findElement((By.linkText(linktext))));
		}
	}

	public void clickLinkByCSS(String selector) throws InterruptedException, MalformedURLException {
		if (getDriver().findElements(By.cssSelector(selector)).size() > 0) {
			click(getDriver().findElement(By.cssSelector(selector)));
		}
	}

	public String convertDateFormat(String oldFormat, String newFormat, String date) {
		SimpleDateFormat newDateFormat = new SimpleDateFormat(oldFormat);
		Date MyDate;
		try {
			MyDate = newDateFormat.parse(date);
			newDateFormat.applyPattern(newFormat);
			return newDateFormat.format(MyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * <h2>Unchecks a radio button (&lt;input type="radio"&gt;) or check box
	 * (&lt;input type="checkbox"&gt;)</h2>
	 * <ul>
	 * <li>Reports which check box or radio button was unchecked</li>
	 * </ul>
	 * 
	 * @param element Checkbox or radio element to uncheck
	 */
	public void deselect(WebElement element) {
		if (element.isSelected()) {
			String name = element.getAttribute("name");
			String id = element.getAttribute("id");

			if (!name.equals("")) {
				report("Unchecked: " + StringUtils
						.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(name)), ' '));
			} else if (!id.equals("")) {
				report("Unchecked: "
						+ StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(id)), ' '));
			} else {
				report("Unchecked checkbox");
			}

			element.click();
		}
	}

	public String differentBetweenTwoDates(String simpleDateFormat, String fromDate, String toDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat(simpleDateFormat);
		try {
			Date date1 = myFormat.parse(fromDate);
			Date date2 = myFormat.parse(toDate);
			long diff = date2.getTime() - date1.getTime();
			return String.valueOf((diff) / ((1000 * 60 * 60 * 24)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <h2>Reports a failure of a verification to the test that will show as red in
	 * the report</h2>
	 * 
	 * @param message           text to report as a failure in the output report
	 * @param continueExecution if <code>true</code>, continues test script
	 *                          execution. Otherwise, throws an AssertionError and
	 *                          stops test execution.
	 */

	/**
	 * <h2>Locates a &lt;button&gt; based on it's visible text</h2>
	 * 
	 * @param locator text used to locate the &lt;button&gt;
	 * @return <code>true</code> if the &lt;button&gt; is found, <code>false</code>
	 *         otherwise
	 * @throws InterruptedException
	 * @throws MalformedURLException
	 */
	public boolean findButtonByText(String locator) throws InterruptedException, MalformedURLException {
		List<WebElement> links = getDriver().findElements(By.cssSelector("button"));
		for (WebElement link : links) {
			String linkText = link.getText();
			if (linkText.equals(locator)) {
				click(link);
				return true;
			}
		}
		return false;
	}

	public WebElement findElementByCSS(String selector) throws MalformedURLException {
		return getDriver().findElement(By.cssSelector(selector));
	}

	public List<WebElement> findElementsByCSS(String selector) throws MalformedURLException {
		return getDriver().findElements(By.cssSelector(selector));
	}

	public WebElement findLinkByHref(String href) throws MalformedURLException {
		List<WebElement> all_links = getDriver().findElements(By.cssSelector("a"));
		for (WebElement we : all_links) {
			if (we.getAttribute("href").contains(href)) {
				return we;
			}
		}
		return null;
	}

	public void findLinkByIndex(String text, int indx) throws InterruptedException, MalformedURLException {
		List<WebElement> all_links = getDriver().findElements(By.cssSelector("a"));
		List<WebElement> links = new ArrayList<WebElement>();
		for (WebElement link : all_links) {
			String linkText = link.getText();
			if (linkText.equals(text)) {
				links.add(link);
			}
		}

		if (links.size() == 0) {

		} else if (indx >= links.size()) {
			click(links.get(0));
		} else {
			click(links.get(indx));
		}
	}

	public boolean isElementAvailable(WebElement elem) throws MalformedURLException {
		getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			if (elem.isEnabled())
				return true;
			else
				return false;
		} catch (Exception e) {
			getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			return false;
		} finally {
			getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	public boolean findLinkByText(String locator) throws InterruptedException, MalformedURLException {
		List<WebElement> links = getDriver().findElements(By.cssSelector("a"));
		for (WebElement link : links) {
			String linkText = link.getText();
			if (linkText.equals(locator)) {
				click(link);
				return true;
			}
		}
		return false;
	}

	public String genSelector(String prefix, String locator, String suffix, int count) {
		/*
		 * String selector = locator; if (count > 1) { for (int i = 1; i < count; i++) {
		 * locator = selector + "+" + locator; } } selector = prefix+locator+suffix;
		 * return selector;
		 */
		return genSelector(prefix, locator, count) + suffix;
	}

	public String genSelector(String prefix, String locator, int count) {
		String selector = locator;
		if (count > 1) {
			for (int i = 1; i < count; i++) {
				locator = selector + "+" + locator;
			}
		}
		selector = prefix + locator;
		if (count < 1) {
			selector = prefix;
		}
		return selector;
	}

	public String genSelector(String prefix, int count, String locator) {
		String selector = locator;
		if (count > 1) {
			for (int i = 1; i < count; i++) {
				locator = selector + "+" + locator;
			}
		}
		selector = prefix + "+" + locator;
		if (count < 1) {
			selector = prefix;
		}
		return selector;
	}

	public String genSelector(String locator, int count) {
		String selector = locator + "(" + count + ")";
		return selector;
	}

	public String genSelector(int count, String locator, String suffix) {
		String selector = locator + "(" + count + ")" + suffix;
		return selector;
	}

	public String genXpath(String prefix, String locator, String suffix, int count) {
		String xpath = prefix + locator + "[" + count + "]" + suffix;
		return xpath;
	}

	public String genXpath(String prefix, String locator, int count) {
		String xpath = prefix + locator + "[" + count + "]";
		return xpath;
	}

	public String getAttribute(WebElement element, String attr) {
		// report("Attribute value of " + attr + "="
		// +element.getAttribute(attr));
		return element.getAttribute(attr);
	}

	/**
	 * <h2>Gets the day of the week as a String</h2>
	 * 
	 * @param inputDay day of the week as an integer
	 * @return day of the week as a String
	 */
	public String getDayOfWeek(int inputDay) {
		switch (inputDay) {
		case 0:
			return "Saturday";
		case 1:
			return "Sunday";
		case 2:
			return "Monday";
		case 3:
			return "Tuesday";
		case 4:
			return "Wednesday";
		case 5:
			return "Thursday";
		case 6:
			return "Friday";
		}
		return null;
	}

	public WebElement getDropdownElement(WebElement element, String selector, int row) throws MalformedURLException {
		List<WebElement> elements = getDriver().findElements(By.cssSelector(selector));
		if (row <= getElementSize(selector)) {
			element = elements.get(row);
		}
		return element;
	}

	public int getElementSize(String selector) throws MalformedURLException {
		List<WebElement> elements = getDriver().findElements(By.cssSelector(selector));
		report("Element Size:" + elements.size());
		return elements.size();
	}

	/**
	 * <h2>Gets the month as an integer</h2>
	 * 
	 * @param inputMonth month as a String
	 * @return month as an integer
	 */
	public int getMonthNum(String inputMonth) {
		if (inputMonth.equals("January")) {
			return 0;
		}
		if (inputMonth.equals("February")) {
			return 1;
		}
		if (inputMonth.equals("March")) {
			return 2;
		}
		if (inputMonth.equals("April")) {
			return 3;
		}
		if (inputMonth.equals("May")) {
			return 4;
		}
		if (inputMonth.equals("June")) {
			return 5;
		}
		if (inputMonth.equals("July")) {
			return 6;
		}
		if (inputMonth.equals("August")) {
			return 7;
		}
		if (inputMonth.equals("September")) {
			return 8;
		}
		if (inputMonth.equals("October")) {
			return 9;
		}
		if (inputMonth.equals("November")) {
			return 10;
		}
		if (inputMonth.equals("December")) {
			return 11;
		}
		return -1;
	}

	/**
	 * <h2>Gets the month as a String</h2>
	 * 
	 * @param inputMonth month as a integer
	 * @return month as a String
	 */
	public String getMonth(int inputMonth) {
		switch (inputMonth) {
		case 0:
			return "January";
		case 1:
			return "February";
		case 2:
			return "March";
		case 3:
			return "April";
		case 4:
			return "May";
		case 5:
			return "June";
		case 6:
			return "July";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "October";
		case 10:
			return "November";
		case 11:
			return "December";
		}
		return "";
	}

	protected char getRandomChineseChar(SecureRandom random) {
		CharsetDecoder gbkCharsetDecoder = Charset.forName("GBK").newDecoder();
		ByteBuffer byteBuf = ByteBuffer.allocate(2);
		CharBuffer charBuf = CharBuffer.allocate(2);

		byteBuf.clear();
		charBuf.clear();
		gbkCharsetDecoder.reset();

		// åŒºï¼š16~55
		byte zone = (byte) (random.nextInt(55 - 16 + 1) + 16);
		// ä½�ï¼š01~94
		byte position = (byte) (random.nextInt(94) + 1);
		byteBuf.put((byte) (zone + 0xA0));
		byteBuf.put((byte) (position + 0xA0));
		byteBuf.rewind();
		charBuf.rewind();

		gbkCharsetDecoder.decode(byteBuf, charBuf, true);
		char chineseChar = charBuf.get(0);
		return chineseChar;
	}

	public static String getRandomName() {
		Random random = new Random();
		return names.get(random.nextInt(names.size()));
	}

	public static String getRandomWord() {
		Random random = new Random();
		return words.get(random.nextInt(words.size()));
	}

	/**
	 * <h2>Returns a Chinese character String of a specific length</h2>
	 * 
	 * @param length length of the wanted Chinese character String
	 * @return random Chinese character String with the given length
	 */
	public String getRandomChineseString(int length) {
		int count = length;
		StringBuilder strBuf = new StringBuilder(count);
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}

		strBuf.setLength(0);
		for (int i = 0; i < count; i++) {
			strBuf.append(getRandomChineseChar(random));
		}

		return strBuf.toString();
	}

	public String getText(WebElement element) {
		String text = new String();

		if (element.getTagName().toString().equalsIgnoreCase("body")) {
			text = element.getTagName().toString();
		} else if (element.getText().replaceAll(" ", "").equalsIgnoreCase("")) {
			text = "Element Tag '" + element.getTagName().toString() + "'";
		} else {
			text = element.getText();
		}
		return text;
	}

	public String getText(By by) throws MalformedURLException {
		String text = new String();
		WebElement element = getDriver().findElement(by);

		for (int i = 0; i <= 5; i++)

		{

			try {
				element = getDriver().findElement(by);

				if (element.getTagName().toString().equalsIgnoreCase("body")) {
					text = element.getTagName().toString();
					return text;
				} else if (element.getText().replaceAll(" ", "").equalsIgnoreCase("")) {
					text = "Element Tag '" + element.getTagName().toString() + "'";
					return text;
				} else {
					text = element.getText();
					return text;
				}

			}

			catch (Exception e) {
				report("Stale");
				// getDriver().navigate().refresh();
				System.out.println(e.getMessage());
			}
		}

		return null;

	}

	public void handleTwoTabs(WebElement... element) throws MalformedURLException {
		ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
		getDriver().switchTo().window(tabs.get(1));
		for (WebElement e : element) {
			e.click();
		}
		getDriver().close();
		report("closed second tab");
		getDriver().switchTo().window(tabs.get(0));
	}

	/**
	 * <h2>Initializes a browser session by setting the base URL</h2>
	 * <ul>
	 * <li>Reports the screenshot location</li>
	 * <li>Loads the corresponding home page URL</li>
	 * </ul>
	 * 
	 * @param _locale locale of the base URL (e.g. INIT_US={testEnvironment}.com)
	 * @throws MalformedURLException
	 */

	public boolean isElementPresent(String id) throws MalformedURLException {
		boolean isPresent = getDriver().findElements(By.id(id)).size() > 0;
		if (isPresent) {
			report("Element present:" + getDriver().findElement(By.id(id)).getText() + "=" + isPresent);
		} else {
			report("Element is not present");
		}
		return isPresent;
	}

	public boolean isElementPresentByLink(String linkText) throws MalformedURLException {
		boolean isPresent = getDriver().findElements(By.linkText(linkText)).size() > 0;
		if (isPresent) {
			report("Element present:" + getDriver().findElement(By.linkText(linkText)).getText() + "=" + isPresent);
		} else {
			report("Element is not present");
		}
		return isPresent;
	}

	public boolean isElementPresentByCSS(String selector) throws MalformedURLException {
		boolean isPresent = getDriver().findElements(By.cssSelector(selector)).size() > 0;
		if (isPresent) {
			// report("Element present:" +
			// driver.findElement(By.cssSelector(selector)).getText() + "=" +
			// isPresent);
			report("Element present:" + isPresent);
		} else {
			report("Element is not present");
		}
		return isPresent;
	}

	public boolean isElementPresentByXpath(String xpath) throws MalformedURLException {
		boolean isPresent = getDriver().findElements(By.xpath(xpath)).size() > 0;
		if (isPresent) {
			report("Element present:" + getDriver().findElement(By.xpath(xpath)).getText() + "=" + isPresent);
		} else {
			report("Element is not present");
		}
		return isPresent;
	}

	/**
	 * <h2>Loads a URL independent of the base URL</h2>
	 * <ul>
	 * <li>Loads URL</li>
	 * <li>Waits for next page to load completely</li>
	 * <li>Takes a screenshot, if flag is set</li>
	 * </ul>
	 * 
	 * @param target_URL full URL of the page to be loaded
	 * @throws MalformedURLException
	 */
	public void loadFullURL(String target_URL) throws MalformedURLException {
		report("Loaded URL (" + target_URL + ")");
		getDriver().get(target_URL);
		waitForPageLoaded(getDriver());

	}

	/**
	 * <h2>Navigates back on the current browser by simulating a back button
	 * press</h2>
	 * 
	 * @throws MalformedURLException
	 */
	public void navigateBack() throws MalformedURLException {
		getDriver().navigate().back();
		waitForPageLoaded(getDriver());
	}

	public void openLightboxLink(String linkTextLocator) throws MalformedURLException {
		List<WebElement> links = getDriver().findElements(By.cssSelector("a"));
		for (WebElement link : links) {
			String linkText = link.getText();
			if (linkText.equals(linkTextLocator)) {
				link.click();
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
				}

				return;
			}
		}
	}

	/**
	 * <h2>Reports a successful verification to the test that will show as green in
	 * the report</h2>
	 * 
	 * @param message text to report as a success in the output report
	 */
	public void pass(String message) {
		Reporter.log("<div class=\"row\" style=\"background-color:#44aa44; color:white\">&nbsp" + message + "</div>");
	}

	/**
	 * <h2>Reports a message in the report</h2>
	 * 
	 * @param message text to report in the output report
	 */
	public void report(String message) {

		Reporter.log("<div class=\"row\">" + message + "</div>");

	}

	public void scrollClick(WebElement element) throws MalformedURLException {
		Actions actions = new Actions(getDriver());

		if (!element.getText().equals("")) {
			report("Clicked: " + element.getText());
		}
		try {
			actions.moveToElement(element).click().perform();
		} catch (ElementNotVisibleException enve) {
			// checkForseePopup();
			report("Clicked: " + element.getText());
			actions.moveToElement(element).click().perform();
		}
		waitForPageLoaded(getDriver());
		// if (screenshotsWanted)
		// takeScreenshot();
	}

	public void jsClick(WebElement element) throws MalformedURLException {
		Actions actions = new Actions(getDriver());

		if (!element.getText().equals("")) {
			report("Clicked: " + element.getText());
		}
		try {
			actions.moveToElement(element).click().perform();
		} catch (ElementNotVisibleException enve) {
			// checkForseePopup();
			report("Clicked: " + element.getText());
			actions.moveToElement(element).click().perform();
		}
		waitForPageLoaded(getDriver());
		// if (screenshotsWanted)
		// takeScreenshot();
	}

	public void scrollTo(int x, int y) throws MalformedURLException {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("scroll(" + x + "," + y + ");");
		report("Scrolled to co-ordinate:" + y);
	}

	/**
	 * <h2>Checks a radio button (&lt;input type="radio"&gt;) or check box
	 * (&lt;input type="checkbox"&gt;)</h2>
	 * <ul>
	 * <li>Reports which check box or radio button was clicked</li>
	 * </ul>
	 * 
	 * @param element checkbox or radio element to check
	 */
	public void select(WebElement element) {
		if (!element.isSelected()) {
			String name = element.getAttribute("name");
			String id = element.getAttribute("id");

			if (!name.equals("")) {
				report("Checked: " + StringUtils
						.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(name)), ' '));
			} else if (!id.equals("")) {
				report("Checked: "
						+ StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(id)), ' '));
			} else {
				report("Checked checkbox");
			}

			element.click();
		}
	}

	/*
	 * public void selectDateFromADF(String month, int date, int year) { String
	 * currentMonthLocator = "li[class*='current-month']"; String
	 * nextMonthBtnLocator = "li[class*='next-month'] i";
	 * 
	 * WebElement currentMonth = getDriver().findElement(By.cssSelector
	 * (currentMonthLocator)); WebElement nextMonthBtn = MyThreadLocal.getDriver(
	 * ).findElement(By.cssSelector(nextMonthBtnLocator));
	 * 
	 * while(!currentMonth.getText().equals(month + " " + year)) {
	 * nextMonthBtn.click();
	 * 
	 * try { Thread.sleep(500); } catch (InterruptedException e) {}
	 * 
	 * currentMonth = getDriver().findElement(By.cssSelector(currentMonthLocator ));
	 * nextMonthBtn = getDriver().findElement(By.cssSelector(nextMonthBtnLocator ));
	 * }
	 * 
	 * List<WebElement> availableDates = getDriver().findElements(By
	 * .cssSelector("td[class*='available-date-cell']")); List<WebElement> _d = new
	 * ArrayList<WebElement>(); for(WebElement availableDate : availableDates) {
	 * WebElement _date =
	 * availableDate.findElement(By.cssSelector("p[class*='t-date']")); if
	 * (_date.getText().equals("" + date)) { _d.add(_date); } }
	 * click(_d.get(_d.size() - 1)); return; }
	 */

	public void selectCashAndPoints() throws InterruptedException, MalformedURLException {
		getDriver().findElements(By.cssSelector("label[for*='cash-and-points-trigger']")).get(0).click();

		List<WebElement> numNightElements = getDriver().findElements(By.cssSelector("div.cash-points-carousel ul li"));

		int numNights = 0;

		for (WebElement e : numNightElements) {
			if (!e.getAttribute("class").equals("void-date")) {
				numNights++;
			}
		}

		List<WebElement> pointRadios = new ArrayList<WebElement>();
		List<WebElement> cashRadios = new ArrayList<WebElement>();

		for (int i = 2; i < numNights + 2; i++) {
			pointRadios.add(getDriver().findElement(By.cssSelector("input[name='day" + i + "'][value='p']")));
			cashRadios.add(getDriver().findElement(By.cssSelector("input[name='day" + i + "'][value='c']")));
		}

		for (int i = 0; i < cashRadios.size() - 1; i++) {
			cashRadios.get(i).click();
		}

		String locator = "input[name='" + cashRadios.get(cashRadios.size() - 1).getAttribute("name") + "'][value='c']";

		click(getDriver().findElement(By.cssSelector("a.t-recalc-lnk")));
		numNightElements = getDriver().findElements(By.cssSelector("div.cash-points-carousel ul li"));
		waitUntilElementIsVisible(numNightElements.get(0));

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void selectDate(boolean isCheckOut, Integer... daysFromToday) throws MalformedURLException {
		Calendar now = Calendar.getInstance();
		Calendar wanted = Calendar.getInstance();
		int date = 1;

		if (daysFromToday.length != 0) {
			wanted.add(Calendar.DATE, daysFromToday[0]);
			date = wanted.get(Calendar.DATE);
		}

		SimpleDateFormat monthFormat = new SimpleDateFormat("M");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

		int _currentMonth = Integer.parseInt(monthFormat.format(now.getTime()));
		int _currentYear = Integer.parseInt(yearFormat.format(now.getTime()));

		int _wantedMonth = Integer.parseInt(monthFormat.format(wanted.getTime()));
		int _wantedYear = Integer.parseInt(yearFormat.format(wanted.getTime()));

		int m1 = _currentYear * 12 + _currentMonth;
		int m2 = _wantedYear * 12 + _wantedMonth;

		int numClicks = m2 - m1;

		Calendar last = Calendar.getInstance();
		last.add(Calendar.DATE, 1);
		if ((last.get(Calendar.MONTH) != now.get(Calendar.MONTH)) && isCheckOut) {
			numClicks--;
		}

		for (int i = 0; i < numClicks; i++) {
			WebElement next_month_button = getDriver()
					.findElement(By.cssSelector("a.ui-datepicker-next.ui-corner-all"));
			waitUntilElementIsVisible(next_month_button);
			next_month_button.click();
		}

		if (numClicks == 0) {
			WebElement ui_date_picker = getDriver().findElement(By.cssSelector("table.ui-datepicker-calendar"));
			waitUntilElementIsVisible(ui_date_picker);
		}

		WebElement ui_date_picker = getDriver().findElement(By.cssSelector("table.ui-datepicker-calendar"));
		List<WebElement> ui_dates = ui_date_picker.findElements(By.cssSelector("a.ui-state-default"));
		for (int i = 0; i < ui_dates.size(); i++) {
			WebElement _date = ui_dates.get(i);
			if (Integer.parseInt(_date.getText()) == date) {
				_date.click();
				return;
			}
		}
	}

	public void selectDate(String month, int date, int year) throws MalformedURLException {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, 1);
		Calendar wanted = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();

		wanted.set(Calendar.MONTH, getMonthNum(month));
		wanted.set(Calendar.YEAR, year);

		tomorrow.add(Calendar.DATE, 1);

		SimpleDateFormat monthFormat = new SimpleDateFormat("M");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

		int _currentMonth = Integer.parseInt(monthFormat.format(now.getTime()));
		int _currentYear = Integer.parseInt(yearFormat.format(now.getTime()));

		int _wantedMonth = Integer.parseInt(monthFormat.format(wanted.getTime()));
		int _wantedYear = Integer.parseInt(yearFormat.format(wanted.getTime()));

		int m1 = _currentYear * 12 + _currentMonth;
		int m2 = _wantedYear * 12 + _wantedMonth;

		int numClicks = m2 - m1;

		// if (tomorrow.get(Calendar.MONTH) != now.get(Calendar.MONTH)) {
		// numClicks--;
		// }

		if (numClicks > 0) {
			for (int i = 0; i < numClicks; i++) {
				WebElement next_month_button = getDriver()
						.findElement(By.cssSelector("a.ui-datepicker-next.ui-corner-all"));
				waitUntilElementIsVisible(next_month_button);
				next_month_button.click();
			}
		} else if (numClicks < 0) {
			for (int i = 0; i < numClicks; i++) {
				WebElement prev_month_button = getDriver()
						.findElement(By.cssSelector("a.ui-datepicker-prev.ui-corner-all"));
				waitUntilElementIsVisible(prev_month_button);
				prev_month_button.click();
			}
		}

		if (numClicks == 0) {
			WebElement ui_date_picker = getDriver().findElement(By.cssSelector("table.ui-datepicker-calendar"));
			waitUntilElementIsVisible(ui_date_picker);
		}

		WebElement ui_date_picker = getDriver().findElement(By.cssSelector("table.ui-datepicker-calendar"));
		List<WebElement> ui_dates = ui_date_picker.findElements(By.cssSelector("a.ui-state-default"));
		for (int i = 0; i < ui_dates.size(); i++) {
			WebElement _date = ui_dates.get(i);
			if (Integer.parseInt(_date.getText()) == date) {
				_date.click();
				return;
			}
		}
	}

	/**
	 * <h2>Selects an element from a drop down (&lt;select&gt; tag) by visible
	 * text</h2>
	 * <ul>
	 * <li>Reports which item was selected from the specific drop down</li>
	 * </ul>
	 * 
	 * @param element &lt;select&gt; element from which to choose an option
	 * @param text    visible text of the option to select
	 */
	public void selectFromDropdown(WebElement element, String text) {

		for (int i = 0; i < 5; i++) {
			try {
				report("setting value " + text);
				Select selection = new Select(element);
				selection.selectByVisibleText(text);
				break;
			} catch (StaleElementReferenceException e) {
				report(i + " run");
				waitForSeconds(10);
				e.toString();
				report("Trying to recover from a stale element :" + e.getMessage());

			}
			// report("Selected " + selection.getFirstSelectedOption().getAttribute("text")
			// + " from " + StringUtils.join(
			// StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(element.getAttribute("name"))),
			// ' '));
		}
	}

	/**
	 * <h2>Selects an element from a drop down (&lt;select&gt; tag) by index</h2>
	 * <ul>
	 * <li>Reports which item was selected from the specific drop down</li>
	 * </ul>
	 * 
	 * @param element &lt;select&gt; element from which to choose an option
	 * @param indx    index of the option to select
	 */
	public void selectFromDropdownByIndex(WebElement element, int indx) {
		Select select = new Select(element);
		select.selectByIndex(indx);
		report("Selected " + select.getFirstSelectedOption().getAttribute("text") + " from " + StringUtils.join(
				StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(element.getAttribute("name"))), ' '));
	}

	/**
	 * <h2>Selects an element from a drop down (&lt;select&gt; tag) by value</h2>
	 * <ul>
	 * <li>Reports which item was selected from the specific drop down</li>
	 * </ul>
	 * 
	 * @param element &lt;select&gt; element from which to choose an option
	 * @param value   value attribute of the &lt;option&gt; to select
	 */
	public void selectFromDropdownByValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
		report("Selected " + select.getFirstSelectedOption().getAttribute("text") + " from " + StringUtils.join(
				StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(element.getAttribute("name"))), ' '));
	}

	/**
	 * <h2>Sets a sub location for a tests screenshots</h2> Helpful when tests are
	 * data driven and may overwrite previous screenshots by default
	 * 
	 * @param subLocation sub location to store screenshots under the main test
	 *                    folder
	 */

	/**
	 * <h2>Types text into an input (&lt;input&gt;)</h2>
	 * 
	 * @param element &lt;input&gt; in which to type the desired text
	 * @param text    input text to type into field
	 */
	public void setText(WebElement element, String text) {
		String elementName = "";
		try {
			elementName = element.getAttribute("name");
		} catch (Exception enve) {
			// checkForseePopup();
		}
		element.clear();
		element.sendKeys(text);
		report("Set "
				+ StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(elementName)), ' ')
				+ " = " + text);

		// checkForseePopup();
	}

	public void setText(By by, String text) throws InterruptedException, MalformedURLException {

		WebElement element;
		element = getDriver().findElement(by);

		for (int i = 0; i < 5; i++) {
			try {

				report("Start click");
				click(element);
				report("Start Clear");
				element.clear();
				// element.click();
				report("send keys");

				element.sendKeys(text);
				report("setting value " + text);
				break;
			} catch (StaleElementReferenceException e) {
				report(i + " run");
				waitForSeconds(20);
				e.toString();
				report("Trying to recover from a stale element :" + e.getMessage());

			}
		}
	}

	/**
	 * <h2>Signs into Marriott.com from the sign in page with a Marriott Rewards
	 * member user name and password</h2>
	 * 
	 * @param username   user name or rewards number of Marriott Rewards member
	 * @param password   password for Marriott Rewards member
	 * @param rememberMe if <code>true</code>, selects remember me check box.
	 *                   Unchecks otherwise
	 */

	/**
	 * <h2>Signs out of Marriott.com from the universal header</h2>
	 */
	public void signOut() {
		// HeaderAndFooter header = new HeaderAndFooter();
		// header.signOut();
	}

	/**
	 * <h2>Verifies that a web element is present and displayed on the web page</h2>
	 * <ul>
	 * <li>Reports whether or not the element is present and displayed</li>
	 * </ul>
	 * 
	 * @param element web element that will be verified as present and visible
	 * @return <code>true</code> if element is present and visible,
	 *         <code>false</code> otherwise
	 */
	public boolean verifyElement(WebElement element) {
		if (element.isDisplayed()) {
			report("Element displayed:" + getText(element) + "=" + element.isDisplayed());
		}
		return element.isDisplayed();
	}

	public void verifyTextContains(String actual, String expected) {
		actual = actual.trim();
		expected = expected.trim();

		actual = actual.replaceAll("&nbsp;", "");
		actual = actual.replaceAll("  ", " ");

		expected = expected.replaceAll("&nbsp;", "");
		expected = expected.replaceAll("  ", " ");

		boolean same = actual.contains(expected);
		if (same) {
			// ss("MESSAGE FOUND: " + expected);
		} else {
			// il("MESSAGE NOT FOUND: " + expected, true);
		}
	}

	public void verifyTextContains(WebElement actual, String expected) {
		String _actual = actual.getText().trim();
		expected = expected.trim();

		_actual = _actual.replaceAll("&nbsp;", "");
		_actual = _actual.replaceAll("  ", " ");

		expected = expected.replaceAll("&nbsp;", "");
		expected = expected.replaceAll("  ", " ");

		boolean same = _actual.contains(expected);
		if (same) {
			// ss("MESSAGE FOUND: " + expected);
		} else {
			// ail("MESSAGE NOT FOUND: " + expected, true);
		}
	}

	public void verifyText(String actual, String expected) {
		actual = actual.trim();
		expected = expected.trim();

		actual = actual.replaceAll("&nbsp;", "");
		actual = actual.replaceAll("  ", " ");

		boolean same = actual.equalsIgnoreCase(expected);
		if (same) {
			// pass("ACTUAL: " + actual + ", EXPECTED: " + expected);
		} else {
			// fail("ACTUAL: " + actual + ", EXPECTED: " + expected, true);
		}
	}

	public void verifyText(String[][] text_array) {
		for (String[] text_set : text_array) {
			verifyText(text_set[0], text_set[1]);
		}
	}

	public void scrollToElement(WebElement wElement) throws MalformedURLException {

		JavascriptExecutor jse;
		for (int i = 0; i < 5; i++) {
			try {

				// waitUntilElementIsVisible(wElement);
				jse = (JavascriptExecutor) getDriver();

				jse.executeScript("window.scrollBy(0,250)", "");
				waitForSeconds(3);
				jse.executeScript("arguments[0].scrollIntoView();", wElement);
				break;
			} catch (StaleElementReferenceException e) {
				report(i + " run");
				waitForSeconds(20);
				e.toString();
				report("Trying to recover from a stale element :" + e.getMessage());

			}
		}
	}

	public void scrollToElement(By by) throws MalformedURLException {
		// actions = new Actions(getDriver());
		WebElement element;
		JavascriptExecutor jse;
		for (int i = 0; i <= 5; i++)

		{

			try {

				element = getDriver().findElement(by);
				jse = (JavascriptExecutor) getDriver();

				jse.executeScript("window.scrollBy(0,250)", "");
				waitForSeconds(3);
				jse.executeScript("arguments[0].scrollIntoView();", element);
				break;

			}

			catch (StaleElementReferenceException e) {

				System.out.println(e.getMessage());
			}
		}
	}

	public void verifyText(String[] actual_text, String[] expected_text) {
		if (actual_text.length == expected_text.length) {
			for (int i = 0; i < actual_text.length; i++) {
				verifyText(actual_text[i], expected_text[i]);
			}
		} else {
			// fail("Incorrect number of elements passed in the array", true);
		}
	}

	public void verifyTextMatch(String expMatch, WebElement element) {
		if (element.getText().matches(expMatch)) {
			// pass("ACTUAL:" + element.getText() + ", EXPECTED MATCH:" + expMatch);
		} else {
			// fail("ACTUAL:" + element.getText() + ", EXPECTED MATCH:" + expMatch, true);
		}
	}

	public void verifyTextMatch(String expMatch, String Actual) {
		if (Actual.matches(expMatch)) {
			// pass("ACTUAL:" + Actual + ", EXPECTED MATCH:" + expMatch);
		} else {
			// fail("ACTUAL:" + Actual + ", EXPECTED MATCH:" + expMatch, true);
		}
	}

	public void verifyTitle(String title) throws MalformedURLException {
		report("Page Title:" + getDriver().getTitle());
		verifyText(title, getDriver().getTitle());
	}

	public void waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, 60);
		try {
			wait.until(expectation);
		} catch (Throwable error) {

		}
	}

	public void waitForSeconds(long waitSeconds) {
		waitSeconds = waitSeconds * 1000;
		Calendar currentTime = Calendar.getInstance();
		long currentTimeMillis = currentTime.getTimeInMillis();
		long secCounter = 0;
		while (secCounter < waitSeconds) {
			Calendar newTime = Calendar.getInstance();
			secCounter = (newTime.getTimeInMillis()) - (currentTimeMillis);
		}
	}

	public void waitUntilElementIsVisible(final WebElement element) throws MalformedURLException {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver _driver) {
				return element.isDisplayed();
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(getDriver(), 60);
		try {
			wait.until(expectation);
		} catch (Throwable error) {
		}
	}

	public void waitUntilElementIsEnabled(final WebElement element) throws MalformedURLException {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver _driver) {
				return element.isEnabled();
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(getDriver(), 75);
		try {
			wait.until(expectation);
		} catch (Throwable error) {
		}
	}

	public boolean waitUntilElementIsClickable(WebElement el) {
		try {
			Wait<WebDriver> wait = new WebDriverWait(getDriver(), 75);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void waitUntilDOJOLoading() throws MalformedURLException {

		waitForSeconds(3);
		// WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		// wait.until(ExpectedConditions.invisibilityOfElementLocated(By
		// .cssSelector(".dojoxGridLoading")));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("overlay overlay-loading")));

	}

	public void waitUntilElementNotVisible(final WebElement element) throws MalformedURLException {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver _driver) {
				return !element.isDisplayed();
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(getDriver(), 10L);
		try {
			wait.until(expectation);
		} catch (Throwable error) {
		}
	}

	/**
	 * <h2>Reports a warning message that will show as yellow in the report</h2>
	 * 
	 * @param message text to report as a warning in the output report
	 */
	public void warning(String message) {
		Reporter.log("<div class=\"row\" style=\"background-color:#F7F905; color:black\">&nbsp" + message + "</div>");
	}

	public void scrollClick(By by) throws MalformedURLException {
		// actions = new Actions(getDriver());
		WebElement element;
		for (int i = 0; i <= 5; i++)

		{

			try {

				element = getDriver().findElement(by);
				((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);

				break;

			}

			catch (StaleElementReferenceException e) {

				System.out.println(e.getMessage());
			}
		}

	}

	public void clickElement(WebElement element) throws MalformedURLException {

		// actions = new Actions(getDriver());

		Capabilities cap = ((RemoteWebDriver) getDriver()).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();

		if (browserName.equalsIgnoreCase("internet explorer")) {

			for (int i = 0; i <= 5; i++)

			{

				try {

					((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);

					break;

				}

				catch (StaleElementReferenceException e) {

					System.out.println(e.getMessage());
				}
			}
		}

		else {
			for (int i = 0; i <= 5; i++)

			{

				try {

					element.click();
					break;

				} catch (StaleElementReferenceException e) {

					System.out.println(e.getMessage());
				}
			}
		}

		waitForPageLoaded(getDriver());
		// if (screenshotsWanted)
		// takeScreenshot();
	}

	public void click(By by) throws MalformedURLException

	{
		WebElement element;
		for (int i = 0; i < 5; i++) {
			try {
				element = getDriver().findElement(by);
				element.click();
				break;
			} catch (StaleElementReferenceException e) {
				element = getDriver().findElement(by);
				report(e.getMessage());
			}
		}
	}

	public void scrollElementClick(By by) throws MalformedURLException {

		WebElement element;
		Actions actions = new Actions(getDriver());
		for (int i = 0; i <= 5; i++)

		{

			try {
				element = getDriver().findElement(by);
				// waitUntilElementIsVisible(element);
				((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);

				click(by);

				break;

			}

			catch (StaleElementReferenceException e) {

				System.out.println(e.getMessage());
			}
		}

	}

	public void sendKeys(String Text) throws MalformedURLException {

		Actions actions = new Actions(getDriver());

		try {

			actions.sendKeys(Text);
			actions.sendKeys(Keys.RETURN);
		}

		catch (StaleElementReferenceException e) {

			System.out.println(e.getMessage());
		}

	}

	public void scrollElementClick(WebElement element) throws InterruptedException, MalformedURLException {
		Actions actions = new Actions(getDriver());
		for (int i = 0; i <= 5; i++)

		{

			try {

				((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);

				click(element);

				break;

			}

			catch (StaleElementReferenceException e) {

				System.out.println(e.getMessage());
			}
		}

	}

	public String getAttribute(By by, String attr) throws MalformedURLException {

		String sAttr = null;
		WebElement element;

		boolean status = true;
		while (status) {
			try {

				element = getDriver().findElement(by);
				waitUntilElementIsVisible(element);
				// report("reading value");
				sAttr = element.getAttribute(attr);
				// report("reading valuee");
				status = false;
				break;
			} catch (StaleElementReferenceException e) {
				report(" test");
			}

		}

		return sAttr;
	}

	/*
	 * public static Connection openOracleConnection() throws SQLException,
	 * ClassNotFoundException {
	 * 
	 * String url = rb.getString("connectionString"); Properties props = new
	 * Properties(); props.setProperty("user", rb.getString("dUID"));
	 * props.setProperty("password", rb.getString("dPWD"));
	 * 
	 * Connection conn = DriverManager.getConnection(url, props);
	 * 
	 * return (conn);
	 * 
	 * }
	 */

	public static String getDataFromDB(String statement, Connection connection)
			throws SQLException, ClassNotFoundException {

		// String sql ="select * from HPPDBO.Hotel where HotelID='98531'";

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = connection.prepareStatement(statement);

		ResultSet result = preStatement.executeQuery();

		while (result.next()) {
			return (result.getString(1));
		}
		return ("");

	}

	public List<String> GetMultipleValuesInDB(String statement, Connection connection) {

		String sTextr = null;
		List<String> lList = new ArrayList<String>();

		try {
			PreparedStatement preStatement = connection.prepareStatement(statement);

			ResultSet rs = preStatement.executeQuery();

			while (rs.next()) {

				sTextr = rs.getString(1);
				lList.add(sTextr);
				System.out.println(lList);
			}
			System.out.println(lList);
			// step5 close the connection object

		} catch (Exception e) {
			System.out.println(e);
		}
		return lList;
	}

	public List<Integer> ConvertListToInteger(List<String> list) {

		List<Integer> lstInt = new ArrayList<Integer>();

		for (int i = 0; i < list.size(); i++) {
			lstInt.add(Integer.parseInt(list.get(i)));
		}

		return lstInt;

	}

	public Set convertListToSet(List list) {
		// create an empty set
		Set set = new HashSet<>();

		// Add each element of list into the set
		for (int i = 0; i < list.size(); i++)
			set.add(list.get(i));

		// return the set
		return set;
	}

	public List<Double> ConvertListToDouble(List<Integer> list) {

		List<Double> lstInt = new ArrayList<Double>();

		for (int i = 0; i < list.size(); i++) {

			double temp = list.get(i);
			lstInt.add(temp);
		}

		return lstInt;

	}

	public static String readjsonData(String path)
			throws IOException, org.json.simple.parser.ParseException, ParseException {

		JSONParser jsonParser = new JSONParser();
		JSONArray sJsonData = null;
		try (FileReader reader = new FileReader(path)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			sJsonData = (JSONArray) obj;
			System.out.println(sJsonData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sJsonData.toString();

	}

	public static String generateStringFromResource(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));

	}

	public String getAlphabetSeq(int n) {
		char[] buf = new char[(int) floor(log(25 * (n + 1)) / log(26))];
		for (int i = buf.length - 1; i >= 0; i--) {
			n--;
			buf[i] = (char) ('A' + n % 26);
			n /= 26;
		}

		return (new String(buf));
	}

	public String TakeScreenshot() throws IOException {
		TakesScreenshot ts = (TakesScreenshot) getDriver();
		File source = ts.getScreenshotAs(OutputType.FILE);
		TestContext testContext = new TestContext();
		String sScenario = testContext.scenarioContext.getContext(Context.Scenario_Name).toString();

		String dest = System.getProperty("user.dir") + "/src/test/java/configs/ErrorScreenshots/" + sScenario + ".png";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);

		return dest;
	}

}
