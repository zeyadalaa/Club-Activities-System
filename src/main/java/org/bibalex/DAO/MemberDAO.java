package org.bibalex.DAO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import org.bibalex.Models.Activity;
import org.bibalex.Models.Member;
import org.bibalex.Models.Skill;

public class MemberDAO {
	

    public List<Member> getMembers() throws SQLException {
		List<Member> member = new ArrayList<>();
		Map<Integer, Member> hashMap = new HashMap<Integer, Member>();
		Set<String> setSkill = new HashSet<String>();
		Set<String> setActivity = new HashSet<String>();
	
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
	        Blob  imageBlob = resultSet.getBlob("image");
	        byte[] imageData = null;
            
	        if(hashMap.containsKey(memberId)) {
	        	Member newMember = hashMap.get(memberId);
		        Skill skill = new Skill(skillId,skillName);
		        Activity activity = new Activity(activityId, activityName,activityDescription,activityMinAge,activityMaxAge);
		        // Check if the skill is not already added
		        if (skill.getName()!= null && !setSkill.contains(skillName)) {
		        	setSkill.add(skillName);
		        	newMember.getSkills().add(skill);
		        }
		        // Check if the activity is not already added
		        if (activity.getName()!= null && !setActivity.contains(activityName)) {
		        	setActivity.add(activityName);
		        	newMember.getActivities().add(activity);
		        }
	            hashMap.remove(memberId); 
	            hashMap.put(memberId, newMember);
	        }
	        else {
	        	if(imageBlob != null)
	        		imageData = imageBlob.getBytes(1,(int) imageBlob.length());

		        Skill skill = new Skill(skillId,skillName);
		        Activity activity = new Activity(activityId, activityName,activityDescription,activityMinAge,activityMaxAge);
		        Member newMember = new Member(memberId, memberNationalId,memberPhone, memberFirst_Name,
		        		memberLast_name, imageData, memberAddress, memberDOB, memberEmail);
		        if(skillId != null) {
		        	setSkill.add(skillName);
		        	newMember.addSkill(skill);
		        }
		        if(activityId != null) {		        	
		        	setActivity.add(activityName);
		        	newMember.addActivity(activity);
		        }
	        	hashMap.put(memberId, newMember);
		        member.add(newMember);
	        }
	    }
	    
	    statement.close();
	    connection1.close();
	    return member;
    }

    public Integer addMember(String firstName,String lastName,Integer nationalID, Integer phone, String email, 
    		Date date,String address, InputStream imageInputStream) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL addMember(?,?,?,?,?,?,?,?,?)";
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
        statement.setBinaryStream(8, imageInputStream);

        statement.registerOutParameter(9, Types.INTEGER);
        statement.executeUpdate();
        
        Integer memberID = statement.getInt(9); 
        
        statement.close();
        connection1.close();
        return memberID;
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

	public Member getMemberByID(Integer memberid) throws SQLException {

		Member member = null;
		Map<Integer, Member> hashMap = new HashMap<Integer, Member>();
	
	    ConnectDB connection = new ConnectDB();
	    String STP= "CALL getMemberByID(?)";
	    Connection connection1 =connection.ConnectToDatabase();
	    CallableStatement statement = null;
	    
	    statement =  connection1.prepareCall(STP);
        statement.setInt(1, memberid);
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
	        Blob  imageBlob = resultSet.getBlob("image");
	        byte[] imageData = null;
            
	        if(hashMap.containsKey(memberId)) {
	        	Member newMember = hashMap.get(memberId);
		        Skill skill = new Skill(skillId,skillName);
		        Activity activity = new Activity(activityId, activityName,activityDescription,activityMinAge,activityMaxAge);
	        	newMember.getSkills().add(skill);
	        	newMember.getActivities().add(activity);
	        }
	        else {
	        	if(imageBlob != null)
	        		imageData = imageBlob.getBytes(1,(int) imageBlob.length());

		        Skill skill = new Skill(skillId,skillName);
		        Activity activity = new Activity(activityId, activityName,activityDescription,activityMinAge,activityMaxAge);
		        member = new Member(memberId, memberNationalId,memberPhone, memberFirst_Name,
		        		memberLast_name, imageData, memberAddress, memberDOB, memberEmail);
		        if(skillId != null)
		        	member.addSkill(skill);
		        if(activityId != null)
		        	member.addActivity(activity);
	        	hashMap.put(memberId, member);
	        }
	    }
	    statement.close();
	    connection1.close();
	    return member;
	}
}
