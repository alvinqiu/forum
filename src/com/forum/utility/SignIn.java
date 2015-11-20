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

	// 是否已签到
	boolean isSignIn = false;

	/*
	 * 签到
	 */
	@RequestMapping("/signIn.json")
	@ResponseBody
	public String signIn(HttpServletRequest request) {
		JSONObject json = new JSONObject();

		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);

		if (userVO != null) {
			// 生成随机数
			Random random = new Random();
			int point = random.nextInt(10) + 1;

			Integer result = expandInfoBiz.signIn(point, userVO.getId());

			if (result > 0) {
				json.put("result", "恭喜您获得了  " + point + " 积分！");
				json.put("success", true);
			} else if (result < 0) {
				json.put("result", "您已经签过到啦，明天再来吧！");
				json.put("success", false);
			} else {
				json.put("result", "请先完善你的个人信息！");
				json.put("success", false);
			}
		}

		json.put("isSignIn", isSignIn);
		return json.toString();
	}
}
