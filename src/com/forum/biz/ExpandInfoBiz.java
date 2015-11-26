package com.forum.biz;

import java.sql.Timestamp;
import java.util.Calendar;
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
	 * 查询个人信息(by userId)
	 */
	public List<ExpandInfoVO> selExpandInfoByUserId(long userId) {
		return expandInfoDao.selExpandInfoByUserId(userId);
	}

	/*
	 * 添加个人信息
	 */
	public Integer addExpandInfo(ExpandInfoVO expandInfoVO) {
		return expandInfoDao.addExpandInfo(expandInfoVO);
	}

	/*
	 *  修改个人信息 (by userId)
	 */
	public Integer updateExpandInfoByUserId(ExpandInfoVO expandInfoVO) {
		return expandInfoDao.updateExpandInfoByUserId(expandInfoVO);
	}

	/*
	 * 查询昵称是否已存在
	 */
	public List<ExpandInfoVO> checkNickNameIsExist(String nickName) {
		return expandInfoDao.checkNickNameIsExist(nickName);
	}
	
	/*
	 * 添加积分
	 */
	public Integer addPoint(long point, long userId){
		return expandInfoDao.addPoint(point, userId);
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
			if (checkSignInTime(expandInfoVOList.get(0).getPointSignInTime())) {
				return -1;// 已签到
			}
		} else {
			return 0;// 无个人信息
		}

		// 获取当前时间
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		return expandInfoDao.signIn(point, userId, timestamp);
	}

	/*
	 * 判断签到时间是否为今天
	 */
	public boolean checkSignInTime(Timestamp ts) {

		if (ts != null) {
			if (ts.after(getDayBegin()) && ts.before(getDayEnd())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 获取当天开始时间戳
	 */
	public Timestamp getDayBegin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 001);
		return new Timestamp(cal.getTimeInMillis());
	}

	/*
	 * 获取当天结束时间戳
	 */
	public Timestamp getDayEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return new Timestamp(cal.getTimeInMillis());
	}
}
