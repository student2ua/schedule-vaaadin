package com.packagename.myapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packagename.myapp.model.*;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.net.HttpURLConnection.HTTP_MULT_CHOICE;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * User: tor
 * Date: 24.02.2020
 * Time: 14:00
 */
public class RestScheduleService implements IScheduleService {
    private static final Logger logger = Logger.getLogger(RestScheduleService.class.getName());

    private ObjectMapper mapper = new ObjectMapper();
    public static final String SERVER = "http://localhost:8080/openAPI/rest";


    public RestScheduleService() {

  /*      Request.Get("http://targethost/homepage")
                .execute().returnContent();
        Request.Post("http://targethost/login")
                .bodyForm(Form.form().add("username", "vip").add("password", "secret").build())
                .execute().returnContent();*/
    }

    @Override
    public List<Faculty> getFaculties() {
        List<Faculty> rez = Collections.emptyList();
        final Faculty[] response = getResponse("/faculties/", Faculty[].class);
        if (response != null) {
            rez = Arrays.asList(response);
            rez.sort((faculty, t1) -> faculty.getFacultyName().compareToIgnoreCase(t1.getFacultyName()));
        }

        return rez;
    }

    @Override
    public List<Speciality> getSpecialities(Faculty faculty) {
        List<Speciality> rez = Collections.emptyList();
        if (faculty != null && faculty.getId() != null) {
            final Speciality[] response = getResponse(
                    String.format("/faculties/%d/specialities/", faculty.getId()), Speciality[].class);
            if (response != null) {
                rez = Arrays.asList(response);
                rez.sort((speciality, t1) -> (speciality.getSpecialityCode() + " " + speciality.getSpecialityName())
                        .compareToIgnoreCase(t1.getSpecialityCode() + " " + t1.getSpecialityName()));
            }
        }
        return rez;
    }

    @Override
    public List<Integer> getCourses(Faculty faculty, Speciality speciality) {
        List<Integer> rez = new ArrayList<>();
        if (faculty != null && faculty.getId() != null) {
            final StudentGroups[] response = getResponse(
                    String.format("/faculties/%d/specialities/%d/sgroups", faculty.getId(), speciality.getId()), StudentGroups[].class);
            if (response != null) {
                Set<Integer> courses = new HashSet<>();
                for (StudentGroups groups : response) {
                    if (groups.getCourse()<=4)
                    courses.add(groups.getCourse());
                }
                rez.addAll(courses);
                rez.sort(Integer::compareTo);
            }
        }
        return rez;
    }

    @Override
    public List<StudentGroups> getStudentGroups(Faculty faculty, Speciality speciality, Integer cource) {
        List<StudentGroups> rez = new ArrayList<>();
        if (faculty != null && faculty.getId() != null && speciality != null && speciality.getId() != null) {
            final StudentGroups[] response = getResponse(
                    String.format("/faculties/%d/specialities/%d/sgroups", faculty.getId(), speciality.getId()), StudentGroups[].class);
            if (response != null) {
                rez = Arrays.asList(response);
            }
            if (cource != null && cource != 0) {
                rez = rez.parallelStream()
                        .filter(studentGroups -> cource.equals(studentGroups.getCourse()))
                        .collect(Collectors.toList());
            }
        }
        return rez;
    }

    @Override
    public List<Student> getStudents(Faculty faculty, Speciality speciality, Integer cource, StudentGroups studentGroups) {

        List<Student> rez = new ArrayList<>();
        if (faculty != null && faculty.getId() != null) {
            final Student[] response = getResponse(
                    String.format("/faculties/%d/specialities/%d/sgroups/%d/students", faculty.getId(), speciality.getId(), studentGroups.getId()), Student[].class);
            if (response != null) {
                rez = Arrays.asList(response);
            }
        }
        return rez;
    }

    @Override
    public List<Department> getDepartments() {
        List<Department> rez = Collections.emptyList();

        final Department[] response = getResponse("/faculties/0/departments", Department[].class);
        if (response != null) rez = Arrays.asList(response);

        return rez;
    }

    @Override
    public List<Teacher> getTeachers(Department department) {
        List<Teacher> rez = Collections.emptyList();
        if (department != null && department.getId() != null) {
            final Teacher[] response = getResponse(String.format("/faculties/0/departments/%d/teachers", department.getId()), Teacher[].class);
            if (response != null) rez = Arrays.asList(response);
        }

        return rez;
    }

    @Override
    public String getStudentSchedule(Integer sgid, Integer studid, Integer week) {
        logger.info(sgid + ", " + studid + ", " + week);
        String format = String.format("/report/schedule/student?sgid=%d&studid=%d", sgid, studid);
        if (week != null) format = format + "&week=" + week;
        return getResponse(format, String.class, ContentType.TEXT_HTML);

    }

    @Override
    public String getEmployeeSchedule(Integer empId, Integer week) {
        logger.info(empId + ", " + week);
        String format = String.format("/report/schedule/employee?employee=%d", empId);
        if (week != null) format = format + "&week=" + week;
        return getResponse(format, String.class, ContentType.TEXT_HTML);
    }

    @Nullable
    private <T> T getResponse(@NotNull String apiUrl, Class<T> itemClass) {
        return getResponse(apiUrl, itemClass, ContentType.APPLICATION_JSON);
    }

    @Nullable
    private <T> T getResponse(@NotNull String apiUrl, Class<T> itemClass, @NotNull ContentType contentType) {
        assert !apiUrl.isEmpty();
        assert apiUrl.startsWith("/");
        try {
            final String uri = SERVER + apiUrl;
            logger.info(uri + ", " + itemClass);
            final HttpResponse httpResponse = Request.Get(uri)
                    .addHeader(HttpHeaders.CONTENT_TYPE, contentType.getMimeType())
                    .addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate")
                    .addHeader(HttpHeaders.ACCEPT_CHARSET, Charsets.UTF_8.name())
//                    .connectTimeout((int) TimeUnit.SECONDS.toMillis(10))
//                    .socketTimeout((int) TimeUnit.SECONDS.toMillis(10))

                    .execute().returnResponse();

            final int code = httpResponse.getStatusLine().getStatusCode();
            if (HTTP_OK <= code && code < HTTP_MULT_CHOICE) {   //<300
                final HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    if (contentType.equals(ContentType.APPLICATION_JSON)) {
                        return mapper.readValue(entity.getContent(), itemClass);
                    } else if (itemClass == String.class) {
                        return (T) EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
