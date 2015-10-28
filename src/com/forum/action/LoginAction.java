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
import java.util.Map.Entry;

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
	@SuppressWarnings("unchecked")
	@RequestMapping("/login.json")
	@ResponseBody
	public String login(UserVO userVO, HttpServletRequest request,
			HttpServletResponse response) throws NoSuchAlgorithmException, IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		String result = loginRemote(userVO);
		map = json2Map(result, map);
		
		String code = map.get("code").toString();
		
		String msg = map.get("msg").toString();
		System.out.println(msg);
		
		String id = null;
		String username = null;
		if(code.equals("1")){
			map = (Map<String, Object>) map.get("user");
			id = map.get("id").toString();
			username = map.get("username").toString();
		}
		
		
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
	
	public String loginRemote(UserVO userVO) throws IOException {
		URL url = new URL("http://api.singwin.cn/comm/user/login");
		URLConnection urlConnection = url.openConnection();
		urlConnection.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(), "utf-8");
		out.write("username=" + userVO.getMail() + "&password=" + userVO.getDarkPass());
		out.flush();
		out.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String line = in.readLine();

		return line;
	}	
	
	/**
	*json字符串转map集合
	*@author ducc
	*@param jsonStrjson字符串
	*@param map接收的map
	*@return
	*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> json2Map(String jsonStr, Map<String, Object> map) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		map = JSONObject.fromObject(jsonObject);
		// 递归map的value,如果
		for (Entry<String, Object> entry : map.entrySet()) {
			json2map1(entry, map);
		}
		return map;
	}
	
	
	/**
	*json转map,递归调用的方法
	*@author ducc
	*@param entry
	*@param map
	*@return
	*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> json2map1(Entry<String, Object> entry,
			Map<String, Object> map) {
		if (entry.getValue() instanceof Map) {
			JSONObject jsonObject = JSONObject.fromObject(entry.getValue());

			Map<String, Object> map1 = JSONObject.fromObject(jsonObject);

			for (Entry<String, Object> entry1 : map1.entrySet()) {
				map1 = json2map1(entry1, map1);
				map.put(entry.getKey(), map1);
			}
		}
		return map;
	}
	
}
