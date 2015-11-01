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
	 * 查询用户
	 */
	public List<UserVO> selectUser(String mail,String darkPass){
		return userInterface.selectUser(mail, darkPass);
	}
	
	/*
	 * 查询用户 by id
	 */
	public UserVO selectUserById(long id){
		return userInterface.selectUserById(id);
	}
	
	/*
	 * �޸��û��������
	 */
	public Integer updateGroup(long groupId,String mail){
		return userInterface.updateGroup(groupId, mail);
	}
	
	/*
	 *  获取用户 除了 groupid
	 */
	public List<UserVO> selectUserExcept(long groupId){
		return userInterface.selectUserExcept(groupId);
	}
	
	/*
	 *  获取用户 by groupid
	 */
	public List<UserVO> selectUserByGroupId(long groupId){
		return userInterface.selectUserByGroupId(groupId);
	}
}
