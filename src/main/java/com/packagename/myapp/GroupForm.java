package com.packagename.myapp;

import com.packagename.myapp.model.Faculty;
import com.packagename.myapp.model.Speciality;
import com.packagename.myapp.model.Student;
import com.packagename.myapp.model.StudentGroups;
import com.packagename.myapp.service.ScheduleService;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class GroupForm extends FormLayout {
    private MainView mainView = null;
    private ScheduleService service = null;
    private Button buttonSubmit = new Button("Расписание");
//    FormLayout layoutWithFormItems = new FormLayout();

    private ComboBox<Faculty> cbFaculty = new ComboBox<>();
    private ComboBox<Speciality> cbSpeciality = new ComboBox<>();
    private ComboBox<Integer> cbCource = new ComboBox<>();
    private ComboBox<StudentGroups> cbGroups = new ComboBox<>();
    private ComboBox<Student> cbStudent = new ComboBox<>();

    public GroupForm(MainView mainView) {
        this.mainView = mainView;
        service = ScheduleService.getInstance();
//        cbFaculty.setReadOnly(true);
        cbFaculty.setRequired(true);
        List<Faculty> listFaculty = service.getFaculties();
        cbFaculty.setItemLabelGenerator(Faculty::getName);
        cbFaculty.setItems(listFaculty);
        addFormItem(cbFaculty, "Факультет");
        cbFaculty.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue() == null) {
                cbSpeciality.setEnabled(false);
                cbSpeciality.setItems(Collections.emptyList());
            } else {
                cbSpeciality.setEnabled(true);
                if (!valueChangeEvent.getValue().equals(valueChangeEvent.getOldValue()))
                    cbSpeciality.setItems(service.getSpecialities(valueChangeEvent.getValue()));
            }
        });

//        cbSpeciality.setReadOnly(true);
        cbSpeciality.setPlaceholder("Специальности на факультете");
        cbSpeciality.setItemLabelGenerator(Speciality::getName);
//        List<Speciality> listSpeciality = service.getSpecialities(valueChangeEvent.getValue());
//        cbSpeciality.setItems(listSpeciality);
        cbSpeciality.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue() == null) {
                cbCource.setEnabled(false);
                cbCource.setItems(Collections.emptyList());
            } else {
                cbCource.setEnabled(true);
                if (!valueChangeEvent.getValue().equals(valueChangeEvent.getOldValue()))
                    cbCource.setItems(service.getCourses(cbFaculty.getValue(), valueChangeEvent.getValue()));
            }
        });
        addFormItem(cbSpeciality, "Специальность");

//        cbCource.setReadOnly(true);
//        cbCource.setItems(service.getCourses(valueChangeEvent.getValue()));
        cbCource.setPlaceholder("Доступные курсы");
        cbCource.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue() == null) {
                cbGroups.setEnabled(false);
                cbGroups.setItems(Collections.emptyList());
            } else {
                cbGroups.setEnabled(true);
                if (!valueChangeEvent.getValue().equals(valueChangeEvent.getOldValue()))
                    cbGroups.setItems(
                            service.getStudentGroups(
                                    cbFaculty.getValue(),
                                    cbSpeciality.getValue(),
                                    valueChangeEvent.getValue()));
            }
        });
        addFormItem(cbCource, "Курс");

//        cbGroups.setReadOnly(true);
        cbGroups.setPlaceholder("Доступные группы");
//        List<StudentGroups> listGroups = service.getStudentGroups(valueChangeEvent.getValue());
        cbGroups.setItemLabelGenerator(StudentGroups::getName);
//        cbGroups.setItems(listGroups);
        cbGroups.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue() == null) {
                cbStudent.setEnabled(false);
                cbStudent.setItems(Collections.emptyList());
            } else {
                cbStudent.setEnabled(true);
                if (!valueChangeEvent.getValue().equals(valueChangeEvent.getOldValue()))
                    cbStudent.setItems(
                            service.getStudents(
                                    cbFaculty.getValue(),
                                    cbSpeciality.getValue(),
                                    cbCource.getValue(),
                                    valueChangeEvent.getValue()));
            }
        });
        addFormItem(cbGroups, "Группа");

//        cbStudent.setReadOnly(true);
        cbStudent.setPlaceholder("Студенты группы");
//        List<Student> listStudent = service.getStudents(cbFaculty.getValue(), cbSpeciality.getValue(), cbCource.getValue(), valueChangeEvent.getValue());
        cbStudent.setItemLabelGenerator((ItemLabelGenerator<Student>) student -> student.getLastName() + " " + student.getFirstName() + " " + student.getMidlName());
//        cbStudent.setItems(listStudent);
        addFormItem(cbStudent, "Студент");

        buttonSubmit.addClickListener(buttonClickEvent -> go());
        add(buttonSubmit);
    }

    private void go() {
//        String ref = String.format("window.location='schedule?group=%d&student=%d'",
        String ref = String.format("schedule?group=%d&student=%d",
                cbGroups.getValue().getId(),
                cbStudent.getValue().getId()
        );
        // Redirect this page immediately
    /*    VaadinServletRequest currentRequest = (VaadinServletRequest) VaadinService.getCurrentRequest();
        HttpServletRequest httpServletRequest = currentRequest.getHttpServletRequest();
        System.out.println("httpServletRequest = " + httpServletRequest);
        System.out.println("getRequestURL = " + httpServletRequest.getRequestURL());
        System.out.println("currentRequest = " + currentRequest.getServerName());
        httpServletRequest = com.vaadin.flow.server.VaadinServletRequest@1b49fcd8
                getRequestURL = http://localhost:8080/
        currentRequest = localhost*/
        getUI().get().getPage().open(/*currentRequest.getServerName() + */ref);
        /*new Button("Logout", event -> {
            // Redirect this page immediately
            getPage().setLocation("/myapp/logout.html");

            // Close the session
            getSession().close();
        }));

        // Notice quickly if other UIs are closed
        setPollInterval(3000);

        Button viewBtn = new Button("Click me", VaadinIcons.EYE);
viewBtn.addClickListener(ev -> {
  if (ev.isCtrlKey()) {
      Page.getCurrent().open("http://www.example.com", "_blank", false);
  } else {
      Page.getCurrent().setLocation("http://www.example.com");
  }
});
*/
    }
}
