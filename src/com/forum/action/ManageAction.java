package com.forum.action;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.biz.GroupBiz;
import com.forum.biz.UserBiz;
import com.forum.vo.GroupVO;
import com.forum.vo.UserVO;

@Controller
public class ManageAction {

	@Autowired
	private GroupBiz groupBiz;
	
	@Autowired
	private UserBiz userBiz;
	
	
	/*
	 * ��ȡ���з���ͨ�û�
	 */
	@RequestMapping("/getAllGroupExceptUser.json")
	@ResponseBody
	public String getAllGroupExceptUser(){
		JSONObject json = new JSONObject();
		long groupId = 3;//1.����Ա   2.����   3.��ͨ�û�
		
		List<UserVO> userList = userBiz.selectUserExcept(groupId);
		
		if(userList.size()>0){
			for (UserVO userVO : userList) {
				GroupVO groupVO = groupBiz.getGroupById(userVO.getGroupId());
				if(groupVO!=null){
					userVO.setGroupName(groupVO.getName());
				}
			}
		}
		
		json.put("UserList", userList);
		
		return json.toString();
	}
	
	/*
	 * ��ȡ�����������
	 */
	@RequestMapping("/getAllGroup.json")
	@ResponseBody
	public String getAllGroup(){
		JSONObject json = new JSONObject();
		
		List<GroupVO> groupList = groupBiz.getAllGroup();
		json.put("GroupList", groupList);
		
		return json.toString();
	}
	
	/*
	 * �����������
	 */
	@RequestMapping("/setGroup.json")
	@ResponseBody
	public String setGroup(@RequestParam("username") String username,@RequestParam("groupid") String groupid){
		JSONObject json = new JSONObject();
		
		Integer result = userBiz.setGroup(Integer.parseInt(groupid), username);
		
		json.put("result", result);
		
		return json.toString();
	}
}
