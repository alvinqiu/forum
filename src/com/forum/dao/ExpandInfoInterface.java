package com.forum.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.forum.vo.ExpandInfoVO;

public interface ExpandInfoInterface {
	@Select("select * from tab_expand_info where col_user_id=#{userId}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "mobile", column = "col_mobile"),
			@Result(property = "head", column = "col_head"),
			@Result(property = "nickName", column = "col_nickname"),
			@Result(property = "point", column = "col_point"),
			@Result(property = "gender", column = "col_gender"),
			@Result(property = "addedTime", column = "col_added_time"),
			@Result(property = "birthday", column = "col_birthday"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "pointSignInTime", column = "col_point_signin_time"),
			@Result(property = "tags", column = "col_tags") })
	public List<ExpandInfoVO> selExpandInfoByUserId(long userId);

	@Insert("insert into tab_expand_info(col_mobile,col_head,col_nickname,col_gender,col_added_time,col_birthday,col_user_id,col_point_signin_time,col_tags)"
			+ " values(#{mobile},#{head},#{nickName},#{gender},#{addedTime},#{birthday},#{userId},#{pointSignInTime},#{tags})")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "mobile", column = "col_mobile"),
			@Result(property = "head", column = "col_head"),
			@Result(property = "nickName", column = "col_nickname"),
			@Result(property = "point", column = "col_point"),
			@Result(property = "gender", column = "col_gender"),
			@Result(property = "addedTime", column = "col_added_time"),
			@Result(property = "birthday", column = "col_birthday"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "pointSignInTime", column = "col_point_signin_time"),
			@Result(property = "tags", column = "col_tags") })
	public Integer addExpandInfo(ExpandInfoVO expandInfoVO);

	@Update("update tab_expand_info set col_mobile=#{mobile},col_head=#{head},col_nickname=#{nickName},col_gender=#{gender},col_added_time=#{addedTime},col_birthday=#{birthday},col_tags=#{tags}"
			+ " where col_user_id=#{userId}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "mobile", column = "col_mobile"),
			@Result(property = "head", column = "col_head"),
			@Result(property = "nickName", column = "col_nickname"),
			@Result(property = "point", column = "col_point"),
			@Result(property = "gender", column = "col_gender"),
			@Result(property = "addedTime", column = "col_added_time"),
			@Result(property = "birthday", column = "col_birthday"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "pointSignInTime", column = "col_point_signin_time"),
			@Result(property = "tags", column = "col_tags") })
	public Integer updateExpandInfoByUserId(ExpandInfoVO expandInfoVO);

	@Select("select * from tab_expand_info where col_nickname=#{nickName}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "mobile", column = "col_mobile"),
			@Result(property = "head", column = "col_head"),
			@Result(property = "nickName", column = "col_nickname"),
			@Result(property = "point", column = "col_point"),
			@Result(property = "gender", column = "col_gender"),
			@Result(property = "addedTime", column = "col_added_time"),
			@Result(property = "birthday", column = "col_birthday"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "pointSignInTime", column = "col_point_signin_time"),
			@Result(property = "tags", column = "col_tags") })
	public List<ExpandInfoVO> checkNickNameIsExist(String nickName);

	@Update("update tab_expand_info set col_point = ifnull(col_point,0)+#{point} , col_point_signin_time=#{pointSignInTime} where col_user_id=#{userId}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "mobile", column = "col_mobile"),
			@Result(property = "head", column = "col_head"),
			@Result(property = "nickName", column = "col_nickname"),
			@Result(property = "point", column = "col_point"),
			@Result(property = "gender", column = "col_gender"),
			@Result(property = "addedTime", column = "col_added_time"),
			@Result(property = "birthday", column = "col_birthday"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "pointSignInTime", column = "col_point_signin_time"),
			@Result(property = "tags", column = "col_tags") })
	public Integer signIn(@Param("point") long point,
			@Param("userId") long userId,
			@Param("pointSignInTime") Timestamp pointSignInTime);
}
