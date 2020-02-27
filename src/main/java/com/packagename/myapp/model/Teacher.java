package com.packagename.myapp.model;

public class Teacher {
    private Integer id;
    private String lastName;
    private String fistName;
    private String middleName;

    public Teacher() {
    }

    public Teacher(Integer id, String lastName, String fistName, String middleName) {
        this.id = id;
        this.lastName = lastName;
        this.fistName = fistName;
        this.middleName = middleName;
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

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
