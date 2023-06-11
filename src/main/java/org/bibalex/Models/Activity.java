package org.bibalex.Models;

import java.util.HashSet;
import java.util.Set;

public class Activity {
    private int id;
    private String name;
    private String description;
    private int minAge;
    private int maxAge;
    private Set<Member> members;
    private Set<Skill> skills;


    public Activity(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Activity(int id, String name,String description, int minAge, int maxAge) {
        this.id = id;
        this.name = name;
        this.description = description;
		this.minAge = minAge;
		this.maxAge = maxAge;
        this.members = new HashSet<>();
        this.skills = new HashSet<>();
    }
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public Set<Member> getMembers() {
        return members;
    }

	
    public void addMember(Member member) {
        members.add(member);
        member.getActivities().add(this);
    }

    public void removeMember(Member member) {
        members.remove(member);
        member.getActivities().remove(this);
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
        skill.getActivities().add(this);
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
        skill.getActivities().remove(this);
    }
    
}
