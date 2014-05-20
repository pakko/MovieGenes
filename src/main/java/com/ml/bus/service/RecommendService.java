package com.ml.bus.service;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ml.bus.dao.RecommendDAO;
import com.ml.model.RecommendItems;
import com.ml.movie.recommender.GeneralRecommendBuilder;
import com.ml.util.Pagination;

@Service
public class RecommendService {

	@Autowired
	RecommendDAO recommendDAO;
	
	@Autowired
	private MemoryService memoryService;

	public void recommendAndSave(String userID) {
		try{
			GeneralRecommendBuilder builder = memoryService.getRecommendbuilder();
			
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
				
				//get recommend items
				long[] recommendItemIDs = builder.getRecommendItems(fs, id);
				if(recommendItemIDs.length <= 0)
					continue;
				
				double similarity = builder.getUserSimilarity(Long.parseLong(userID), id);
				
				RecommendItems ri = new RecommendItems(userID, String.valueOf(id), String.format("%.2f", similarity * 10), commonItemIDs, recommendItemIDs);
				recommendDAO.save(ri);
			}
			memoryService.getRecommendItemsInStore().add(userID);
			} catch (TasteException e) {
			e.printStackTrace();
		}
	}
	
	public Pagination findByPageAndId(Pagination pager, String id) {
		return recommendDAO.findByPageAndId(pager, id);
	}
	
}
