package org.bibalex.Models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Skill {

    private int id;
    private String name;
    private Set<Member> members;
    private Set<Activity> activities;

    public Skill(int id, String name) {
        this.id = id;
        this.name = name;
        this.members = new HashSet<>();
        this.activities = new HashSet<>();
    }
    
    public Set<Member> getMembers() {
        return members;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void addMember(Member member) {
        members.add(member);
        member.getSkills().add(this);
    }

    public void removeMember(Member member) {
        members.remove(member);
        member.getSkills().remove(this);
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
        activity.getSkills().add(this);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
        activity.getSkills().remove(this);
    }
    
}
