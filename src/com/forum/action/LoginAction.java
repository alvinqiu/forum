package com.forum.action;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.biz.UserBiz;
import com.forum.utility.Constants;
import com.forum.vo.UserVO;

@Controller
public class LoginAction {

	@Autowired
	private UserBiz userBiz;

	/*
	 * 登录
	 */
	@RequestMapping("/login.json")
	@ResponseBody
	public String login(UserVO userVO, HttpServletRequest request,
			HttpServletResponse response) throws NoSuchAlgorithmException {

		List<UserVO> userVOList = userBiz.selectUser(userVO);

		JSONObject json = new JSONObject();
		if (userVOList.size() > 0) {// 用户存在
			if (userVOList.get(0).getIsActive() > 0) {// 已激活
				json.put("success", true);

				HttpSession session = request.getSession();
				session.setAttribute(Constants.LOGINED_USER, userVOList.get(0));
			} else {// 用户未激活
				json.put("result", "账户未激活,系统已发送一封邮件至您的邮箱，请点击邮件中链接进行激活操作！");
				json.put("success", false);
				
				//发送激活邮件
				userBiz.sendActivationMail(userVOList.get(0), request);
			}
		} else {// 用户不存在
			json.put("result", "用户名或密码错误!");
			json.put("success", false);
		}

		return json.toString();
	}
}
