package com.forum.vo;

import java.sql.Timestamp;

public class PraiseUserRelationVO {

	private long id;
	private long userId;
	private long postId;
	private Timestamp praiseTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public Timestamp getPraiseTime() {
		return praiseTime;
	}

	public void setPraiseTime(Timestamp praiseTime) {
		this.praiseTime = praiseTime;
	}

}
