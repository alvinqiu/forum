package com.forum.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forum.vo.PraiseUserRelationVO;

@Repository
public class PraiseUserRelationDao {

	@Autowired
	private PraiseUserRelationInterface praiseUserRelationInterface;

	/*
	 * 添加一个点赞记录
	 */
	public Integer addRelation(PraiseUserRelationVO praiseUserRelationVO) {
		return praiseUserRelationInterface.addRelation(praiseUserRelationVO);
	}

	/*
	 * 查询点赞记录 by post id
	 */
	public List<PraiseUserRelationVO> selRelationByPostId(long postId) {
		return praiseUserRelationInterface.selRelationByPostId(postId);
	}

	/*
	 * 查询用户是否已有点赞记录 by user id & by post id
	 */
	public List<PraiseUserRelationVO> checkPraiseExist(long userId, long postId) {
		return praiseUserRelationInterface.checkPraiseExist(userId, postId);
	}
}
