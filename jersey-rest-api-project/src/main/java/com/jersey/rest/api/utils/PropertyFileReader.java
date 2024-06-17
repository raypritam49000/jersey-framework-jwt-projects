package com.jersey.rest.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

	public static String getPropertyValue(String key) {
		Properties prop = new Properties();
		try {
			InputStream in = PropertyFileReader.class.getClassLoader().getResourceAsStream("application.properties");
			prop.load(in);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop.getProperty(key);
	}
}