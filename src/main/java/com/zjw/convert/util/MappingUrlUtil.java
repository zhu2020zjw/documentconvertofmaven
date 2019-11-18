package com.zjw.convert.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * 	地址映射工具类
 * @author Administrator
 *
 */
public class MappingUrlUtil {
	
	public static void MappingUrl(String sourceUrl, HttpServletResponse response) {
		File file = new File(sourceUrl);
		String fileName = file.getName();
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("application/octet-stream;charset=UTF-8");
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
			bis = new BufferedInputStream(new FileInputStream(file));
        	bos = new BufferedOutputStream(response.getOutputStream());
        	
        	byte[] buffer = new byte[1024];
        	int len;
        	while ((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
