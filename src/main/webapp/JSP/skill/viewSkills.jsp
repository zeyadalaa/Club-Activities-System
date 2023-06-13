

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 

<%@ page import="java.util.List" %>
<%@ page import="org.bibalex.Models.Skill" %>
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
	    <table>
	        <tr>
	            <th>Skill name</th>
	            <th>Actions</th>
	        </tr>
	        
      		<tr class="rows">
        		
	        		   <tr class="rows">
<%
		        		List<Skill> skills = (List<Skill>) request.getAttribute("skills");
	        		   if (skills != null)
						for(int i = 0 ;i<skills.size();i++){
	        			%>
	        		   <tr class="rows">
	       			   <td><%out.println(skills.get(i).getName()); %></td>

					   <td>
					   
		       			   <div class="action-buttons">
			       			   <form action="${pageContext.request.contextPath}/Skill" method="POST">
								  <input type="hidden" name="action" value="edit" >
								  <input type="hidden" name="skillid" value="<%=skills.get(i).getId() %>"> 
					                <button type="submit" class="updateButton">Update</button>
							   </form>
							   
			       			   <form action="${pageContext.request.contextPath}/Skill" method="POST">
								  <input type="hidden" name="action" value="delete">
									<input type="hidden" name="skillid" value="<%=skills.get(i).getId() %>">
				                	<button type="submit" class="deleteButton">Delete</button>
							   </form>
						   </div>
			            </td>
			            <%}%>
	        </tr>
		</table>
	    <form action="${pageContext.request.contextPath}/Skill" method="POST">
		  <input type="hidden" name="action" value="add">
		  <button type="submit" class="addButton">Add Skill</button>
		</form>
    </div>

</body>

</html>