package com.ml.bus.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ml.util.CrawlUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getUserId(@RequestParam("access_token") String token) {
    	System.out.println(token);
    	Map<String, String> params = new HashMap<String, String>(1);
    	params.put("Authorization", "Bearer " + token);
    	return CrawlUtil.crawl("https://api.douban.com/v2/user/~me", params);
    }
}
