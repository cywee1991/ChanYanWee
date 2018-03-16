package com.optimum.dao;

import com.optimum.pojo.User;

public class UserDAOImpl implements UserDAO {

	private boolean loginStatus;
	
	@Override
	public boolean loginAuthentication(User refUser) {
		
		
		
		if (refUser.getUserName().equals("admin")&& refUser.getUserPassword().equals("admin123")) {
			
			loginStatus = true;
			
			
		} else {
			loginStatus = false;
		}
		
		return loginStatus;
	}
	}


