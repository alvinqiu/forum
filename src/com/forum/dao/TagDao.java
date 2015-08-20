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
	 * ��ӱ�ǩ
	 */
	public Integer insert(TagVO tagVO){
		return tagInterface.save(tagVO);
	}
	
	/*
	 * ���userId��ѯ��ǩ
	 */
	public List<TagVO> selectById(long userId){
		return tagInterface.selectTagByUserId(userId);
	}
	
	/*
	 * �洢��ǩ���û���Ӧ��ϵ
	 */
	public Integer saveRelation(TagUserRelationVO tagUserRelationVO){
		return tagInterface.saveRelation(tagUserRelationVO);
	}
}
