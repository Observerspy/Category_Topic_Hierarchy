package Obe.Util.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * read properties 
 * 
 * @author bing
 * 
 */
public class PropertiesUtils {
	public static String CLASS_DIR = PropertiesUtils.class.getResource("/")
			.getFile();

	/**
	 * @function: deal with path
	 * @return
	 */
	public static String processPath(String dir) {
		return dir.replaceAll("%20", " ");
	}

	/**
	 * @function: get files attributes
	 * @param args
	 * @return
	 */
	public static String getPropertiesValue(String args, String pfilename) {
		Properties propertie = new Properties();
		String configpath = "D:/study/crawl/crawl/" + pfilename;
		String configdir = processPath(configpath);

		String value = "";
		try {
			FileInputStream inputFile = new FileInputStream(configdir);
			propertie.load(inputFile);

			if (propertie.containsKey(args)) {
				value = propertie.getProperty(args).trim();
			}
			inputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * read properties
	 * 
	 * @param configpath
	 * @return
	 */
	public static Map<String, String> getPropertiesMap(String configpath) {
		Map<String, String> propertiesMap = new HashMap<String, String>();
		Properties properties = new Properties();
		String configdir = processPath(configpath);

		try {
			FileInputStream inputFile = new FileInputStream(configdir);
			properties.load(inputFile);

			Set<Object> keys = properties.keySet();
			for (Object key : keys) {
				propertiesMap.put(key.toString(), properties.get(key)
						.toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertiesMap;
	}
}
