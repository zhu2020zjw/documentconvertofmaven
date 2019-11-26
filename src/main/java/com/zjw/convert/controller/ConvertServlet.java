package com.zjw.convert.controller;

import java.io.File;
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
	private static String path; //�ļ��ϴ��ĵ�ַ
	private static String suffix;//����ϴ��ļ��ĺ�׺��
	private DocumentConvert documentConvert = new DocumentConvert();//�ĵ�ת����service
	
	private static Properties properties;
	
	static{
		properties = new Properties();
		try {
			properties.load(DownLoadServlet.class.getClassLoader().getResourceAsStream("path.properties"));
		} catch (Exception e) {
			try {
				throw new NoPropertiesException("û���ⲿ�ļ������ߵ������");
			} catch (NoPropertiesException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	//ͨ�����������Ӧ�ķ���
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		path = UpLoadServlet.targetPath + File.separator + request.getParameter("uploadPath");
		System.out.println(path);
		suffix = path.split("\\.")[path.split("\\.").length-1];
		if(path.equals(null)) {
			try {
				throw new PathException("�ļ�δ�ϴ��������ϴ��ļ�");
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
	
	//����Ԥ��
	protected void previewOfSD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = PropertiesInitUtil.properties.getProperty("targetName") +  "targetOfSD/" + request.getParameter("uploadPath").split("\\.")[0];
		File file = new File(fileName);
		if(!file.exists()) {
			file.mkdir();
		}
		
		String result = documentConvert.convertBySuffix(suffix, path, fileName + "/targetOfSD.html",false);
		
		String targetFileName = "http://" + ip + ":8080/Demo/targetOfSD/" + request.getParameter("uploadPath").split("\\.")[0] + File.separator + "targetOfSD.html";//���ڽ�����ʾ�ĵ�ַ
		System.out.println(targetFileName+"..");
		if(result != null) {
			request.setAttribute("targetFileName",targetFileName);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}else {
			request.setAttribute("message", "����Ԥ�����ִ���");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	//����Ԥ��
	protected void previewOfHD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = PropertiesInitUtil.properties.getProperty("targetName") +  "targetOfHD/" + request.getParameter("uploadPath").split("\\.")[0];
		File file = new File(fileName);
		if(!file.exists()) {
			file.mkdir();
		}
		String result = documentConvert.convertBySuffix(suffix, path, fileName + "/targetOfHD.html",true);
		String targetFileName = "http://" + ip + ":8080/Demo/targetOfHD/" + request.getParameter("uploadPath").split("\\.")[0] + File.separator + "targetOfHD.html";
		if(result != null) {
			request.setAttribute("targetFileNameOfHD",targetFileName);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}else {
			request.setAttribute("message", "����Ԥ�����ִ���");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	protected void convert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetOfSuffix = request.getParameter("suffix");//���Ҫת�����ļ��ĺ�׺��
		String name = request.getParameter("uploadPath");
		String fileName = PropertiesInitUtil.properties.getProperty("targetName");
		
		String targetFileName = null;
		if(targetOfSuffix.equals("html")) {
			targetFileName = fileName + "targetOfSD\\" + request.getParameter("uploadPath").split("\\.")[0] + "\\targetOfSD" + "\\targetOfSD." + targetOfSuffix;
			System.out.println(targetFileName+"........***");
		}else {
			targetFileName = fileName + request.getParameter("uploadPath").split("\\.")[0] + "\\target." + targetOfSuffix;
		}
		try {
			String result = documentConvert.convertBySuffix(suffix, targetOfSuffix, path, targetFileName);
			if(targetOfSuffix.equals("html")) {
				targetFileName = "http://" + ip + ":8080/Demo/targetOfSD/" + request.getParameter("uploadPath").split("\\.")[0] + "/targetOfSD." + targetOfSuffix;
			}else {
				targetFileName = "http://" + ip + ":8080/Demo/" + request.getParameter("uploadPath").split("\\.")[0] + "/target." + targetOfSuffix;
			}
			if(result != null) {
				request.setAttribute("convertIP",targetFileName);
				request.getSession().setAttribute("name", name);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}else {
				request.setAttribute("message", "ת�����ִ���");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		} catch (SuffixException e) {
			e.printStackTrace();
		}
	}
}
