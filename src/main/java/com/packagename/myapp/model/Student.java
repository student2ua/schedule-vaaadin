package com.packagename.myapp.model;

public class Student {
    private Integer id;
    private String lastName,firstName,midlName;

    public Student(Integer id, String lastName, String firstName, String midlName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.midlName = midlName;
    }

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidlName() {
        return midlName;
    }

    public void setMidlName(String midlName) {
        this.midlName = midlName;
    }
}
