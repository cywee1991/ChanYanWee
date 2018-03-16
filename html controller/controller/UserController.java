package com.optimum.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.optimum.pojo.User;
import com.optimum.dao.*;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
	
	private UserDAO refUserDAO;
	private User refUser;
	
	private RequestDispatcher refRequestDispatcher;
	
	public UserController() {
		refUserDAO = new UserDAOImpl();
		refUser = new User();
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter  out = response.getWriter();
		
		// get data from html page
		
		String userName = request.getParameter("uname");
		String password = request.getParameter("pwd");
	
		// set data to POJO class
		
		refUser.setUserName(userName);
		refUser.setUserPassword(password);
		
		if (refUserDAO.loginAuthentication(refUser)) {  //true  
			refRequestDispatcher = request.getRequestDispatcher("Welcome.jsp");
			refRequestDispatcher.forward(request, response);
			
		} else {           //false
			
			out.println("<html><body><font color='red'>Invalid Login or Password</font></body></html>");
			refRequestDispatcher = request.getRequestDispatcher("login.html");
			refRequestDispatcher.include(request, response);
				
		}
		
	}

}
