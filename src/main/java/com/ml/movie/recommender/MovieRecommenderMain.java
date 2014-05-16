package com.ml.movie.recommender;

import java.io.IOException;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

public class MovieRecommenderMain {
	private static final int NUM_TOP_PREFERENCES = 20;

	private static Recommender recommender;

	public static void main(String[] args) throws TasteException, IOException {
		/*String confFile = Constants.defaultConfigFile;
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(confFile));
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}
		MongoDB m = new MongoDB(props);
		List<UserRattedMovies> urms = m.findAll(UserRattedMovies.class, Constants.userRattedMoviesCollectionName);
		StringBuffer sb = new StringBuffer();
		for(UserRattedMovies urm: urms) {
			sb.append(urm.getUserID() + "\t" + urm.getMovieID() + "\t" + urm.getRating() + "\t" + urm.getTimestamp() + "\n");
		}
		File file = new File("src/main/resources/user_ratedmovies-timestamps.dat");
		if(file.exists())
			file.delete();
		FileUtils.writeStringToFile(file, sb.toString());
		*/
		recommender = new MovieUserBasedRecommender();
		
		long userID = 75;
		int howMany = 10;

		List<RecommendedItem> items = recommender.recommend(userID, howMany);
		writeDebugRecommendations(userID, items);
	}

	private static void writeDebugRecommendations(long userID,
			Iterable<RecommendedItem> items)
			throws TasteException {
		DataModel dataModel = recommender.getDataModel();
		System.out.print("User:");
		System.out.println(userID);
		System.out.print("Recommender: ");
		System.out.println(recommender);
		System.out.println();
		System.out.print("Top ");
		System.out.print(NUM_TOP_PREFERENCES);
		System.out.println(" Preferences:");
		PreferenceArray rawPrefs = dataModel.getPreferencesFromUser(userID);
		int length = rawPrefs.length();
		PreferenceArray sortedPrefs = rawPrefs.clone();
		sortedPrefs.sortByValueReversed();
		// Cap this at NUM_TOP_PREFERENCES just to be brief
		int max = Math.min(NUM_TOP_PREFERENCES, length);
		for (int i = 0; i < max; i++) {
			Preference pref = sortedPrefs.get(i);
			System.out.print(pref.getValue());
			System.out.print('\t');
			System.out.println(pref.getItemID());
		}
		System.out.println();
		System.out.println("Recommendations:");
		for (RecommendedItem recommendedItem : items) {
			System.out.print(recommendedItem.getValue());
			System.out.print('\t');
			System.out.println(recommendedItem.getItemID());
		}
	}

}
