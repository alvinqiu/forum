package com.forum.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forum.vo.PostVO;

@Repository
public class PostDao {

	@Autowired
	private PostInterface postInterface;

	/*
	 * �������
	 */
	public Integer addPost(PostVO postVO) {
		return postInterface.addPost(postVO);
	}

	/*
	 * ��ȡ��������
	 */
	public List<PostVO> getAllPost() {
		return postInterface.getAllPost();
	}

	/*
	 * 获取范围内帖子(start ,end) 管理员
	 */
	public List<PostVO> getAllPost(long start, long end) {
		return postInterface.getAllPostTopFive4Admin(start, end);
	}

	/*
	 * 获取范围内帖子(start ,end) 管理员除外角色
	 */
	public List<PostVO> getAllPost(long start, long end, boolean visible) {
		return postInterface.getAllPostTopFive(start, end, visible);
	}

	/*
	 * ��ȡ������������ by moduleId
	 */
	public List<PostVO> getAllPostCount(long moduleId) {
		return postInterface.getAllPostCount(moduleId);
	}

	/*
	 * ��ȡ�������� (limit)
	 */
	public List<PostVO> getAllPostLimit(long start, long end, long moduleId) {
		return postInterface.getAllPostLimit(start, end, moduleId);
	}

	/*
	 * ���id��ȡ����
	 */
	public PostVO getPostById(long id) {
		return postInterface.getPostById(id);
	}

	/*
	 * 根据用户Id获取一个范围的帖子 （包含未审核帖子）
	 */
	public List<PostVO> getPostByUserId(long userId, long start, long end) {
		return postInterface.getPostByUserIdLimit(userId, start, end);
	}
	
	/*
	 * 根据用户Id获取帖子 （包含未审核帖子）
	 */
	public List<PostVO> getPostByUserId(long userId) {
		return postInterface.getPostByUserId(userId);
	}

	/*
	 * ���post id ��ȡ����
	 */
	public List<PostVO> getCommentByPostId(long postId) {
		return postInterface.getCommentByPostId(postId);
	}

	/*
	 * ��ȡ���������
	 */
	public List<PostVO> getAllPostByHold() {
		return postInterface.getAllPostByHold();
	}

	/*
	 * �������
	 */
	public Integer passPost(String type, long id) {
		return postInterface.passPostById(type, id);
	}

	/*
	 * ���ø��� ��by id��
	 */
	public Integer setHighLight(String highLight, long id) {
		return postInterface.setHighLight(highLight, id);
	}

	/*
	 * �����ö� ��by id��
	 */
	public Integer setTop(String top, long id) {
		return postInterface.setTop(top, id);
	}

	/*
	 * ɾ�� ��by id��
	 */
	public Integer del(long id) {
		return postInterface.del(id);
	}
}
