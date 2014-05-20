package com.ml.bus.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ml.db.IBaseDB;
import com.ml.model.RecommendItems;
import com.ml.util.Constants;
import com.ml.util.Pagination;

public class RecommendDAO {
	
	@Autowired
	IBaseDB baseDB;
	
	public List<RecommendItems> findAll() {
		return baseDB.findAll(RecommendItems.class, Constants.recommendCollectionName);
	}
	
	public List<RecommendItems> findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userID").is(id));
		return baseDB.find(query, RecommendItems.class, Constants.recommendCollectionName);
	}
	
	public void save(RecommendItems item) {
		baseDB.save(item, Constants.recommendCollectionName);
	}
	
	public Pagination findByPageAndId(Pagination pager, String id) {
		int limitSize = pager.getLimitSize();
		int startIndex = pager.getStartIndex();
		
		Query query = new Query();
		query.addCriteria(Criteria.where("userID").is(id));
		if(pager.getSortOrder().equals("desc")){
			query.with(new Sort(new Sort.Order(Direction.DESC, pager.getSortField())));
		}
		else if(pager.getSortOrder().equals("asc")){
			query.with(new Sort(new Sort.Order(Direction.ASC, pager.getSortField())));
		}
		query = query.skip(startIndex).limit(limitSize);
		
		List<RecommendItems> items = baseDB.find(query, RecommendItems.class, Constants.recommendCollectionName);
		int totalCount = (int) baseDB.count(query, Constants.recommendCollectionName);

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
