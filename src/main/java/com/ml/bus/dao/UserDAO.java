package com.ml.bus.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ml.db.IBaseDB;
import com.ml.model.DoubanUser;
import com.ml.model.UserRattedMovies;
import com.ml.util.Constants;
import com.ml.util.Pagination;

public class UserDAO {
	
	@Autowired
	IBaseDB baseDB;
	
	public List<DoubanUser> findAll() {
		return baseDB.findAll(DoubanUser.class, Constants.dbUserCollectionName);
	}
	
	public List<DoubanUser> findByName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(name));
		return baseDB.find(query, DoubanUser.class, Constants.dbUserCollectionName);
	}
	
	public List<DoubanUser> findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userID").is(id));
		return baseDB.find(query, DoubanUser.class, Constants.dbUserCollectionName);
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
		
		List<DoubanUser> items = baseDB.find(query, DoubanUser.class, Constants.dbUserCollectionName);
		int totalCount = (int) baseDB.count(query, Constants.dbUserCollectionName);

		int totalPage = (int)(totalCount / limitSize) + 1;
		if((totalCount % limitSize) == 0) {
			totalPage = totalPage - 1;
		}
		pager.setItems(items);
		pager.setTotalCount(totalCount);
		pager.setTotalPage(totalPage);
		
		return pager;
	}
	
	public Pagination findRatedByPageAndId(Pagination pager, String userID) {
		int limitSize = pager.getLimitSize();
		int startIndex = pager.getStartIndex();
		
		Query query = new Query();
		query.addCriteria(Criteria.where("userID").is(Long.parseLong(userID)));
		if(pager.getSortOrder().equals("desc")){
			query.with(new Sort(new Sort.Order(Direction.DESC, pager.getSortField())));
		}
		else if(pager.getSortOrder().equals("asc")){
			query.with(new Sort(new Sort.Order(Direction.ASC, pager.getSortField())));
		}
		query = query.skip(startIndex).limit(limitSize);
		
		List<UserRattedMovies> items = baseDB.find(query, UserRattedMovies.class, Constants.userRattedMoviesCollectionName);
		int totalCount = (int) baseDB.count(query, Constants.userRattedMoviesCollectionName);

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
