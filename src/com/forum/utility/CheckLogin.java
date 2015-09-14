package com.forum.utility;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.biz.ExpandInfoBiz;
import com.forum.utility.Constants.GroupType;
import com.forum.vo.ExpandInfoVO;
import com.forum.vo.UserVO;

@Controller
public class CheckLogin {

	@Autowired
	private ExpandInfoBiz expandInfoBiz;

	// 用户名（昵称 or 邮箱地址）
	public String USERNAME = "";
	// 是否为管理员
	boolean isAdmin = false;
	// 是否已签到
	boolean isSignIn = false;

	/*
	 * 是否登录 return 昵称 or mail
	 */
	@RequestMapping("/checkLogin.json")
	@ResponseBody
	public String checkLogin(HttpServletRequest request) {
		JSONObject json = new JSONObject();

		List<ExpandInfoVO> expandInfoVOList;
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);

		if (userVO != null) {
			// 获取用户个人信息
			expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(userVO
					.getId());

			if (expandInfoVOList.size() > 0) {
				USERNAME = expandInfoVOList.get(0).getNickName();

				// 判断签到时间是否为今天
				long day = expandInfoBiz.checkSignInTime(expandInfoVOList.get(0).getPointSignInTime());

				if (day == 0) {
					isSignIn = true;
				}

			} else {
				USERNAME = userVO.getMail();
			}
			isAdmin = userVO.getGroupId() == GroupType.admin.getValue() ? true
					: false;
			json.put("name", USERNAME);
			json.put("success", true);
		} else {
			json.put("success", false);
		}

		json.put("isAdmin", isAdmin);
		json.put("isSignIn", isSignIn);
		return json.toString();
	}

}
