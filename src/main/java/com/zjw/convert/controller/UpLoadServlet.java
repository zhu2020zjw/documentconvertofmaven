package com.zjw.convert.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zjw.convert.util.PropertiesInitUtil;

//@MultipartConfig
public class UpLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//�ļ��ϴ������ļ��ĵ�ַ
	public static String targetPath = null;

	// �ϴ�����
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 8; // 8MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ����Ƿ�Ϊ��ý���ϴ�
		if (!ServletFileUpload.isMultipartContent(request)) {
			// ���������ֹͣ
			PrintWriter writer = response.getWriter();
			writer.println("Error: ��������� enctype=multipart/form-data");
			writer.flush();
			return;
		}
		// �����ϴ�����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// ������ʱ�洢Ŀ¼
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// ��������ļ��ϴ�ֵ
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// �����������ֵ (�����ļ��ͱ�����)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// ���Ĵ���
		upload.setHeaderEncoding("UTF-8");

		// ������ʱ·�����洢�ϴ����ļ�
		// ���·����Ե�ǰӦ�õ�Ŀ¼
		String uploadPath = PropertiesInitUtil.properties.getProperty("uploadPath");
		System.out.println(uploadPath);
		
		// ���Ŀ¼�������򴴽�
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		try {
			// ���������������ȡ�ļ�����
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// ����������
				for (FileItem item : formItems) {
					// �����ڱ��е��ֶ�
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						String filePath = uploadPath + File.separator + fileName;
						System.out.println(filePath);
						File storeFile = new File(filePath);
						// �����ļ���Ӳ��
						item.write(storeFile);
						targetPath = uploadPath;
						request.getSession().setAttribute("path", fileName);
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("�ļ��ϴ�ʧ�ܣ�����");
			ex.printStackTrace();
		}
		// ��ת�� index.jsp
		request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
}