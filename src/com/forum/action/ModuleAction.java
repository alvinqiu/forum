package com.forum.action;

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
	 * ��ȡ����ģ��
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
	 * ���ģ��
	 */
	@RequestMapping("/addModule.json")
	@ResponseBody
	public String addModule(@RequestParam("name") String name,@RequestParam("desc") String desc,@RequestParam("visible") String visible){
		ModuleVO moduleVO = new ModuleVO();
		moduleVO.setName(name);
		moduleVO.setDesc(desc);
		moduleVO.setSort("");
		moduleVO.setParentId(0);
		moduleVO.setVisible(Boolean.parseBoolean(visible));
		Integer result = moduleBiz.addModule(moduleVO);
		
		JSONObject json = new JSONObject();
		
		if(result>0){
			json.put("success", true);
		}
		else{
			json.put("success", false);
		}
		
		return json.toString();
	}
	
	/*
	 * �޸�ģ��
	 */
	@RequestMapping("/editModule.json")
	@ResponseBody
	public String editModule(@RequestParam("id") String id,@RequestParam("name") String name,@RequestParam("desc") String desc,@RequestParam("visible") String visible) throws UnsupportedEncodingException{
		ModuleVO moduleVO = new ModuleVO();
		moduleVO.setId(Integer.parseInt(id));
		moduleVO.setName(name);
		moduleVO.setDesc(desc);
		moduleVO.setSort("");
		moduleVO.setParentId(0);
		moduleVO.setVisible(Boolean.parseBoolean(visible));
		Integer result = moduleBiz.editModule(moduleVO);
		
		JSONObject json = new JSONObject();
		
		if(result>0){
			json.put("success", true);
		}
		else{
			json.put("success", false);
		}
		
		return json.toString();
		
	}
	
	/*
	 * ɾ��ģ��
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
