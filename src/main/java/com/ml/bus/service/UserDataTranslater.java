package com.ml.bus.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ml.db.MongoDB;
import com.ml.model.DoubanRatings;
import com.ml.model.Movie;
import com.ml.model.UserRattedMovies;
import com.ml.util.Constants;

public class UserDataTranslater {
	private Map<String, Movie> movieMap;
	private void preprocess(List<Movie> movies) {
		movieMap = new HashMap<String, Movie>(movies.size());
		for(Movie movie: movies) {
			movieMap.put(movie.getImdbID(), movie);
		}
	}
	
	private void processDup(MongoDB mongo, List<Movie> movies, List<UserRattedMovies> urms) {
		Map<String, Movie> movieMap = new HashMap<String, Movie>(movies.size());
		for(Movie movie: movies) {
			movieMap.put(movie.getId(), movie);
		}
		int i = 0;
		for(UserRattedMovies urm: urms) {
			if(movieMap.get(String.valueOf(urm.getMovieID())) == null) {
				System.out.println(urm.getMovieID());
				i++;
				mongo.delete(urm, Constants.userRattedMoviesCollectionName);
			}
		}
		System.out.println(i);
	}
	
	//1, if movie not exist, add to movie collection
	//2, if movie exist, add ratings to userRattedMovies collection
	public void translate(MongoDB mongo, List<DoubanRatings> drms) {
		List<Movie> moviesToBeAddList = new ArrayList<Movie>();
		List<UserRattedMovies> ratingsToBeAddList = new ArrayList<UserRattedMovies>(drms.size());
		for(DoubanRatings drm: drms) {
			Movie movie = movieMap.get(drm.getImdbID());
			
			//1, add movie
			if(movie == null) {
				String title = drm.getDoubanTitle();
				String imdbID = drm.getImdbID();
				String doubanID = drm.getDoubanUrl();
				String id = doubanID + "" + (imdbID == null ? "" : imdbID);
				Double rating = drm.getDoubanRate();
				String picUrl = drm.getPicUrl();
				List<String> genres = drm.getGenres();
				movie = new Movie(id, title,
						imdbID, doubanID, rating, picUrl, genres);
				
				movieMap.put(imdbID, movie);
				moviesToBeAddList.add(movie);
			}

			//2, add ratings
			Long userID = Long.parseLong(drm.getUserID());
			Long movieID = Long.parseLong(movie.getId());
			Double rating = drm.getDoubanRate();
			Long timestamp = drm.getTimestamp();
			UserRattedMovies urm = new UserRattedMovies(userID, movieID, rating, timestamp);
			ratingsToBeAddList.add(urm);
		}
		
		//save
		for(Movie movie: moviesToBeAddList) {
			mongo.save(movie, Constants.movieCollectionName);
		}
		
		for(UserRattedMovies urm: ratingsToBeAddList) {
			mongo.save(urm, Constants.userRattedMoviesCollectionName);
		}
				
	}
	
	public static void main(String[] args) throws ParseException {
		String confFile = Constants.defaultConfigFile;
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(confFile));
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}
		MongoDB m = new MongoDB(props);
		
		//get
		List<Movie> movies = m.findAll(Movie.class, Constants.movieCollectionName);
		List<DoubanRatings> drms = m.findAll(DoubanRatings.class, Constants.doubanRatingsCollectionName);
		List<UserRattedMovies> urms = m.findAll(UserRattedMovies.class, Constants.userRattedMoviesCollectionName);

		//process and translate
		UserDataTranslater udt = new UserDataTranslater();
		
		//udt.processDup(m, movies, urms);
		
		udt.preprocess(movies);
		udt.translate(m, drms);
		
		
    }
	
	

	
}
