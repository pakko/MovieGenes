package com.ml.movie.recommender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.mahout.cf.taste.common.NoSuchItemException;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class GeneralRecommendBuilder implements Recommender {
	// Class logger
	private static final Logger log = LoggerFactory
			.getLogger(MongoDBDataModel.class);

	/**
	 * Default user threshold
	 */
	protected static double defaultUserThreshold = 0.8;

	/**
	 * Default user similarity method
	 */
	protected static String defaultSimilarityMeasure = "euclidean";

	/**
	 * Default neighborhood type
	 */
	protected static String defaultNeighborhoodType = "nearest";

	/**
	 * Default number of Neighbors
	 */
	protected static int defaultNeighborsNumber = 10;

	/**
	 * Default maximum number of recommendations
	 */
	protected static int defaultMaxRecommendations = 10;

	/**
	 * User threshold
	 */
	protected double userThreshold = defaultUserThreshold;

	/**
	 * Neighborhood type
	 */
	protected String neighborhoodType = defaultNeighborhoodType;

	/**
	 * User similarity method
	 */
	protected String similarityMeasure = defaultSimilarityMeasure;

	/**
	 * Number of Neighbors
	 */
	protected int neighborsNumber = defaultNeighborsNumber;

	/**
	 * Maximum number of recommendations
	 */
	protected int maxRecommendations = defaultMaxRecommendations;

	/**
	 * Recommender variables
	 */
	protected MongoDBDataModel dataModel;
	protected static UserSimilarity similarity;
	protected static UserNeighborhood neighborhood;
	protected static MovieUserBasedRecommender recommender;

	public void start(MongoDBDataModel dataModel) {
		build(dataModel);
	}

	public void start(MongoDBDataModel dataModel, double userThreshold,
			int neighborsNumber, int maxRecommendations,
			String similarityMeasure, String neighborhoodType) {
		this.userThreshold = userThreshold;
		this.neighborsNumber = neighborsNumber;
		this.maxRecommendations = maxRecommendations;
		this.similarityMeasure = similarityMeasure;
		this.neighborhoodType = neighborhoodType;
		build(dataModel);
	}

	public void build(MongoDBDataModel dataModel) {
		try {
			this.dataModel = dataModel;
			if (similarityMeasure.equals("log")) {
				similarity = new LogLikelihoodSimilarity(this.dataModel);
			} else if (similarityMeasure.equals("pearson")) {
				similarity = new PearsonCorrelationSimilarity(this.dataModel);
			} else if (similarityMeasure.equals("spearman")) {
				similarity = new SpearmanCorrelationSimilarity(this.dataModel);
			} else if (similarityMeasure.equals("tanimoto")) {
				similarity = new TanimotoCoefficientSimilarity(this.dataModel);
			} else {
				similarity = new EuclideanDistanceSimilarity(this.dataModel);
			}
			if (neighborhoodType.equals("threshold")) {
				neighborhood = new ThresholdUserNeighborhood(userThreshold,
						similarity, dataModel);
			} else {
				neighborhood = new NearestNUserNeighborhood(neighborsNumber,
						similarity, this.dataModel);
			}
			recommender = new MovieUserBasedRecommender(this.dataModel,
					neighborhood, similarity);
		} catch (Exception e) {
			log.error("Error while starting recommender.", e);
		}
	}
	
	public void build2() {
		try {
			DataModel dataModel = new MovieFileDataModel();
			recommender = new MovieUserBasedRecommender(dataModel);
		} catch (Exception e) {
			log.error("Error while starting recommender.", e);
		}
	}

	/**
	 * Returns a list of recommended users IDs for the given user ID
	 */
	public List<List<String>> recommend(String id,
			Iterable<List<String>> items, boolean recommendUsers)
			throws NullPointerException, NoSuchUserException,
			NoSuchItemException, IllegalArgumentException {
		long userID = Long.parseLong(id);
		try {
			FastIDSet userItems = dataModel.getItemIDsFromUser(userID);
			if (items != null) {
				for (List<String> item : items) {
					long itemID = Long.parseLong(item.get(0));
					if (userItems.contains(itemID)) {
						dataModel.refreshData(id, items, true);
						break;
					}
				}
			}
		} catch (NoSuchUserException e) {
			dataModel.refreshData(id, items, true);
		} catch (TasteException e) {
			log.error("Error while requesting recommendations.", e);
		}
		if (recommendUsers) {
			return recommendUsers(userID);
		} else {
			return recommendItems(userID);
		}
	}

	/**
	 * Returns a list of recommended users IDs for the given user ID
	 */
	public List<List<String>> recommendUsers(long id) {
		List<List<String>> result = null;
		try {
			long[] recomendations = recommender.mostSimilarUserIDs(id, maxRecommendations);
			result = new ArrayList<List<String>>();
			for (long r : recomendations) {
				List<String> user = new ArrayList<String>();
				user.add(String.valueOf(r));
				result.add(user);
			}
		} catch (TasteException e) {
			log.error("Error while processing user recommendations for user " + id, e);
		}
		return result;
	}

	/**
	 * Returns a list of recommended item IDs for the given user ID
	 */
	public List<List<String>> recommendItems(long id) {
		List<List<String>> result = null;
		try {
			List<RecommendedItem> recomendations = recommender.recommend(id, maxRecommendations);
			result = new ArrayList<List<String>>();
			for (RecommendedItem r : recomendations) {
				List<String> item = new ArrayList<String>();
				item.add(String.valueOf(r.getItemID()));
				item.add(Float.toString(r.getValue()));
				result.add(item);
			}
		} catch (TasteException e) {
			log.error("Error while processing item recommendations for user " + id, e);
		}
		return result;
	}

	public void refreshData(String userID, Iterable<List<String>> items,
			boolean add) throws NullPointerException, NoSuchUserException,
			NoSuchItemException, IllegalArgumentException {
		dataModel.refreshData(userID, items, add);
	}

	@Override
	public void refresh(Collection<Refreshable> arg0) {
		dataModel.refresh(arg0);
	}

	@Override
	public float estimatePreference(long arg0, long arg1) throws TasteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DataModel getDataModel() {
		return dataModel;
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany)
			throws TasteException {
		return recommender.recommend(userID, howMany);
	}

	@Override
	public List<RecommendedItem> recommend(long arg0, int arg1, IDRescorer arg2)
			throws TasteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePreference(long arg0, long arg1) throws TasteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPreference(long arg0, long arg1, float arg2)
			throws TasteException {
		// TODO Auto-generated method stub

	}
}
