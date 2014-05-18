package com.ml.bus.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ml.db.IBaseDB;
import com.ml.db.MongoDB;
import com.ml.model.DoubanRatings;
import com.ml.model.DoubanUser;
import com.ml.model.Movie;
import com.ml.util.Constants;
import com.ml.util.CrawlUtil;

@Service
public class UserDataCrawler {
	
	private final String API_URL = "http://api.douban.com/v2";
	private final String MOVIE_URL = "http://movie.douban.com";

	private final String FORMAT_PATTERN = "yyyy-MM-dd";
	private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN);
	
	private Map<String, Double> rattingMap = new HashMap<String, Double>(9) {
		private static final long serialVersionUID = 1L;
		{
			put("rating1-t", 1.0);
			put("rating15-t", 1.5);
			put("rating2-t", 2.0);
			put("rating25-t", 2.5);
			put("rating3-t", 3.0);
			put("rating35-t", 3.5);
			put("rating4-t", 4.0);
			put("rating45-t", 4.5);
			put("rating5-t", 5.0);
	}};
	
	public int getSimplePage(String content) {
		Document doc = Jsoup.parse(content);
        Elements trs = doc.select("div.paginator a");
        String page = trs.get(trs.size() - 2).text();
        return Integer.parseInt(page);
	}
	
	String regEx="[^0-9]";   
    Pattern p = Pattern.compile(regEx);
	public int getMultiPage(String content) {
		Document doc = Jsoup.parse(content);
        Elements trs = doc.select("#paginator span.total");
        Matcher m = p.matcher(trs.text());   
        return Integer.parseInt(m.replaceAll("").trim());
	}
	
	//for simple page
	public void parseUserRatedMovies(String html, IBaseDB m, String userID)  {
		String doubanID = null;
		try{
	        Document doc = Jsoup.parse(html);
	        Elements trs = doc.select("div.article ul.list-view li");
	        for (Element row: trs) {  
	        	Elements cols = row.children();
	        	doubanID = cols.select("div.title a").attr("href").split("/")[4];
	        	String urate = cols.select("div.date span").attr("class");
	        	String date = cols.select("div.date").text();
	        	
	        	//get genres
	        	String json = CrawlUtil.crawl(API_URL + "/movie/subject/" + doubanID, null);
	        	ObjectMapper objectMapper = new ObjectMapper();
	        	Map map = objectMapper.readValue(json, HashMap.class);
	        	String href = (String) map.get("alt");
	        	String title = (String) map.get("title");
	        	String picUrl = (String) ((Map) map.get("images")).get("large");
	        	List<String> genres = (List<String>) map.get("genres");
	        	Object drate = ((Map) map.get("rating")).get("average");
	        	Double doubanRate = null;
	        	if(drate instanceof Double){
	        		doubanRate = (Double) drate;
	        	}
	        	else if(drate instanceof Integer){
	        		doubanRate = ((Integer) drate).doubleValue();
	        	}
	        	
	        	//get imdb id
	        	String content = CrawlUtil.crawl(href, null);
	        	Document cdoc = Jsoup.parse(content);
	            Elements x = cdoc.select("#info a");
	            String imdbID = null;
	            for(Element r: x) {
	            	if(r.attr("href").contains("imdb")) {
	            		imdbID = r.text().substring(2);
	            		break;
	            	}
	            }
	        	
	        	//translate ratting
	        	Double userRate;
	        	if(urate == null || urate.equals(""))
	        		userRate = 0.0;
	        	else 
	        		userRate = rattingMap.get(urate);
	        	
	        	//format date
	        	date = date.replaceAll("\u00a0", "").replaceAll(" ", "").trim();
	        	Date d = sdf.parse(date);
	        	long timestamp = d.getTime();
	        	
	        	DoubanRatings durm = new DoubanRatings(userID, doubanID, title,
	        			imdbID, userRate, timestamp, picUrl, genres, doubanRate);
	        	System.out.println(durm);
	        	m.save(durm, Constants.doubanRatingsCollectionName);
	        }
		} catch(Exception e){
			System.err.println("error url:" + doubanID);
			e.printStackTrace();
		}
	}
	
	public void parseMovie(String html, IBaseDB m) {
		String doubanID = null;
		try{
	        Document doc = Jsoup.parse(html);
	        Elements trs = doc.select("div.article ol.grid_view li");
	
	        for (Element row: trs) {  
	        	Elements cols = row.children();
	        	
	        	doubanID = cols.select("div.hd a").attr("href").split("/")[4];
	        	
	        	//get genres
	        	String json = CrawlUtil.crawl(API_URL + "/movie/subject/" + doubanID, null);
	        	ObjectMapper objectMapper = new ObjectMapper();
	        	Map map = objectMapper.readValue(json, HashMap.class);
	        	String href = (String) map.get("alt");
	        	String title = (String) map.get("title");
	        	String picUrl = (String) ((Map) map.get("images")).get("large");
	        	List<String> genres = (List<String>) map.get("genres");
	        	Object rate = ((Map) map.get("rating")).get("average");
	        	Double doubanRate = null;
	        	if(rate instanceof Double){
	        		doubanRate = (Double) rate;
	        	}
	        	else if(rate instanceof Integer){
	        		doubanRate = ((Integer) rate).doubleValue();
	        	}

	        	//get imdb id
	        	String content = CrawlUtil.crawl(href, null);
	        	if(content == null)
	        		continue;
	        	Document cdoc = Jsoup.parse(content);
	            Elements x = cdoc.select("#info a");
	            String imdbID = null;
	            for(Element r: x) {
	            	if(r.attr("href").contains("imdb")) {
	            		imdbID = r.text().substring(2);
	            		break;
	            	}
	            }
	        	
				String id = doubanID + "" + (imdbID == null ? "" : imdbID);
				
	        	Movie movie = new Movie(id, title, imdbID, doubanID, doubanRate, picUrl, genres);
	        	System.out.println(movie);
	        	
	        	if(movieMap.get(imdbID) == null)
	        		m.save(movie, Constants.movieCollectionName);
	        }
		} catch(Exception e){
			System.err.println("error url:" + doubanID);
			e.printStackTrace();
		}
	}
	
	public void parseUser(String html, IBaseDB m) {
		String doubanID = null;
		try{
	        Document doc = Jsoup.parse(html);
	        Elements trs = doc.select("#comments div.comment-item");
	
	        for (Element row: trs) {  
	        	Elements cols = row.children();
	        	String username = cols.select("div.avatar").first().select("a").attr("href").split("/")[4];
	        	
	        	//get user id
	        	String json = CrawlUtil.crawl(API_URL + "/user/" + username, null);
	        	ObjectMapper objectMapper = new ObjectMapper();
	        	Map map = objectMapper.readValue(json, HashMap.class);
	        	String id = (String) map.get("id");
	        	DoubanUser user = new DoubanUser(username, -1, id);
	        	System.out.println(user);
				m.save(user, Constants.dbUserCollectionName);
				
	        }
		} catch(Exception e){
			System.err.println("error url:" + doubanID);
			e.printStackTrace();
		}
	}
	
	private Map<String, Movie> movieMap;
	public void preprocess(List<Movie> movies) {
		movieMap = new HashMap<String, Movie>(movies.size());
		for(Movie movie: movies) {
			movieMap.put(movie.getImdbID(), movie);
		}
	}
	
	//crawl user's rating movies
	public void crawlRatingList(IBaseDB m, String userID) {
		String url0 = MOVIE_URL + "/people/" + userID + "/collect?start=0&sort=time&rating=all&filter=all&mode=list";
		String content = CrawlUtil.crawl(url0, null);
		int pageNum = getSimplePage(content);
		
		for(int i = 0; i < pageNum; i++) {
			String url = MOVIE_URL + "/people/" + userID + "/collect?start=" + i * 30 + "&sort=time&rating=all&filter=all&mode=list";
			String html = CrawlUtil.crawl(url, null);
			parseUserRatedMovies(html, m, userID);
		}
	}
	
	//crawl top 250 movies
	public void crawlMovie(IBaseDB m) {
		String url0 = MOVIE_URL + "/top250";
		String content = CrawlUtil.crawl(url0, null);
		int pageNum = getSimplePage(content);

		for(int i = 0; i < pageNum; i++) {
			String url = MOVIE_URL + "/top250?start=" + i * 25 + "&filter=&type=";
			String html = CrawlUtil.crawl(url, null);
			parseMovie(html, m);
		}
	}
	
	//from movie's comments to crawl users
	public void crawlUser(IBaseDB m, String movieID) {
		String url0 = MOVIE_URL + "/subject/" + movieID + "/comments";
		String content = CrawlUtil.crawl(url0, null);
		int pageNum = getMultiPage(content);
		
		for(int i = 0; i < pageNum; i++) {
			String url = MOVIE_URL + "/subject/" + movieID + "/comments?start=" + i * 20 + "&limit=20&sort=new_score";
			String html = CrawlUtil.crawl(url, null);
			parseUser(html, m);
		}
	}
	
	//transfer old data to new 
	public void transferUser(IBaseDB m, List<DoubanUser> users) {
		String userID = null;
		try{
			for(DoubanUser user: users) {
				//get genres
	        	String json = CrawlUtil.crawl(API_URL + "/user/" + user.getId(), null);
	        	ObjectMapper objectMapper = new ObjectMapper();
	        	Map map = objectMapper.readValue(json, HashMap.class);
	        	userID = (String) map.get("id");
	        	m.delete(user, Constants.dbUserCollectionName);
	        	DoubanUser newUser = new DoubanUser(user.getId(), -1, userID);
	        	System.out.println(newUser);
				m.save(newUser, Constants.dbUserCollectionName);
			}
		} catch(Exception e){
			System.err.println("error url:" + userID);
			e.printStackTrace();
		}
	}

	//movielens data to douban movie
	public void transferMovie(IBaseDB m, List<Movie> movies) {
		String doubanID = null;
		try{
			for(Movie movie: movies) {
				//get genres
	        	String json = CrawlUtil.crawl(API_URL + "/movie/imdb/tt" + movie.getImdbID(), null);
	        	if(json == null ||json.equals(""))
	        		continue;
	        	ObjectMapper objectMapper = new ObjectMapper();
	        	Map map = objectMapper.readValue(json, HashMap.class);
	        	String title = ((String) map.get("alt_title")).split("/")[0];
	        	doubanID = ((String) map.get("alt")).split("/")[4];
	        	List<String> genres = (List<String>) ((Map) map.get("attrs")).get("movie_type");
	        	
	        	m.delete(movie, Constants.movieCollectionName);
	        	Movie newMovie = new Movie(movie.getId(), title, movie.getImdbID(), doubanID, movie.getRating(), movie.getPicUrl(), genres);
				System.out.println(newMovie);
	        	m.save(newMovie, Constants.movieCollectionName);
			}
		} catch(Exception e){
			System.err.println("error url:" + doubanID);
			e.printStackTrace();
		}
	}
	
	//fill data that title is blank and rating is 0
	public void transferBlankMovie(IBaseDB m, List<Movie> movies) {
		String doubanID = null;
		try{
			for(Movie movie: movies) {
				//get genres
				doubanID = movie.getDbID();
	        	String json = CrawlUtil.crawl(API_URL + "/movie/subject/" + doubanID, null);
	        	if(json == null ||json.equals(""))
	        		continue;
	        	ObjectMapper objectMapper = new ObjectMapper();
	        	Map map = objectMapper.readValue(json, HashMap.class);
	        	String title = (String) map.get("title");
	        	Object rate = ((Map) map.get("rating")).get("average");
	        	Double doubanRate = null;
	        	if(rate instanceof Double){
	        		doubanRate = (Double) rate;
	        	}
	        	else if(rate instanceof Integer){
	        		doubanRate = ((Integer) rate).doubleValue();
	        	}
	        	
	        	m.delete(movie, Constants.movieCollectionName);
	        	Movie newMovie = new Movie(movie.getId(), title, movie.getImdbID(), doubanID, 
	        			doubanRate, movie.getPicUrl(), movie.getGenres());
				System.out.println(newMovie);
	        	m.save(newMovie, Constants.movieCollectionName);
			}
		} catch(Exception e){
			System.err.println("error url:" + doubanID);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ParseException, InterruptedException, JsonParseException, JsonMappingException, IOException {
		String confFile = Constants.defaultConfigFile;
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(confFile));
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}
		MongoDB m = new MongoDB(props);
		UserDataCrawler jp = new UserDataCrawler();
		//List<Movie> movies = m.findAll(Movie.class, Constants.movieCollectionName);
		List<DoubanUser> users = m.findAll(DoubanUser.class, Constants.dbUserCollectionName);

		

		//crawl movie
		//jp.preprocess(movies);
		//jp.crawlMovie(m);
		
		//crawUser
		String movieID = "1296736";
		//jp.crawlUser(m, movieID);
		
		//transfer user, for old added users only add username
		jp.transferUser(m, users);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("dbID").is(null));
		//List<Movie> movies = m.find(query, Movie.class, Constants.movieCollectionName);
		//System.out.println(movies.size());
		
		//jp.transferMovie(m, movies);
		
		String userID = "3032305";
		//jp.crawlRatingList(m, userID);
		
    }
}
