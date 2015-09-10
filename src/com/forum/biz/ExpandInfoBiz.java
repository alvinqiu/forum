package com.forum.biz;

import java.sql.Timestamp;
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
	public List<ExpandInfoVO> selExpandInfoByUserId(long userId) {
		return expandInfoDao.selExpandInfoByUserId(userId);
	}

	/*
	 * �����û���Ϣ
	 */
	public Integer addExpandInfo(ExpandInfoVO expandInfoVO) {
		return expandInfoDao.addExpandInfo(expandInfoVO);
	}

	/*
	 * �޸��û���Ϣ
	 */
	public Integer updateExpandInfoByUserId(ExpandInfoVO expandInfoVO) {
		return expandInfoDao.updateExpandInfoByUserId(expandInfoVO);
	}

	/*
	 * �ж��ǳ��Ƿ��Ѵ���
	 */
	public List<ExpandInfoVO> checkNickNameIsExist(String nickName) {
		return expandInfoDao.checkNickNameIsExist(nickName);
	}

	/*
	 * 签到
	 */
	public Integer signIn(long point, long userId) {
		// 获取用户个人信息
		List<ExpandInfoVO> expandInfoVOList = expandInfoDao
				.selExpandInfoByUserId(userId);

		if (expandInfoVOList.size() > 0) {
			// 判断签到时间是否为今天
			long day = checkSignInTime(expandInfoVOList.get(0).getPointSignInTime());

			if (day == 0) {
				return -1;//已签到
			}
		}else{
			return 0;//无个人信息
		}

		// 获取当前时间
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		return expandInfoDao.signIn(point, userId, timestamp);
	}
	
	/*
	 * 判断签到时间是否为今天
	 */
	public long checkSignInTime(Timestamp ts) {
		Timestamp today = new Timestamp(System.currentTimeMillis());

		long day = (today.getTime() - ts.getTime()) / 1000 / 60 / 60 / 24;
		return day;
	}
}
