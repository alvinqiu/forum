package com.forum.biz;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forum.dao.UserDao;
import com.forum.utility.Constants;
import com.forum.utility.MD5;
import com.forum.utility.RegisterValidateService;
import com.forum.vo.UserVO;

@Service
@Transactional
public class UserBiz {

	@Autowired
	private UserDao userDao;

	/*
	 * 新增用户
	 */
	public Integer addUser(UserVO userVO, HttpServletRequest request)
			throws NoSuchAlgorithmException {
		Integer result = 0;

		if (userDao.checkEmailExist(userVO.getMail())) {
			result = -1;
		} else {
			String darkPassword = MD5.md5(userVO.getDarkPass());
			userVO.setDarkPass(darkPassword);
			userVO.setIsActive(0);
			userVO.setGroupId(Constants.GroupType.user.getValue());// 普通用户
			result = userDao.addUser(userVO);

			if (result > 0) {
				// 发送激活邮件
				sendActivationMail(userVO, request);
			}
		}

		return result;
	}

	/*
	 * 发送激活邮件
	 */
	public void sendActivationMail(UserVO userVO, HttpServletRequest request) {
		RegisterValidateService rvs = new RegisterValidateService();
		String url = request.getRequestURL().toString()
				.replace(request.getRequestURI(), "");
		String path = request.getContextPath();
		rvs.processregister(userVO.getMail(), url + path);
	}

	/*
	 * 激活用户
	 */
	public Integer updateActive(long isActive, String mail) {
		return userDao.updateActive(isActive, mail);
	}

	/*
	 * 查询用户
	 */
	public List<UserVO> selectUser(UserVO userVO)
			throws NoSuchAlgorithmException {
		String darkPass = MD5.md5(userVO.getDarkPass());
		return userDao.selectUser(userVO.getMail(), darkPass);
	}
	
	/*
	 * 查询用户 by id
	 */
	public UserVO selectUserById(long id, String username) {

		UserVO userVO = userDao.selectUserById(id);
		if (userVO == null) {
			userVO = new UserVO();
			userVO.setId(id);
			userVO.setMail(username);
			userVO.setIsActive(0);
			userVO.setGroupId(Constants.GroupType.user.getValue());// 普通用户
			userDao.addUser(userVO);
		}
		return userVO;
	}

	/*
	 * 设置用户类型
	 */
	public Integer setGroup(long groupId, String mail) {
		Integer result = 0;

		if (userDao.checkEmailExist(mail)) {
			result = userDao.updateGroup(groupId, mail);
		} else {
			result = -1;
		}

		return result;
	}

	/*
	 * 查询用户 by groupid
	 */
	public List<UserVO> selectUserExcept(long groupId) {
		return userDao.selectUserExcept(groupId);
	}

}
