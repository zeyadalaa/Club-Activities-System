package org.bibalex.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bibalex.Models.Activity;

public class MemberActivityDAO {
	
    public void addMemberActivity(Integer memberID, Integer activityID) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL addMemberActivity(?,?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, memberID);
        statement.setInt(2, activityID);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
    }
    
    public void deleteMemberActivity(Integer activityID, Integer memberid) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL deleteMemberActivity(?,?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, activityID);
        statement.setInt(2, memberid);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
    }
    
    public List<Activity> getMemberActivityByID(Integer memberID) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL getMemberActivitiesByID(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        List<Activity> list = new ArrayList<Activity>();
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, memberID);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next()) {
	        String activityName= resultSet.getString("name");
	        Integer activityID= Integer.parseInt(resultSet.getString("id"));
	        Activity activity = new Activity(activityID, activityName);
	        System.out.println(activityName);
	        list.add(activity);
	    }
        
        statement.close();
        connection1.close();
		return list;
    }
}
