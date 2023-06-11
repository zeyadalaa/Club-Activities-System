package org.bibalex.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class MemberSkillDAO {
	
    public void addMemberSkill(Integer memberID, Integer skill_id) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL addMemberSkill(?,?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, memberID);
        statement.setInt(2, skill_id);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
    }
    
}
