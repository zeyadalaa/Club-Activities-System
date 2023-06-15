package org.bibalex.Servlet.Skills;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.bibalex.Models.Skill;

/**
 * Servlet implementation class SkillServlet
 */
@WebServlet("/Skill")
public class SkillServlet extends HttpServlet {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
		System.out.println("iam here in activity servlet");
		String action = request.getParameter("action") ;
		if (action == null || action.equals("")) {
		    action = "home";
		}
		
		try {
			switch (action) {
			case "add":
				showNewForm(request, response);
				break;
			case "insert":
				insertSkill(request, response);
				break;
			case "delete":
				deleteSkill(request, response);
				break;
			case "edit":
				editForm(request, response);
				break;
			case "update":
				updateSkill(request, response);
				break;
			default:
				showData(request, response);
				break;
			}
		} catch (SQLException | ServletException | IOException ex) {
			ex.printStackTrace();
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
		
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/skill/addSkill.jsp");
	    
	    dispatcher.forward(request, response);
	}
	
	private void insertSkill(HttpServletRequest request, HttpServletResponse response)   {
		String skillName = request.getParameter("SkillName");

		try {
			skillDAO.addSkill(skillName);
			showData(request, response);
		}catch (SQLIntegrityConstraintViolationException e) {
		    try {
			    String errorMessage = "Skill already existed !";
			    request.setAttribute("errorMessage", errorMessage);
				request.getRequestDispatcher("/JSP/skill/addSkill.jsp").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException | ServletException | IOException e) {
		    try {
			    String errorMessage = "An error occurred while performing the operation. Please try again later.";
			    request.setAttribute("errorMessage", errorMessage);
			    showNewForm(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		    
		}
	}
	
	private void deleteSkill(HttpServletRequest request, HttpServletResponse response) {
		int skillid = Integer.parseInt( request.getParameter("skillid") );
		try {
			skillDAO.deleteSkill(skillid);
			showData(request, response);
		} catch (SQLException | ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateSkill(HttpServletRequest request, HttpServletResponse response)  {
		
		int skillID = Integer.parseInt( request.getParameter("skillid") );
		String skillName = request.getParameter("SkillName");
		try {
			skillDAO.updateSkill(skillID,skillName);
			showData(request, response);
		}catch (SQLIntegrityConstraintViolationException e) {
		    
		    try {
			    String errorMessage = "Skill name already existed !";
			    request.setAttribute("errorMessage", errorMessage);
		    	editForm(request, response);
			} catch (ServletException | IOException | SQLException e1) {
				e1.printStackTrace();
			}
		    
		} catch (SQLException | ServletException | IOException e) {
		    
		    try {
			    String errorMessage = "An error occurred while performing the operation. Please try again later.";
			    request.setAttribute("errorMessage", errorMessage);
		    	editForm(request, response);
			} catch (ServletException | IOException | SQLException e1) {
				e1.printStackTrace();
			}
		    
		}
	}
	
	private void editForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int skillid = Integer.parseInt(request.getParameter("skillid"));
		Skill skill = skillDAO.getSkillsByID(skillid);
		request.setAttribute("skill", skill);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/skill/addSkill.jsp");
		dispatcher.forward(request, response);

	}
	
	private void showData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Skill> skills = null;
		skills = skillDAO.getSkills();
	    request.setAttribute("skills", skills); 
	    response.setCharacterEncoding("UTF-8");
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/skill/viewSkills.jsp");
	    
	    dispatcher.forward(request, response);
	}
}
