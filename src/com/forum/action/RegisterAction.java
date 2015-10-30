package com.forum.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.biz.ExpandInfoBiz;
import com.forum.biz.UserBiz;
import com.forum.utility.Constants;
import com.forum.vo.ExpandInfoVO;
import com.forum.vo.UserVO;

@Controller
public class RegisterAction {

	@Autowired
	private UserBiz userBiz;
	
	@Autowired
	private ExpandInfoBiz expandInfoBiz;

	/*
	 * 注册
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/register.json")
	@ResponseBody
	public String add(UserVO userVO, HttpServletRequest request)
			throws NoSuchAlgorithmException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonStr = registerRemote(userVO);
		map = Constants.json2Map(jsonStr, map);

		String code = map.get("code").toString();

		String msg = map.get("msg").toString();
		System.out.println(msg);

		String id = null;
		String username = null;
		JSONObject json = new JSONObject();

		if (code.equals("1")) {
			map = (Map<String, Object>) map.get("user");
			id = map.get("id").toString();
			username = map.get("username").toString();

			userVO.setId(Integer.parseInt(id));
			userBiz.addUser(userVO, request);

			//添加个人信息
			ExpandInfoVO expandInfoVO = new ExpandInfoVO();
			expandInfoVO.setUserId(Integer.parseInt(id));
			expandInfoVO.setNickName(username);
			expandInfoBiz.addExpandInfo(expandInfoVO);
				
			json.put("result", "注册成功，系统会发送一封邮件至您的邮箱，请查阅邮件并点击内容中链接进行激活！");
		} else if (code.equals("100")) {
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
	public String activate(String email, HttpServletRequest request,
			HttpServletResponse response) {
		Integer result = 0;
		long isActive = 1;

		result = userBiz.updateActive(isActive, email);

		StringBuilder sb = new StringBuilder();
		if (result > 0) {
			sb.append("激活成功，3秒后跳转至登录页！");
			String path = request.getContextPath();
			response.setHeader("refresh", "3;url=" + path + "/login.html");
		} else {
			sb.append("激活失败，请检查链接！");
		}
		return sb.toString();
	}

	/*
	 * 公共服务器注册
	 */
	public String registerRemote(UserVO userVO) throws IOException {
		URL url = new URL("http://api.singwin.cn/comm/user/sign_in");
		URLConnection urlConnection = url.openConnection();
		urlConnection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(
				urlConnection.getOutputStream(), "utf-8");
		out.write("username=" + userVO.getMail() + "&password="
				+ userVO.getDarkPass());
		out.flush();
		out.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String line = in.readLine();

		return line;
	}
}
