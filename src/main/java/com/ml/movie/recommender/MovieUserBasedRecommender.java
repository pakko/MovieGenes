package com.ml.movie.recommender;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.AveragingPreferenceInferrer;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MovieUserBasedRecommender implements Recommender {

	private final GenericUserBasedRecommender recommender;

	/**
	 * @throws IOException
	 *             if an error occurs while creating the
	 *             {@link GroupLensDataModel}
	 * @throws TasteException
	 *             if an error occurs while initializing this
	 *             {@link GroupLensRecommender}
	 */
	public MovieUserBasedRecommender() throws IOException, TasteException {
		//this(new MovieFileDataModel());
        
		this(new MongoDBDataModel("10.74.68.13", 27017, "countly", "userRattedMovies", true, true, 
				new SimpleDateFormat("yyyy MM dd HH:mm:ss"), "userID", "movieID", "rating", "timestamp"));
	}

	/**
	 * <p>
	 * Alternate constructor that takes a {@link DataModel} argument, which
	 * allows this {@link Recommender} to be used with the
	 * {@link org.apache.mahout.cf.taste.eval.RecommenderEvaluator} framework.
	 * </p>
	 * 
	 * @param dataModel
	 *            data model
	 * @throws TasteException
	 *             if an error occurs while initializing this
	 *             {@link GroupLensRecommender}
	 */
	public MovieUserBasedRecommender(DataModel model) throws TasteException {
		UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);
		userSimilarity.setPreferenceInferrer(new AveragingPreferenceInferrer(
				model));
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(10,
				userSimilarity, model);

		recommender = new GenericUserBasedRecommender(model, neighborhood,
				userSimilarity);
	}

	public MovieUserBasedRecommender(DataModel model,
			UserNeighborhood neighborhood, UserSimilarity userSimilarity)
			throws TasteException {

		recommender = new GenericUserBasedRecommender(model, neighborhood,
				userSimilarity);
	}

	public List<RecommendedItem> recommend(long userID, int howMany)
			throws TasteException {
		return recommender.recommend(userID, howMany);
	}

	public List<RecommendedItem> recommend(long userID, int howMany,
			IDRescorer rescorer) throws TasteException {
		return recommender.recommend(userID, howMany, rescorer);
	}

	public long[] mostSimilarUserIDs(long userID, int howMany)
			throws TasteException {
		return recommender.mostSimilarUserIDs(userID, howMany);
	}

	public float estimatePreference(long userID, long itemID)
			throws TasteException {
		return recommender.estimatePreference(userID, itemID);
	}

	public void setPreference(long userID, long itemID, float value)
			throws TasteException {
		recommender.setPreference(userID, itemID, value);
	}

	public void removePreference(long userID, long itemID)
			throws TasteException {
		recommender.removePreference(userID, itemID);
	}

	public DataModel getDataModel() {
		return recommender.getDataModel();
	}

	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		recommender.refresh(alreadyRefreshed);
	}

	public String toString() {
		return "UserBasedRecommender[recommender:" + recommender + ']';
	}

}
