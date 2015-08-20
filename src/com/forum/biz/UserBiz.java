package com.forum.biz;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forum.dao.UserDao;
import com.forum.utility.MD5;
import com.forum.utility.RegisterValidateService;
import com.forum.vo.UserVO;

@Service
@Transactional
public class UserBiz {

	@Autowired
	private UserDao userDao;
	
	/*
	 * �����û�
	 */
	public Integer addUser(UserVO userVO,HttpServletRequest request) throws NoSuchAlgorithmException{
		Integer result = 0;
		
		if(userDao.checkEmailExist(userVO.getMail())){
			result=-1;
		}
		else {
			String darkPassword = MD5.md5(userVO.getDarkPass());
			userVO.setDarkPass(darkPassword);
			userVO.setIsActive(0);
			userVO.setGroupId(3);//1.����Ա  2.����  3.��ͨ�û�
			result=userDao.addUser(userVO);
			
			if(result>0){
				//  ���ͼ����ʼ�
				RegisterValidateService rvs = new RegisterValidateService();
				
				String url = request.getRequestURL().toString().replace(request.getRequestURI(),"");
				
				rvs.processregister(userVO.getMail(),url);
			}
		}
		
		
		return result;
	}
	
	/*
	 * �����û�
	 */
	public Integer updateActive(long isActive,String mail){
		return userDao.updateActive(isActive,mail);
	}
	
	/*
	 * ��ѯ�û�
	 */
	public List<UserVO> selectUser(UserVO userVO) throws NoSuchAlgorithmException{
		String darkPass = MD5.md5(userVO.getDarkPass());
		return userDao.selectUser(userVO.getMail(), darkPass);
	}
	
	/*
	 * �����������
	 */
	public Integer setGroup(long groupId,String mail){
		Integer result = 0;
		
		if(userDao.checkEmailExist(mail)){
			result=userDao.updateGroup(groupId, mail);
		}
		else {
			result=-1;
		}
		
		return result;
	}
	
	/*
	 * ��ȡ�����û� ���� groupId ���⣩
	 */
	public List<UserVO> selectUserExcept(long groupId){
		return userDao.selectUserExcept(groupId);
	}
	
}
