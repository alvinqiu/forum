package com.forum.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forum.vo.TagUserRelationVO;
import com.forum.vo.TagVO;

@Repository
public class TagDao {

	@Autowired
	private TagInterface tagInterface;
	
	/*
	 * 添加标签
	 */
	public Integer insert(TagVO tagVO){
		return tagInterface.insert(tagVO);
	}
	
	/*
	 * 根据userId查询标签
	 */
	public List<TagVO> selectById(long userId){
		return tagInterface.selectTagByUserId(userId);
	}
	
	/*
	 * 添加user与tag的关系
	 */
	public Integer insertRelation(TagUserRelationVO tagUserRelationVO){
		return tagInterface.insertRelation(tagUserRelationVO);
	}
}
