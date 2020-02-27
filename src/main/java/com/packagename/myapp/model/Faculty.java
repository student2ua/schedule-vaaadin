package com.packagename.myapp.model;

public class Faculty {
    private Integer id;
    private String facultyName;
    private String shortName;

    public Faculty() {
    }

    public Faculty(int i, String s) {
        this.id = i;
        this.facultyName = s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
