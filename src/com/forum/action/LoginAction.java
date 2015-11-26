package com.forum.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
public class LoginAction {

	@Autowired
	private UserBiz userBiz;
	
	@Autowired
	private ExpandInfoBiz expandInfoBiz;

	/*
	 * 登录
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/login.json")
	@ResponseBody
	public String login(UserVO userVO, HttpServletRequest request,
			HttpServletResponse response) throws NoSuchAlgorithmException,
			IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		String result = loginRemote(userVO);
		map = Constants.json2Map(result, map);

		String code = map.get("code").toString();

		String msg = map.get("msg").toString();
		System.out.println(msg);

		String id = null;
		String username = null;
		JSONObject json = new JSONObject();

		if (code.equals("1")) {// 用户存在
			map = (Map<String, Object>) map.get("user");
			id = map.get("id").toString();
			username = map.get("username").toString();

			userVO = userBiz.selectUserById(Integer.parseInt(id), username);
			
			if (userVO.getIsActive() > 0) {// 已激活
				json.put("success", true);
				HttpSession session = request.getSession();
				session.setAttribute(Constants.LOGINED_USER, userVO);

			} else {
				List<ExpandInfoVO> expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(Integer.parseInt(id));
				if (expandInfoVOList.size() == 0) {
					// 添加个人信息
					ExpandInfoVO expandInfoVO = new ExpandInfoVO();
					expandInfoVO.setUserId(Integer.parseInt(id));
					expandInfoVO.setNickName(getName(username));
					expandInfoVO.setHead(Constants.HEADPATH);
					expandInfoBiz.addExpandInfo(expandInfoVO);
				}
				
				json.put("result", "账户未激活,系统已发送一封邮件至您的邮箱，请点击邮件中链接进行激活操作！");
				json.put("success", false);

				// 发送激活邮件
				userBiz.sendActivationMail(userVO, request);
			}
		} else {// 用户不存在
			json.put("result", "用户名或密码错误!");
			json.put("success", false);
		}

		return json.toString();
	}

	public String loginRemote(UserVO userVO) throws IOException {
		URL url = new URL("http://api.singwin.cn/comm/user/login");
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

	/*
	 * 截取邮箱地址‘@’左边部分作为默认昵称
	 */
	public String getName(String name){
		String pat="[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9]+(\\.(com|cn))";
		Pattern pattern = Pattern.compile(pat);
		Matcher matcher = pattern.matcher(name);
		
		if(matcher.matches()){
			name = name.split("@")[0];
		}
		return name;
	}
}
