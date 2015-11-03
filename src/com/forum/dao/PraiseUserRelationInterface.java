package com.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.forum.vo.PraiseUserRelationVO;

public interface PraiseUserRelationInterface {

	@Insert("insert into tab_praise_user_relation(col_user_id,col_post_id,col_praise_time) values(#{userId},#{postId},#{praiseTime})")
	@Results(value = { @Result(property = "id", column = "col_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "postId", column = "col_post_id"),
			@Result(property = "praiseTime", column = "col_praise_time") })
	public Integer addRelation(PraiseUserRelationVO praiseUserRelationVO);

	@Select("select * from tab_praise_user_relation where col_post_id=#{postId}")
	@Results(value = { @Result(property = "id", column = "col_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "postId", column = "col_post_id"),
			@Result(property = "praiseTime", column = "col_praise_time") })
	public List<PraiseUserRelationVO> selRelationByPostId(long postId);

	@Select("select * from tab_praise_user_relation where col_user_id=#{userId} and col_post_id=#{postId}")
	@Results(value = { @Result(property = "id", column = "col_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "postId", column = "col_post_id"),
			@Result(property = "praiseTime", column = "col_praise_time") })
	public List<PraiseUserRelationVO> checkPraiseExist(
			@Param("userId") long userId, @Param("postId") long postId);
}
