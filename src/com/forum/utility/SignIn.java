package com.forum.utility;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.biz.ExpandInfoBiz;
import com.forum.vo.UserVO;

@Controller
public class SignIn {

	@Autowired
	private ExpandInfoBiz expandInfoBiz;
	
	/*
	 * ǩ��
	 */
	@RequestMapping("/signIn.json")
	@ResponseBody
	public String signIn(HttpServletRequest request){
		JSONObject json = new JSONObject();
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute(Constants.LOGINED_USER);
		
		if(userVO!=null){
			// ���10���ڵ������
			Random random = new Random();
			int point =  random.nextInt(10)+1;
					
			Integer result = expandInfoBiz.signIn(point, userVO.getId());
			
			if(result>0){
				json.put("result", "ǩ���ɹ������� "+point+" ���֣�");
				json.put("success", true);
			}
			else {
				json.put("result", "ǩ��ʧ�ܣ�");
				json.put("success", false);
			}
		}
		else{
			json.put("result", "δ��¼");
			json.put("success", false);
		}
		
		return json.toString();
	}
}
