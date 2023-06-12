package org.bibalex.Filter;


import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;

//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;

public class MyFilter implements Filter{
	public void init(FilterConfig arg0) throws ServletException {}  

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        HttpSession session = request.getSession(false); 
		System.out.println("iam in do filter");

	    if (session != null && session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
	        chain.doFilter(request, response);
	    } else {
			String errorMessage = "Please login";
			request.setAttribute("errorMessage", errorMessage);
		    request.getRequestDispatcher("/JSP/loginPage/loginPage.jsp").forward(request, response);
	    }
		
	}
    
}
