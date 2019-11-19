package com.zjw.convert.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zjw.convert.bean.Computer;
import com.zjw.convert.exception.NoPropertiesException;
import com.zjw.convert.exception.PathException;
import com.zjw.convert.exception.SuffixException;
import com.zjw.convert.service.DocumentConvert;
import com.zjw.convert.util.PropertiesInitUtil;

public class ConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String ip = Computer.getIp();
	private static String path; //文件上传的地址
	private static String suffix;//获得上传文件的后缀名
	private DocumentConvert documentConvert = new DocumentConvert();//文档转换的service
	
	private static Properties properties;
	
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	//通过反射调用相应的方法
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		path = UpLoadServlet.targetPath;
		suffix = path.split("\\.")[path.split("\\.").length-1];
		if(path.equals(null)) {
			try {
				throw new PathException("文件未上传，请先上传文件");
			} catch (PathException e) {
				e.printStackTrace();
			}
		}
	
		String methodString = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodString, HttpServletRequest.class,HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//标清预览
	protected void previewOfSD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetFileName = PropertiesInitUtil.properties.getProperty("targetName") + "targetOfSD.html";//用于做转换的目的地址
		String result = documentConvert.convertBySuffix(suffix, path, targetFileName);
		targetFileName = "http://" + ip + ":8080/Demo/targetOfSD.html";//用于进行显示的地址
		if(result != null) {
			request.setAttribute("targetFileName",targetFileName);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		return;
	}
	
	//高清预览
	protected void previewOfHD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetFileName = PropertiesInitUtil.properties.getProperty("targetName") + "targetOfHD.html";
		String result = documentConvert.convertBySuffix(suffix, path, targetFileName);
		targetFileName = "http://" + ip + ":8080/Demo/targetOfHD.html";
		if(result != null) {
			request.setAttribute("targetFileNameOfHD",targetFileName);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		return;
	}
	
	protected void convert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetOfSuffix = request.getParameter("suffix");//获得要转换的文件的后缀名
		String targetFileName = PropertiesInitUtil.properties.getProperty("targetName") + "target." + targetOfSuffix;
		try {
			String result = documentConvert.convertBySuffix(suffix, targetOfSuffix, path, targetFileName);
			targetFileName = "http://" + ip + ":8080/Demo/target." + targetOfSuffix;
			if(result != null) {
				request.setAttribute("convertIP",targetFileName);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		} catch (SuffixException e) {
			e.printStackTrace();
		}
	}
}
