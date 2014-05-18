package com.ml.movie.recommender;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ml.util.Constants;

public class MovieRecommenderInitiator {

	// Logger
	private static final Logger log = LoggerFactory.getLogger(MovieRecommenderInitiator.class);

	/**
	 * Default MongoDB host. Default: localhost
	 */
	protected static String defaultMongoHost = "localhost";
	/**
	 * Default MongoDB port. Default: 27017
	 */
	protected static int defaultMongoPort = 27017;

	/**
	 * Default MongoDB database. Default: recommender
	 */
	protected static String defaultMongoDB = "recommender";

	/**
	 * Default MongoDB authentication flag. Default: false (authentication is
	 * not required)
	 */
	protected static boolean defaultMongoAuth = false;

	/**
	 * Default MongoDB user. Default: recommender
	 */
	protected static String defaultMongoUsername = "recommender";

	/**
	 * Default MongoDB password. Default: recommender
	 */
	protected static String defaultMongoPassword = "recommender";

	/**
	 * Default MongoDB table/collection. Default: items
	 */
	protected static String defaultMongoCollection = "items";

	/**
	 * Default MongoDB update flag. When this flag is activated, the DataModel
	 * updates both model and database. Default: true
	 */
	protected static boolean defaultMongoManage = true;

	/**
	 * Default MongoDB user ID field. Default: user_id
	 */
	protected static String defaultMongoUserID = "user_id";

	/**
	 * Default MongoDB item ID field. Default: item_id
	 */
	protected static String defaultMongoItemID = "item_id";

	/**
	 * Default MongoDB preference value field. Default: preference
	 */
	protected static String defaultMongoPreference = "preference";

	protected static String defaultMongoTimestamp = "timestamp";

	/**
	 * Default MongoDB final remove flag. Default: false
	 */
	protected static boolean defaultMongoFinalRemove = false;

	/**
	 * Default MongoDB date format. Default:
	 * "EE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)"
	 */
	protected static SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
			"EE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");

	/**
	 * Default user threshold
	 */
	protected static double defaultUserThreshold = 0.8;

	/**
	 * Default number of Neighbors
	 */
	protected static int defaultNeighborsNumber = 10;

	/**
	 * Default maximum number of recommendations
	 */
	protected static int defaultMaxRecommendations = 10;

	/**
	 * Default user similarity method
	 */
	protected static String defaultSimilarityMeasure = "euclidean";

	/**
	 * Default neighborhood type
	 */
	protected static String defaultNeighborhoodType = "nearest";

	/**
	 * MongoDB host
	 */
	protected static String mongoHost = defaultMongoHost;

	/**
	 * MongoDB port
	 */
	protected static int mongoPort = defaultMongoPort;

	/**
	 * MongoDB database
	 */
	protected static String mongoDB = defaultMongoDB;

	/**
	 * MongoDB authentication flag. If this flag is set to false, authentication
	 * is not required.
	 */
	protected static boolean mongoAuth = defaultMongoAuth;

	/**
	 * MongoDB user
	 */
	protected static String mongoUsername = defaultMongoUsername;

	/**
	 * MongoDB pass
	 */
	protected static String mongoPassword = defaultMongoPassword;

	/**
	 * MongoDB table/collection
	 */
	protected static String mongoCollection = defaultMongoCollection;

	/**
	 * MongoDB update flag. When this flag is activated, the DataModel updates
	 * both model and database
	 */
	protected static boolean mongoManage = defaultMongoManage;

	/**
	 * MongoDB user ID field
	 */
	protected static String mongoUserID = defaultMongoUserID;

	/**
	 * MongoDB item ID field
	 */
	protected static String mongoItemID = defaultMongoItemID;

	/**
	 * MongoDB preference value field
	 */
	protected static String mongoPreference = defaultMongoPreference;

	protected static String mongoTimestamp = defaultMongoTimestamp;

	/**
	 * MongoDB final remove flag. Default: false
	 */
	protected static boolean mongoFinalRemove = defaultMongoFinalRemove;

	/**
	 * MongoDB date format
	 */
	protected static SimpleDateFormat dateFormat = defaultDateFormat;

	/**
	 * User threshold
	 */
	protected double userThreshold = defaultUserThreshold;

	/**
	 * Number of Neighbors
	 */
	protected int neighborsNumber = defaultNeighborsNumber;

	/**
	 * Maximum number of recommendations
	 */
	protected int maxRecommendations = defaultMaxRecommendations;

	/**
	 * Neighborhood type
	 */
	protected static String neighborhoodType = defaultNeighborhoodType;

	/**
	 * User similarity method
	 */
	protected static String similarityMeasure = defaultSimilarityMeasure;

	private GeneralRecommendBuilder recommendBuilder;
	
	public void initialized() {
		getParameters();
		recommendBuilder = new GeneralRecommendBuilder();
		try {
			if (mongoAuth) {
				recommendBuilder.start(new MongoDBDataModel(mongoHost, mongoPort,
						mongoDB, mongoCollection, mongoManage,
						mongoFinalRemove, dateFormat, mongoUsername,
						mongoPassword, mongoUserID, mongoItemID,
						mongoPreference, mongoTimestamp), userThreshold,
						neighborsNumber, maxRecommendations, similarityMeasure,
						neighborhoodType);
			} else {
				recommendBuilder.start(new MongoDBDataModel(mongoHost, mongoPort,
						mongoDB, mongoCollection, mongoManage,
						mongoFinalRemove, dateFormat, mongoUserID, mongoItemID,
						mongoPreference, mongoTimestamp), userThreshold,
						neighborsNumber, maxRecommendations, similarityMeasure,
						neighborhoodType);
			}
		} catch (Exception e) {
			log.error("Error building model");
		}
		
	}

	private void getParameters() {
		try {
			log.info("Reading parameters");
			Properties properties = new Properties();
			properties.load(new BufferedInputStream(new FileInputStream(Constants.defaultConfigFile)));

			mongoHost = properties.getProperty("mongo.host");
			log.info("mongo.host read from configuration file: " + mongoHost);

			mongoPort = Integer.parseInt(properties.getProperty("mongo.port"));
			log.info("mongo.port read from configuration file: " + mongoPort);

			mongoDB = properties.getProperty("mongo.db");
			log.info("mongo.db read from configuration file: " + mongoDB);

			mongoCollection = properties.getProperty("MONGO_COLLECTION");
			log.info("MONGO_COLLECTION read from configuration file: " + mongoCollection);

			mongoManage = Boolean.parseBoolean(properties.getProperty("MONGO_MANAGE"));
			log.info("MONGO_MANAGE read from configuration file: " + mongoManage);

			mongoFinalRemove = Boolean.parseBoolean(properties.getProperty("MONGO_FINAL_REMOVE"));
			log.info("MONGO_FINAL_REMOVE read from configuration file: " + mongoFinalRemove);

			String dateFormatProperty = properties.getProperty("DATE_FORMAT");
			if (dateFormatProperty.equals("NULL")) {
				dateFormat = null;
			} else {
				dateFormat = new SimpleDateFormat(dateFormatProperty);
			}
			log.info("DATE_FORMAT read from configuration file: " + dateFormat);

			mongoAuth = Boolean.parseBoolean(properties.getProperty("MONGO_AUTH"));
			log.info("MONGO_AUTH read from configuration file: " + mongoAuth);

			if (mongoAuth) {
				mongoUsername = properties.getProperty("mongo.user");
				log.info("mongo.user read from configuration file: " + mongoUsername);

				mongoPassword = properties.getProperty("mongo.password");
				log.info("mongo.password read from configuration file: " + mongoPassword);
			}

			mongoUserID = properties.getProperty("MONGO_USER_FIELD");
			log.info("MONGO_USER_FIELD read from configuration file: " + mongoUserID);

			mongoItemID = properties.getProperty("MONGO_ITEM_FIELD");
			log.info("MONGO_ITEM_FIELD read from configuration file: " + mongoItemID);

			mongoPreference = properties.getProperty("MONGO_PREFERENCE_FIELD");
			log.info("MONGO_PREFERENCE_FIELD read from configuration file: " + mongoPreference);
			
			mongoTimestamp = properties.getProperty("MONGO_TIMESTAMP");
			log.info("MONGO_TIMESTAMP read from configuration file: " + mongoTimestamp);

			similarityMeasure = properties.getProperty("SIMILARITY_MEASURE");
			log.info("SIMILARITY_MEASURE read from configuration file: " + similarityMeasure);

			neighborhoodType = properties.getProperty("NEIGHBORHOOD");
			log.info("NEIGHBORHOOD read from configuration file: " + neighborhoodType);

			userThreshold = Double.parseDouble(properties.getProperty("USER_TH"));
			log.info("USER_TH read from configuration file: " + userThreshold);

			neighborsNumber = Integer.parseInt(properties.getProperty("NEIGHBORS_NUMBER"));
			log.info("NEIGHBORS_NUMBER read from configuration file: " + neighborsNumber);

			maxRecommendations = Integer.parseInt(properties.getProperty("MAX_RECOMMENDATIONS"));
			log.info("MAX_RECOMMENDATIONS read from configuration file: " + maxRecommendations);

		} catch (Exception e) {
			log.error("Error while starting recommender.", e);
		}
	}

	public GeneralRecommendBuilder getRecommendBuilder() {
		return recommendBuilder;
	}

	public void setRecommendBuilder(GeneralRecommendBuilder recommendBuilder) {
		this.recommendBuilder = recommendBuilder;
	}
	
	
}
