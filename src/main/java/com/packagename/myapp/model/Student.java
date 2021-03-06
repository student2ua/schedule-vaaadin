package com.packagename.myapp.model;

public class Student {
    private Integer id;
    private String lastName;
    private String firstName;
    private String middleName;

    public Student(Integer id, String lastName, String firstName, String midlName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = midlName;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
