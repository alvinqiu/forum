package com.forum.vo;

public class UserVO {

	private long id;
	private String mail;
	private String darkPass;
	private long groupId;
	private long isActive;
	private String groupName;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getDarkPass() {
		return darkPass;
	}
	public void setDarkPass(String darkPass) {
		this.darkPass = darkPass;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public long getIsActive() {
		return isActive;
	}
	public void setIsActive(long isActive) {
		this.isActive = isActive;
	}
	
	
}
