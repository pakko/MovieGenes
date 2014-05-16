package com.ml.bus.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.ml.db.MongoDB;
import com.ml.model.Movie;
import com.ml.model.UserRattedMovies;
import com.ml.model.UserTaggedMovies;
import com.ml.util.Constants;

public class DataImporter {

	private static Map<String, List<String>> genresMap = new HashMap<String, List<String>>();
	private static void processGenres() throws IOException {
		List<String> movieGenresLines = FileUtils.readLines(new File(Constants.movieGenresFilePath), "UTF-8");
		
		for(String genre: movieGenresLines) {
			String[] g = genre.split("\t");
			List<String> v = genresMap.get(g[0]);
			if(v == null)
				v = new ArrayList<String>();
			v.add(g[1]);
			genresMap.put(g[0], v);
		}
		System.out.println(genresMap);
	}
	
	public static void movieImport(MongoDB mongo) throws IOException {
		processGenres();
		
		List<String> movieLines = FileUtils.readLines(new File(Constants.movieFilePath), "UTF-8");
		for(String movieLine: movieLines) {
			
			String[] m = movieLine.split("\t");
			Movie movie = null;
			if(movieLine.contains("\\N")) {
				movie = new Movie(m[0], m[1], m[2], null, 0.0, m[4], genresMap.get(m[0]));
				/*movie = new Movie(m[0], m[1], m[2], m[3], m[4], Integer.parseInt(m[5]), null,
						null, null, null, null, null, null, null, null, null, null, null, null, null, null);*/
			}
			else {
				movie = new Movie(m[0], m[1], m[2], null, Double.parseDouble(m[7]), m[20], genresMap.get(m[0]));
				/*movie = new Movie(m[0], m[1], m[2], m[3], m[4], Integer.parseInt(m[5]), m[6], 
						Double.parseDouble(m[7]), Long.parseLong(m[8]), Long.parseLong(m[9]), Long.parseLong(m[10]), Long.parseLong(m[11]), 
						Double.parseDouble(m[12]), Long.parseLong(m[13]), Long.parseLong(m[14]), Long.parseLong(m[15]), Long.parseLong(m[16]), 
						Double.parseDouble(m[17]), Long.parseLong(m[18]), Long.parseLong(m[19]), m[20]);*/
			}
			mongo.save(movie, Constants.movieCollectionName);
		}
	}
	
	public static void userRatedMoviesImport(MongoDB mongo) throws IOException {
		List<String> userRatedMoviesLines = FileUtils.readLines(new File(Constants.userRattedMoviesFilePath), "UTF-8");
		for(String userRatedMoviesLine: userRatedMoviesLines) {
			String[] m = userRatedMoviesLine.split("\t");
			
			UserRattedMovies userRatedMovies = new UserRattedMovies(Long.parseLong(m[0]), Long.parseLong(m[1]), Double.parseDouble(m[2]), Long.parseLong(m[3]));
			mongo.save(userRatedMovies, Constants.userRattedMoviesCollectionName);
		}
	}

	public static void userTaggedMoviesImport(MongoDB mongo) throws IOException {
		List<String> userTaggedMoviesLines = FileUtils.readLines(new File(Constants.userTaggedMoviesFilePath), "UTF-8");
		for(String userTaggedMoviesLine: userTaggedMoviesLines) {
			String[] m = userTaggedMoviesLine.split("\t");

			UserTaggedMovies userTaggedMovies = new UserTaggedMovies(Long.parseLong(m[0]), Long.parseLong(m[1]), Long.parseLong(m[2]), Long.parseLong(m[3]));
			mongo.save(userTaggedMovies, Constants.userTaggedMoviesCollectionName);
		}
	}
	
	public static void main(String[] args) throws IOException {
		String confFile = Constants.defaultConfigFile;
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(confFile));
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}
		MongoDB m = new MongoDB(props);
		//DataImporter.movieImport(m);
		DataImporter.userRatedMoviesImport(m);
		//DataImporter.userTaggedMoviesImport(m);
	}
}
