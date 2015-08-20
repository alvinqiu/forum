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
	 * ��¼
	 */
	@RequestMapping("/login.json")
	@ResponseBody
	public String login(UserVO userVO,HttpServletRequest request,HttpServletResponse response) throws NoSuchAlgorithmException{
		
		List<UserVO> userVOList=userBiz.selectUser(userVO);
		
		JSONObject json = new JSONObject();
		if(userVOList.size()>0){
			if(userVOList.get(0).getIsActive()>0){
				//json.put("result", "��¼�ɹ�!");
				json.put("success", true);
				
				HttpSession session = request.getSession();
				session.setAttribute(Constants.LOGINED_USER, userVOList.get(0));
			}
			else{
				json.put("result", "δ����!");
				json.put("success", false);
			}
		}
		else{
			json.put("result", "�˻������ڻ��˺���������!");
			json.put("success", false);
		}
		
		return json.toString();
	}
}
