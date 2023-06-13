package org.bibalex.Servlet.Activities;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

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

import org.bibalex.DAO.ActivityDAO;
import org.bibalex.DAO.MemberDAO;
import org.bibalex.DAO.SkillDAO;
import org.bibalex.Models.Activity;

import jakarta.servlet.RequestDispatcher;

/**
 * Servlet implementation class ActivityServlet
 */
@WebServlet("/Activity")
public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private MemberDAO memberDAO;
	private ActivityDAO activityDAO;
	private SkillDAO skillDAO ;
	
	public void init() {
		memberDAO = new MemberDAO();
		activityDAO = new ActivityDAO();
		skillDAO = new SkillDAO();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("iam here in activity servlet");
		String action = request.getParameter("action") ;
		if (action == null || action.equals("")) {
		    action = "home";
		}
		System.out.println(action.isEmpty()+" "+ action + " ssssssssssssssss");
		try {
			switch (action) {
			case "add":
				showData(request, response);
				break;
			case "insert":
				//insertEmployee(request, response);
				break;
			case "delete":
				//deleteEmployee(request, response);
				break;
			case "edit":
				//editForm(request, response);
				break;
			case "update":
				//updateEmployee(request, response);
				break;
			default:
				showData(request, response);
				break;
			}
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	private void showData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Activity> activities = null;
		try {
			activities = activityDAO.getActivities();
		    request.setAttribute("activities", activities); 
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/activity/viewActivites.jsp");
	    
	    dispatcher.forward(request, response);
	}

}
