package com.forum.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginOut {

	/**
	 * 退出登录
	 * @return true
	 */
	@RequestMapping("/loginOut.json")
	@ResponseBody
	public String LoginOut(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.LOGINED_USER);
		return "true";
	} 
}
