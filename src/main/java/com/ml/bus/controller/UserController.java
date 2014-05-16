package com.ml.bus.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getUserId(@RequestParam("access_token") String token) {
    	System.out.println(token);
    	return crawl("https://api.douban.com/v2/user/~me", token);
    }
    
    
    private String crawl(String site, String token) {
		HttpURLConnection httpConn = null;
		InputStream in = null;
		try {
			URL url = new URL(site);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.setRequestProperty("Authorization", "Bearer " + token);

			in = httpConn.getInputStream();
			
			return handleResponse("UTF-8", in);
		
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
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
	
	private String handleResponse(String charset, InputStream in) throws IOException {
        String content = IOUtils.toString(in, charset);
        if(content.equals(""))
        	return null;
        return content;
    }
}
