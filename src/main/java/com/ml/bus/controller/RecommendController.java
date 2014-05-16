package com.ml.bus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.NoSuchItemException;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ml.bus.service.MemoryService;
import com.ml.bus.service.MovieService;
import com.ml.model.Movie;
import com.ml.movie.recommender.GeneralRecommendBuilder;

@Controller
@RequestMapping(value = "/recommend")
public class RecommendController {

	@Autowired
    private MovieService movieService;
	
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
			throws NullPointerException, NoSuchUserException, NoSuchItemException, IllegalArgumentException {
		
		GeneralRecommendBuilder recommender = memoryService.getRecommendbuilder();
		List<List<String>> items = recommender.recommend(userID, null, false);
	
		List<Movie> moviesList = new ArrayList<Movie>(items.size());
		
		for (List<String> item : items) {
			List<Movie> movies = movieService.findById(String.valueOf(item.get(0)));
			System.out.println(movies + "_" + item.get(0));
			if(movies.size() > 0)
				moviesList.add(movies.get(0));
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalPages", 10);
		result.put("currentPage", 1);
		result.put("totalRecords", 100);
		result.put("items", moviesList);
		
		return result;
	}
}
