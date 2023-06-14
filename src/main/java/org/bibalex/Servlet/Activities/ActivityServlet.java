package org.bibalex.Servlet.Activities;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

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
import org.bibalex.DAO.ActivitySkillDAO;
import org.bibalex.DAO.MemberDAO;
import org.bibalex.DAO.SkillDAO;
import org.bibalex.Models.Activity;
import org.bibalex.Models.Skill;

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
	private ActivitySkillDAO activitySkillDAO ;
	
	public void init() {
		memberDAO = new MemberDAO();
		activityDAO = new ActivityDAO();
		skillDAO = new SkillDAO();
		activitySkillDAO = new ActivitySkillDAO();
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
				showNewForm(request, response);
				break;
			case "insert":
				insertActivity(request, response);
				break;
			case "delete":
				deleteActivity(request, response);
				break;
			case "edit":
				editForm(request, response);
				break;
			case "update":
				updateActivity(request, response);
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
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Skill> skills;
		try {
			skills = skillDAO.getSkills();
		    request.setAttribute("skills", skills); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/activity/addActivity.jsp");
	    
	    dispatcher.forward(request, response);
	}
	

	private void insertActivity(HttpServletRequest request, HttpServletResponse response) 
			 {
		String ActivityName = request.getParameter("ActivityName");
		String ActivityDescription = request.getParameter("ActivityDescription");
		int skillMinimumAge = Integer.parseInt( request.getParameter("ActivityMinimumAge") );
		int skillMaximumAge = Integer.parseInt( request.getParameter("ActivityMaximumAge") );
		String[] selectedSkills = request.getParameterValues("skillsNames[]");
		try {
			Integer activityID = activityDAO.addActivity(ActivityName,ActivityDescription,skillMinimumAge,skillMaximumAge);

			if (selectedSkills != null) {
			    for (String skillId : selectedSkills) {
			    	Integer skillID = Integer.parseInt(skillId);
			    	activitySkillDAO.addActivitySkill(activityID, skillID);
			    }
			}
			
			showData(request, response);
			
		}catch (SQLIntegrityConstraintViolationException e) {
		    try {
			    String errorMessage = "Activity name already existed !";
			    request.setAttribute("errorMessage", errorMessage);
			    showNewForm(request, response);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
		    try {
			    String errorMessage = "An error occurred while performing the operation. Please try again later.";
			    request.setAttribute("errorMessage", errorMessage);
				showData(request, response);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private void updateActivity(HttpServletRequest request, HttpServletResponse response)  {

		String ActivityName = request.getParameter("ActivityName");
		String ActivityDescription = request.getParameter("ActivityDescription");
		int skillMinimumAge = Integer.parseInt( request.getParameter("ActivityMinimumAge") );
		int skillMaximumAge = Integer.parseInt( request.getParameter("ActivityMaximumAge") );
		String[] selectedSkills = request.getParameterValues("skillsNames[]");
		Integer activityID = Integer.parseInt( request.getParameter("activityid") );
		try {
			activityDAO.updateActivity(activityID,ActivityName,ActivityDescription,skillMinimumAge,skillMaximumAge);
			activitySkillDAO.deleteActivitySkill(activityID);
			if (selectedSkills != null) {
			    for (String skillId : selectedSkills) {
			    	Integer skillID = Integer.parseInt(skillId);
			    	activitySkillDAO.addActivitySkill(activityID, skillID);
			    }
			}
			
			showData(request, response);
		}catch (SQLIntegrityConstraintViolationException e) {
		    try {
			    String errorMessage = "Activity name already existed !";
			    request.setAttribute("errorMessage", errorMessage);
		    	editForm(request, response);
			} catch (ServletException | IOException | SQLException e1) {
				e1.printStackTrace();
			}
		    
		} catch (SQLException | ServletException | IOException e) {
			e.printStackTrace();
		    
		    try {
			    String errorMessage = "An error occurred while performing the operation. Please try again later.";
			    request.setAttribute("errorMessage", errorMessage);
		    	editForm(request, response);
			} catch (ServletException | IOException | SQLException e1) {
				e1.printStackTrace();
			}
		    
		}
	}
	
	private void deleteActivity(HttpServletRequest request, HttpServletResponse response) {
		int activityid = Integer.parseInt( request.getParameter("activityid") );
		try {
			activityDAO.deleteActivity(activityid);
			showData(request, response);
		} catch (SQLException | ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void editForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int activityid = Integer.parseInt(request.getParameter("activityid"));
		Activity activity = activityDAO.getActivityByID(activityid);
		Set<String> selectedActivity = activitySkillDAO.getActivitySkillsByID(activityid);
		List<Skill> skills = skillDAO.getSkills();
		
		request.setAttribute("activity", activity);
		request.setAttribute("skills", skills);
		request.setAttribute("selectedActivity", selectedActivity);
		

		RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/activity/addActivity.jsp");
		dispatcher.forward(request, response);

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
