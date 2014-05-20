package com.ml.bus.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.ml.movie.recommender.GeneralRecommendBuilder;
import com.ml.movie.recommender.MovieRecommenderInitiator;


@Service
public class MemoryService {
	
	private GeneralRecommendBuilder recommendbuilder;
	
	private List<String> movieGenres;
	
	private List<String> recommendItemsInStore;
	
	@PostConstruct 
    public void init() throws IOException{ 
		initRecommendBuilder();
		
		initMovieGenres();
		
		recommendItemsInStore = new ArrayList<String>();
    }
	
	private void initRecommendBuilder() {
		MovieRecommenderInitiator ri = new MovieRecommenderInitiator();
		ri.initialized();
		this.setRecommendbuilder(ri.getRecommendBuilder());
	}
	
	private void initMovieGenres() {
		movieGenres = new ArrayList<String>();
		movieGenres.add("动作");
		movieGenres.add("冒险");
		movieGenres.add("动画");
		movieGenres.add("传记");
		movieGenres.add("喜剧");
		movieGenres.add("犯罪");
		movieGenres.add("纪录片");
		movieGenres.add("剧情");
		movieGenres.add("家庭");
		movieGenres.add("奇幻");
		movieGenres.add("黑色幽默");
		movieGenres.add("史诗");
		movieGenres.add("恐怖");
		movieGenres.add("音乐");
		movieGenres.add("歌舞");
		movieGenres.add("悬疑");
		movieGenres.add("浪漫");
		movieGenres.add("科幻");
		movieGenres.add("体育");
		movieGenres.add("惊悚");
		movieGenres.add("战争");
		movieGenres.add("西部");
	}

	public GeneralRecommendBuilder getRecommendbuilder() {
		return recommendbuilder;
	}

	public void setRecommendbuilder(GeneralRecommendBuilder recommendbuilder) {
		this.recommendbuilder = recommendbuilder;
	}

	public List<String> getMovieGenres() {
		return movieGenres;
	}

	public void setMovieGenres(List<String> movieGenres) {
		this.movieGenres = movieGenres;
	}

	public List<String> getRecommendItemsInStore() {
		return recommendItemsInStore;
	}

	public void setRecommendItemsInStore(List<String> recommendItemsInStore) {
		this.recommendItemsInStore = recommendItemsInStore;
	}
	
	
}
