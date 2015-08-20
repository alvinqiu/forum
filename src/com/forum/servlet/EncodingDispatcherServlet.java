package com.forum.servlet;

import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodingDispatcherServlet extends DispatcherServlet {
	private static final long serialVersionUID = -4532965622576671360L;
	
	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		super.doService(request, response);
	}
}
