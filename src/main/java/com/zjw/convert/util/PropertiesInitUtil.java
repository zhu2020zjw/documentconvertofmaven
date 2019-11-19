package com.zjw.convert.util;

import java.util.Properties;

import com.zjw.convert.controller.DownLoadServlet;
import com.zjw.convert.exception.NoPropertiesException;

public class PropertiesInitUtil {
	public static Properties properties;//初始化properties
	
	static{
		properties = new Properties();
		try {
			properties.load(DownLoadServlet.class.getClassLoader().getResourceAsStream("path.properties"));
		} catch (Exception e) {
			try {
				throw new NoPropertiesException("没有外部文件，或者导入错误");
			} catch (NoPropertiesException e1) {
				e1.printStackTrace();
			}
		}
	}
}
