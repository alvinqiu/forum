package com.forum.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.forum.utility.Constants;
import com.forum.vo.UserVO;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final String[] IGNORE_URI = { "getAllPost", "getAllModule",
			"checkLogin" };

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		String url = request.getRequestURL().toString();
		for (String s : IGNORE_URI) {
			if (url.contains(s)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			HttpSession session = request.getSession();
			UserVO userVO = (UserVO) session
					.getAttribute(Constants.LOGINED_USER);
			if (userVO != null) {
				//request.getRequestDispatcher("/login.html").forward(request, response);
				//response.sendRedirect("/login.html");
				flag = true;
			}else{
				String path = request.getContextPath();
				response.sendRedirect(path+"/login.html");
				//response.sendRedirect("/login.html");
				return true;
			}
		}
		
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
