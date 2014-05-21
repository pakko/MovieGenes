package com.ml.bus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ml.bus.service.UserDataCrawler;
import com.ml.db.IBaseDB;
import com.ml.model.DoubanUser;
import com.ml.model.Movie;
import com.ml.util.Constants;


@Controller
@RequestMapping(value = "/crawl")
public class CrawlController {

    @Autowired
    private UserDataCrawler userDataCrawler;
    
    @Autowired
	IBaseDB baseDB;
    
    @RequestMapping(value = "/transferMovie", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String transferMovie() {
    	
    	Query query = new Query();
		query.addCriteria(Criteria.where("dbID").is(null));
		List<Movie> movies = baseDB.find(query, Movie.class, Constants.movieCollectionName);
		System.out.println(movies.size());
		
		userDataCrawler.transferMovie(baseDB, movies);
		
		return "{\"success\": \"ok\"}";
    }
    
    @RequestMapping(value = "/transferBlankMovie", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String transferBlankMovie() {
    	
    	Query query = new Query();
    	//query.addCriteria(Criteria.where("title").is(""));
		query.addCriteria(Criteria.where("rating").is(0));
		List<Movie> movies = baseDB.find(query, Movie.class, Constants.movieCollectionName);
		System.out.println(movies.size());
		
		userDataCrawler.transferBlankMovie(baseDB, movies);
		
		return "{\"success\": \"ok\"}";
    }
    
    @RequestMapping(value = "/transferUser", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String transferUser() {
    	
    	Query query = new Query();
		query.addCriteria(Criteria.where("marked").is(0));
		List<DoubanUser> users = baseDB.find(query, DoubanUser.class, Constants.dbUserCollectionName);
		System.out.println(users.size());
		
		userDataCrawler.transferUser(baseDB, users);
		
		return "{\"success\": \"ok\"}";
    }
    
    @RequestMapping(value = "/crawlMovie", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String crawlMovie() {
    	
    	List<Movie> movies = baseDB.findAll(Movie.class, Constants.movieCollectionName);
    	System.out.println(movies.size());
		
		userDataCrawler.preprocess(movies);
		userDataCrawler.crawlMovie(baseDB);
		
		return "{\"success\": \"ok\"}";
    }
    
    @RequestMapping(value = "/crawlUser/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String crawlUser(@PathVariable("id") String id) {
    	
    	//String movieID = "1296736";
    	userDataCrawler.crawlUser(baseDB, id);
		
		return "{\"success\": \"ok\"}";
    }
    
    
    
    @RequestMapping(value = "/crawlRatingList/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String crawlRatingList(@PathVariable("id") String id) {
    	
    	//String userID = "3032305";
    	userDataCrawler.crawlRatingList(baseDB, id);
		/*
    	Query query = new Query();
		query.addCriteria(Criteria.where("marked").is(-1));
		List<DoubanUser> users = baseDB.find(query, DoubanUser.class, Constants.dbUserCollectionName);
    	for(DoubanUser user: users) {
    		userDataCrawler.crawlRatingList(baseDB, user.getId());
    		
	    	baseDB.delete(user, Constants.dbUserCollectionName);
	    	DoubanUser newUser = new DoubanUser(user.getId(), -2, id);
	    	System.out.println(newUser);
	    	baseDB.save(newUser, Constants.dbUserCollectionName);
    	}*/
    	
		return "{\"success\": \"ok\"}";
    }
    
    @RequestMapping(value = "/crawlRatingLists", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String crawlRatingLists(
    		@RequestParam(value = "ids[]", required = false) String[] ids) {
    	
    	for(String id: ids) {
    		try{
	    		userDataCrawler.crawlRatingList(baseDB, id);
	    		
	    		Query query = new Query();
	    		query.addCriteria(Criteria.where("userID").is(id));
	    		List<DoubanUser> users = baseDB.find(query, DoubanUser.class, Constants.dbUserCollectionName);
	    		if(users.size() <= 0)
	    			continue;
	    		baseDB.delete(users.get(0), Constants.dbUserCollectionName);
		    	DoubanUser newUser = new DoubanUser(users.get(0).getId(), -2, id);
		    	System.out.println(newUser);
		    	baseDB.save(newUser, Constants.dbUserCollectionName);
    		} catch (Exception e) {
    			System.err.println("Error id: " + id + ", erro info: " + e.getMessage());
    		}
    	}
    	
		return "{\"success\": \"ok\"}";
    }
}
