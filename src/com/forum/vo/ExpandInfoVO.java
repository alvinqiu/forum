package com.forum.vo;

import java.sql.Timestamp;

public class ExpandInfoVO {

	private long id;
	private String mobile;
	private String head;
	private String nickName;
	private long point;
	private int gender;
	private Timestamp addedTime;
	private String birthday;
	private long userId;
	private Timestamp pointSignInTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Timestamp getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(Timestamp addedTime) {
		this.addedTime = addedTime;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Timestamp getPointSignInTime() {
		return pointSignInTime;
	}

	public void setPointSignInTime(Timestamp pointSignInTime) {
		this.pointSignInTime = pointSignInTime;
	}
}
