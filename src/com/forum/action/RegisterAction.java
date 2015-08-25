package com.forum.action;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.biz.UserBiz;
import com.forum.vo.UserVO;

@Controller
public class RegisterAction {

	@Autowired
	private UserBiz userBiz;

	/*
	 * 注册
	 */
	@RequestMapping(value = "/register.json")
	@ResponseBody
	public String add(UserVO userVO, HttpServletRequest request)
			throws NoSuchAlgorithmException {
		Integer result = 0;

		result = userBiz.addUser(userVO, request);

		JSONObject json = new JSONObject();
		if (result > 0) {
			json.put("result", "注册成功，系统会发送一封邮件至您的邮箱，请查阅邮件并点击内容中链接进行激活！");
		} else if (result < 0) {
			json.put("result", "此邮箱地址已被注册！");
		} else {
			json.put("result", "注册失败，请联系管理员！");
		}

		return json.toString();
	}

	/*
	 * 激活账户
	 */
	@RequestMapping(value = "/activate.json")
	@ResponseBody
	public String activate(String email, HttpServletResponse response) {
		Integer result = 0;
		long isActive = 1;

		result = userBiz.updateActive(isActive, email);

		StringBuilder sb = new StringBuilder();
		if (result > 0) {
			sb.append("激活成功，3秒后跳转至登录页！");
			response.setHeader("refresh", "3;url=/forum/login.html");
		} else {
			sb.append("激活失败，请检查链接！");
		}
		return sb.toString();
	}
}
