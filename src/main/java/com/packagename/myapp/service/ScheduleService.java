package com.packagename.myapp.service;

import com.packagename.myapp.model.*;

import java.util.Arrays;
import java.util.List;

public class ScheduleService {

    public static ScheduleService getInstance() {
        return new ScheduleService();
    }

    public List<Faculty> getFaculties() {
        return Arrays.asList(
                new Faculty(1, "1111"),
                new Faculty(2, "2"),
                new Faculty(3, "33333"),
                new Faculty(4, "4444")
        );
    }

    public List<Speciality> getSpecialities(Faculty value) {
        return Arrays.asList(
                new Speciality(1500, "6.051.020 Економічна кібернетика"),
                new Speciality(1502, "6.051.100 Бізнес-статистика і аналітика"),
                new Speciality(1510, "6.121.010 Інженерія програмного забезпечення")
        );
    }

    public List<Integer> getCourses(Faculty faculty, Speciality speciality) {
        return Arrays.asList(1, 2, 3, 4);
    }

    public List<StudentGroups> getStudentGroups(Faculty faculty, Speciality speciality, Integer cource) {
        return Arrays.asList(new StudentGroups(1, "6.0.0.19.1"));
    }

    public List<Student> getStudents(Faculty faculty, Speciality speciality, Integer cource, StudentGroups studentGroups) {
        return Arrays.asList(
                new Student(398261, "Бугай", "Ілля", "Сергійович"),
                new Student(398221, "Коптілов", "Нікіта", "Сергійович"),
                new Student(398195, "Маслюк", "Світлана", "Миколаївна")
        );
    }

    public List<Department> getDepartments() {
        return Arrays.asList(
                new Department(1, "Dep 1"),
                new Department(2, "Dep 2"),
                new Department(3, "Dep 3")
        );
    }

    public List<Teacher> getTeachers(Department value) {
        return Arrays.asList(
                new Teacher(1, "t1"),
                new Teacher(2, "t2"),
                new Teacher(3, "t3"),
                new Teacher(4, "t4")
        );
    }
}
