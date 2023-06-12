

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<%@ page import="java.util.List" %>
<%@ page import="org.bibalex.Models.Member" %>
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
                <li><a href="${pageContext.request.contextPath}/JSP/member/viewMembers.jsp">Members</a></li>
                <li><a href="${pageContext.request.contextPath}/JSP/activity/viewActivites.jsp">Activities</a></li>
                <li><a href="${pageContext.request.contextPath}/JSP/skill/viewSkills.jsp">Skills</a></li>
            </ul>
        </nav>
    </div>
	<div class="container">
	    <table>
	        <tr>
	        	<th>First Name</th>
	            <th>Last Name</th>
	            <th>National ID</th>
	            <th>Phone</th>
	            <th>Email</th>
	            <th>DOB</th>
	            <th>Address</th>
	            <th>Skills</th>
	            <th>Activities</th>
	            <th>Actions</th>
	        </tr>
	        
      		<tr class="rows">
        		
	        		   <tr class="rows">
	        		   
	        		   <%
		        		List<Member> members = (List<Member>) request.getAttribute("members");
	        		   if (members != null)
						for(int i = 0 ;i<members.size();i++){
	        			%>
	        		   <tr class="rows">
	       			   <td><%out.println(members.get(i).getFirstName()); %></td>
	       			   <td><%out.println(members.get(i).getLastName()); %></td>
	       			   <td><%out.println(members.get(i).getNationalID()); %></td>
	       			   <td><%out.println(members.get(i).getPhone()); %></td>
	       			   <td><%out.println(members.get(i).getEmail()); %></td>
	       			   <td><%out.println(members.get(i).getDOB()); %></td>
	       			   <td><%out.println(members.get(i).getAddress()); %></td>
					   <td>
						<%
							for ( Skill  var : members.get(i).getSkills())
								out.println(var.getName() != null ? var.getName() + "<br>" : "" );
						
						%>
					   </td> 
					   <td >
						<%
							for ( Activity  var : members.get(i).getActivities())
								out.println(var.getName()+"<br>");
						%>
					   </td> 
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
			            <%}%>
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
	    <form action="${pageContext.request.contextPath}/Member" method="POST">
		  <input type="hidden" name="action" value="add">
		  <button type="submit" class="addButton">Add Member</button>
		</form>
	    <!-- <button  class="addButton">Add</button> -->
    </div>

</body>

</html>