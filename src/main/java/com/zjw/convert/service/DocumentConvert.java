package com.zjw.convert.service;

import com.zjw.convert.exception.SuffixException;
import com.zjw.convert.util.ConvertorPool;

import application.dcs.Convert;

public class DocumentConvert {

	private static ConvertorPool instance;
	
	static{
		instance = ConvertorPool.getInstance();
	}
	
	//MS转HTML标清预览
	public String MSToHtml(String sourceFileName,String targetFileName) {
		Convert convert = instance.getConvert();
		try {
			//convert.insertTextWaterMark("我是zjw", "宋体", 30, Color.gray, true, true, true);
			int result = convert.convertMStoHTML(sourceFileName, targetFileName);
			System.out.println(result);
			if(result == 0) {
				return targetFileName;
			}
		} catch (Exception e) {
			System.out.println("转换出现异常");
		} finally {
			instance.returnConvert(convert);
		}
		return null;
	}
	
	//MS转HTML高清预览
	public String MSToHtmlOfHD(String sourceFileName,String targetFileName) {
		Convert convert = instance.getConvert();
		try {
			int result = convert.convertMStoHtmlOfSvg(sourceFileName, targetFileName);
			System.out.println(result);
			if(result == 0) {
				return targetFileName;
			}
		} catch (Exception e) {
			System.out.println("转换出现异常");
		} finally {
			instance.returnConvert(convert);
		}
		return null;
	}
	
	//MS转PDF
	public String MSToPDF(String sourceFileName,String targetFileName) {
		Convert convert = instance.getConvert();
		try {
			int result = convert.convertMStoPDF(sourceFileName, targetFileName);
			System.out.println(result);
			if(result == 0) {
				return targetFileName;
			}
		} catch (Exception e) {
			System.out.println("转换出现异常");
		} finally {
			instance.returnConvert(convert);
		}
		return null;
	}
	
	//PDF转HTML
	public String pdfToHtml(String sourceFileName,String targetFileName) {
		Convert convert = instance.getConvert();
		try {
			int result = convert.convertPdfToHtml(sourceFileName, targetFileName);
			System.out.println(result);
			if(result == 0) {
				return targetFileName;
			}
		} catch (Exception e) {
			System.out.println("转换出现异常");
		} finally {
			instance.returnConvert(convert);
		}
		return null;
	}
	
	//通过上传文件的后缀名，调用相应的方法，进行预览
	public String convertBySuffix(String suffix,String sourceFileName,String targetFileName,boolean isHD) {
		String result = null;
		switch (suffix) {
		case "doc":
		case "docx":
			if(isHD) {
				result = MSToHtmlOfHD(sourceFileName, targetFileName);
			}else {
				result = MSToHtml(sourceFileName, targetFileName);
			}
			break;
		case "pdf":
			result = pdfToHtml(sourceFileName, targetFileName);
			break;
		default:
			break;
		}
		return result;
	}
	
	//通过上传文件的后缀名，以及要转换的文件的后缀名，调用相应的方法，进行转换
	public String convertBySuffix(String sourceSuffix,String targetSuffix,String sourceFileName,String targetFileName) throws SuffixException {
		String result = null;
		if((sourceSuffix.equals("doc")||(sourceSuffix.equals("docx"))) && targetSuffix.equals("html")) {
			result = MSToHtml(sourceFileName, targetFileName);
		}else if((sourceSuffix.equals("doc")||(sourceSuffix.equals("docx"))) && targetSuffix.equals("pdf")) {
			result = MSToPDF(sourceFileName, targetFileName);
		}else if(sourceSuffix.equals("pdf") && targetSuffix.equals("html")) {
			result = pdfToHtml(sourceFileName, targetFileName);
		}else {
			throw new SuffixException("后缀名错误");
		}
		return result;
	}
}
