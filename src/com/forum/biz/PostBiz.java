package com.forum.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forum.dao.PostDao;
import com.forum.vo.PostVO;

@Service
@Transactional
public class PostBiz {

	@Autowired
	private PostDao postDao;

	/*
	 * 新增帖子
	 */
	public Integer addPost(PostVO postVO) {
		return postDao.addPost(postVO);
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
}
