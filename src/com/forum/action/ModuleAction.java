package com.forum.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.biz.ModuleBiz;
import com.forum.utility.Constants;
import com.forum.vo.ModuleVO;
import com.forum.vo.UserVO;

@Controller
public class ModuleAction {

	@Autowired
	private ModuleBiz moduleBiz;
	
	/*
	 * 获取所有版块
	 */
	@RequestMapping("/getAllModule.json")
	@ResponseBody
	public String getAllModule(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute(Constants.LOGINED_USER);
		
		JSONObject json = new JSONObject();
		
		List<ModuleVO> moduleVOList;
		
		if (userVO != null && userVO.getGroupId() == Constants.GroupType.admin.getValue()) {
			moduleVOList = moduleBiz.selectAllModule();
		} else {
			moduleVOList = moduleBiz.selectAllModule(false);
		}
		
		json.put("moduleVOList", moduleVOList);
		
		return json.toString();
	}
	
	/*
	 * 添加模块
	 */
	@RequestMapping("/addModule.json")
	@ResponseBody
	public String addModule(HttpServletRequest request,
			@RequestParam("name") String name,
			@RequestParam("imgPath") String imgPath,
			@RequestParam("desc") String desc,
			@RequestParam("visible") String visible) throws IOException {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);
		JSONObject json = new JSONObject();

		if (userVO != null && userVO.getGroupId() == Constants.GroupType.admin.getValue()) {
			ModuleVO moduleVO = new ModuleVO();
			moduleVO.setName(name);
			moduleVO.setImgPath(imgPath);
			moduleVO.setDesc(desc);
			moduleVO.setSort("");
			moduleVO.setParentId(0);
			moduleVO.setVisible(Boolean.parseBoolean(visible));
			Integer result = moduleBiz.addModule(moduleVO);

			if (result > 0) {
				json.put("success", true);
				json.put("result", "添加模块成功！");
				json.put("redirect", false);
			} else {
				json.put("success", false);
				json.put("result", "添加模块失败！");
				json.put("redirect", false);
			}
		} else {
			json.put("success", false);
			json.put("result", "未登录！");
			json.put("redirect", true);
		}

		return json.toString();
	}
	
	/*
	 * 修改模块
	 */
	@RequestMapping("/editModule.json")
	@ResponseBody
	public String editModule(@RequestParam("id") String id,
			@RequestParam("name") String name,
			@RequestParam("imgPath") String imgPath,
			@RequestParam("desc") String desc,
			@RequestParam("visible") String visible)
			throws UnsupportedEncodingException {
		ModuleVO moduleVO = new ModuleVO();
		moduleVO.setId(Integer.parseInt(id));
		moduleVO.setName(name);
		moduleVO.setImgPath(imgPath);
		moduleVO.setDesc(desc);
		moduleVO.setSort("");
		moduleVO.setParentId(0);
		moduleVO.setVisible(Boolean.parseBoolean(visible));
		Integer result = moduleBiz.editModule(moduleVO);

		JSONObject json = new JSONObject();

		if (result > 0) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}

		return json.toString();

	}
	
	/*
	 * 删除模块
	 */
	@RequestMapping("/delModule.json")
	@ResponseBody
	public String delModule(@RequestParam("id") String id){
		
		Integer result = moduleBiz.delModule(Integer.parseInt(id));
		
		JSONObject json = new JSONObject();
		
		if(result>0){
			json.put("success", true);
		}
		else{
			json.put("success", false);
		}
		
		return json.toString();
		
	}
}
