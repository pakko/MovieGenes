package com.ml.model;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
    @CompoundIndex(name = "recommendItemsIndex", def = "{'userID': -1, 'similarUserID': -1}", unique = true)
})
public class RecommendItems {
	@Id
	String id;
	String userID;
	String similarUserID;
	String userSimilarity;
	long[] commons;
	long[] recoomendItems;
	
	public RecommendItems() {}
			
	public RecommendItems(String userID, String similarUserID,
			String userSimilarity, long[] commons, long[] recoomendItems) {
		this.userID = userID;
		this.similarUserID = similarUserID;
		this.userSimilarity = userSimilarity;
		this.commons = commons;
		this.recoomendItems = recoomendItems;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getSimilarUserID() {
		return similarUserID;
	}
	public void setSimilarUserID(String similarUserID) {
		this.similarUserID = similarUserID;
	}
	public String getUserSimilarity() {
		return userSimilarity;
	}
	public void setUserSimilarity(String userSimilarity) {
		this.userSimilarity = userSimilarity;
	}
	public long[] getCommons() {
		return commons;
	}
	public void setCommons(long[] commons) {
		this.commons = commons;
	}
	public long[] getRecoomendItems() {
		return recoomendItems;
	}
	public void setRecoomendItems(long[] recoomendItems) {
		this.recoomendItems = recoomendItems;
	}
	@Override
	public String toString() {
		return "RecommendItems [userID=" + userID + ", similarUserID="
				+ similarUserID + ", userSimilarity=" + userSimilarity
				+ ", commons=" + Arrays.toString(commons) + ", recoomendItems="
				+ Arrays.toString(recoomendItems) + "]";
	}
	
	
}
