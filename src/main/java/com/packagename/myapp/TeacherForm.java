package com.packagename.myapp;

import com.packagename.myapp.model.*;
import com.packagename.myapp.service.ScheduleService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TeacherForm extends FormLayout {
    private MainView mainView = null;
    private ScheduleService service = null;
    private Button buttonSubmit = new Button("Расписание");
//    FormLayout layoutWithFormItems = new FormLayout();

    private ComboBox<Department> cbDepartment = new ComboBox<>();
    private ComboBox<Teacher> cbTeacher = new ComboBox<>();

    public TeacherForm(MainView mainView) {
        this.mainView = mainView;

        service = ScheduleService.getInstance();
//        cbDepartment.setReadOnly(true);
        cbDepartment.setRequired(true);
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
        addFormItem(cbDepartment, "Кафедра");

        cbTeacher.setRequired(true);
        cbTeacher.setPlaceholder("Преподаватели на кафедре");
        cbTeacher.setItemLabelGenerator(Teacher::getName);
        addFormItem(cbTeacher, "Преподаватель");

        buttonSubmit.addClickListener(buttonClickEvent -> go());
        add(buttonSubmit);
    }

    private void go() {
        String ref = String.format("schedule?employee=%d",
                cbTeacher.getValue().getId());
        getUI().get().getPage().open(ref);

    }
}
