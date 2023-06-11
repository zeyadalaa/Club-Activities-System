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

public class SkillDAO {

    public List<Skill> getSkills() throws SQLException {
		List<Skill> skills = new ArrayList<>();
	
	    ConnectDB connection = new ConnectDB();
	    String STP= "CALL getSkills()";
	    Connection connection1 =connection.ConnectToDatabase();
	    CallableStatement statement = null;
	    
	    statement =  connection1.prepareCall(STP);
	    ResultSet resultSet = statement.executeQuery();
	
	    while (resultSet.next()) {
	        Integer skillId = resultSet.getInt("id");
	        String skillName= resultSet.getString("name");
	        Skill skill = new Skill(skillId,skillName);
	        
	        skills.add(skill);
	    }
	    statement.close();
	    connection1.close();
	    return skills;
    }
    
    public List<Skill> getSkillsByID(int skillID) throws SQLException {
		List<Skill> skills = new ArrayList<>();
	
	    ConnectDB connection = new ConnectDB();
	    String STP= "CALL getSkills(?)";
	    Connection connection1 =connection.ConnectToDatabase();
	    CallableStatement statement = null;
	    
	    statement =  connection1.prepareCall(STP);
        statement.setInt(1, skillID);
	    ResultSet resultSet = statement.executeQuery();
	
	    while (resultSet.next()) {
	        Integer skillId = resultSet.getInt("id");
	        String skillName= resultSet.getString("name");
	        Skill skill = new Skill(skillId,skillName);
	        
	        skills.add(skill);
	    }
	    statement.close();
	    connection1.close();
	    return skills;
    }
    
    public void deleteSkill(int skillID) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL deleteSkill(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setInt(1, skillID);
        statement.executeUpdate();
        statement.close();
        connection1.close();
    }
    
    public void addSkill(String name) throws SQLException {
        ConnectDB connection = new ConnectDB();
        String STP= "CALL addSkill(?)";
        Connection connection1 =connection.ConnectToDatabase();
        CallableStatement statement = null;
        
    	statement = connection1.prepareCall(STP);    
        statement.setString(1, name);
        statement.executeUpdate();
        
        statement.close();
        connection1.close();
    }
    
}
