package com.forum.vo;

import java.sql.Timestamp;

public class PostVO {

	private long id;
	private String subject;
	private String content;
	private String contentText;
	private String type;
	private String highLight;
	private String top;
	private long parentId;
	private String parentContentSummary;
	private String attach;
	private Timestamp submitTime;
	private long moduleId;
	private long userId;
	private long praise;
	private String name;// 昵称(临时)
	private long commentCount;// 回复数(临时)
	private String moduleName;// 版块名称(临时)
	private boolean checkPraise;// 当前用户是否已点赞(临时)
	private String imgStr;// 首页图片路径

	public String getImgStr() {
		return imgStr;
	}

	public void setImgStr(String imgStr) {
		this.imgStr = imgStr;
	}

	public boolean isCheckPraise() {
		return checkPraise;
	}

	public void setCheckPraise(boolean checkPraise) {
		this.checkPraise = checkPraise;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHighLight() {
		return highLight;
	}

	public void setHighLight(String highLight) {
		this.highLight = highLight;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getParentContentSummary() {
		return parentContentSummary;
	}

	public void setParentContentSummary(String parentContentSummary) {
		this.parentContentSummary = parentContentSummary;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Timestamp getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPraise() {
		return praise;
	}

	public void setPraise(long praise) {
		this.praise = praise;
	}
}
