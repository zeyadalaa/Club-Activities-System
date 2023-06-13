package org.bibalex.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.bibalex.Models.Activity;
import org.bibalex.Models.Skill;

public class ActivitySkillDAO {

	
    public void addActivitySkill(Integer activityID, Integer skillID) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL addActivitySkill(?,?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, activityID);
        statement.setInt(2, skillID);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
    }
    

    public void deleteActivitySkill(Integer activityID) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL deleteActivitySkill(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, activityID);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
    }

    public Set<String> getActivitySkillsByID(Integer activityID) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL getActivitySkillsByID(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        Set<String> Set = new TreeSet<String>();
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, activityID);
	    ResultSet resultSet = statement.executeQuery();
        

	    while (resultSet.next()) {
	        String skillName= resultSet.getString("name");
	        Set.add(skillName);
	    }
        
        statement.close();
        connection1.close();
		return Set;
    }
}
