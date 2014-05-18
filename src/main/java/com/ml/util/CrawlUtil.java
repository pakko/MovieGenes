package com.ml.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class CrawlUtil {

	public static String crawl(String site, Map<String, String> params) {
		HttpURLConnection httpConn = null;
		InputStream in = null;
		try {
			URL url = new URL(site);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");
			if(params != null) {
				for(String key: params.keySet()) {
					httpConn.setRequestProperty(key, params.get(key));
				}
			}
			
			in = httpConn.getInputStream();
			
			//for douban limitation, sleep
			Thread.sleep(8000);
			
			return handleResponse("UTF-8", in);
		
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
			httpConn.disconnect();
		}
		return null;
	}
	
	private static String handleResponse(String charset, InputStream in) throws IOException {
        String content = IOUtils.toString(in, charset);
        if(content.equals(""))
        	return null;
        return content;
    }
}
