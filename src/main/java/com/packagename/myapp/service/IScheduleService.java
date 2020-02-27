package com.packagename.myapp.service;

import com.packagename.myapp.model.*;

import java.util.List;

/**
 * User: tor
 * Date: 24.02.2020
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public interface IScheduleService {
    List<Faculty> getFaculties();

    List<Speciality> getSpecialities(Faculty value);

    List<Integer> getCourses(Faculty faculty, Speciality speciality);

    List<StudentGroups> getStudentGroups(Faculty faculty, Speciality speciality, Integer cource);

    List<Student> getStudents(Faculty faculty, Speciality speciality, Integer cource, StudentGroups studentGroups);

    List<Department> getDepartments();

    List<Teacher> getTeachers(Department value);
    String getStudentSchedule(Integer sgid, Integer studid, Integer week);
    String getEmployeeSchedule(Integer empId, Integer week);
}
