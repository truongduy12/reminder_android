package com.example.reminder.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String email;
    private String name;
    private long dob;
    private Gender gender;
    private String avtUrl;
    private UserRole role;
    private boolean promoEnable;
    private List<Reminder> reminders;

    public User() {}

    public User(String email, String name, long dob, Gender gender, boolean promoEnable) {
        this.email = email;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.promoEnable = promoEnable;
        this.role = UserRole.User;
        this.reminders = new ArrayList<Reminder>();
    }

    public enum Gender {
        Male, Female
    }

    private enum UserRole {
        Admin, User
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isPromoEnable() {
        return promoEnable;
    }

    public void setPromoEnable(boolean promoEnable) {
        this.promoEnable = promoEnable;
    }

    public String getAvtUrl() {
        return avtUrl;
    }

    public void setAvtUrl(String avtUrl) {
        this.avtUrl = avtUrl;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
