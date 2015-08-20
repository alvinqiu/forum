package com.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.forum.vo.GroupVO;

public interface GroupInterface {

	@Select("select * from tab_user_group")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "code", column = "col_code"),
			@Result(property = "name", column = "col_name")
	})
	public List<GroupVO> getAll();
	
	@Select("select * from tab_user_group where col_id =#{id}")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "code", column = "col_code"),
			@Result(property = "name", column = "col_name")
	})
	public GroupVO getGroupById(long id);
}
