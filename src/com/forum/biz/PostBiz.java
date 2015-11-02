package com.forum.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forum.dao.PostDao;
import com.forum.dao.UserDao;
import com.forum.utility.Constants;
import com.forum.utility.SendMail;
import com.forum.vo.PostVO;
import com.forum.vo.UserVO;

@Service
@Transactional
public class PostBiz {

	@Autowired
	private PostDao postDao;

	@Autowired
	private UserDao userDao;

	/*
	 * 新增帖子
	 */
	public Integer addPost(PostVO postVO) {
		return postDao.addPost(postVO);
	}

	/*
	 * 发送审核提醒邮件
	 */
	public void sendMsgMail(PostVO postVO) {
		// 添加邮件标题
		String subject = "有待审核帖子，请尽快至管理后台进行帖子审核！";

		// 添加邮件内容
		StringBuffer sb = new StringBuffer();
		sb.append("请登录晟越科技用户论坛管理后台审核以下帖子：<br/>");
		sb.append("标题: " + postVO.getSubject() + "<br/>");
		sb.append("作者: " + postVO.getName() + "<br/>");

		StringBuffer toEmail = new StringBuffer();
		// 获取管理员邮箱
		List<UserVO> userVOList = userDao
				.selectUserByGroupId(Constants.GroupType.admin.getValue());
		for (UserVO userVO : userVOList) {
			if (toEmail.length() != 0) {
				toEmail.append(",");
			}
			toEmail.append(userVO.getMail());
		}

		// 发送邮件
		SendMail.send(toEmail.toString(), subject, sb.toString());
	}

	/*
	 * 获取所有帖子
	 */
	public List<PostVO> getAllPost() {
		return postDao.getAllPost();
	}

	/*
	 * 获取范围内帖子 (start ,end)
	 */
	public List<PostVO> getAllPost(long start, long end, boolean visible) {
		if (visible) {// 管理员
			return postDao.getAllPost(start, end);
		} else {
			return postDao.getAllPost(start, end, false);
		}
	}

	/*
	 * 获取板块内帖子的数量 (by moduleId)
	 */
	public List<PostVO> getAllPostCount(long moduleId) {
		return postDao.getAllPostCount(moduleId);
	}

	/*
	 * 获取板块内所有帖子(limit)
	 */
	public List<PostVO> getAllPostLimit(long start, long end, long moduleId) {
		return postDao.getAllPostLimit(start, end, moduleId);
	}

	/*
	 * 根据id获取帖子
	 */
	public PostVO getPostById(long id) {
		return postDao.getPostById(id);
	}

	/*
	 * 根据用户Id获取一个范围的帖子 （包含未审核帖子）
	 */
	public List<PostVO> getPostByUserId(long userId, long start, long end) {
		return postDao.getPostByUserId(userId, start, end);
	}

	/*
	 * 根据用户Id获取帖子 （包含未审核帖子）
	 */
	public List<PostVO> getPostByUserId(long userId) {
		return postDao.getPostByUserId(userId);
	}

	/*
	 * 根据用户Id获取一个范围的评论
	 */
	public List<PostVO> getAllCommentByUserId(long userId, long start, long end) {
		return postDao.getAllCommentByUserId(userId, start, end);
	}

	/*
	 * 根据用户Id获取评论
	 */
	public List<PostVO> getAllCommentByUserId(long userId) {
		return postDao.getAllCommentByUserId(userId);
	}

	/*
	 * 根据帖子id获取对应的所有评论
	 */
	public List<PostVO> getCommentByPostId(long postId) {
		return postDao.getCommentByPostId(postId);
	}

	/*
	 * 获取所有待审核的帖子
	 */
	public List<PostVO> getAllPostByHold() {
		return postDao.getAllPostByHold();
	}

	/*
	 * 审核帖子
	 */
	public Integer passPost(String type, long id) {
		return postDao.passPost(type, id);
	}

	/*
	 * 设置高亮
	 */
	public Integer setHighLight(String highLight, long id) {
		return postDao.setHighLight(highLight, id);
	}

	/*
	 * 设置置顶
	 */
	public Integer setTop(String top, long id) {
		return postDao.setTop(top, id);
	}

	/*
	 * 删除帖子
	 */
	public Integer del(long id) {
		return postDao.del(id);
	}

	/*
	 * 点赞 (by id)
	 */
	public Integer addPraise(long praise, long id) {
		return postDao.addPraise(praise, id);
	}

	/*
	 * 模糊查询(limit) visible true:是管理员; false:普通用户
	 */
	public List<PostVO> search4Limit(String kw, long start, long end,
			boolean visible) {
		if (visible) {
			return postDao.search4LimitToAdmin(kw, start, end);
		} else {
			return postDao.search4Limit(kw, start, end);
		}
	}

	/*
	 * 模糊查询 visible true:是管理员; false:普通用户
	 */
	public List<PostVO> search(String kw, boolean visible) {
		if (visible) {
			return postDao.searchToAdmin(kw);
		} else {
			return postDao.search(kw);
		}
	}
}
