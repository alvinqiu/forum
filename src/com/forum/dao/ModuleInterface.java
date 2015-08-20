package com.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.forum.vo.ModuleVO;

public interface ModuleInterface {

	@Select("select * from tab_module")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "name", column = "col_name"),
			@Result(property = "sort", column = "col_sort"),
			@Result(property = "desc", column = "col_desc"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "visible", column = "col_visible")
	})
	public List<ModuleVO> selectAll();
	
	@Select("select * from tab_module where col_visible=#{visible}")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "name", column = "col_name"),
			@Result(property = "sort", column = "col_sort"),
			@Result(property = "desc", column = "col_desc"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "visible", column = "col_visible")
	})
	public List<ModuleVO> selectAllByVisible(boolean visible);
	
	@Insert("insert into tab_module(col_name,col_sort,col_desc,col_parent_id,col_visible) values(#{name},#{sort},#{desc},#{parentId},#{visible})")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "name", column = "col_name"),
			@Result(property = "sort", column = "col_sort"),
			@Result(property = "desc", column = "col_desc"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "visible", column = "col_visible")
	})
	public Integer addModule(ModuleVO moduleVO);
	
	@Update("update tab_module set col_name=#{name},col_desc=#{desc},col_sort=#{sort},col_parent_id=#{parentId},col_visible=#{visible} where col_id=#{id}")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "name", column = "col_name"),
			@Result(property = "sort", column = "col_sort"),
			@Result(property = "desc", column = "col_desc"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "visible", column = "col_visible")
	})
	public Integer editModule(ModuleVO moduleVO);
	
	@Delete("delete from tab_module where col_id=#{id}")
	@Results(value={
			@Result(property = "id", column = "col_id"),
			@Result(property = "name", column = "col_name"),
			@Result(property = "sort", column = "col_sort"),
			@Result(property = "desc", column = "col_desc"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "visible", column = "col_visible")
	})
	public Integer delModule(long id);
}
