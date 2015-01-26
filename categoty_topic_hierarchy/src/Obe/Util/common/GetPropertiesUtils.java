package Obe.Util.common;

import java.io.File;
import java.util.Date;

/**
 * get dir utils
 * 
 * @author bing
 * 
 */
public class GetPropertiesUtils {
	/**
	 * files dir
	 */
	private static String CONFIG_FILENAME = ConstantUtils.CONFIG;

	/**
	 * attribute name
	 */
	private static String INPUTD = ConstantUtils.INPUTD;
	private static String OUTPUTD = ConstantUtils.OUTPUTD;


	/**
	 * find input dir
	 * 
	 * @return
	 */
	public static String getInputDir() {
		String tempdir = PropertiesUtils.getPropertiesValue(INPUTD,
				CONFIG_FILENAME);

		File file = new File(tempdir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return tempdir;
	}
	
	public static String getoutPutDir() {
		String tempdir = PropertiesUtils.getPropertiesValue(OUTPUTD,
				CONFIG_FILENAME);

		File file = new File(tempdir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return tempdir;
	}
	
}
