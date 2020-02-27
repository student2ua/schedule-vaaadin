package com.packagename.myapp;

import com.packagename.myapp.model.Department;
import com.packagename.myapp.model.Teacher;
import com.packagename.myapp.service.IScheduleService;
import com.packagename.myapp.service.RestScheduleService;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;

import java.util.Collections;
import java.util.List;

public class TeacherForm extends FormLayout {
    private MainView mainView = null;
    private IScheduleService service = null;
    private Button buttonSubmit = new Button("Расписание");
//    FormLayout layoutWithFormItems = new FormLayout();

    private ComboBox<Department> cbDepartment = new ComboBox<>();
    private ComboBox<Teacher> cbTeacher = new ComboBox<>();

    public TeacherForm(MainView mainView) {
        this.mainView = mainView;

//        service = ScheduleService.getInstance();
        service = new RestScheduleService();
//        cbDepartment.setReadOnly(true);
        cbDepartment.setRequired(true);
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
        addFormItem(cbDepartment, "Кафедра");
        cbTeacher.setRequired(true);
        cbTeacher.setPlaceholder("Преподаватели на кафедре");
        cbTeacher.setItemLabelGenerator((ItemLabelGenerator<Teacher>) student -> student.getLastName() + " " + student.getFistName() + " " + student.getMiddleName());
        addFormItem(cbTeacher, "Преподаватель");

        buttonSubmit.addClickListener(buttonClickEvent -> go());
        add(buttonSubmit);
    }

    private void go() {
//        String ref = String.format("schedule?employee=%d",
        String ref = String.format("employee.html?employee=%d",
                cbTeacher.getValue().getId());
        getUI().get().getPage().open(ref);

    }
}
