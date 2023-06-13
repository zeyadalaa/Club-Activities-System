package org.bibalex.DAO;

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

import org.bibalex.Models.Activity;
import org.bibalex.Models.Member;
import org.bibalex.Models.Skill;

public class ActivityDAO {
	
    public Integer addActivity(String name ,String description ,Integer min_age, Integer max_age) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL addActivity(?,?,?,?,?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setString(1, name);
        statement.setString(2, description);
        statement.setInt(3, min_age);
        statement.setInt(4, max_age);
        statement.registerOutParameter(5, Types.INTEGER);
        statement.executeUpdate();
        
        Integer memberID = statement.getInt(5); 
        
        statement.close();
        connection1.close();
        return memberID;
    }
    

    public void deleteActivity(int activityID) throws SQLException {

        ConnectDB connection = new ConnectDB();
        String STP= "CALL deleteActivity(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, activityID);
        statement.executeUpdate();
        statement.close();
        connection1.close();
    }
    
    

    public List<Activity> getActivities() throws SQLException {
		List<Activity> activities = new ArrayList<>();
		
		//hashmap to check if this activity already existed but with different skills required
		Map<Integer, Activity> hashMap = new HashMap<Integer, Activity>();
	
	    ConnectDB connection = new ConnectDB();
	    String STP= "CALL getActivities()";
	    Connection connection1 =connection.ConnectToDatabase();
	    CallableStatement statement = null;
	    
	    statement =  connection1.prepareCall(STP);
	    ResultSet resultSet = statement.executeQuery();
	
	    while (resultSet.next()) {
	        int activityId = resultSet.getInt("id");
	        String activityName = resultSet.getString("name");
	        String activityDescription= resultSet.getString("description");
	        int min_age = resultSet.getInt("min_age");
	        int max_age = resultSet.getInt("max_age");
	        Integer skillId = resultSet.getInt("skill_id");
	        String skillName= resultSet.getString("skill_name");
	        Skill skill = new Skill(skillId,skillName);
	        if(hashMap.containsKey(activityId)) {
	        	Activity newActivity = hashMap.get(activityId);
		        newActivity.getSkills().add(skill);
	        }
	        else {
		        Activity activity = new Activity(activityId, activityName, activityDescription, min_age, max_age);
		        if(skillId != null)
		        	activity.addSkill(skill);
	        	hashMap.put(activityId, activity);
	        	activities.add(activity);
	        }
	    }
	    statement.close();
	    connection1.close();
	    return activities;
    }
    
    public Activity getActivityByID(int activityID) throws SQLException {
    	Activity activity = null;
    	Skill skill = null;
        ConnectDB connection = new ConnectDB();
        String STP= "CALL getActivityByID(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;

        statement =  connection1.prepareCall(STP);
        statement.setInt(1, activityID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
	        int activityId = resultSet.getInt("id");
	        String activityName = resultSet.getString("name");
	        String activityDescription= resultSet.getString("description");
	        int min_age = resultSet.getInt("min_age");
	        int max_age = resultSet.getInt("max_age");
	        Integer skillId = resultSet.getInt("skill_id");
	        String skillName= resultSet.getString("skill_name");
	        skill = new Skill(skillId,skillName);
	        if (activity == null) 
		        activity = new Activity(activityId, activityName, activityDescription, min_age, max_age);
        	
	        if(skillId != null)
        		activity.addSkill(skill);
	        
        }

        statement.close();
        connection1.close();
        return activity;
    }
    
    public void updateActivity(int activityID, String name ,String description ,Integer min_age, Integer max_age) throws SQLException {
                    

        ConnectDB connection = new ConnectDB();
        String STP= "CALL updateActivity(?,?,?,?,?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;

    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, activityID);
        statement.setString(2, name);
        statement.setString(3, description);
        statement.setInt(4, min_age);
        statement.setInt(5, max_age);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
    }

}
