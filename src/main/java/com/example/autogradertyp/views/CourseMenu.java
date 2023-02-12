package com.example.autogradertyp.views;

import com.example.autogradertyp.data.entity.Course;
import com.example.autogradertyp.data.service.CourseService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@RolesAllowed({"ROLE_ADMIN"})
@Route("courses")
public class CourseMenu extends VerticalLayout {

    public CourseMenu(CourseService courseService) {

        List<Course> courses = courseService.getAllCourses();
        add(new H1("Select course:"));
        HorizontalLayout assignmentsOptions = new HorizontalLayout();


        if (courses != null) {

            if (courses.size() == 0) {

                Label message = new Label("Not courses available");
                add(message);

            } else {

                for (Course course : courses) {

                    Button courseChoice = new Button(course.getName());
                    courseChoice.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                    courseChoice.addClickListener(e ->

                            courseChoice.getUI().ifPresent(ui -> ui.navigate(MainMenu.class)));

                    courseChoice.setHeight(30, Unit.MM);
                    courseChoice.setWidth(60, Unit.MM);
                    assignmentsOptions.add(courseChoice);

                }
                add(assignmentsOptions);


            }
        }


        HorizontalLayout bottomRow = new HorizontalLayout();
        Button newCourse = new Button("Create new course:");

        Button goBackButton = new Button("Back");
        bottomRow.add(goBackButton);
        add(newCourse, bottomRow);

        newCourse.addClickListener(e -> newCourse.getUI().ifPresent(ui -> ui.navigate(CreateCourseView.class))

        );

        goBackButton.addClickListener(e ->

                goBackButton.getUI().ifPresent(ui -> ui.navigate(MainMenu.class)));

    }

}
