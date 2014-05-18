package com.ml.bus.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.NoSuchItemException;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ml.bus.service.MemoryService;
import com.ml.bus.service.MovieService;
import com.ml.bus.service.UserService;
import com.ml.model.Movie;
import com.ml.movie.recommender.GeneralRecommendBuilder;

@Controller
@RequestMapping(value = "/recommend")
public class RecommendController {

	@Autowired
    private MovieService movieService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private MemoryService memoryService;
	
	@RequestMapping(value = "/user/{userID}/users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<List<String>> getRecommendedUsers(@PathVariable(value = "userID") String userID) 
			throws NullPointerException, NoSuchUserException, NoSuchItemException, IllegalArgumentException {
		
		GeneralRecommendBuilder recommender = memoryService.getRecommendbuilder();
		List<List<String>> users = recommender.recommend(userID, null, true);
	
		return users;
		
	}
	
	@RequestMapping(value = "/user/{userID}/items", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> getRecommendedItems(@PathVariable(value = "userID") String userID) 
			throws NullPointerException, IllegalArgumentException, TasteException {
		
		GeneralRecommendBuilder builder = memoryService.getRecommendbuilder();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		List<List<String>> items = builder.recommend(userID, null, false);
		FastIDSet fs = new FastIDSet(items.size());
		for (List<String> item : items) {
			fs.add(Long.parseLong(item.get(0)));
		}
		
		long[] userIDs = builder.mostSimilarUserIDs(userID);
		for(long id: userIDs) {
			//find common items
			long[] commonItemIDs = builder.getCommonItems(Long.parseLong(userID), id);
			if(commonItemIDs.length <= 0)
				continue;
			
			//get similar user's name
			/*List<DoubanUser> user = userService.findById(String.valueOf(id));
			if(user.size() <= 0)
				continue;*/
			
			//get recommend items
			long[] commonRecommendItemIDs = builder.getRecommendItems(fs, id);
			if(commonRecommendItemIDs.length <= 0)
				continue;
			
			//transfer to common movies
			List<Movie> commonMovies = new ArrayList<Movie>(commonItemIDs.length);
			for(long itemID: commonItemIDs) {
				List<Movie> movies = movieService.findById(String.valueOf(itemID));
				commonMovies.addAll(movies);
			}
			
			//transfer to recommend movies
			List<Movie> recommendMovies = new ArrayList<Movie>(commonRecommendItemIDs.length);
			for(long recommendItemID: commonRecommendItemIDs) {
				List<Movie> movies = movieService.findById(String.valueOf(recommendItemID));
				recommendMovies.addAll(movies);
			}
			Collections.sort(recommendMovies, new Comparator<Movie>() {
				@Override
				public int compare(Movie m1, Movie m2) {
					return m1.getRating() > m2.getRating() ? 1 : -1;
				}
			});
			
			double similarity = builder.getUserSimilarity(Long.parseLong(userID), id);
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			//map.put("userName", user.get(0).getId());
			map.put("userName", id);
			map.put("userSimilarity", String.format("%.2f", similarity * 10));
			map.put("commons", commonMovies);
			map.put("recommendItems", recommendMovies);
			resultList.add(map);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalPages", 10);
		result.put("currentPage", 1);
		result.put("totalRecords", 100);
		result.put("items", resultList);
		
		return result;
	}
}
