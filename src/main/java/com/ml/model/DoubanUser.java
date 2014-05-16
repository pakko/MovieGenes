package com.ml.model;

import org.springframework.data.annotation.Id;

public class DoubanUser {
	@Id
	private String id;
	private Integer marked;
	
	public DoubanUser(){}

	public DoubanUser(String id, Integer marked) {
		super();
		this.id = id;
		this.marked = marked;
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

	@Override
	public String toString() {
		return "DoubanUser [id=" + id + ", marked=" + marked + "]";
	}
	
	
}
