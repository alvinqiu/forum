package com.forum.action;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.biz.ExpandInfoBiz;
import com.forum.biz.TagBiz;
import com.forum.utility.Constants;
import com.forum.utility.Verify;
import com.forum.vo.ExpandInfoVO;
import com.forum.vo.TagVO;
import com.forum.vo.UserVO;

@Controller
public class EditPreviewAction {

	@Autowired
	private ExpandInfoBiz expandInfoBiz;

	@Autowired
	private TagBiz tagBiz;

	@Autowired
	private Verify verify;

	/*
	 * 获取个人信息
	 */
	@RequestMapping("/preview.json")
	@ResponseBody
	public String getPreview(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);

		JSONObject json = new JSONObject();
		if (userVO != null) {
			List<ExpandInfoVO> expandInfoVOList = expandInfoBiz
					.selExpandInfoByUserId(userVO.getId());
			if (expandInfoVOList.size() > 0) {
				json.put("result", expandInfoVOList.get(0));
			}
			List<TagVO> tagVOList = tagBiz.selectTagByUserId(userVO.getId());
			if (tagVOList.size() > 0) {
				json.put("tagVOList", tagVOList);
			}
		}
		return json.toString();
	}

	/*
	 * 新增 or修改个人信息 key -1==新增 0==修改
	 */
	@RequestMapping("/editPreview.json")
	@ResponseBody
	public String editPreview(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("key") long key,
			@RequestParam("tagList") String tagList, ExpandInfoVO expandInfoVO) {
		JSONObject json = new JSONObject();
		Integer result = 0;
		// 查询是否登录
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);
		if (userVO == null) {
			response.setHeader("refresh", "2;url=/forum/login.html");
			json.put("success", false);
			json.put("result", "您还未登录！");
		} else {

			String nickName = request.getParameter("nickName");
			if (nickName != "") {
				List<ExpandInfoVO> expandInfoVOList = expandInfoBiz
						.checkNickNameIsExist(nickName);
				if (expandInfoVOList.size() > 0 && key < 0) {
					json.put("success", false);
					json.put("result", "此昵称已存在！");
				} else {
					expandInfoVO.setUserId(userVO.getId());
					// 当前时间
					Timestamp timestamp = new Timestamp(
							System.currentTimeMillis());
					expandInfoVO.setAddedTime(timestamp);

					if (key < 0) {
						result = expandInfoBiz.addExpandInfo(expandInfoVO);

						// 新增标签
						tagBiz.insertTag(tagList, userVO.getId());
					} else {
						result = expandInfoBiz
								.updateExpandInfoByUserId(expandInfoVO);
					}

					if (result > 0) {
						json.put("success", true);
						json.put("result", "个人信息更新成功！");
					} else {
						json.put("success", false);
						json.put("result", "个人信息更新失败！");
					}
				}
			} else {
				json.put("success", false);
				json.put("result", "昵称不能为空！");
			}
		}
		return json.toString();
	}
}
