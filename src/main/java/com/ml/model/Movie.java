package com.ml.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Movie {
	@Id
	private String id;
	private String title;
	@Indexed(unique = true)
	private String imdbID;
	
	/*
	private String spanishTitle;
	private String imdbPictureURL;
	private Integer year;
	private String rtID;
	private Double rtAllCriticsRating;
	private Long rtAllCriticsNumReviews;
	private Long rtAllCriticsNumFresh;
	private Long rtAllCriticsNumRotten;
	private Long rtAllCriticsScore;
	private Double rtTopCriticsRating;
	private Long rtTopCriticsNumReviews;
	private Long rtTopCriticsNumFresh;
	private Long rtTopCriticsNumRotten;
	private Long rtTopCriticsScore;
	private Double rtAudienceRating;
	private Long rtAudienceNumRatings;
	private Long rtAudienceScore;
	private String rtPictureURL;
	*/
	
	private String dbID;	//mapping to douban ID
	private Double rating;	//mapping to rtAllCriticsRating
	private String picUrl;	//mapping to rtPictureURL
	private List<String> genres;
	
	public Movie() {}
	
	public Movie(String id, String title, String imdbID, String dbID,
			Double rating, String picUrl, List<String> genres) {
		super();
		this.id = id;
		this.title = title;
		this.imdbID = imdbID;
		this.dbID = dbID;
		this.rating = rating;
		this.picUrl = picUrl;
		this.setGenres(genres);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImdbID() {
		return imdbID;
	}
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	public String getDbID() {
		return dbID;
	}
	public void setDbID(String dbID) {
		this.dbID = dbID;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
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

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", imdbID=" + imdbID
				+ ", dbID=" + dbID + ", rating=" + rating + ", picUrl="
				+ picUrl + ", genres=" + genres + "]";
	}
	
	
	
}
