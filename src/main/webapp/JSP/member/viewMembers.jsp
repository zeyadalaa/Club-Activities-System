
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="org.bibalex.Models.Member" %>
<%@ page import="org.bibalex.Models.Skill" %>
<%@ page import="org.bibalex.Models.Activity" %>
<%@ page import="java.util.Base64" %>

<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/view.css">
    <meta charset="UTF-8">
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
        <th></th>
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

      <% 
      List<Member> members = (List<Member>) request.getAttribute("members");
      if (members != null) {
        for (int i = 0; i < members.size(); i++) {
          Member member = members.get(i);
          %>
          <tr class="rows">
            <td>
              <%-- Encode the byte array to Base64 --%>
              <% if (member.getImage() != null) {
                String encodedImage = Base64.getEncoder().encodeToString(member.getImage());
                %>
                <img src="data:image/jpeg;base64, <%= encodedImage %>" alt="User Image" />
              <% } %>
            </td>
            <td><%= member.getFirstName() %></td>
            <td><%= member.getLastName() %></td>
            <td><%= member.getNationalID() %></td>
            <td><%= member.getPhone() %></td>
            <td><%= member.getEmail() %></td>
            <td><%= member.getDOB() %></td>
            <td><%= member.getAddress() %></td>
            <td>
              <% for (Skill skill : member.getSkills()) { %>
                <%= skill.getName() != null ? skill.getName() + "<br>" : "" %>
              <% } %>
            </td>
            <td>
              <% for (Activity activity : member.getActivities()) { %>
                <%= activity.getName() != null ? activity.getName() + "<br>" : "" %>
              <% } %>
            </td>
            <td>
              <div class="action-buttons">
                <form action="${pageContext.request.contextPath}/Member" method="POST">
                  <input type="hidden" name="action" value="edit">
                  <input type="hidden" name="memberid" value="<%= member.getId() %>">
                  <button type="submit" class="updateButton">Update</button>
                </form>
                <form action="${pageContext.request.contextPath}/Member" method="POST">
                  <input type="hidden" name="action" value="delete">
                  <input type="hidden" name="memberid" value="<%= member.getId() %>">
                  <button type="submit" class="deleteButton">Delete</button>
                </form>
              </div>
            </td>
          </tr>
        <% } %>
      <% } %>
    </table>
    <form action="${pageContext.request.contextPath}/Member" method="POST">
      <input type="hidden" name="action" value="add">
      <button type="submit" class="addButton">Add Member</button>
    </form>
  </div>
</body>
</html>
