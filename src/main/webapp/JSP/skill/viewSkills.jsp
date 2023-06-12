

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<%@ page import="java.util.List" %>
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
                <li><a href="${pageContext.request.contextPath}/JSP/member/viewMembers.jsp">Members</a></li>
                <li><a href="${pageContext.request.contextPath}/JSP/activity/viewActivites.jsp">Activities</a></li>
                <li><a href="${pageContext.request.contextPath}/JSP/skill/viewSkills.jsp">Skills</a></li>
            </ul>
        </nav>
    </div>
	<div class="container">
	    <table>
	        <tr>
	            <th>Skill name</th>
	            <th>Actions</th>
	        </tr>
	        
      		<tr class="rows">
        		
	        		   <tr class="rows">
	       			   <td>zeyad</td>
	       			   <td>
		       			   <div class="action-buttons">
			       			   <form action="${pageContext.request.contextPath}/Employee" method="POST">
								  <input type="hidden" name="action" value="edit" >
								  <%-- <input type="hidden" name="employeeid" value="<%=employees.get(i).getId() %>"> --%>
					                <button type="submit" class="updateButton">Update</button>
							   </form>
							   
			       			   <form action="${pageContext.request.contextPath}/Employee" method="POST">
								  <input type="hidden" name="action" value="delete">
								  <%-- <input type="hidden" name="employeeid" value="<%=employees.get(i).getId() %>"> --%>
				                	<button type="submit" class="deleteButton">Delete</button>
							   </form>
						   </div>
			            </td>
	        </tr>
		</table>
		
		<%-- <td><a href="edit?id=<%= employees.get(i).getId() %>">Edit</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="delete?id=<%= employees.get(i).getId() %>">Delete</a></td>
    		 --%>		
	        			<!-- <tr class="rows"> -->
	            <%-- <td><%= employee.getId() %></td>
	            <td><%= employee.getFirst_name() %></td>
	            <td><%= employee.getLast_name() %></td>
	            <td><%= employee.getDOB() %></td>
	            <td><%= employee.getEmail() %></td>
	            <td><%= employee.getDepartment_id() %></td> --%><!-- 
	            <td>
	                <button>Delete</button>
	                <button>Update</button>
	            </td> -->
	    <form action="${pageContext.request.contextPath}/Skill" method="POST">
		  <input type="hidden" name="action" value="add">
		  <button type="submit" class="addButton">Add Skill</button>
		</form>
	    <!-- <button  class="addButton">Add</button> -->
    </div>

</body>

</html>