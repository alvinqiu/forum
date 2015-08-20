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
	 * �������
	 */
	public Integer addPost(PostVO postVO){
		return postDao.addPost(postVO);
	}
	
	/*
	 * ��ȡ��������
	 */
	public List<PostVO> getAllPost(){
		return postDao.getAllPost();
	}
	
	/*
	 * ��ȡ�������� (start ,end)
	 */
	public List<PostVO> getAllPost(long start,long end){
		return postDao.getAllPost(start,end);
	}
	
	/*
	 * ��ȡ������������ by moduleId
	 */
	public List<PostVO> getAllPostCount(long moduleId){
		return postDao.getAllPostCount(moduleId);
	}
	
	/*
	 * ��ȡ��������(limit)
	 */
	public List<PostVO> getAllPostLimit(long start,long end,long moduleId){
		return postDao.getAllPostLimit(start,end,moduleId);
	}
	
	/*
	 * ���id��ȡ����
	 */
	public PostVO getPostById(long id){
		return postDao.getPostById(id);
	}
	
	/*
	 * ���post id ��ȡ����
	 */
	public List<PostVO> getCommentByPostId(long postId){
		return postDao.getCommentByPostId(postId);
	}
	
	/*
	 * ��ȡ���������
	 */
	public List<PostVO> getAllPostByHold(){
		return postDao.getAllPostByHold();
	}
	
	/*
	 * �������
	 */
	public Integer passPost(String type,long id){
		return postDao.passPost(type, id);
	}
	
	/*
	 * ���ø��� ��by id��
	 */
	public Integer setHighLight(String highLight,long id){
		return postDao.setHighLight(highLight,id);
	}
	
	/*
	 * �����ö� ��by id��
	 */
	public Integer setTop(String top,long id){
		return postDao.setTop(top, id);
	}
	
	/*
	 * ɾ�� ��by id��
	 */
	public Integer del(long id){
		return postDao.del(id);
	}
}
