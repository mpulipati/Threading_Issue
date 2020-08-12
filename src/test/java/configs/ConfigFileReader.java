package configs;

import java.util.ResourceBundle;

import enums.DriverType;

public class ConfigFileReader {
// private Properties properties;
// private final String propertyFilePath= "configs//Configuration.properties";
	public static ResourceBundle rb;

	public ConfigFileReader() {
		try {
			rb = ResourceBundle.getBundle("configs.Configuration");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("configs.Configuration not found");
		}
	}

	public String getDriverPath() {
		String driverPath = rb.getString("driverPath");
		if (driverPath != null)
			return driverPath;
		else
			throw new RuntimeException(
					"Driver Path not specified in the Configuration.properties file for the Key:driverPath");
	}

	public long getImplicitlyWait() {
		String implicitlyWait = rb.getString("implicitlyWait");
		if (implicitlyWait != null) {
			try {
				return Long.parseLong(implicitlyWait);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Not able to parse value : " + implicitlyWait + " in to Long");
			}
		}
		return 30;
	}

	public String getApplicationUrl() {
		String url = rb.getString("url");
		if (url != null)
			return url;
		else
			throw new RuntimeException(
					"Application Url not specified in the Configuration.properties file for the Key:url");
	}

	public DriverType getBrowser() {
		String browserName = rb.getString("browser");
		if (browserName == null || browserName.equals("chrome"))
			return DriverType.CHROME;
		else if (browserName.equalsIgnoreCase("firefox"))
			return DriverType.FIREFOX;
		else if (browserName.equals("iexplorer"))
			return DriverType.INTERNETEXPLORER;
		else
			throw new RuntimeException(
					"Browser Name Key value in Configuration.properties is not matched : " + browserName);
	}

	public Boolean getBrowserWindowSize() {
		String windowSize = rb.getString("windowMaximize");
		if (windowSize != null)
			return Boolean.valueOf(windowSize);
		return true;
	}

	@SuppressWarnings("unused")
	public String getReportConfigPath() {
		String reportConfigPath = System.getProperty("user.dir") + "/src/test/java/configs/extent-config.xml";
		if (reportConfigPath != null)
			return reportConfigPath;
		else
			throw new RuntimeException(
					"Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
	}
}
