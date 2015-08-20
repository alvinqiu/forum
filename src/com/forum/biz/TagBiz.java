package com.forum.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forum.dao.TagDao;
import com.forum.vo.TagUserRelationVO;
import com.forum.vo.TagVO;

@Service
@Transactional
public class TagBiz {

	@Autowired
	private TagDao tagDao;
	
	/*
	 * ��ӱ�ǩ
	 */
	public Integer insertTag(String tagList,long userId){
		TagVO tagVO = new TagVO();
		TagUserRelationVO tagUserRelationVO = new TagUserRelationVO();
		String str="";
		Integer result=0;
		String[] tagArray = tagList.split(",");
		for (int i = 0; i < tagArray.length; i++) {
			str = tagArray[i];
			if(str!=""){
				tagVO.setName(str);
				result=tagDao.insert(tagVO);
				if(result>0){
					tagUserRelationVO.setTagId(tagVO.getId());
					tagUserRelationVO.setUserId(userId);
					result=tagDao.saveRelation(tagUserRelationVO);
				}
			}
		}
		return result;
	}
	
	/*
	 * ���userId��ѯ��ǩ
	 */
	public List<TagVO> selectTagByUserId(long userId){
		return tagDao.selectById(userId);
	}
}
