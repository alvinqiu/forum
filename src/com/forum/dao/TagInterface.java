package com.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.forum.vo.TagUserRelationVO;
import com.forum.vo.TagVO;


public interface TagInterface {

	@Insert("insert into tab_tag(col_name) values(#{name})")
	@SelectKey(before = false, keyProperty = "id", resultType = long.class, statement = { "select LAST_INSERT_ID() as id" })
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "name", column = "col_name")
	})
	public Integer save(TagVO tagVO);
	
	@Select("select * from tab_tag where col_id in (select col_tag_id from tab_tag_user_relation where col_user_id=#{userId})")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "name", column = "col_name"),
			@Result(property = "tagId", column = "col_tag_id"),
			@Result(property = "userId", column = "col_user_id")
	})
	public List<TagVO> selectTagByUserId(long userId);
	
	@Insert("insert into tab_tag_user_relation(col_user_id,col_tag_id) values(#{userId},#{tagId})")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "tagId", column = "col_tag_id")
	})
	public Integer saveRelation(TagUserRelationVO tagUserRelationVO);
}
