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
	 * 新增标签
	 */
	public Integer insertTag(String tagList, long userId) {
		TagVO tagVO = new TagVO();
		TagUserRelationVO tagUserRelationVO = new TagUserRelationVO();
		String str = "";
		Integer result = 0;
		String[] tagArray = tagList.split(",");
		
		for (int i = 0; i < tagArray.length; i++) {
			str = tagArray[i];
			if (str != "") {
				
				//添加标签
				tagVO.setName(str);
				result = tagDao.insert(tagVO);
				
				if (result > 0) {
					tagUserRelationVO.setTagId(tagVO.getId());
					tagUserRelationVO.setUserId(userId);
					result = tagDao.insertRelation(tagUserRelationVO);
				}
			}
		}
		
		return result;
	}

	/*
	 * 根据userId查询标签
	 */
	public List<TagVO> selectTagByUserId(long userId) {
		return tagDao.selectById(userId);
	}
}
