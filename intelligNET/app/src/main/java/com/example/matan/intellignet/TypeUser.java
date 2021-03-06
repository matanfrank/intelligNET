package com.example.matan.intellignet;

import java.io.Serializable;

/**
 * Created by matan on 16/05/2016.
 */
public class TypeUser implements Serializable
{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthday;
    private String gender;
    private int CWP_finished;
    private String lastUseDate;
    private int helpForDay;

    //for signup login
    public TypeUser(String username, String password, String firstName, String lastName, String birthday, String gender) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.CWP_finished = 0;
        this.helpForDay = 5;
        this.lastUseDate = "";
    }

    //for login
    public TypeUser(String username, String password, String firstName, String lastName, String birthday, String gender, int CWP_finished, int helpForDay) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.CWP_finished = CWP_finished;
        this.helpForDay = helpForDay;
        this.lastUseDate = "";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public int getCWP_finished() {
        return CWP_finished;
    }

    public int getHelpForDay() {
        return helpForDay;
    }

    public String getLastUseDate()
    {
        return lastUseDate;
    }

    public void setLastUseDate(String lastUseDate)
    {
        this.lastUseDate = lastUseDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHelpForDay(int helpForDay) {
        this.helpForDay = helpForDay;
    }
}
