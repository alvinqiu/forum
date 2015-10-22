package com.forum.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forum.dao.ModuleDao;
import com.forum.vo.ModuleVO;

@Service
@Transactional
public class ModuleBiz {

	@Autowired
	private ModuleDao moduleDao;
	
	/*
	 * ��ȡ����ģ�� (admin)
	 */
	public List<ModuleVO> selectAllModule(){
		return moduleDao.selectAll();
	}
	
	/*
	 * ��ȡ����ģ�� (others)
	 */
	public List<ModuleVO> selectAllModule(boolean visible){
		return moduleDao.selectAll(visible);
	}
	
	/*
	 * 根据Id查询版块
	 */
	public ModuleVO selectModuleById(long id){
		return moduleDao.selectModuleById(id);
	}
	
	/*
	 * ���ģ��
	 */
	public Integer addModule(ModuleVO moduleVO){
		return moduleDao.addModule(moduleVO);
	}
	
	/*
	 * �޸�ģ��
	 */
	public Integer editModule(ModuleVO moduleVO){
		return moduleDao.editModule(moduleVO);
	}
	
	/*
	 * ɾ��ģ��
	 */
	public Integer delModule(long id){
		return moduleDao.delModule(id);
	}
}
