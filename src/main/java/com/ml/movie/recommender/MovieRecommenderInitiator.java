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
	private static final Logger log = LoggerFactory
			.getLogger(MongoDBDataModel.class);

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
		GeneralRecommendBuilder recommendBuilder = new GeneralRecommendBuilder();
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
			//recommendBuilder.build2();
			this.recommendBuilder = recommendBuilder;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			log.error("Error building model");
		}
		
	}

	private void getParameters() {
		try {
			log.info("Reading parameters");
			Properties properties = new Properties();
			properties.load(new BufferedInputStream(new FileInputStream(Constants.defaultConfigFile)));

			String mongoHostProperty = properties.getProperty("mongo.host");
			if (mongoHostProperty != null && mongoHostProperty.length() > 0) {
				try {
					mongoHost = mongoHostProperty;
				} catch (Exception e) {
					log.error("Property [mongo.host] on properties file has an invalid value("
							+ mongoHostProperty + ")");
					System.exit(0);
				}
			}
			log.info("mongo.host read from configuration file: " + mongoHost);

			String mongoPortProperty = properties.getProperty("mongo.port");
			if (mongoPortProperty != null && mongoPortProperty.length() > 0) {
				try {
					mongoPort = Integer.parseInt(mongoPortProperty);
				} catch (Exception e) {
					log.error("Property [mongo.port] on properties file has an invalid value("
							+ mongoPortProperty + ")");
					System.exit(0);
				}
			}
			log.info("mongo.port read from configuration file: " + mongoPort);

			String mongoDBProperty = properties.getProperty("mongo.db");
			if (mongoDBProperty != null && mongoDBProperty.length() > 0) {
				try {
					mongoDB = mongoDBProperty;
				} catch (Exception e) {
					log.error("Property [mongo.db] on properties file has an invalid value("
							+ mongoDBProperty + ")");
					System.exit(0);
				}
			}
			log.info("mongo.db read from configuration file: " + mongoDB);

			String mongoCollectionProperty = properties
					.getProperty("MONGO_COLLECTION");
			if (mongoCollectionProperty != null
					&& mongoCollectionProperty.length() > 0) {
				try {
					mongoCollection = mongoCollectionProperty;
				} catch (Exception e) {
					log.error("Property [MONGO_COLLECTION] on properties file has an invalid value("
							+ mongoCollectionProperty + ")");
					System.exit(0);
				}
			}
			log.info("MONGO_COLLECTION read from configuration file: "
					+ mongoCollection);

			String mongoManageProperty = properties.getProperty("MONGO_MANAGE");
			if (mongoManageProperty != null && mongoManageProperty.length() > 0) {
				try {
					mongoManage = Boolean.parseBoolean(mongoManageProperty);
				} catch (Exception e) {
					log.error("Property [MONGO_MANAGE] on properties file has an invalid value("
							+ mongoManageProperty + ")");
					System.exit(0);
				}
			}
			log.info("MONGO_MANAGE read from configuration file: "
					+ mongoManage);

			String mongoFinalRemoveProperty = properties
					.getProperty("MONGO_FINAL_REMOVE");
			if (mongoFinalRemoveProperty != null
					&& mongoFinalRemoveProperty.length() > 0) {
				try {
					mongoFinalRemove = Boolean
							.parseBoolean(mongoFinalRemoveProperty);
				} catch (Exception e) {
					log.error("Property [MONGO_FINAL_REMOVE] on properties file has an invalid value("
							+ mongoFinalRemoveProperty + ")");
					System.exit(0);
				}
			}
			log.info("MONGO_FINAL_REMOVE read from configuration file: "
					+ mongoFinalRemove);

			String dateFormatProperty = properties.getProperty("DATE_FORMAT");
			if (dateFormatProperty != null && dateFormatProperty.length() > 0) {
				try {
					if (dateFormatProperty.equals("NULL")) {
						dateFormat = null;
					} else {
						dateFormat = new SimpleDateFormat(dateFormatProperty);
					}
				} catch (Exception e) {
					log.error("Property [DATE_FORMAT] on properties file has an invalid value("
							+ dateFormatProperty + ")");
					System.exit(0);
				}
			}
			log.info("DATE_FORMAT read from configuration file: "
					+ (dateFormat == null ? "NULL" : dateFormat.toPattern()));

			String mongoAuthProperty = properties.getProperty("MONGO_AUTH");
			if (mongoAuthProperty != null && mongoAuthProperty.length() > 0) {
				try {
					mongoAuth = Boolean.parseBoolean(mongoAuthProperty);
				} catch (Exception e) {
					log.error("Property [MONGO_AUTH] on properties file has an invalid value("
							+ mongoAuthProperty + ")");
					System.exit(0);
				}
			}
			log.info("MONGO_AUTH read from configuration file: " + mongoAuth);

			if (mongoAuth) {
				String mongoUsernameProperty = properties
						.getProperty("mongo.user");
				if (mongoUsernameProperty != null
						&& mongoUsernameProperty.length() > 0) {
					try {
						mongoUsername = mongoUsernameProperty;
					} catch (Exception e) {
						log.error("Property [mongo.user] on properties file has an invalid value("
								+ mongoUsernameProperty + ")");
						System.exit(0);
					}
				}
				log.info("mongo.user read from configuration file: "
						+ mongoUsername);

				String mongoPasswordProperty = properties
						.getProperty("mongo.password");
				if (mongoPasswordProperty != null
						&& mongoPasswordProperty.length() > 0) {
					try {
						mongoPassword = mongoPasswordProperty;
					} catch (Exception e) {
						log.error("Property [mongo.password] on properties file has an invalid value("
								+ mongoPasswordProperty + ")");
						System.exit(0);
					}
				}
				log.info("mongo.password read from configuration file: "
						+ mongoPassword);
			}

			String mongoUserIDProperty = properties
					.getProperty("MONGO_USER_FIELD");
			if (mongoUserIDProperty != null && mongoUserIDProperty.length() > 0) {
				try {
					mongoUserID = mongoUserIDProperty;
				} catch (Exception e) {
					log.error("Property [MONGO_USER_FIELD] on properties file has an invalid value("
							+ mongoUserIDProperty + ")");
					System.exit(0);
				}
			}
			log.info("MONGO_USER_FIELD read from configuration file: "
					+ mongoUserID);

			String mongoItemIDProperty = properties
					.getProperty("MONGO_ITEM_FIELD");
			if (mongoItemIDProperty != null && mongoItemIDProperty.length() > 0) {
				try {
					mongoItemID = mongoItemIDProperty;
				} catch (Exception e) {
					log.error("Property [MONGO_ITEM_FIELD] on properties file has an invalid value("
							+ mongoItemIDProperty + ")");
					System.exit(0);
				}
			}
			log.info("MONGO_ITEM_FIELD read from configuration file: "
					+ mongoItemID);

			String mongoPreferenceProperty = properties
					.getProperty("MONGO_PREFERENCE_FIELD");
			if (mongoPreferenceProperty != null
					&& mongoPreferenceProperty.length() > 0) {
				try {
					mongoPreference = mongoPreferenceProperty;
				} catch (Exception e) {
					log.error("Property [MONGO_PREFERENCE_FIELD] on properties file has an invalid value("
							+ mongoPreferenceProperty + ")");
					System.exit(0);
				}
			}
			log.info("MONGO_PREFERENCE_FIELD read from configuration file: "
					+ mongoPreference);
			
			String mongoTimestampProperty = properties
					.getProperty("MONGO_TIMESTAMP");
			if (mongoTimestampProperty != null
					&& mongoTimestampProperty.length() > 0) {
				try {
					mongoTimestamp = mongoTimestampProperty;
				} catch (Exception e) {
					log.error("Property [MONGO_TIMESTAMP] on properties file has an invalid value("
							+ mongoTimestampProperty + ")");
					System.exit(0);
				}
			}
			log.info("MONGO_TIMESTAMP read from configuration file: "
					+ mongoTimestampProperty);

			String similarityMeasureProperty = properties
					.getProperty("SIMILARITY_MEASURE");
			if (similarityMeasureProperty != null
					&& similarityMeasureProperty.length() > 0) {
				try {
					similarityMeasure = similarityMeasureProperty;
				} catch (Exception e) {
					log.error("Property [SIMILARITY_MEASURE] on properties file has an invalid value("
							+ similarityMeasureProperty + ")");
					System.exit(0);
				}
			}
			log.info("SIMILARITY_MEASURE read from configuration file: "
					+ similarityMeasure);

			String neighborHoodProperty = properties
					.getProperty("NEIGHBORHOOD");
			if (neighborHoodProperty != null
					&& neighborHoodProperty.length() > 0) {
				try {
					neighborhoodType = neighborHoodProperty;
				} catch (Exception e) {
					log.error("Property [NEIGHBORHOOD] on properties file has an invalid value("
							+ neighborHoodProperty + ")");
					System.exit(0);
				}
			}
			log.info("NEIGHBORHOOD read from configuration file: "
					+ neighborhoodType);

			String userThresholdProperty = properties.getProperty("USER_TH");
			if (userThresholdProperty != null
					&& userThresholdProperty.length() > 0) {
				try {
					userThreshold = Double.parseDouble(userThresholdProperty);
				} catch (Exception e) {
					log.error("Property [USER_TH] on properties file has an invalid value("
							+ userThresholdProperty + ")");
					System.exit(0);
				}
			}
			log.info("USER_TH read from configuration file: " + userThreshold);

			String neighborsNumberProperty = properties
					.getProperty("NEIGHBORS_NUMBER");
			if (neighborsNumberProperty != null
					&& neighborsNumberProperty.length() > 0) {
				try {
					neighborsNumber = Integer.parseInt(neighborsNumberProperty);
				} catch (Exception e) {
					log.error("Property [NEIGHBORS_NUMBER] on properties file has an invalid value("
							+ neighborsNumberProperty + ")");
					System.exit(0);
				}
			}
			log.info("NEIGHBORS_NUMBER read from configuration file: "
					+ neighborsNumber);

			String maxRecommendationProperty = properties
					.getProperty("MAX_RECOMMENDATIONS");
			if (maxRecommendationProperty != null
					&& maxRecommendationProperty.length() > 0) {
				try {
					maxRecommendations = Integer
							.parseInt(maxRecommendationProperty);
				} catch (Exception e) {
					log.error("Property [MAX_RECOMMENDATIONS] on properties file has an invalid value("
							+ maxRecommendationProperty + ")");
					System.exit(0);
				}
			}
			log.info("MAX_RECOMMENDATIONS read from configuration file: "
					+ maxRecommendations);

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
