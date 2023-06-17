package org.bibalex.Servlet.Members;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.bibalex.DAO.ActivityDAO;
import org.bibalex.DAO.ActivitySkillDAO;
import org.bibalex.DAO.MemberActivityDAO;
import org.bibalex.DAO.MemberDAO;
import org.bibalex.DAO.MemberSkillDAO;
import org.bibalex.DAO.SkillDAO;
import org.bibalex.Models.Activity;
import org.bibalex.Models.Member;
import org.bibalex.Models.Skill;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;


/**
 * Servlet implementation class Member
 */
@WebServlet("/Member")
@MultipartConfig(
        fileSizeThreshold = 1024 * 10,  // 10 KB
        maxFileSize = 1024 * 1024 * 10,       // 10 MB
        maxRequestSize = 1024 * 1024 * 10    // 10 MB 
)
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private MemberDAO memberDAO;
	private ActivityDAO activityDAO;
	private SkillDAO skillDAO ;
	private MemberActivityDAO memberActivityDAO ;
	private MemberSkillDAO  memberSkillDAO;
	
	public void init() {
		memberDAO = new MemberDAO();
		activityDAO = new ActivityDAO();
		skillDAO = new SkillDAO();
		memberActivityDAO = new MemberActivityDAO();
		memberSkillDAO = new MemberSkillDAO();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("iam here in member servlet");
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
				insertMember(request, response);
				break;
			case "delete":
				deleteMember(request, response);
				break;
			case "deleteMemberActivity":
				deleteMemberActivity(request, response);
				break;
			case "edit":
				editForm(request, response);
				break;
			case "update":
				updateMember(request, response);
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
	
	private void editForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		Integer memberid = Integer.parseInt(request.getParameter("memberid"));
		Member member = memberDAO.getMemberByID(memberid);
		Set<String> selectedSkills = memberSkillDAO.getMemberSkillsByID(memberid);
		List<Skill> skills = skillDAO.getSkills();
		Set<Activity> selectedActivities = memberActivityDAO.getMemberActivityByID(memberid);
		List<Activity> activities = activityDAO.getActivities();
		
		request.setAttribute("member", member);
		request.setAttribute("skills", skills);
		request.setAttribute("selectedSkills", selectedSkills);
		request.setAttribute("selectedActivities", selectedActivities);
		request.setAttribute("activities", activities);
		

		RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/member/addMember.jsp");
		dispatcher.forward(request, response);

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
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/member/addMember.jsp");
	    
	    dispatcher.forward(request, response);
	}
	

	
	private void insertMember(HttpServletRequest request, HttpServletResponse response) 
	 {
		String firstName = request.getParameter("MemberFirstName");
		String lastName = request.getParameter("MemberLastName");
		int nationalID = Integer.parseInt( request.getParameter("MemberNationalID") );
		int phone = Integer.parseInt( request.getParameter("MemberPhone") );
		String email = request.getParameter("MemberEmail");
		String dobString = request.getParameter("MemberDOB");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate;
		try {
		    utilDate = format.parse(dobString);
		} catch (Exception e) {
		    throw new RuntimeException("Invalid date format: " + dobString, e);
		}
		java.sql.Date DOB = new java.sql.Date(utilDate.getTime());

		String address = request.getParameter("MemberAddress");
		

	    Part imagePart = null;
	    InputStream imageInputStream = null;
		try {
			imagePart = request.getPart("imageFile");
			imageInputStream = imagePart.getInputStream();
		} catch (IOException | ServletException e2) {
			e2.printStackTrace();
		}
		
		
		String[] selectedSkills = request.getParameterValues("skillsNames[]");
		try {
			if(imagePart == null || !(imagePart.getSize() > 0)) {
			    // Use a default image when no image is provided
			    imageInputStream = new FileInputStream("C:\\Users\\zeyad\\Desktop\\default.jpg");
				System.out.println("-----------------------------------------------------------");
			}
			System.out.println("+++++++++++++++++++++++++++++++");
			
			Integer memberID = memberDAO.addMember(firstName, lastName, nationalID, phone, email, DOB, address,imageInputStream);

			if (selectedSkills != null) {
			    for (String skillId : selectedSkills) {
			    	Integer skillID = Integer.parseInt(skillId);
			    	memberSkillDAO.addMemberSkill(memberID, skillID);
			    }
			}
			
			showData(request, response);
			
		}catch (SQLIntegrityConstraintViolationException e) {
		   try {
			    String errorMessage = "National ID or Email or Phone already existed !";
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

	
	private void updateMember(HttpServletRequest request, HttpServletResponse response)  {

		try {
			int memberID = Integer.parseInt( request.getParameter("memberid") );
			String firstName = request.getParameter("MemberFirstName");
			String lastName = request.getParameter("MemberLastName");
			int nationalID = Integer.parseInt( request.getParameter("MemberNationalID") );
			int phone = Integer.parseInt( request.getParameter("MemberPhone") );
			String email = request.getParameter("MemberEmail");
			String dobString = request.getParameter("MemberDOB");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date utilDate;
		    utilDate = format.parse(dobString);
			java.sql.Date DOB = new java.sql.Date(utilDate.getTime());
			String address = request.getParameter("MemberAddress");
			
		    Part imagePart = null;
		    InputStream imageInputStream = null;
			imagePart = request.getPart("imageFile");
			imageInputStream = imagePart.getInputStream();
			String[] selectedSkills = request.getParameterValues("skillsNames[]");
	
			String activityValue = request.getParameter("activityIdDropdown");
			String[] activityParts = activityValue.split("Option");
			String activityIdString = activityParts[0];
			Integer activityId=null;
			
			if(!activityIdString.isEmpty())
				activityId = Integer.parseInt(activityIdString);
			if(imagePart != null && imagePart.getSize() > 0)
				memberDAO.updateMemberWithImage(memberID,firstName, lastName, nationalID, phone, email, DOB, address, imageInputStream);
			else
				memberDAO.updateMemberWithoutImage(memberID,firstName, lastName, nationalID, phone, email, DOB, address);
	
			memberSkillDAO.deleteMemberSkills(memberID);
			if (selectedSkills != null) {
			    for (String skillId : selectedSkills) {
			    	Integer skillID = Integer.parseInt(skillId);
			    	memberSkillDAO.addMemberSkill(memberID, skillID);
			    }
			}
			if(activityId != null)
				memberActivityDAO.addMemberActivity(memberID, activityId);
			showData(request, response);
		}catch (SQLIntegrityConstraintViolationException e) {
		    try {
			    String errorMessage = "Activity name already existed !";
			    request.setAttribute("errorMessage", errorMessage);
		    	editForm(request, response);
			} catch (ServletException | IOException | SQLException e1) {
				e1.printStackTrace();
			}
		    
		} catch (SQLException e) {
	    	String errorMessage;
		    
		    try {
		    	if (e.getSQLState().equals("45000")) {
		    		errorMessage = e.getMessage();
	    		}else
	            	errorMessage = "An error occurred while performing the operation. Please try again later.";
			    request.setAttribute("errorMessage", errorMessage);
		    	editForm(request, response);
			} catch (ServletException | IOException | SQLException e1) {
				e1.printStackTrace();
			}
		}catch ( ServletException | IOException  e) {
			try {
	        	String errorMessage = "An error occurred while performing the operation. Please try again later.";
			    request.setAttribute("errorMessage", errorMessage);
				editForm(request, response);
			} catch (SQLException | ServletException | IOException e1) {
				e1.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deleteMemberActivity(HttpServletRequest request, HttpServletResponse response) {
		int activityid = Integer.parseInt( request.getParameter("activityid") );
		int memberid = Integer.parseInt( request.getParameter("memberID") );
		try {
			memberActivityDAO.deleteMemberActivity(activityid,memberid);
			showData(request, response);
		} catch (SQLException | ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteMember(HttpServletRequest request, HttpServletResponse response) {
		int memberid = Integer.parseInt( request.getParameter("memberid") );
		try {
			memberDAO.deleteMember(memberid);
			showData(request, response);
		} catch (SQLException | ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Member> members = null;
		List<Skill> skills;
		try {
			members = memberDAO.getMembers();
		    request.setAttribute("members", members); 
			skills = skillDAO.getSkills();
		    request.setAttribute("skills", skills); 
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/member/viewMembers.jsp");
	    
	    dispatcher.forward(request, response);
	}
}
