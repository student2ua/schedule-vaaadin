package com.packagename.myapp;

import com.packagename.myapp.model.Faculty;
import com.packagename.myapp.model.Speciality;
import com.packagename.myapp.model.Student;
import com.packagename.myapp.model.StudentGroups;
import com.packagename.myapp.service.IScheduleService;
import com.packagename.myapp.service.RestScheduleService;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.server.VaadinServlet;

import java.util.Collections;
import java.util.List;

class GroupForm extends FormLayout {
    private MainView mainView;
    private IScheduleService service;
    private Button buttonSubmit = new Button("Розклад");
//    FormLayout layoutWithFormItems = new FormLayout();

    private ComboBox<Faculty> cbFaculty = new ComboBox<>();
    private ComboBox<Speciality> cbSpeciality = new ComboBox<>();
    private ComboBox<Integer> cbCource = new ComboBox<>();
    private ComboBox<StudentGroups> cbGroups = new ComboBox<>();
    private ComboBox<Student> cbStudent = new ComboBox<>();

    GroupForm(MainView mainView) {
        this.mainView = mainView;
//        setResponsiveSteps(new FormLayout.ResponsiveStep("100%", 1, ResponsiveStep.LabelsPosition.ASIDE));
        setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));
//        service = ScheduleService.getInstance();
        service = new RestScheduleService();
//        cbFaculty.setReadOnly(true);
        cbFaculty.setRequired(true);
        cbFaculty.setWidthFull();
        List<Faculty> listFaculty = service.getFaculties();
        cbFaculty.setItemLabelGenerator(Faculty::getFacultyName);
        cbFaculty.setItems(listFaculty);
        addFormItem(cbFaculty, "1.Факультет");
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
        cbSpeciality.setWidthFull();
        cbSpeciality.setPlaceholder("Спеціальності на факультеті");
        cbSpeciality.setItemLabelGenerator((ItemLabelGenerator<Speciality>) speciality -> speciality.getSpecialityCode()+" "+speciality.getSpecialityName());
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
        addFormItem(cbSpeciality, "2.Спеціальність");

        cbCource.setWidthFull();
        cbCource.setPlaceholder("Доступні курси");
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
        addFormItem(cbCource, "3.Курс");

        cbGroups.setWidthFull();
        cbGroups.setPlaceholder("Доступні групи");
        cbGroups.setItemLabelGenerator(StudentGroups::getName);
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
        addFormItem(cbGroups, "4.Група");

        cbStudent.setWidthFull();
        cbStudent.setPlaceholder("Студенти групи");
        cbStudent.setItemLabelGenerator((ItemLabelGenerator<Student>) student -> student.getLastName() + " " + student.getFirstName() + " " + student.getMiddleName());
        addFormItem(cbStudent, "5.Студент");

        buttonSubmit.addClickListener(buttonClickEvent -> go());
        add(buttonSubmit);
    }

    private void go() {
//        String ref = String.format("window.location='schedule?group=%d&student=%d'",
//        String ref = String.format("schedule?group=%d&student=%d",
        String ref = String.format("/student.html?group=%d&student=%d",
                cbGroups.getValue().getId(),
                cbStudent.getValue().getId()
        );
        String servletPath = VaadinServlet.getCurrent().getServletContext().getContextPath(); // URL for this web app at runtime.
        UI.getCurrent().getPage().open(servletPath+ref);

        // Redirect this page immediately
    /*    VaadinServletRequest currentRequest = (VaadinServletRequest) VaadinService.getCurrentRequest();
        HttpServletRequest httpServletRequest = currentRequest.getHttpServletRequest();
        System.out.println("httpServletRequest = " + httpServletRequest);
        System.out.println("getRequestURL = " + httpServletRequest.getRequestURL());
        System.out.println("currentRequest = " + currentRequest.getServerName());
        httpServletRequest = com.vaadin.flow.server.VaadinServletRequest@1b49fcd8
                getRequestURL = http://localhost:8080/
        currentRequest = localhost*/
//        getUI().get().getPage().open(/*currentRequest.getServerName() + */ref);
//        UI.getCurrent().getPage().open(/*currentRequest.getServerName() + */ref);


//        https://vaadin.com/forum/thread/16959344/how-to-use-browserwindowopener-together-with-buttonrenderer-in-a-grid
//        BrowserOpenerRenderer <MyBean> renderer = new BrowserOpenerRenderer<>(PopupUI.class, "Pop Up", cl -> {});

  /* https://stackoverflow.com/questions/51691041/generate-an-html-page-and-open-in-a-new-window-from-a-vaadin-8-app
  Resource resource = new ExternalResource( servletPath + "/person.html" );  // Defining an external resource as a URL that is not really so external -- will call back into this same web app.
        BrowserWindowOpener webPageOpener = new BrowserWindowOpener( resource );
        webPageOpener.setWindowName( "Person ID: " + personUuid.getValue() );  // Set title of the new window to be opened.
        String param = "person_id";
        webPageOpener.setParameter( param , personUuid.getValue() );
        String windowFeaturesString = String.format( "width=%d,height=%d,resizable" , Page.getCurrent().getBrowserWindowWidth() , Page.getCurrent().getBrowserWindowHeight() ); // Same size as original window.
        webPageOpener.setFeatures( windowFeaturesString );  // Example: "width=800,height=600,resizable".
        webPageOpener.extend( webPageButton ); // Connect opener with button.
        System.out.println( "TRACE BrowserWindowOpener URL: " + webPageOpener.getUrl() );*/

//        new BrowserWindowOpener(runtimeGeneratedResource())                 .extend(button);
    }
}
