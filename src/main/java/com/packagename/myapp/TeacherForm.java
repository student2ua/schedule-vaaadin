package com.packagename.myapp;

import com.packagename.myapp.model.Department;
import com.packagename.myapp.model.Teacher;
import com.packagename.myapp.service.IScheduleService;
import com.packagename.myapp.service.RestScheduleService;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;

import java.util.Collections;
import java.util.List;

class TeacherForm extends FormLayout {
    private MainView mainView;
    private IScheduleService service;
    private Button buttonSubmit = new Button("Розклад");
//    FormLayout layoutWithFormItems = new FormLayout();

    private ComboBox<Department> cbDepartment = new ComboBox<>();
    private Select<Teacher> cbTeacher = new Select<>();

    TeacherForm(MainView mainView) {
        this.mainView = mainView;
       setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1));
//        service = ScheduleService.getInstance();
        service = new RestScheduleService();

//        cbDepartment.setReadOnly(true);
        cbDepartment.setRequired(true);
        cbDepartment.setWidthFull();
//        cbDepartment.getElement().setAttribute("colspan", "2");
//        cbDepartment.setHeightFull();
        List<Department> listFaculty = service.getDepartments();
        cbDepartment.setItemLabelGenerator(Department::getName);
        cbDepartment.setItems(listFaculty);
        cbDepartment.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue() == null) {
                cbTeacher.setEnabled(false);
                cbTeacher.setItems(Collections.emptyList());
            } else {
                cbTeacher.setEnabled(true);
                if (!valueChangeEvent.getValue().equals(valueChangeEvent.getOldValue()))
                    cbTeacher.setItems(service.getTeachers(valueChangeEvent.getValue()));
            }
        });
        addFormItem(cbDepartment, "1.Кафедра");
        cbTeacher.setWidthFull();
//        cbTeacher.getElement().setAttribute("colspan", "2");
//        cbTeacher.setRequired(true);
        cbTeacher.setPlaceholder("Викладачі на кафедрі");
        cbTeacher.setItemLabelGenerator((ItemLabelGenerator<Teacher>) student -> student.getLastName() + " " + student.getFistName() + " " + student.getMiddleName());
        addFormItem(cbTeacher, "2.Викладач");

        buttonSubmit.addClickListener(buttonClickEvent -> go());
        add(buttonSubmit);
    }

    private void go() {
//        String ref = String.format("schedule?employee=%d",
        String ref = String.format("employee.html?employee=%d",
                cbTeacher.getValue().getId());
        UI.getCurrent().getPage().open(ref);

    }
}
