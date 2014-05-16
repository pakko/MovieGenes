package com.ml.model;

import java.util.List;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
    @CompoundIndex(name = "doubanRatingsIndex", def = "{'userID': -1, 'doubanUrl': -1}", unique = true)
})
public class DoubanRatings {
	private String userID;
	private String doubanUrl;
	private String doubanTitle;
	private String imdbID;
	private Double userRate;
	private Long timestamp;
	private String picUrl;
	private List<String> genres;
	private Double doubanRate;
	
	public DoubanRatings() {}

	public DoubanRatings(String userID, String doubanUrl, String doubanTitle,
			String imdbID, Double userRate, Long timestamp, String picUrl,
			List<String> genres, Double doubanRate) {
		super();
		this.userID = userID;
		this.doubanUrl = doubanUrl;
		this.doubanTitle = doubanTitle;
		this.imdbID = imdbID;
		this.userRate = userRate;
		this.timestamp = timestamp;
		this.picUrl = picUrl;
		this.genres = genres;
		this.doubanRate = doubanRate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getDoubanUrl() {
		return doubanUrl;
	}

	public void setDoubanUrl(String doubanUrl) {
		this.doubanUrl = doubanUrl;
	}

	public String getDoubanTitle() {
		return doubanTitle;
	}

	public void setDoubanTitle(String doubanTitle) {
		this.doubanTitle = doubanTitle;
	}

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public Double getUserRate() {
		return userRate;
	}

	public void setUserRate(Double userRate) {
		this.userRate = userRate;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public Double getDoubanRate() {
		return doubanRate;
	}

	public void setDoubanRate(Double doubanRate) {
		this.doubanRate = doubanRate;
	}

	@Override
	public String toString() {
		return "DoubanRatings [userID=" + userID + ", doubanUrl=" + doubanUrl
				+ ", doubanTitle=" + doubanTitle + ", imdbID=" + imdbID
				+ ", userRate=" + userRate + ", timestamp=" + timestamp
				+ ", picUrl=" + picUrl + ", genres=" + genres + ", doubanRate="
				+ doubanRate + "]";
	}

	
}
