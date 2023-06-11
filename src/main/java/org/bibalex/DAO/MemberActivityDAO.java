package org.bibalex.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

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
    
}
