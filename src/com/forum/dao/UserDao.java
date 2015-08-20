package com.forum.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forum.dao.UserInterface;
import com.forum.vo.UserVO;

@Repository
public class UserDao {

	@Autowired
	private UserInterface userInterface;
	
	/*
	 * �����û�
	 */
	public Integer addUser(UserVO userVO){
		return userInterface.add(userVO);
	}
	
	/*
	 * �����ַ�Ƿ��Ѵ���
	 */
	public Boolean checkEmailExist(String mail){
		List<UserVO> result = userInterface.checkEmailExist(mail);
		
		return result.size() > 0 ? true : false;
	}
	
	/*
	 * �����û�
	 */
	public Integer updateActive(long isActive,String mail){
		return userInterface.updateActive(isActive,mail);
	}
	
	/*
	 * ��ѯ�û�
	 */
	public List<UserVO> selectUser(String mail,String darkPass){
		return userInterface.selectUser(mail, darkPass);
	}
	
	/*
	 * �޸��û��������
	 */
	public Integer updateGroup(long groupId,String mail){
		return userInterface.updateGroup(groupId, mail);
	}
	
	/*
	 *  ��ȡ�����û� ���� groupId ���⣩
	 */
	public List<UserVO> selectUserExcept(long groupId){
		return userInterface.selectUserExcept(groupId);
	}
}
