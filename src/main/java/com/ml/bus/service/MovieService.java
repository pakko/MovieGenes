package com.ml.bus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ml.bus.dao.MoviesDAO;
import com.ml.model.Movie;
import com.ml.util.Pagination;

@Service
public class MovieService {

	@Autowired
	MoviesDAO moviesDAO;

	public List<Movie> findAll() {
		return moviesDAO.findAll();
	}
	
	public List<Movie> findByName(String title) {
		return moviesDAO.findByName(title);
	}
	
	public List<Movie> findById(String id) {
		return moviesDAO.findById(id);
	}
	
	public Pagination findByPage(Pagination pager) {
		return moviesDAO.findByPage(pager);
	}

	public Pagination findByPageAndGenres(Pagination pager, String genres) {
		return moviesDAO.findByPageAndGenres(pager, genres);
	}
	
}
