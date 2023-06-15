package org.bibalex.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

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
    
    public Set<String> getMemberSkillsByID(Integer memberID) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL getMemberSkillsByID(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        Set<String> Set = new TreeSet<String>();
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, memberID);
	    ResultSet resultSet = statement.executeQuery();
        

	    while (resultSet.next()) {
	        String skillName= resultSet.getString("name");
	        Set.add(skillName);
	    }
        
        statement.close();
        connection1.close();
		return Set;
    }

	public void deleteMemberSkills(int memberID) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL deleteMemberSkills(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, memberID);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
	}
    
}
