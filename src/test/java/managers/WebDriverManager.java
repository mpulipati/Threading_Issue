package managers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

public class WebDriverManager {
	// private static WebDriver driver;
	// private static DriverType driverType;
	// private static String Browser = null;;
	public static String Node;
	private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	private static final String FireFox_DRIVER_PROPERTY = "webdriver.gecko.driver";
	private static final String IE_DRIVER_PROPERTY = "webdriver.ie.driver";
	protected static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<>();

	public WebDriverManager() {
		// driverType = FileReaderManager.getInstance().getCofig_Reader().getBrowser();
		// Browser = "firefox";
		Node = "http://op2-op-oss1:4445/wd/hub";
		System.out.println(Node);
	}

	public static WebDriver getDriver() throws MalformedURLException {
		if (dr.get() == null) {
			String Browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
					.getParameter("browser");
			// String Browser = "chrome";
			createLocalDriver(Browser);
		}
		return dr.get();
	}

	public static void terminateDriver() {
		dr.remove();
	}

	private static void createLocalDriver(String sBrowser) throws MalformedURLException {
		System.out.println(sBrowser);
		RemoteWebDriver driver = null;

		switch (sBrowser.toUpperCase()) {
		case "FIREFOX":
			System.out.println("The thread ID for Firefox is " + Thread.currentThread().getId());

			String os = System.getProperty("os.name").toLowerCase();

			if (os.contains("win")) {
				System.out
						.println(FileReaderManager.getInstance().getCofig_Reader().getDriverPath() + "geckodriver.exe");
				System.setProperty(FireFox_DRIVER_PROPERTY,
						FileReaderManager.getInstance().getCofig_Reader().getDriverPath() + "geckodriver.exe");
			} else if (os.contains("linux")) {
				System.out.println("linux");
				System.setProperty(FireFox_DRIVER_PROPERTY,
						FileReaderManager.getInstance().getCofig_Reader().getDriverPath() + "geckodriver");
			}

			/*
			 * DesiredCapabilities cap = DesiredCapabilities.firefox();
			 * cap.setPlatform(Platform.LINUX); FirefoxOptions fOptions = new
			 * FirefoxOptions();
			 * 
			 * System.out.println(Node); driver = new RemoteWebDriver(new URL(Node), cap);
			 */
			driver = new FirefoxDriver();

			setWebDriver(driver);
			// driver.set(new FirefoxDriver());

			getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			getDriver().get(FileReaderManager.getProperties().getString("url"));
			break;
		case "CHROME":

			String oSystem = System.getProperty("os.name").toLowerCase();

			if (oSystem.contains("win")) {
				System.setProperty(CHROME_DRIVER_PROPERTY,
						FileReaderManager.getInstance().getCofig_Reader().getDriverPath() + "chromedriver.exe");
			} else if (oSystem.contains("linux")) {
				System.out.println("The thread ID for chrome is " + Thread.currentThread().getId());
				System.setProperty(CHROME_DRIVER_PROPERTY,
						FileReaderManager.getInstance().getCofig_Reader().getDriverPath() + "chromedriver");
			}

			// driver = new ChromeDriver();
			// driver.get(FileReaderManager.getProperties().getString("url"));
			/*
			 * DesiredCapabilities Chrocap = DesiredCapabilities.chrome(); ChromeOptions
			 * Options = new ChromeOptions(); Chrocap.setCapability("platform", "LINUX"); //
			 * Options.setCapability("version", "84");
			 * 
			 * Options.merge(Chrocap);
			 * 
			 * System.out.println(Node); driver = new RemoteWebDriver(new URL(Node),
			 * Options);
			 */
			driver = new ChromeDriver();

			setWebDriver(driver);
			getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			getDriver().get(FileReaderManager.getProperties().getString("url"));
//			driver.set(new RemoteWebDriver(new URL(Node), Options));
//
//			// driver = new FirefoxDriver();
//			driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			driver.get().get(FileReaderManager.getProperties().getString("url"));
			break;
		case "INTERNETEXPLORER":

			System.setProperty(IE_DRIVER_PROPERTY,
					FileReaderManager.getInstance().getCofig_Reader().getDriverPath() + "IEDriverServer.exe");
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability("nativeEvents", false);
			// ieCapabilities.setCapability("ignoreZoomSetting", true);
			ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
			ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
			ieCapabilities.setCapability("disable-popup-blocking", true);
			ieCapabilities.setCapability("enablePersistentHover", true);
			System.out.println("connecting ie in " + Node);
			driver = new RemoteWebDriver(new URL(Node), ieCapabilities);
			setWebDriver(driver);
			getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			getDriver().get(FileReaderManager.getProperties().getString("url"));
			break;
		}

		if (FileReaderManager.getInstance().getCofig_Reader().getBrowserWindowSize())
			getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(
				FileReaderManager.getInstance().getCofig_Reader().getImplicitlyWait(), TimeUnit.SECONDS);
		// return driver.get();
	}

	public void closeDriver() throws MalformedURLException {
		// getDriver().close();

		System.out.println("The thread ID for " + Thread.currentThread().getId());
		System.out.println("browser closed 1");

		getDriver().quit();
		System.out.println("browser closed 2");

		dr.set(null);
		System.out.println("browser closed 3");
		System.out.println("The thread ID for " + Thread.currentThread().getId());
	}

	public static void setWebDriver(RemoteWebDriver driver) {
		dr.set(driver);
	}

}