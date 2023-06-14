package org.bibalex.Models;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Member {

    private int id;
    private int phone;
    private int nationalID;
    private String firstName;
    private String lastName;

	private byte[] image;
    private String address;
    private Date DOB;
	private String email;
    private Set<Activity> activities;
    private Set<Skill> skills;
    
    public Member(int id, int nationalID,int phone, String firstName, String lastName, byte[] image, String address, Date dOB, String email) {
		this.id = id;
		this.nationalID = nationalID;
		this.phone = phone;
		this.firstName = firstName;
		this.lastName = lastName;
		this.image = image;
		this.address = address;
		DOB = dOB;
		this.email = email;
        this.activities = new HashSet<>();
        this.skills = new HashSet<>();
	}
    
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getNationalID() {
		return nationalID;
	}
	
	public void setNationalID(int nationalID) {
		this.nationalID = nationalID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    public int getPhone() {
		return phone;
	}


	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Date getDOB() {
		return DOB;
	}
	
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<Activity> getActivities() {
		return activities;
	}
	
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

    public void addActivity(Activity activity) {
        activities.add(activity);
        activity.getMembers().add(this);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
        activity.getMembers().remove(this);
    }

	public Set<Skill> getSkills() {
		return skills;
	}
	
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
    
    public void addSkill(Skill skill) {
        skills.add(skill);
        skill.getMembers().add(this);
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
        skill.getMembers().remove(this);
    }

	
}
