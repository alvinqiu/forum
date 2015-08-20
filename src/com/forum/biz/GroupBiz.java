package com.forum.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forum.dao.GroupDao;
import com.forum.vo.GroupVO;



@Service
@Transactional
public class GroupBiz {

	@Autowired
	private GroupDao groupDao;
	
	/*
	 * ��ȡ�����������
	 */
	public List<GroupVO> getAllGroup(){
		return groupDao.getAllGroup();
	} 
	
	/*
	 * ��ȡ������� ��by groupId��
	 */
	public GroupVO getGroupById(long groupId){
		return groupDao.getGroupById(groupId);
	}
}
