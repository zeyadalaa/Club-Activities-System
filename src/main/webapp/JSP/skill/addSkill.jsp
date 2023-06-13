
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="org.bibalex.Models.Skill" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/addPages.css">
    <meta charset="UTF-8">
    <title>Add Section</title>
    <script>
        function showError(message) {
            alert(message); // Replace with your preferred pop-up mechanism
        }
    </script>
</head>
<body>
    <div class="container-nav">
        <nav>
            <ul>
            	<li><a href="${pageContext.request.contextPath}/Member">Members</a></li>
                <li><a href="${pageContext.request.contextPath}/Activity">Activities</a></li>
                <li><a href="${pageContext.request.contextPath}/Skill">Skills</a></li>
            </ul>
        </nav>
    </div>
    <div class="AddSkill">
	    <div class="container">
	    <% if (request.getAttribute("errorMessage") != null) { %>
        <script>
            showError("<%= request.getAttribute("errorMessage") %>");
        </script>
    	<% } %>
	    
	    <%Skill skills = (Skill) request.getAttribute("skill"); %>
	        <%
					if(skills != null) {
					%>
	        			<h1>Edit Skill</h1>
		          	<%}else{ %>
	        			<h1>Add Skill</h1>
		          <%} %>
	        <form action="Skill" method="POST" class="add-employee-form">
	
	            <label for="SkillName">Skill Name:</label>
				<input type="hidden" name="skillid" value="${skill.id}">
	            <input type="text" name="SkillName" id="SkillName"  value="${skill.name}" required><br>
	
	
				<%
					if(skills != null) {
				%>
				    <input type="hidden" name="action" value="update">
		            <input class="add" type="submit" value="Update skill">
				<%}else{ %>
				    <input type="hidden" name="action" value="insert">
		            <input class="add" type="submit" value="Add skill">
	            <%} %>
				
	        </form>
	    </div>
    </div>
</body>
</html>
