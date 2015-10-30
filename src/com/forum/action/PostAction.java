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
import com.forum.biz.ModuleBiz;
import com.forum.biz.PostBiz;
import com.forum.biz.UserBiz;
import com.forum.utility.Constants;
import com.forum.vo.ExpandInfoVO;
import com.forum.vo.ModuleVO;
import com.forum.vo.PostVO;
import com.forum.vo.UserVO;

@Controller
public class PostAction {

	@Autowired
	private PostBiz postBiz;
	
	@Autowired
	private UserBiz userBiz;

	@Autowired
	private ExpandInfoBiz expandInfoBiz;
	
	@Autowired
	private ModuleBiz moduleBiz;

	private long PageCount = 10;// 每页显示10条数据
	
	private long AddPostPoint = 3;// 发帖获得积分

	private long AddCommentPoint = 2;// 评论获得积分
	
	private long BudgetPoint = 50;// 发帖积分标准，小于则需审核帖子；大于或等于则无需审核帖子

	/*
	 * 添加帖子
	 */
	@RequestMapping("/addPost.json")
	@ResponseBody
	public String addPost(HttpServletRequest request, PostVO postVO) {
		JSONObject json = new JSONObject();
		// 验证是否登录
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);
		
		if (userVO != null) {
			
			long userId = userVO.getId();
			postVO.setUserId(userId);
			
			List<ExpandInfoVO> expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(userId);
			if (expandInfoVOList.size() > 0 && expandInfoVOList.get(0).getPoint() >= BudgetPoint) {
				postVO.setType(Constants.PostType.common.getValue());// 设置为已审核状态
			}else{
				postVO.setType(Constants.PostType.authstr.getValue());// 设置为待审核状态
			}

			// 获取当前时间
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			postVO.setSubmitTime(timestamp);

			Integer result = postBiz.addPost(postVO);

			if (result > 0) {
				
				//获得发帖积分
				expandInfoBiz.addPoint(AddPostPoint, userVO.getId());
				
				json.put("success", true);
				
				if (postVO.getType() == Constants.PostType.common.getValue()) {
					json.put("result", "发布成功！");
				} else {
					json.put("result", "帖子提交成功,待管理员审核！");
					
					//发送邮件给管理员
					
				}
			} else {
				json.put("success", false);
				json.put("result", "发布失败！");
			}
		} else {
			// 未登录
			json.put("success", false);
			json.put("result", "请登录！");
		}

		return json.toString();
	}

	/*
	 * 获取所有帖子
	 */
	@RequestMapping("/getAllPost.json")
	@ResponseBody
	public String getAllPost(HttpServletRequest request,
			@Param("page") long page) {
		JSONObject json = new JSONObject();
		String id = request.getParameter("moduleId") != null ? request
				.getParameter("moduleId") : "0";

		long moduleId = Integer.parseInt(id);
		page = (page <= 0) ? 1 : page;

		if (page > 0) {
			long start = Math.abs(1 - page) * PageCount;
			long end = page * PageCount;

			HttpSession session = request.getSession();
			UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);
			long groupId = userVO != null ? userVO.getGroupId() : Constants.GroupType.user.getValue();// 如未登录则视为普通用户处理
			json.put("GroupId", groupId);

			List<PostVO> postVOList;
			if (moduleId > 0) {
				postVOList = postBiz.getAllPostLimit(start, end, moduleId);

				int total = postBiz.getAllPostCount(moduleId).size();
				total = (int) Math.ceil((double) total / PageCount);
				json.put("Total", total);
			} else {
				if (groupId == Constants.GroupType.admin.getValue()) {// 管理员
					postVOList = postBiz.getAllPost(start, end - 5, true);
				} else {
					postVOList = postBiz.getAllPost(start, end - 5, false);
				}
			}

			List<ExpandInfoVO> expandInfoVOList;
			String postContent = "";
			if (postVOList.size() > 0) {
				for (PostVO postVO : postVOList) {
					// 昵称
					expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(postVO.getUserId());
					if (expandInfoVOList.size() > 0) {
						postVO.setName(expandInfoVOList.get(0).getNickName());
					}else{
						userVO = userBiz.selectUserById(postVO.getUserId(), "");
						postVO.setName(userVO.getMail());
					}
					// 回复数
					postVO.setCommentCount(postBiz.getCommentByPostId(
							postVO.getId()).size());

					// 截取帖子内容一部分（纯文本）
					postContent = postVO.getContentText();
					if (postContent.length() >= 100) {
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
	 * 根据id获取帖子
	 */
	@RequestMapping("/getPostById.json")
	@ResponseBody
	public String getPostById(HttpServletRequest request,
			@RequestParam("id") long id) {
		JSONObject json = new JSONObject();
		List<ExpandInfoVO> expandInfoVOList;
		if (id > 0) {

			HttpSession session = request.getSession();
			UserVO userVO = (UserVO) session
					.getAttribute(Constants.LOGINED_USER);

			if (userVO != null) {
				json.put("GroupId", userVO.getGroupId());
			} else {
				json.put("GroupId", 3);// 普通用户处理
			}

			PostVO postVO = postBiz.getPostById(id);
			// 昵称
			expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(postVO.getUserId());
			if (expandInfoVOList.size() > 0) {
				postVO.setName(expandInfoVOList.get(0).getNickName());
			}else{
				userVO = userBiz.selectUserById(postVO.getUserId(), "");
				postVO.setName(userVO.getMail());
			}
			
			// 回复数
			postVO.setCommentCount(postBiz.getCommentByPostId(postVO.getId()).size());

			json.put("PostVO", postVO);
		}

		return json.toString();
	}

	/*
	 * 我的帖子 根据用户Id获取帖子 （包含未审核帖子）
	 */
	@RequestMapping("/getMyPost.json")
	@ResponseBody
	public String getMyPost(HttpServletRequest request, @Param("page") long page) {
		JSONObject json = new JSONObject();
		List<PostVO> postVOList = null;
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);

		page = (page <= 0) ? 1 : page;

		if (page > 0) {
			long start = Math.abs(1 - page) * PageCount;
			long end = page * PageCount;

			if (userVO != null) {
				postVOList = postBiz.getPostByUserId(userVO.getId(), start, end);

				int total = postBiz.getPostByUserId(userVO.getId()).size();
				total = (int) Math.ceil((double) total / PageCount);
				json.put("Total", total);
			}

			List<ExpandInfoVO> expandInfoVOList;
			String postContent = "";
			if (postVOList.size() > 0) {
				for (PostVO postVO : postVOList) {
					// 昵称
					expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(postVO.getUserId());
					if (expandInfoVOList.size() > 0) {
						postVO.setName(expandInfoVOList.get(0).getNickName());
					}else{
						userVO = userBiz.selectUserById(postVO.getUserId(), "");
						postVO.setName(userVO.getMail());
					}
					
					// 回复数
					postVO.setCommentCount(postBiz.getCommentByPostId(postVO.getId()).size());

					// 截取帖子内容一部分
					postContent = postVO.getContent();
					if (postContent.length() >= 100) {
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
	 * 我的回复 根据用户Id获取评论
	 */
	@RequestMapping("/getMyReply.json")
	@ResponseBody
	public String getMyReply(HttpServletRequest request,
			@Param("page") long page) {
		JSONObject json = new JSONObject();
		List<PostVO> postVOList = null;
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);

		page = (page <= 0) ? 1 : page;

		if (page > 0) {
			long start = Math.abs(1 - page) * PageCount;
			long end = page * PageCount;

			if (userVO != null) {
				postVOList = postBiz.getAllCommentByUserId(userVO.getId(), start, end);

				int total = postBiz.getAllCommentByUserId(userVO.getId()).size();
				total = (int) Math.ceil((double) total / PageCount);
				json.put("Total", total);
			}

			List<ExpandInfoVO> expandInfoVOList;
			String postContent = "";
			if (postVOList.size() > 0) {
				for (PostVO postVO : postVOList) {
					// 昵称
					expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(postVO.getUserId());
					if (expandInfoVOList.size() > 0) {
						postVO.setName(expandInfoVOList.get(0).getNickName());
					}else{
						userVO = userBiz.selectUserById(postVO.getUserId(), "");
						postVO.setName(userVO.getMail());
					}
					
					// 回复数
					postVO.setCommentCount(postBiz.getCommentByPostId(postVO.getId()).size());

					// 截取帖子内容一部分
					postContent = postVO.getContent();
					if (postContent.length() >= 30) {
						postContent = postContent.substring(0, 30);
					}

					postVO.setContent(postContent);
				}

				json.put("postList", postVOList);
			}
		}

		return json.toString();
	}

	/*
	 * 添加评论
	 */
	@RequestMapping("/addComment.json")
	@ResponseBody
	public String addComment(HttpServletRequest request,
			@RequestParam("content") String content, @RequestParam("id") long id) {
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);
		JSONObject json = new JSONObject();
		if (id > 0) {
			PostVO postVO = postBiz.getPostById(id);
			String postContent = postVO.getContentText();
			if (postContent.length() >= 30) {
				postContent = postContent.substring(0, 30);
			}

			PostVO postVOClone = postVO;

			postVOClone.setContent(content);
			postVOClone.setParentId(id);
			postVOClone.setParentContentSummary(postContent);
			postVOClone.setUserId(userVO.getId());

			// 获取当前时间
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			postVOClone.setSubmitTime(timestamp);

			Integer result = postBiz.addPost(postVOClone);

			if (result > 0) {
				
				//获得评论积分
				expandInfoBiz.addPoint(AddCommentPoint, userVO.getId());
				
				json.put("success", true);
			} else {
				json.put("success", false);
			}
		}

		return json.toString();
	}

	/*
	 * 获取评论
	 */
	@RequestMapping("/getComment.json")
	@ResponseBody
	public String getComment(@RequestParam("id") long id) {
		JSONObject json = new JSONObject();
		List<ExpandInfoVO> expandInfoVOList;
		if (id != 0) {
			List<PostVO> postVOList = postBiz.getCommentByPostId(id);

			for (PostVO postVO : postVOList) {
				// 昵称
				expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(postVO.getUserId());
				if (expandInfoVOList.size() > 0) {
					postVO.setName(expandInfoVOList.get(0).getNickName());
				}else{
					UserVO userVO = userBiz.selectUserById(postVO.getUserId(), "");
					postVO.setName(userVO.getMail());
				}
			}

			json.put("postVOList", postVOList);
		}
		return json.toString();
	}

	/*
	 * 获取所有待审核的帖子
	 */
	@RequestMapping("/getAllPostByHold.json")
	@ResponseBody
	public String getAllPostByHold() {
		JSONObject json = new JSONObject();

		List<PostVO> postVOList = postBiz.getAllPostByHold();
		json.put("postVOList", postVOList);

		return json.toString();
	}

	/*
	 * 审核帖子
	 */
	@RequestMapping("/passPost.json")
	@ResponseBody
	public String passPost(@RequestParam("id") long id) {
		JSONObject json = new JSONObject();
		if (id != 0) {
			String type = "2"; // 设为普通贴
			Integer result = postBiz.passPost(type, id);
			if (result > 0) {
				json.put("success", true);
			} else {
				json.put("success", false);
			}
		}
		return json.toString();
	}

	/*
	 * 设置高亮 (by id)
	 */
	@RequestMapping("/setHighLight.json")
	@ResponseBody
	public String setHighLight(@RequestParam("Id") long id) {
		JSONObject json = new JSONObject();
		if (id != 0) {
			String highLight = "1";
			Integer result = postBiz.setHighLight(highLight, id);

			if (result > 0) {
				json.put("success", true);
			} else {
				json.put("success", false);
			}
		}
		return json.toString();
	}

	/*
	 * 设置置顶 (by id)
	 */
	@RequestMapping("/setTop.json")
	@ResponseBody
	public String setTop(@RequestParam("Id") long id) {
		JSONObject json = new JSONObject();
		if (id != 0) {
			String top = "1";
			Integer result = postBiz.setTop(top, id);

			if (result > 0) {
				json.put("success", true);
			} else {
				json.put("success", false);
			}
		}
		return json.toString();
	}

	/*
	 * 删除帖子 (by id)
	 */
	@RequestMapping("/del.json")
	@ResponseBody
	public String del(@RequestParam("Id") long id) {
		JSONObject json = new JSONObject();
		if (id != 0) {
			Integer result = postBiz.del(id);

			if (result > 0) {
				json.put("success", true);
			} else {
				json.put("success", false);
			}
		}
		return json.toString();
	}

	/*
	 * 模糊查询帖子
	 */
	@RequestMapping("/search.json")
	@ResponseBody
	public String search(HttpServletRequest request,
			@RequestParam("kw") String kw, @Param("page") long page) {
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Constants.LOGINED_USER);
		List<PostVO> postVOList = null;

		page = (page <= 0) ? 1 : page;

		if (page > 0) {
			long start = Math.abs(1 - page) * PageCount;
			long end = page * PageCount;

			if (kw != "") {
				int total = 0;
				
				if (userVO != null && userVO.getGroupId() == Constants.GroupType.admin.getValue()) {
					//管理员权限
					postVOList = postBiz.search4Limit(kw, start, end, true);
					total = postBiz.search(kw, true).size();
				} else {
					//普通用户权限
					postVOList = postBiz.search4Limit(kw, start, end, false);
					total = postBiz.search(kw, false).size();
				}

				total = (int) Math.ceil((double) total / PageCount);
				json.put("Total", total);

				List<ExpandInfoVO> expandInfoVOList;
				String postContent = "";
				if (postVOList.size() > 0) {
					for (PostVO postVO : postVOList) {
						// 昵称
						expandInfoVOList = expandInfoBiz.selExpandInfoByUserId(postVO.getUserId());
						if (expandInfoVOList.size() > 0) {
							postVO.setName(expandInfoVOList.get(0).getNickName());
						}else{
							userVO = userBiz.selectUserById(postVO.getUserId(), "");
							postVO.setName(userVO.getMail());
						}
						
						// 回复数
						postVO.setCommentCount(postBiz.getCommentByPostId(postVO.getId()).size());

						// 截取帖子内容一部分
						postContent = postVO.getContent();
						if (postContent.length() >= 30) {
							postContent = postContent.substring(0, 30);
						}
						postVO.setContent(postContent);

						// 所属版块
						String moduleName = "";
						ModuleVO m = moduleBiz.selectModuleById(postVO.getModuleId());
						if(m!=null){
							moduleName = m.getName();
						}else{
							moduleName = "未知版块";
						}
						postVO.setModuleName(moduleName);
					}

					json.put("postList", postVOList);
				}
			}
		}
		return json.toString();
	}
}
