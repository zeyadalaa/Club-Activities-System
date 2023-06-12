package org.bibalex.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bibalex.Models.Activity;
import org.bibalex.Models.Member;
import org.bibalex.Models.Skill;

public class MemberDAO {
	

    public List<Member> getMembers() throws SQLException {
		List<Member> member = new ArrayList<>();
		Map<Integer, Member> hashMap = new HashMap<Integer, Member>();
	
	    ConnectDB connection = new ConnectDB();
	    String STP= "CALL getMembers()";
	    Connection connection1 =connection.ConnectToDatabase();
	    CallableStatement statement = null;
	    
	    statement =  connection1.prepareCall(STP);
	    ResultSet resultSet = statement.executeQuery();
	
	    while (resultSet.next()) {
	        int memberId = resultSet.getInt("id");
	        String memberFirst_Name = resultSet.getString("first_name");
	        String memberLast_name = resultSet.getString("last_name");
	        int memberNationalId = resultSet.getInt("national_id");
	        int memberPhone = resultSet.getInt("phone");
	        String memberEmail = resultSet.getString("email");
	        Date memberDOB = resultSet.getDate("DOB");
	        String memberAddress = resultSet.getString("address");
	        Integer  skillId = resultSet.getInt("skill_id");
	        String skillName= resultSet.getString("skill_name");
	        Integer  activityId = resultSet.getInt("activity_id");
	        String activityName= resultSet.getString("activity_name");
	        String activityDescription= resultSet.getString("description");
	        Integer  activityMinAge = resultSet.getInt("min_age");
	        Integer  activityMaxAge = resultSet.getInt("max_age");
	        if(hashMap.containsKey(memberId)) {
	        	Member newMember = hashMap.get(memberId);
		        Skill skill = new Skill(skillId,skillName);
		        Activity activity = new Activity(activityId, activityName,activityDescription,activityMinAge,activityMaxAge);
	        	newMember.getSkills().add(skill);
	        	newMember.getActivities().add(activity);
	        }
	        else {

		        Skill skill = new Skill(skillId,skillName);
		        Activity activity = new Activity(activityId, activityName,activityDescription,activityMinAge,activityMaxAge);
		        Member newMember = new Member(memberId, memberNationalId,memberPhone, memberFirst_Name,
		        		memberLast_name, "", memberAddress, memberDOB, memberEmail);
		        if(skillId != null)
		        	newMember.addSkill(skill);
		        if(activityId != null)
		        	newMember.addActivity(activity);
	        	hashMap.put(memberId, newMember);
		        member.add(newMember);
	        }
	    }
	    statement.close();
	    connection1.close();
	    return member;
    }
    

    public void addMember(String firstName,String lastName,Integer nationalID, Integer phone, String email, 
    		Date date,String address) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL addMember(?,?,?,?,?,?,?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setInt(3, nationalID);
        statement.setInt(4, phone);
        statement.setString(5, email);
        statement.setDate(6, date);
        statement.setString(7, address);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
    }


    public void updateMember(String firstName,String lastName,Integer nationalID, Integer phone, String email, 
    		Date date,String address) throws SQLException {
                    

        ConnectDB connection = new ConnectDB();
        String STP= "CALL updateEmployee(?,?,?,?,?,?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;

    	statement = connection1.prepareCall(STP);    
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setInt(3, nationalID);
        statement.setInt(4, phone);
        statement.setString(5, email);
        statement.setDate(6, date);
        statement.setString(7, address);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
    }


    public void deleteMember(int memberId) throws SQLException {

        ConnectDB connection = new ConnectDB();
        String STP= "CALL deleteMember(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, memberId);
        statement.executeUpdate();
        statement.close();
        connection1.close();
    }
}
