package com.ml.bus.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ml.bus.service.MemoryService;
import com.ml.movie.recommender.GeneralRecommendBuilder;

@Controller
@RequestMapping(value = "/preferences")
public class PreferenceResource {

	// Logger
	private static final Logger log = LoggerFactory.getLogger(MongoDBDataModel.class);

	@Autowired
	private MemoryService memoryService;

	static String XMLDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	// Create a preference of a user
	@RequestMapping(value = "/{userID}/{itemID}/{weight}", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String findById(
    		@PathVariable("userID") String userID,
    		@PathVariable("itemID") String itemID,
    		@PathVariable("weight") String weight) {
    	
		refreshPreference(userID, itemID, weight, true);
		
		return "{\"success\": \"ok\"}";
    }
	
	// Refresh preferences
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public void refreshPreferences() {
		log.info("Refresh REST");
		GeneralRecommendBuilder recommender = memoryService.getRecommendbuilder();
		try {
			recommender.refresh(null);
		} catch (Exception e) {
			log.info("Bad refresh");
		}
	}

	// Delete a preference of a user
	@RequestMapping(value = "/{userID}/{itemID}", method = RequestMethod.DELETE, produces = "application/json")
	public String deletePreference(
			@PathVariable("userID") String userID,
    		@PathVariable("itemID") String itemID) {
		
		refreshPreference(userID, itemID, "", false);
		
		return "{\"success\": \"ok\"}";
	}

	private void refreshPreference(String userID, String itemID, String weight, boolean add) {
		GeneralRecommendBuilder recommender = memoryService.getRecommendbuilder();
		List<List<String>> items = new ArrayList<List<String>>();
		List<String> tuple = new ArrayList<String>();
		tuple.add(itemID);
		tuple.add(weight);
		items.add(tuple);
		try {
			recommender.refreshData(userID, items, add);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

}
