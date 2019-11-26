package com.zjw.convert.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zjw.convert.exception.NoFileException;
import com.zjw.convert.util.CompressUtils;
import com.zjw.convert.util.PropertiesInitUtil;

public class DownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String uploadPath = request.getParameter("uploadPath");
		String pathOfPropertie = PropertiesInitUtil.properties.getProperty("targetName") + uploadPath.split("\\.")[0];
		File file = new File(uploadPath);
		if(!file.exists()) {
			file.mkdir();
		}
		
		System.out.println(request.getParameter("link")+"+++++");
		
		String[] paths = request.getParameter("link").split("//");
		String path = (paths[1].split("/"))[paths[1].split("/").length-1];
		
		//用于下载html格式
		if(Arrays.asList(path.split("\\.")).contains("html")) {
			downLoadHtml(path,request,response);
			return;
		}
		
		File f = new File(pathOfPropertie + File.separator + path);
		System.out.println(pathOfPropertie + File.separator + path + "--------");
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}
		
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;

		response.reset(); // 非常重要
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
		OutputStream out = response.getOutputStream();
		
		while ((len = br.read(buf)) > 0) {
			out.write(buf, 0, len);
			out.flush();
		}
		br.close();
		out.close();
	}

	public void downLoadHtml(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//html文件的绝对路径和与其对应的.files文件的绝对路径
		String pathParent = PropertiesInitUtil.properties.getProperty("targetName") + path.split("\\.")[0] + File.separator + request.getParameter("uploadPath").split("\\.")[0] + File.separator + path.split("\\.")[0];
		File fileParent = new File(pathParent);
		if(!fileParent.exists()) {
			fileParent.mkdir();
		}
		String filePath = pathParent + "\\" + path;
		System.out.println(filePath+"***");
		String dirPath = pathParent +"\\" + path.split("\\.")[0] + ".files";
		
		File file = new File(filePath);
		File dirFile = new File(dirPath);
		
		if((!file.exists()) || (!dirFile.isDirectory())) {
			try {
				throw new NoFileException("文件或者文件夹不存在");
			} catch (NoFileException e) {
				e.printStackTrace();
			}
		}
		
		//压缩后的文件的绝对路径
		String zipPathDir = PropertiesInitUtil.properties.getProperty("targetName") + request.getParameter("uploadPath");
		File file2 = new File(zipPathDir);
		if(!file2.exists()) {
			file2.mkdir();
		}
		String zipPath = zipPathDir + "/downHtml.zip";
		CompressUtils.zip(pathParent, zipPath);
		
		File zipFile = new File(zipPath);
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(zipFile));
		byte[] buf = new byte[1024];
		int len = 0;

		response.reset(); // 非常重要
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + zipFile.getName());
		OutputStream out = response.getOutputStream();
		
		while ((len = br.read(buf)) > 0) {
			out.write(buf, 0, len);
			out.flush();
		}
		br.close();
		out.close();
	}
}
