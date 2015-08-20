package com.forum.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forum.vo.GroupVO;

@Repository
public class GroupDao {

	@Autowired
	private GroupInterface groupInterface;
	
	/*
	 * ��ȡ�����������
	 */
	public List<GroupVO> getAllGroup(){
		return groupInterface.getAll();
	}
	
	/*
	 * ��ȡ������� ��by groupId��
	 */
	public GroupVO getGroupById(long groupId){
		return groupInterface.getGroupById(groupId);
	}
}
