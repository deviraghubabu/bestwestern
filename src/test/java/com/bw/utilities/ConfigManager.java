package com.bw.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author E002253
 *
 */
public class ConfigManager {
	private static Properties config = null;

	/**
	 * 
	 * @throws IOException
	 */
	public static synchronized void loadConfig() throws IOException {
		if (System.getProperty("azureRun").equalsIgnoreCase("true")) {
			Constants.configFolderPath = "config/";
		}
		config = new Properties();
		String fileName;
		fileName = Constants.configFolderPath.concat("config.properties");
		Properties properties = new Properties();
		FileReader fileReader = new FileReader(new File(fileName).getAbsolutePath());
		properties.load(fileReader);
		String environment = properties.getProperty("environment");
		String browser = properties.getProperty("browser");
		fileName = Constants.configFolderPath.concat(environment).concat(".properties");
		config.load(new FileReader(new File(fileName).getAbsolutePath()));
		config.setProperty("browser", browser);
	}

	/**
	 * 
	 * @param property
	 * @return
	 */

	public static synchronized String getProperty(String property) {
		try {
			if (config == null)
				loadConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config.getProperty(property);
	}
}
