package com.ml.util;

public class Constants {

	public static final String separator = "/";
	public static String currentDir = Constants.class.getResource("/")
			.getPath();

	public static final String defaultConfigFile = currentDir + separator
			+ "default.properties";

	public static final String movieCollectionName = "movie";
	public static final String userRattedMoviesCollectionName = "userRattedMovies";
	public static final String userTaggedMoviesCollectionName = "userTaggedMovies";
	public static final String doubanRatingsCollectionName = "doubanRatings";
	public static final String dbUserCollectionName = "dbuser";
	public static final String recommendCollectionName = "recommend";

	public static final String movieFilePath = currentDir + separator + "movies.dat";
	public static final String userRattedMoviesFilePath = currentDir + separator + "user_ratedmovies-timestamps.dat";
	public static final String userTaggedMoviesFilePath = currentDir + separator + "user_taggedmovies-timestamps.dat";
	public static final String movieGenresFilePath = currentDir + separator + "movie_genres.dat";

	
}
