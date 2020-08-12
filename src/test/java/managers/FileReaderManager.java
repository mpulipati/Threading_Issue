package managers;

import java.util.ResourceBundle;

import configs.ConfigFileReader;
import configs.Xls_Reader;

public class FileReaderManager {

	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static Xls_Reader xls_Reader;
	private static ConfigFileReader configFileReader;
	public static ResourceBundle rb;
	String rPath = System.getProperty("user.dir") + "/src/test/java/configs/Scenarios.xlsx";

	public static FileReaderManager getInstance() {
		return fileReaderManager;
	}

	public static ResourceBundle getProperties() {
		rb = ResourceBundle.getBundle("configs.Configuration");
		return rb;
	}

	public ConfigFileReader getCofig_Reader() {
		return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
	}

	public Xls_Reader getXls_Reader() {
		return (xls_Reader == null) ? new Xls_Reader(rPath) : xls_Reader;
	}
}