package com.forum.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forum.vo.ModuleVO;

@Repository
public class ModuleDao {

	@Autowired
	private ModuleInterface moduleInterface;
	
	/*
	 * ��ȡ����ģ�� (admin)
	 */
	public List<ModuleVO> selectAll(){
		return moduleInterface.selectAll();
	}
	
	/*
	 * ��ȡ����ģ�� (others)
	 */
	public List<ModuleVO> selectAll(boolean visible){
		return moduleInterface.selectAllByVisible(visible);
	}
	
	/*
	 * 根据id查询版块
	 */
	public ModuleVO selectModuleById(long id){
		return moduleInterface.selectModuleById(id);
	}
	
	/*
	 * ���ģ��
	 */
	public Integer addModule(ModuleVO moduleVO){
		return moduleInterface.addModule(moduleVO);
	}
	
	/*
	 * �޸�ģ��
	 */
	public Integer editModule(ModuleVO moduleVO){
		return moduleInterface.editModule(moduleVO);
	}
	
	/*
	 * ɾ��ģ��
	 */
	public Integer delModule(long id){
		return moduleInterface.delModule(id);
	}
}
