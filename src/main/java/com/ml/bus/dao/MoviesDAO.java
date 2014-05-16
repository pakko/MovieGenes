package com.ml.bus.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ml.db.IBaseDB;
import com.ml.model.Movie;
import com.ml.util.Constants;
import com.ml.util.Pagination;

public class MoviesDAO {
	
	@Autowired
	IBaseDB baseDB;
	
	public List<Movie> findAll() {
		return baseDB.findAll(Movie.class, Constants.movieCollectionName);
	}
	
	public List<Movie> findByName(String title) {
		Query query = new Query();
		query.addCriteria(Criteria.where("title").regex(title));
		return baseDB.find(query, Movie.class, Constants.movieCollectionName);
	}
	
	public List<Movie> findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return baseDB.find(query, Movie.class, Constants.movieCollectionName);
	}
	
	public Pagination findByPage(Pagination pager) {
		int limitSize = pager.getLimitSize();
		int startIndex = pager.getStartIndex();
		
		Query query = new Query();
		if(pager.getSortOrder().equals("desc")){
			query.with(new Sort(new Sort.Order(Direction.DESC, pager.getSortField())));
		}
		else if(pager.getSortOrder().equals("asc")){
			query.with(new Sort(new Sort.Order(Direction.ASC, pager.getSortField())));
		}
		query = query.skip(startIndex).limit(limitSize);
		
		List<Movie> items = baseDB.find(query, Movie.class, Constants.movieCollectionName);
		int totalCount = (int) baseDB.count(query, Constants.movieCollectionName);

		int totalPage = (int)(totalCount / limitSize) + 1;
		if((totalCount % limitSize) == 0) {
			totalPage = totalPage - 1;
		}
		pager.setItems(items);
		pager.setTotalCount(totalCount);
		pager.setTotalPage(totalPage);
		
		return pager;
	}

	public Pagination findByPageAndGenres(Pagination pager, String genres) {
		int limitSize = pager.getLimitSize();
		int startIndex = pager.getStartIndex();
		
		Query query = new Query();
		query.addCriteria(Criteria.where("genres").in(genres));
		
		if(pager.getSortOrder().equals("desc")){
			query.with(new Sort(new Sort.Order(Direction.DESC, pager.getSortField())));
		}
		else if(pager.getSortOrder().equals("asc")){
			query.with(new Sort(new Sort.Order(Direction.ASC, pager.getSortField())));
		}
		query = query.skip(startIndex).limit(limitSize);
		
		List<Movie> items = baseDB.find(query, Movie.class, Constants.movieCollectionName);
		int totalCount = (int) baseDB.count(query, Constants.movieCollectionName);

		int totalPage = (int)(totalCount / limitSize) + 1;
		if((totalCount % limitSize) == 0) {
			totalPage = totalPage - 1;
		}
		pager.setItems(items);
		pager.setTotalCount(totalCount);
		pager.setTotalPage(totalPage);
		
		return pager;
	}
	

}
