package org.bibalex.Servlet.Admin;


import java.io.IOException;
import java.security.MessageDigest;

import org.bibalex.Configuration.Configuration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;

	 public void init() {
        ServletContext servletContext = getServletContext();
        Configuration.loadConfig(servletContext);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String adminUsername = Configuration.getAdminUsername();
		String adminPassword = Configuration.getAdminPassword();
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = null;
		if(adminUsername.equals(hashPassword(userName)) && adminPassword.equals(hashPassword(password))) {
			session = request.getSession();
			session.setAttribute("loggedIn", true);
			
		    request.getRequestDispatcher("/Member").forward(request, response);
		}else {
			String errorMessage = "Please enter username and password correct";
			request.setAttribute("errorMessage", errorMessage);
		    request.getRequestDispatcher("/JSP/loginPage/loginPage.jsp").forward(request, response);
		}	

	}
	
	public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null; 
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
