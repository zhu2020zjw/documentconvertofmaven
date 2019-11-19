package com.zjw.convert.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zjw.convert.util.PropertiesInitUtil;

public class DownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathOfPropertie = PropertiesInitUtil.properties.getProperty("targetName");
		System.out.println(request.getParameter("path"));
		String[] paths = request.getParameter("path").split("//");
		String path = (paths[1].split("/"))[paths[1].split("/").length-1];
		File f = new File(pathOfPropertie + path);
		System.out.println(pathOfPropertie + path);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			System.out.println("文件不存在");
			return;
		}
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;

		response.reset(); // 非常重要
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
		OutputStream out = response.getOutputStream();
		
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		br.close();
		out.close();
	}

}
