package com.ml.bus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ml.bus.service.MovieService;
import com.ml.bus.service.MemoryService;
import com.ml.model.Movie;
import com.ml.util.Pagination;


@Controller
@RequestMapping(value = "/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;
    
    @Autowired
    private MemoryService memoryService;
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> getMovie(HttpServletRequest servletRequest) {
    	
    	String genres = servletRequest.getParameter("genres");
    	System.out.println(genres);
    	Pagination pager = new Pagination(servletRequest);
    	if(genres.equals("all")) {
    		movieService.findByPage(pager);
    	}
    	else {
    		movieService.findByPageAndGenres(pager, genres);
    	}
    	
    	@SuppressWarnings("unchecked")
		List<Movie> movies = (List<Movie>) pager.getItems();
    	Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalPages", pager.getTotalPage());
		result.put("currentPage", pager.getCurrentPage());
		result.put("totalRecords", pager.getTotalCount());
		result.put("items", movies);
		
		return result;
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Movie> getMovie() {
    	
    	List<Movie> Movies = movieService.findAll();
		
		return Movies;
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Movie> findByName(@RequestParam("name") String name) {
    	
    	List<Movie> movies = movieService.findByName(name);
		
		return movies;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Movie findById(@PathVariable("id") String id) {
    	
    	List<Movie> movies = movieService.findById(id);
		
		return movies.get(0);
    }
    
    @RequestMapping(value = "/{id}/related", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Movie> findByRelated(@PathVariable("id") String id) {
    	
    	List<Movie> movies = movieService.findById(id);
		
		return movies;
    }
    
    @RequestMapping(value = "/genres", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<String> getGenres() {
    	
    	Map<String, String> genres = memoryService.getMovieGenres();
    	List<String> list = new ArrayList<String>(genres.size());
    	for(String genre: genres.keySet()) {
    		list.add(genres.get(genre));
    	}
		return list;
    }
    
}
