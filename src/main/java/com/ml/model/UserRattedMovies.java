package com.ml.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
    @CompoundIndex(name = "userRattedMoviesIndex", def = "{'userID': -1, 'movieID': -1}", unique = true)
})
public class UserRattedMovies {
	@Id
	private String id;
	private Long userID;
	private Long movieID;
	private Double rating;
	private Long timestamp;
	
	public UserRattedMovies() {}

	public UserRattedMovies(Long userID, Long movieID, Double rating,
			Long timestamp) {
		super();
		this.userID = userID;
		this.movieID = movieID;
		this.rating = rating;
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "UserRatedMovies [userID=" + userID
				+ ", movieID=" + movieID + ", rating=" + rating
				+ ", timestamp=" + timestamp + "]";
	}
	
	
	
}
