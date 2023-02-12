package com.example.autogradertyp.views;

import com.example.autogradertyp.backend.DeadlineScheduler;
import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Course;
import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.service.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class that builds a UI for creating a new assignment
 *
 * @author Fahd Alsahali
 * @version 2.0
 * @date 13/11/2022
 * @since 01/02/2023
 */

@RolesAllowed({"ROLE_ADMIN"})
@Route("create-assignment")
public class CreateAssignmentView extends VerticalLayout {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private SecurityUserDetailsService userService;

    @Autowired
    private SubmissionService submissionService;

    private Select<Course> select;

    /**
     * Creates a create new assigment view
     */
    public CreateAssignmentView(CourseService courseService) {

        TextField assignmentName = new TextField();
        assignmentName.setLabel("Entre assignment name:");
        add(assignmentName);

        createCoursesSelectMenu(courseService);

        DateTimePicker deadline = new DateTimePicker("Assignment deadline:");
        add(deadline);

        Label testCaseMessage = new Label("Entre test case information:");
        add(testCaseMessage);

        VerticalLayout testCaseSection = new VerticalLayout();
        HorizontalLayout testCaseLayout = new HorizontalLayout();

        TextField testCaseInput = new TextField();
        testCaseInput.setLabel("Input:");
        testCaseLayout.add(testCaseInput);

        TextField testCaseExpectedOutput = new TextField();
        testCaseExpectedOutput.setLabel("Expected output:");
        testCaseLayout.add(testCaseExpectedOutput);

        TextField numberOfMarks = new TextField();
        numberOfMarks.setLabel("Marks:");
        testCaseLayout.add(numberOfMarks);


        testCaseSection.add(testCaseLayout);
        add(testCaseSection);

        ArrayList<TextField> testCasesValues = new ArrayList<>();
        ArrayList<HorizontalLayout> testCasesLayout = new ArrayList<>();

        Button addTestCase = new Button("Add test case:");
        add(addTestCase);

        addTestCase.addClickListener(e -> {

            testCasesLayout.add(new HorizontalLayout());
            testCasesValues.add(new TextField());
            int index = testCasesValues.size() - 1;
            testCasesValues.get(index).setLabel("Input:");
            testCasesLayout.get(testCasesLayout.size() - 1).add(testCasesValues.get(index));

            testCasesValues.add(new TextField());
            index = testCasesValues.size() - 1;
            testCasesValues.get(index).setLabel("Expected output:");
            testCasesLayout.get(testCasesLayout.size() - 1).add(testCasesValues.get(index));

            testCasesValues.add(new TextField());
            index = testCasesValues.size() - 1;
            testCasesValues.get(index).setLabel("Marks:");
            testCasesLayout.get(testCasesLayout.size() - 1).add(testCasesValues.get(index));

            testCaseSection.add(testCasesLayout.get(testCasesLayout.size() - 1));

        });

        Button submit = new Button("Create");
        add(submit);

        submit.addClickListener(e -> {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User author = userService.loadUserByUsername(authentication.getName());

            Assignment assignment = new Assignment(assignmentName.getValue(), select.getValue(), author, deadline.getValue());
            assignmentService.add(assignment);

            TestCase testCase = new TestCase(testCaseInput.getValue(), testCaseExpectedOutput.getValue(), Integer.parseInt(numberOfMarks.getValue()), assignment);
            testCaseService.add(testCase);

            for (int i = 0; i < testCasesValues.size(); i = i + 3) {

                testCaseService.add(new TestCase(testCasesValues.get(i).getValue(), testCasesValues.get(i + 1).getValue(), Integer.parseInt(testCasesValues.get(i + 2).getValue()), assignment));
            }
            startCountDown(assignment);

        });


        VerticalLayout bottomRow = new VerticalLayout();

        bottomRow.setAlignItems(Alignment.END);

        Button goBackButton = new Button("Back");
        bottomRow.add(goBackButton);
        add(bottomRow);

        goBackButton.addClickListener(e ->

                goBackButton.getUI().ifPresent(ui -> ui.navigate(MainMenu.class)));
    }

    private void createCoursesSelectMenu (CourseService courseService) {

        select = new Select<>();
        select.setLabel("Course");

        select.setItemLabelGenerator(Course::getName);

         List<Course> courses = courseService.getAllCourses();
        select.setItems(courses);
        add(select);

    }

    /**
     * A method that starts a timer to the deadline of the assignment
     *
     * @param assignment The assignment which the timer for its deadline will start
     */
    private void startCountDown(Assignment assignment) {

        Long remainingTime = LocalDateTime.now().until(assignment.getDeadline(), ChronoUnit.SECONDS);

        Timer timer = new Timer();
        TimerTask task = new DeadlineScheduler(assignment, submissionService, assignmentService);


        timer.schedule(task, remainingTime * 1000);
    }

}
