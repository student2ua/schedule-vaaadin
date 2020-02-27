package com.packagename.myapp.model;

public class Speciality {
    private Integer id;
    private String specialityCode;
    private String specialityName;
    private String specialityNumber;
    private String shortName;
    private String qualName;

    public Speciality(Integer id, String name) {
        this.id = id;
        this.specialityName = name;
    }

    public Speciality() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecialityCode() {
        return specialityCode;
    }

    public void setSpecialityCode(String specialityCode) {
        this.specialityCode = specialityCode;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getSpecialityNumber() {
        return specialityNumber;
    }

    public void setSpecialityNumber(String specialityNumber) {
        this.specialityNumber = specialityNumber;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getQualName() {
        return qualName;
    }

    public void setQualName(String qualName) {
        this.qualName = qualName;
    }
}
