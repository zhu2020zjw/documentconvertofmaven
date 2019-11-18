package com.zjw.convert.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zjw.convert.util.MappingUrlUtil;

public class MappingFilter extends HttpFilter{

	/**
	 *	µÿ÷∑”≥…‰
	 */
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String path = "E:\\output\\target.html";
		MappingUrlUtil.MappingUrl(path, response);
		filterChain.doFilter(request, response);
	}
}