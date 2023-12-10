package com.lbcoding.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userprofile")
public class UserProfile {
    @Id
    @GeneratedValue
    long userprofile_id;

    long user_id;
    String first_name;
    String last_name;
    String gender;

    public UserProfile() {
    }

    public UserProfile(long userprofile_id, long user_id, String first_name, String last_name, String gender) {
        this.userprofile_id = userprofile_id;
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    public long getUserprofile_id() {
        return userprofile_id;
    }

    public void setUserprofile_id(long userprofile_id) {
        this.userprofile_id = userprofile_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
