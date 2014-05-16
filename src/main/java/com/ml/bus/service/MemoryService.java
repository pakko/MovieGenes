package com.ml.bus.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.ml.movie.recommender.GeneralRecommendBuilder;
import com.ml.movie.recommender.MovieRecommenderInitiator;


@Service
public class MemoryService {
	
	private GeneralRecommendBuilder recommendbuilder;
	
	private Map<String, String> movieGenres;
	
	@PostConstruct 
    public void init() throws IOException{ 
		initRecommendBuilder();
		
		initMovieGenres();
		
    }
	
	private void initRecommendBuilder() {
		MovieRecommenderInitiator ri = new MovieRecommenderInitiator();
		ri.initialized();
		this.setRecommendbuilder(ri.getRecommendBuilder());
	}
	
	private void initMovieGenres() {
		movieGenres = new HashMap<String, String>();
		movieGenres.put("Action", "动作");
		movieGenres.put("Adventure", "");
		movieGenres.put("Animation", "动画");
		movieGenres.put("Biography", "传记");
		movieGenres.put("Comedy", "喜剧");
		movieGenres.put("Crime", "犯罪");
		movieGenres.put("Documentary", "纪录片");
		movieGenres.put("Drama", "剧情");
		movieGenres.put("Family", "家庭");
		movieGenres.put("Fantasy", "魔幻");
		movieGenres.put("Film-noir", "黑色幽默");
		movieGenres.put("History", "史诗");
		movieGenres.put("Horror", "恐怖");
		movieGenres.put("Music", "音乐");
		movieGenres.put("Musical", "");
		movieGenres.put("Mystery", "");
		movieGenres.put("Romance", "浪漫");
		movieGenres.put("Sci-fi", "科幻");
		movieGenres.put("Sport", "");
		movieGenres.put("Thriller", "惊悚");
		movieGenres.put("War", "战争");
		movieGenres.put("Western", "");
	}

	public GeneralRecommendBuilder getRecommendbuilder() {
		return recommendbuilder;
	}

	public void setRecommendbuilder(GeneralRecommendBuilder recommendbuilder) {
		this.recommendbuilder = recommendbuilder;
	}

	public Map<String, String> getMovieGenres() {
		return movieGenres;
	}

	public void setMovieGenres(Map<String, String> movieGenres) {
		this.movieGenres = movieGenres;
	}
	
	
	
}
