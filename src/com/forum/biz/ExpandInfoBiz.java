package com.forum.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forum.dao.ExpandInfoDao;
import com.forum.vo.ExpandInfoVO;

@Service
@Transactional
public class ExpandInfoBiz {

	@Autowired
	private ExpandInfoDao expandInfoDao;
	
	/*
	 * ��ȡ�û���Ϣ(by userId)
	 */
	public List<ExpandInfoVO> selExpandInfoByUserId(long userId){
		return expandInfoDao.selExpandInfoByUserId(userId);
	}
	
	/*
	 * �����û���Ϣ
	 */
	public Integer addExpandInfo(ExpandInfoVO expandInfoVO){
		return expandInfoDao.addExpandInfo(expandInfoVO);
	}
	
	/*
	 * �޸��û���Ϣ
	 */
	public Integer updateExpandInfoByUserId(ExpandInfoVO expandInfoVO){
		return expandInfoDao.updateExpandInfoByUserId(expandInfoVO);
	}
	
	/*
	 * �ж��ǳ��Ƿ��Ѵ���
	 */
	public List<ExpandInfoVO> checkNickNameIsExist(String nickName){
		return expandInfoDao.checkNickNameIsExist(nickName);
	}
	
	/*
	 * ǩ��
	 */
	public Integer signIn(long point,long userId){
		return expandInfoDao.signIn(point, userId);
	}
}
