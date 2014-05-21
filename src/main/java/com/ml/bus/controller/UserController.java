package com.ml.bus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ml.bus.service.UserService;
import com.ml.model.DoubanUser;
import com.ml.util.CrawlUtil;
import com.ml.util.Pagination;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserService userService;
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getUserId(@RequestParam("access_token") String token) {
    	System.out.println(token);
    	Map<String, String> params = new HashMap<String, String>(1);
    	params.put("Authorization", "Bearer " + token);
    	return CrawlUtil.crawl("https://api.douban.com/v2/user/~me", params);
    }
    
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> getUser(HttpServletRequest servletRequest) {
    	
    	Pagination pager = new Pagination(servletRequest);
    	userService.findByPage(pager);
    	
    	@SuppressWarnings("unchecked")
		List<DoubanUser> users = (List<DoubanUser>) pager.getItems();
    	Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalPages", pager.getTotalPage());
		result.put("currentPage", pager.getCurrentPage());
		result.put("totalRecords", pager.getTotalCount());
		result.put("items", users);
		
		return result;
    }
}
