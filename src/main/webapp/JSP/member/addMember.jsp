
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="org.bibalex.Models.Skill" %>
<%@ page import="org.bibalex.Models.Activity" %>
<%@ page import="org.bibalex.Models.Member" %>
<%@ page import="java.util.Base64" %>
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
	    
	    <%Member member = (Member) request.getAttribute("member"); 

	    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date currentDate = new java.util.Date();
	    String formattedDate = dateFormat.format(currentDate);
	    %>
	        <%
					if(member != null) {
					%>
	        			<h1>Edit Member</h1>
		          	<%}else{ %>
	        			<h1>Add Member</h1>
		          <%} %>
	        <form action="Member" method="POST" class="add-employee-form" enctype="multipart/form-data">
	
	            <label for="MemberFirstName">First name:</label>
				<input type="hidden" name="memberid" value="${member.id}">
	            <input type="text" name="MemberFirstName" id="MemberFirstName"  value="${member.firstName}" required><br>
	
				<label for="MemberLastName ">Last name:</label>
	            <input type="text" name="MemberLastName" id="MemberLastName"  value="${member.lastName}" required><br>
	            
				<label for="MemberNationalID ">National ID:</label>
	            <input type="number" name="MemberNationalID" id="MemberNationalID"  value="${member.nationalID}" required><br>
	            
				<label for="MemberPhone ">Phone Number:</label>
	            <input type="number" name="MemberPhone" id="MemberPhone"  value="${member.phone}" required><br>
	            
				<label for="MemberEmail ">Email:</label>
	            <input type="email" name="MemberEmail" id="MemberEmail"  value="${member.email}" required min="1"><br>
				
	            <label for="MemberDOB">Date of Birth:</label>
				<input type="date" name="MemberDOB" id="MemberDOB" value="<%= formattedDate %>" max="<%= formattedDate %>" required><br>
				
				<label for="MemberAddress ">Address:</label>
	            <input type="text" name="MemberAddress" id="MemberAddress"  value="${member.address}" required><br><br>
				
				<%
				 Set<Activity>selectedActivities = ( Set<Activity>) request.getAttribute("selectedActivities");
				 List<Activity>activities = ( List<Activity>) request.getAttribute("activities");
				 
				 if(activities != null){%>
				<label for="activityIdDropdown ">Activities :</label>
				 <select class="dropdown" name="activityIdDropdown" id="activityIdDropdown" >
						        <option></option>
				<%

					for(int i = 0 ;i<activities.size();i++){
						if(selectedActivities != null && activities.get(i).getName() != null && !(selectedActivities.contains(activities.get(i).getName())) ){
	        		%>
						        <option value="<%=activities.get(i).getId()%>"><%=activities.get(i).getName()%></option>
			        <%}} %>
						
				</select>
				 <%}%>
					
					
	            
	            <%
        		   if(member != null && member.getImage() != null){
       		   			String encodedImage = Base64.getEncoder().encodeToString(member.getImage());
				%>
				<br>
				<label for="imageFile ">Upload Image:</label>
				
	            	<img src="data:image/jpeg;base64, <%= encodedImage %>" alt="User Image" />
			    <%}else %>
			    	<input type="file" name="imageFile" accept="image/*">
				<label>Select skills needed:</label>
				    <% List<Skill> skills = (List<Skill>) request.getAttribute("skills");
				    	Set<String> selectedSkills = (Set<String>) request.getAttribute("selectedSkills");
						if(skills != null)
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
					if(member != null) {
				%>
				    <input type="hidden" name="action" value="update">
		            <input class="add" type="submit" value="Update Member">
				<%}else{ %>
				    <input type="hidden" name="action" value="insert">
		            <input class="add" type="submit" value="Add Member">
	            <%} %>
				
	        </form>
	        <%
	        if(member != null && selectedActivities != null){
				%>
						<label for="MemberActivity "> Activities enrolled in: </label>
				<%
						for (Activity activity : selectedActivities) {
				%>
						<div class = "activities">
							<form action="${pageContext.request.contextPath}/Member" method="POST">
				                <label id="activityLabel" ><%= activity.getName() %></label>
								  <input type="hidden" name="action" value="deleteMemberActivity">
								  <input type="hidden" name="activityid" value="<%= activity.getId() %>">
								  <input type="hidden" name="memberID" value="<%= member.getId() %>">
			                		<button type="submit" class="deleteButton">Delete</button>
	                		</form>
						</div>
						<%}
				} %>
	    </div>
    </div>
</body>
</html>
