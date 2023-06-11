package org.bibalex.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

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
    
    
}