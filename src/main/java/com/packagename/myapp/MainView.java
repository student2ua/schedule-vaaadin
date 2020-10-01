package com.packagename.myapp;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The main view contains a tabs.
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
//@Theme(value = Material.class, variant = Material.DARK)
public class MainView extends VerticalLayout implements PageConfigurator {
    public MainView() {

        Tab groupTab = new Tab("Розклад на групу");
        GroupForm groupForm = new GroupForm(this);
        groupTab.add(groupForm);


        Tab teacherTab = new Tab("Розклад на Викладача");
        TeacherForm teacherForm = new TeacherForm(this);
        teacherTab.add(teacherForm);
        teacherForm.setVisible(false);

       /* Tab tab3 = new Tab("Tab three");
        Div page3 = new Div();
        page3.setText("Page#3");
        page3.setVisible(false);*/

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(groupTab, groupForm);
        tabsToPages.put(teacherTab, teacherForm);
//        tabsToPages.put(tab3, page3);
        Tabs tabs = new Tabs(groupTab, teacherTab/*, tab3*/);
        tabs.addThemeVariants(TabsVariant.MATERIAL_FIXED);

        Div pages = new Div(groupForm, teacherForm/*, page3*/);
        Set<Component> pagesShown = Stream.of(groupForm)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        tabs.setFlexGrowForEnclosedTabs(1);
        // Use TextField for standard text input
/*        TextField textField = new TextField("Your name");

        // Button click listeners can be defined as lambda expressions
        GreetService greetService = new GreetService();
        Button button = new Button("Say hello",
                e -> Notification.show(greetService.greet(textField.getValue())));

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");
*/

//        add(tabs,textField, button);
        add(tabs, pages);
//        add(groupForm);

        VaadinSession.getCurrent().addRequestHandler(
                new RequestHandler() {
                    @Override
                    public boolean handleRequest(VaadinSession session,
                                                 VaadinRequest request,
                                                 VaadinResponse response)
                            throws IOException {
                        switch (request.getPathInfo()) {
                            case "/student.html": {
                                String groupString = request.getParameter("group");  // In real-work, validate the results here.

                                String studentString = request.getParameter("student");  // In real-work, validate the results here.
                                if (groupString == null || groupString.isEmpty() || !groupString.matches("[0-9]+") ||
                                        studentString == null || studentString.isEmpty() || !studentString.matches("[0-9]+")) {
//                                    ErrorViewShower.showError() ;
                                    VaadinSession.getCurrent().getErrorHandler().error(new ErrorEvent(new ParseException("помилка у данних", 0)));
                                }
                                String weekString = request.getParameter("week");  // In real-work, validate the results here.

                                // Build HTML.
                                String html = ScheduleHtmlUtils.renderGroupSheduleHtml(groupString, studentString, weekString);
                                // Send out the generated text as HTML, in UTF-8 character encoding.
                                response.setContentType("text/html; charset=utf-8");
                                response.getWriter().append(html);
                                response.getWriter().flush();
                                response.getWriter().close();
                                return true; // We wrote a response

                            }
                            case "/employee.html": {
                                String employeeString = request.getParameter("employee");
                                String weekString = request.getParameter("week");
                                // Build HTML.
                                String html = ScheduleHtmlUtils.renderEmployeeSheduleHtml(employeeString, weekString);
                                // Send out the generated text as HTML, in UTF-8 character encoding.
                                response.setContentType("text/html; charset=utf-8");
                                response.getWriter().append(html);
                                response.getWriter().flush();
                                response.getWriter().close();
                                return true;
                            }
                        }
                        return false; // No response was written
                    }
                });
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addLink("shortcut icon", "icons/favicon.ico");
    }
}
