package com.ml.bus.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.mahout.cf.taste.common.NoSuchItemException;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ml.bus.service.MemoryService;
import com.ml.bus.service.MovieService;
import com.ml.bus.service.RecommendService;
import com.ml.bus.service.UserService;
import com.ml.model.Movie;
import com.ml.model.RecommendItems;
import com.ml.movie.recommender.GeneralRecommendBuilder;
import com.ml.util.Pagination;

@Controller
@RequestMapping(value = "/recommend")
public class RecommendController {

	@Autowired
    private MovieService movieService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private MemoryService memoryService;
	
	@Autowired
	private RecommendService recommendService;
	
	@RequestMapping(value = "/user/{userID}/users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<List<String>> getRecommendedUsers(@PathVariable(value = "userID") String userID) 
			throws NullPointerException, NoSuchUserException, NoSuchItemException, IllegalArgumentException {
		
		GeneralRecommendBuilder recommender = memoryService.getRecommendbuilder();
		List<List<String>> users = recommender.recommend(userID, null, true);
	
		return users;
		
	}
	
	@RequestMapping(value = "/user/{userID}/items", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> getRecommendedItems(
			@PathVariable(value = "userID") String userID,
			HttpServletRequest servletRequest) {
		
		if(!memoryService.getRecommendItemsInStore().contains(userID))
			recommendService.recommendAndSave(userID);
		
		String genres = servletRequest.getParameter("genres");
		Pagination pager = new Pagination(servletRequest);
		recommendService.findByPageAndId(pager, userID);
		
		List<RecommendItems> items = (List<RecommendItems>) pager.getItems();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for(RecommendItems item: items) {
			//get similar user's name
			/*List<DoubanUser> user = userService.findById(item.getSimilarUserID());
			if(user.size() <= 0)
				continue;*/
			
			//transfer to common movies
			List<Movie> commonMovies = new ArrayList<Movie>(item.getCommons().length);
			for(long itemID: item.getCommons()) {
				List<Movie> movies = movieService.findById(String.valueOf(itemID));
				commonMovies.addAll(movies);
			}
			
			//transfer to recommend movies
			List<Movie> recommendMovies = new ArrayList<Movie>(item.getRecoomendItems().length);
			for(long recommendItemID: item.getRecoomendItems()) {
				List<Movie> movies = movieService.findById(String.valueOf(recommendItemID));
				if(genres.equals("所有"))
					recommendMovies.addAll(movies);
				else {
					if(movies.get(0).getGenres().contains(genres))
						recommendMovies.addAll(movies);
				}
				
			}
			Collections.sort(recommendMovies, new Comparator<Movie>() {
				@Override
				public int compare(Movie m1, Movie m2) {
					return m1.getRating() > m2.getRating() ? 1 : -1;
				}
			});
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName", item.getSimilarUserID());
			map.put("userSimilarity", item.getUserSimilarity());
			map.put("commons", commonMovies);
			map.put("recommendItems", recommendMovies);
			resultList.add(map);
		}
		
    	Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalPages", pager.getTotalPage());
		result.put("currentPage", pager.getCurrentPage());
		result.put("totalRecords", pager.getTotalCount());
		result.put("items", resultList);
		
		return result;
	}
}
