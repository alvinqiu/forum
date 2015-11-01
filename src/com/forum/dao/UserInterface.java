package com.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.forum.vo.UserVO;

public interface UserInterface {
	
	@Insert("insert into tab_user(col_id,col_email,col_dark_pass,col_group_id,col_is_active)"
			+ " values(#{id},#{mail},#{darkPass},#{groupId},#{isActive})")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "mail", column = "col_email"),
			@Result(property = "darkPass", column = "col_dark_pass"),
			@Result(property = "groupId", column = "col_group_id"),
			@Result(property = "isActive", column = "col_is_active")
	})
	public Integer add(UserVO userVO);
	
	@Select("select * from tab_user where col_email=#{mail}")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "mail", column = "col_email"),
			@Result(property = "darkPass", column = "col_dark_pass"),
			@Result(property = "groupId", column = "col_group_id"),
			@Result(property = "isActive", column = "col_is_active")
	})
	public List<UserVO> checkEmailExist(String mail);
	
	@Update("update tab_user set col_is_active=#{isActive} where col_email=#{mail}")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "mail", column = "col_email"),
			@Result(property = "darkPass", column = "col_dark_pass"),
			@Result(property = "groupId", column = "col_group_id"),
			@Result(property = "isActive", column = "col_is_active")
	})
	public Integer updateActive(@Param("isActive") long isActive,@Param("mail") String mail);
	
	@Select("select * from tab_user where col_email=#{mail} and col_dark_pass=#{darkPass}")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "mail", column = "col_email"),
			@Result(property = "darkPass", column = "col_dark_pass"),
			@Result(property = "groupId", column = "col_group_id"),
			@Result(property = "isActive", column = "col_is_active")
	})
	public List<UserVO> selectUser(@Param("mail") String mail,@Param("darkPass") String darkPass);
	
	@Select("select * from tab_user where col_id=#{id}")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "mail", column = "col_email"),
			@Result(property = "darkPass", column = "col_dark_pass"),
			@Result(property = "groupId", column = "col_group_id"),
			@Result(property = "isActive", column = "col_is_active")
	})
	public UserVO selectUserById(long id);
	
	@Update("update tab_user set col_group_id=#{groupId} where col_email=#{mail}")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "mail", column = "col_email"),
			@Result(property = "darkPass", column = "col_dark_pass"),
			@Result(property = "groupId", column = "col_group_id"),
			@Result(property = "isActive", column = "col_is_active")
	})
	public Integer updateGroup(@Param("groupId") long groupId,@Param("mail") String mail);
	
	@Select("select * from tab_user where col_group_id!=#{groupId} and col_is_active=1")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "mail", column = "col_email"),
			@Result(property = "darkPass", column = "col_dark_pass"),
			@Result(property = "groupId", column = "col_group_id"),
			@Result(property = "isActive", column = "col_is_active")
	})
	public List<UserVO> selectUserExcept(long groupId);
	
	@Select("select * from tab_user where col_group_id=#{groupId} and col_is_active=1")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "mail", column = "col_email"),
			@Result(property = "darkPass", column = "col_dark_pass"),
			@Result(property = "groupId", column = "col_group_id"),
			@Result(property = "isActive", column = "col_is_active")
	})
	public List<UserVO> selectUserByGroupId(long groupId);
}
