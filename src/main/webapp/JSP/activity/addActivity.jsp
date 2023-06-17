
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="org.bibalex.Models.Skill" %>
<%@ page import="org.bibalex.Models.Activity" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/addPages.css">
    <meta charset="UTF-8">
    <title>Add Activity</title>
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
	    
	    <%Activity activity = (Activity) request.getAttribute("activity"); %>
	        <%
					if(activity != null) {
					%>
	        			<h1>Edit Activity</h1>
		          	<%}else{ %>
	        			<h1>Add Activity</h1>
		          <%} %>
	        <form action="Activity" method="POST" class="add-employee-form">
	
	            <label for="Activity ">Activity Name:</label>
				<input type="hidden" name="activityid" value="${activity.id}">
	            <input type="text" name="ActivityName" id="ActivityName"  value="${activity.name}" required><br>
	
				<label for="Activity ">Description:</label>
	            <input type="text" name="ActivityDescription" id="ActivityDescription"  value="${activity.description}" required><br>
				
				<label for="Activity ">Minimum Age:</label>
	            <input type="number" name="ActivityMinimumAge" id="ActivityMinimumAge"  value="${activity.minAge}" required min="1" 
	            onchange="document.getElementById('ActivityMaximumAge').min=parseInt(this.value);"
	            ><br>
				
				<label for="Activity ">Maximum Age:</label>
	            <input type="number" name="ActivityMaximumAge" id="ActivityMaximumAge" value="${activity.maxAge}" required ><br>
				
				<label>Select skills needed:</label>
				    <% List<Skill> skills = (List<Skill>) request.getAttribute("skills");
				    	Set<String> selectedSkills = (Set<String>) request.getAttribute("selectedSkills");

				       for (int i = 0; i < skills.size(); i++) { %>
					       <div class= " check-box">
					       <%
					        if(selectedSkills!=null && selectedSkills.contains(skills.get(i).getName())){
					       %>
					        	<input type="checkbox" name="skillsNames[]" id="skill<%=skills.get(i).getId()%>" value="<%=skills.get(i).getId()%>" checked>
			        		<%}else{ %>
					        	<input type="checkbox" name="skillsNames[]" id="skill<%=skills.get(i).getId()%>" value="<%=skills.get(i).getId()%>">
				        	<%} %>
					        <label for="skill<%=skills.get(i).getId()%>"><%=skills.get(i).getName()%></label>
					       </div>
				    <% } %>
				<%
					if(activity != null) {
				%>
				    <input type="hidden" name="action" value="update">
		            <input class="add" type="submit" value="Update Activity">
				<%}else{ %>
				    <input type="hidden" name="action" value="insert">
		            <input class="add" type="submit" value="Add Activity">
	            <%} %>
				
	        </form>
	    </div>
    </div>
</body>
</html>
