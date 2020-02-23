package com.packagename.myapp.model;

public class Faculty {
    private Integer id;
    private String name;

    public Faculty() {
    }

    public Faculty(int i, String s) {
        this.id = i;
        this.name = s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
