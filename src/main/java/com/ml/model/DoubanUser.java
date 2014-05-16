package com.ml.model;

import org.springframework.data.annotation.Id;

public class DoubanUser {
	@Id
	private String id;
	private Integer marked;
	private String userID;
	
	public DoubanUser(){}

	public DoubanUser(String id, Integer marked, String userID) {
		super();
		this.id = id;
		this.marked = marked;
		this.userID = userID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMarked() {
		return marked;
	}

	public void setMarked(Integer marked) {
		this.marked = marked;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "DoubanUser [id=" + id + ", marked=" + marked + ", userID="
				+ userID + "]";
	}

	
}
