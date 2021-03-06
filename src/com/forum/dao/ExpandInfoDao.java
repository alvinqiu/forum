package com.forum.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forum.vo.ExpandInfoVO;

@Repository
public class ExpandInfoDao {

	@Autowired
	private ExpandInfoInterface expandInfoInterface;

	/*
	 * ��ȡ�û���Ϣ(by userId)
	 */
	public List<ExpandInfoVO> selExpandInfoByUserId(long userId) {
		return expandInfoInterface.selExpandInfoByUserId(userId);
	}

	/*
	 * 添加个人信息
	 */
	public Integer addExpandInfo(ExpandInfoVO expandInfoVO) {
		return expandInfoInterface.addExpandInfo(expandInfoVO);
	}

	/*
	 * �޸��û���Ϣ
	 */
	public Integer updateExpandInfoByUserId(ExpandInfoVO expandInfoVO) {
		return expandInfoInterface.updateExpandInfoByUserId(expandInfoVO);
	}

	/*
	 * �ж��ǳ��Ƿ��Ѵ���
	 */
	public List<ExpandInfoVO> checkNickNameIsExist(String nickName) {
		return expandInfoInterface.checkNickNameIsExist(nickName);
	}

	/*
	 * 签到
	 */
	public Integer signIn(long point, long userId, Timestamp pointSignInTime) {
		return expandInfoInterface.signIn(point, userId, pointSignInTime);
	}
	
	/*
	 * 添加积分
	 */
	public Integer addPoint(long point, long userId) {
		return expandInfoInterface.addPoint(point, userId);
	}
}
