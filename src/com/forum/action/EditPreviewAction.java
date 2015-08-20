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
	 * ���ʸ�����Ϣ����
	 */
	@RequestMapping("/preview.json")
	@ResponseBody
	public String getPreview(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute(Constants.LOGINED_USER);
		
		JSONObject json = new JSONObject();
		if(userVO!=null){
			List<ExpandInfoVO> expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(userVO.getId());
			if(expandInfoVOList.size()>0){
				json.put("result", expandInfoVOList.get(0));
			}
			List<TagVO> tagVOList = tagBiz.selectTagByUserId(userVO.getId());
			if(tagVOList.size()>0){
				json.put("tagVOList", tagVOList);
			}
		}
		return json.toString();
	}
	
	/*
	 * ������Ϣ����
	 * key   -1==����   0==�޸�
	 */
	@RequestMapping("/editPreview.json")
	@ResponseBody
	public String editPreview(HttpServletRequest request,HttpServletResponse response,@RequestParam("key") long key,
			@RequestParam("tagList") String tagList,ExpandInfoVO expandInfoVO){
		JSONObject json = new JSONObject();
		Integer result = 0;
		// ��֤�û�
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute(Constants.LOGINED_USER);
		if (userVO==null) {
			response.setHeader("refresh", "2;url=/forum/login.html");
			json.put("success", false);
			json.put("result", "δ��¼��");
		}
		else{
		
			String nickName = request.getParameter("nickName");
			if(nickName!=""){
				List<ExpandInfoVO> expandInfoVOList = expandInfoBiz.checkNickNameIsExist(nickName);
				if(expandInfoVOList.size()>0 && key<0){
					json.put("success", false);
					json.put("result", "�ǳ��Ѵ��ڣ�");
				}
				else{
					expandInfoVO.setUserId(userVO.getId());
					//��ǰʱ��
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					expandInfoVO.setAddedTime(timestamp);
					
					if(key<0){
						result = expandInfoBiz.addExpandInfo(expandInfoVO);
						
						//��ӱ�ǩ
						tagBiz.insertTag(tagList,userVO.getId());
					}
					else{
						result = expandInfoBiz.updateExpandInfoByUserId(expandInfoVO);
					}
					
					
					if(result>0){
						json.put("success", true);
						json.put("result", "��Ϣ��ĳɹ���");
					}
					else{
						json.put("success", false);
						json.put("result", "��Ϣ���ʧ�ܣ�");
					}
				}
			}
			else{
				json.put("success", false);
				json.put("result", "�ǳƲ���Ϊ�գ�");
			}
		}
		return json.toString();
	}
}
