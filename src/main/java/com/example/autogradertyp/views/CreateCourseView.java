package com.example.autogradertyp.views;


import com.example.autogradertyp.data.entity.Course;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.CourseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@RolesAllowed({"ROLE_ADMIN"})
@Route(value = "create-course", layout = MainLayout.class)
public class CreateCourseView extends VerticalLayout {

    @Autowired
    private CourseService courseService;


    public CreateCourseView (){

        TextField courseName = new TextField();
        courseName.setLabel("Entre course name:");
        add(courseName);

        TextField courseCode = new TextField();
        courseCode.setLabel("Entre course code:");
        add(courseCode);

        Button submit = new Button("Create");
        add(submit);

        submit.addClickListener(e -> {

            Course course = new Course(courseName.getValue(), courseCode.getValue());

            courseService.add(course);
            submit.getUI().ifPresent(ui -> ui.navigate(CourseMenu.class));
        });

        addBackButton();

        }

        private void addBackButton(){

            VerticalLayout bottomRow = new VerticalLayout();

            bottomRow.setAlignItems(Alignment.END);

            Button goBackButton = new Button("Back");
            bottomRow.add(goBackButton);
            add(bottomRow);

            goBackButton.addClickListener(e ->

                    goBackButton.getUI().ifPresent(ui -> ui.navigate(CourseMenu.class))
            );
        }
}
