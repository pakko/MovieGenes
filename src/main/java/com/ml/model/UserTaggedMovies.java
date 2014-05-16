package com.ml.model;

public class UserTaggedMovies {
	private Long userID;
	private Long movieID;
	private Long tagID;
	private Long timestamp;
	
	public UserTaggedMovies() {}

	public UserTaggedMovies(Long userID, Long movieID, Long tagID,
			Long timestamp) {
		super();
		this.userID = userID;
		this.movieID = movieID;
		this.tagID = tagID;
		this.timestamp = timestamp;
	}
	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getMovieID() {
		return movieID;
	}

	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}

	public Long getTagID() {
		return tagID;
	}

	public void setTagID(Long tagID) {
		this.tagID = tagID;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "UserTaggedMovies [userID=" + userID
				+ ", movieID=" + movieID + ", tagID=" + tagID + ", timestamp="
				+ timestamp + "]";
	}

	
	
}
