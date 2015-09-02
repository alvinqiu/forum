package com.forum.action;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forum.biz.ExpandInfoBiz;
import com.forum.biz.PostBiz;
import com.forum.utility.Constants;
import com.forum.vo.ExpandInfoVO;
import com.forum.vo.PostVO;
import com.forum.vo.UserVO;

@Controller
public class PostAction {

	@Autowired
	private PostBiz postBiz;
	
	@Autowired
	private ExpandInfoBiz expandInfoBiz;
	
	private long PageCount = 10;//ÿҳ��ʾ��������
	
	/*
	 * 添加帖子
	 */
	@RequestMapping("/addPost.json")
	@ResponseBody
	public String addPost(HttpServletRequest request,PostVO postVO){
		JSONObject json = new JSONObject();
		
		//验证是否登录
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute(Constants.LOGINED_USER);
		if(userVO!=null){
			postVO.setUserId(userVO.getId());
			postVO.setType("3");//设置为待审核状态
			
			//获取当前时间
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			postVO.setSubmitTime(timestamp);
			
			Integer result = postBiz.addPost(postVO);
			
			if(result>0){
				json.put("success", true);
				json.put("result","发布成功！");
			}
			else{
				json.put("success", false);
				json.put("result","发布失败！");
			}
		}else{
			// 未登录
			json.put("success", false);
			json.put("result","请登录！");
		}
		
		return json.toString();
	}
	
	/*
	 * 获取所有帖子
	 */
	@RequestMapping("/getAllPost.json")
	@ResponseBody
	public String getAllPost(HttpServletRequest request,@Param("page") long page){
		JSONObject json = new JSONObject();
		String id = request.getParameter("moduleId")!=null?request.getParameter("moduleId"):"0";
		
		long moduleId= Integer.parseInt(id);
		page = (page<=0)?1:page;
		
		if(page>0){
			long start = Math.abs(1 - page) * PageCount;
			long end = page * PageCount;
			
			HttpSession session = request.getSession();
			UserVO userVO = (UserVO)session.getAttribute(Constants.LOGINED_USER);
			long groupId = userVO != null ? userVO.getGroupId() : Constants.GroupType.user.getValue();//如未登录则视为普通用户处理
			json.put("GroupId", groupId);
			
			List<PostVO> postVOList;
			if(moduleId>0){
				postVOList = postBiz.getAllPostLimit(start,end,moduleId);
				
				int total = postBiz.getAllPostCount(moduleId).size();
				total = (int)Math.ceil((double)total/PageCount);
				json.put("Total", total);
			}
			else{
				if(groupId==Constants.GroupType.admin.getValue()){//管理员
					postVOList = postBiz.getAllPost(start, end - 5, true);
				}
				else{
					postVOList = postBiz.getAllPost(start, end - 5, false);
				}
			}
			
			List<ExpandInfoVO> expandInfoVOList;
			String postContent="";
			if(postVOList.size()>0){
				for (PostVO postVO : postVOList) {
					//昵称
					expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(postVO.getUserId());
					if(expandInfoVOList.size()>0){
						postVO.setName(expandInfoVOList.get(0).getNickName());
					}
					//回复数
					postVO.setCommentCount(postBiz.getCommentByPostId(postVO.getId()).size());
					
					//截取帖子内容一部分
					postContent = postVO.getContent();
					if(postContent.length()>=100){
						postContent = postContent.substring(0, 100);
					}
					
					postVO.setContent(postContent);
				}
				
				json.put("postList", postVOList);
			}
		}
		
		return json.toString();
	}
	
	/*
	 * ���id��ȡ����
	 */
	@RequestMapping("/getPostById.json")
	@ResponseBody
	public String getPostById(HttpServletRequest request,@RequestParam("id") long id){
		JSONObject json = new JSONObject();
		List<ExpandInfoVO> expandInfoVOList;
		if(id>0){
			
			HttpSession session = request.getSession();
			UserVO userVO = (UserVO)session.getAttribute(Constants.LOGINED_USER);
			
			if(userVO!=null){
				json.put("GroupId", userVO.getGroupId());
			}
			else{
				json.put("GroupId", 3);//���Ϊ��¼��Ĭ��Ϊ��ͨ�û�
			}
			
			PostVO postVO = postBiz.getPostById(id);
			//�ǳ�
			expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(postVO.getUserId());
			if(expandInfoVOList.size()>0){
				postVO.setName(expandInfoVOList.get(0).getNickName());
			}
			//�ظ���
			postVO.setCommentCount(postBiz.getCommentByPostId(postVO.getId()).size());
			
			json.put("PostVO", postVO);
		}
		
		return json.toString();
	}
	
	/*
	 * �������
	 */
	@RequestMapping("/addComment.json")
	@ResponseBody
	public String addComment(@RequestParam("content") String content,@RequestParam("id") long id){
		JSONObject json = new JSONObject();
		if(id>0){
			PostVO postVO = postBiz.getPostById(id);
			String postContent = postVO.getContent();
			if(postContent.length()>=30){
				postContent = postContent.substring(0, 30);
			}
			
			PostVO postVOClone = postVO;
			
			postVOClone.setContent(content);
			postVOClone.setParentId(id);
			postVOClone.setParentContentSummary(postContent);
			//��ǰʱ��
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			postVOClone.setSubmitTime(timestamp);
			
			Integer result = postBiz.addPost(postVOClone);
			
			if(result>0){
				json.put("success", true);
			}
			else{
				json.put("success", false);
			}
		}
		
		
		return json.toString();
	}
	
	/*
	 * ���post id��ȡ����
	 */
	@RequestMapping("/getComment.json")
	@ResponseBody
	public String getComment(@RequestParam("id") long id){
		JSONObject json = new JSONObject();
		List<ExpandInfoVO> expandInfoVOList;
		if(id!=0){
			List<PostVO> postVOList = postBiz.getCommentByPostId(id);
			
			for (PostVO postVO : postVOList) {
				//�ǳ�
				expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(postVO.getUserId());
				if(expandInfoVOList.size()>0){
					postVO.setName(expandInfoVOList.get(0).getNickName());
				}
			}
			
			json.put("postVOList", postVOList);
		}
		return json.toString();
	}
	
	/*
	 * ��ȡ���������
	 */
	@RequestMapping("/getAllPostByHold.json")
	@ResponseBody
	public String getAllPostByHold(){
		JSONObject json = new JSONObject();

		List<PostVO> postVOList = postBiz.getAllPostByHold();
		json.put("postVOList", postVOList);

		return json.toString();
	}
	
	/*
	 * ������� ��by id��
	 */
	@RequestMapping("/passPost.json")
	@ResponseBody
	public String passPost(@RequestParam("id") long id){
		JSONObject json = new JSONObject();
		if(id!=0){
			String type = "2"; //��ͨ��
			Integer result = postBiz.passPost(type, id);
			if(result>0){
				json.put("success", true);
			}else{
				json.put("success", false);
			}
		}
		return json.toString();
	}
	
	/*
	 * ���ø��� (by id)
	 */
	@RequestMapping("/setHighLight.json")
	@ResponseBody
	public String setHighLight(@RequestParam("Id") long id){
		JSONObject json = new JSONObject();
		if(id!=0){
			String highLight = "1";
			Integer result = postBiz.setHighLight(highLight, id);
			
			if(result>0){
				json.put("success", true);
			}else{
				json.put("success", false);
			}
		}
		return json.toString();
	}
	
	/*
	 * �����ö� (by id)
	 */
	@RequestMapping("/setTop.json")
	@ResponseBody
	public String setTop(@RequestParam("Id") long id){
		JSONObject json = new JSONObject();
		if(id!=0){
			String top = "1";
			Integer result = postBiz.setTop(top, id);
			
			if(result>0){
				json.put("success", true);
			}else{
				json.put("success", false);
			}
		}
		return json.toString();
	}
	
	/*
	 * ɾ�� (by id)
	 */
	@RequestMapping("/del.json")
	@ResponseBody
	public String del(@RequestParam("Id") long id){
		JSONObject json = new JSONObject();
		if(id!=0){
			Integer result = postBiz.del(id);
			
			if(result>0){
				json.put("success", true);
			}else{
				json.put("success", false);
			}
		}
		return json.toString();
	}
	
}
