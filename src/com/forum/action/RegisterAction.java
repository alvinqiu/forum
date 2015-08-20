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
	 * �����û�
	 */
	@RequestMapping(value="/register.json")
	@ResponseBody
	public String add(UserVO userVO,HttpServletRequest request) throws NoSuchAlgorithmException{
		Integer result=0;
		
		result = userBiz.addUser(userVO, request);
		
		JSONObject json = new JSONObject();
		if(result>0){
			json.put("result", "ע��ɹ����㽫���յ�һ���ʼ��������ʼ����url����ȷ�ϼ��");
		}
		else if(result<0){
			json.put("result", "�������Ѵ��ڣ�");
		}
		else{
			json.put("result", "ע��ʧ�ܣ�");
		}
		
		return json.toString();
	}
	
	/*
	 * ��֤�����û�
	 */
	@RequestMapping(value="/activate.json")
	@ResponseBody
	public String activate(String email,HttpServletResponse response){
		Integer result=0;
		long isActive=1;
		
		result = userBiz.updateActive(isActive,email);
		
		StringBuilder sb = new StringBuilder();
		if(result>0){
			sb.append("����ɹ���3�����ת����¼ҳ��");
			response.setHeader("refresh", "3;url=/forum/login.html");
		}
		else{
			sb.append("����ʧ�ܣ�");
		}
		return sb.toString();
	}
}
