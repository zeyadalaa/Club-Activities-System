

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<%@ page import="java.util.List" %>
<%@ page import="org.bibalex.Models.Skill" %>
<%@ page import="org.bibalex.Models.Activity" %>
<!-- <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="jquery-3.6.0.min.js"></script> -->


<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/view.css">
<meta charset="ISO-8859-1">    
<title>Master Detail</title>
</head>
<body>
    <div class="container">
        <nav>
            <ul>
            	<li><a href="${pageContext.request.contextPath}/Member">Members</a></li>
                <li><a href="${pageContext.request.contextPath}/Activity">Activities</a></li>
                <li><a href="${pageContext.request.contextPath}/Skill">Skills</a></li>
            </ul>
        </nav>
    </div>
	<div class="container">
		<% if (request.getAttribute("errorMessage") != null) { %>
        <script>
            showError("<%= request.getAttribute("errorMessage") %>");
        </script>
    	<% } %>
	    
	    <table>
	        <tr>
	            <th>Activity name</th>
	            <th>Description</th>
	            <th>Minimum Age</th>
	            <th>Maximum Age</th>
	            <th>Skills Needed</th>
	            <th>Actions</th>
	        </tr>
	        
      		<tr class="rows">
        		
	        		   <tr class="rows">
	       			   <%
		        		List<Activity> activities = (List<Activity>) request.getAttribute("activities");
	        		   if (activities != null)
						for(int i = 0 ;i<activities.size();i++){
	        			%>
	        		   <tr class="rows">
	       			   <td><%out.println(activities.get(i).getName()); %></td>
	       			   <td><%out.println(activities.get(i).getDescription()); %></td>
	       			   <td><%out.println(activities.get(i).getMinAge()); %></td>
	       			   <td><%out.println(activities.get(i).getMaxAge()); %></td>
					   <td>
						<%
							for ( Skill  var : activities.get(i).getSkills())
								out.println(var.getName() != null ? var.getName() + "<br>" : "" );
						
						%>
					   </td> 
					   <td>
					   
		       			   <div class="action-buttons">
			       			   <form action="${pageContext.request.contextPath}/Activity" method="POST">
								  <input type="hidden" name="action" value="edit" >
								  <input type="hidden" name="activityid" value="<%=activities.get(i).getId() %>">
					                <button type="submit" class="updateButton">Update</button>
							   </form>
							   
			       			   <form action="${pageContext.request.contextPath}/Activity" method="POST">
								  <input type="hidden" name="action" value="delete">
								 	<input type="hidden" name="activityid" value="<%=activities.get(i).getId() %>">
				                	<button type="submit" class="deleteButton">Delete</button>
							   </form>
						   </div>
			            </td>
			            <%}%>
	        </tr>
		</table>
		
	    <form action="${pageContext.request.contextPath}/Activity" method="POST">
		  <input type="hidden" name="action" value="add">
		  <button type="submit" class="addButton">Add Activity</button>
		</form>
    </div>

</body>

</html>