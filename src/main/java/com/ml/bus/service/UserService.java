package com.ml.bus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ml.bus.dao.UserDAO;
import com.ml.model.DoubanUser;
import com.ml.util.Pagination;

@Service
public class UserService {

	@Autowired
	UserDAO userDAO;

	public List<DoubanUser> findAll() {
		return userDAO.findAll();
	}
	
	public List<DoubanUser> findByName(String title) {
		return userDAO.findByName(title);
	}
	
	public List<DoubanUser> findById(String id) {
		return userDAO.findById(id);
	}
	
	public Pagination findByPage(Pagination pager) {
		return userDAO.findByPage(pager);
	}
	
}
