package com.example.autogradertyp.views;


import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.SecurityUserDetailsService;
import com.example.autogradertyp.data.service.SubmissionService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@PermitAll
@Route("assignment/:assignment-ID?")
public class AssignmentView extends VerticalLayout implements BeforeEnterObserver {

    private Assignment targetAssignment;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SecurityUserDetailsService userService;


    public AssignmentView() {

        newSubmission();
        goBackButton();

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        Optional<String> assignmentID = beforeEnterEvent.getRouteParameters().get("assignment-ID");

        this.targetAssignment = assignmentService.getAssigmentById(Long.valueOf(assignmentID.get()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(authentication.getName());

        ArrayList<Submission> submissions = submissionService.getStudentSubmissionsForAssignment(targetAssignment, user);

        // Reverse to view from newest to oldest
        Collections.reverse(submissions);

        currentGrade(submissions);
        showPastAttempts(submissions);
    }

    private void currentGrade(ArrayList<Submission> submissions) {

        add(new H1("Current mark:"));

        if (submissions.size() != 0) {

            Submission keptSubmission = submissionService.getUserCurrentSubmission(submissions);

            add(new H1(keptSubmission.getMarks() + " out of " + keptSubmission.getTotalMarks()
                    + " submitted at " + keptSubmission.getSubmissionDataTimeFormatted()));
        } else {

            add(new H1("No submissions available for this assignment."));
        }
    }

    private void newSubmission() {
        Button newSubmission = new Button("New submission:");
        newSubmission.setWidthFull();
        newSubmission.setHeight(40, Unit.MM);
        newSubmission.addClickListener(e ->

                newSubmission.getUI().ifPresent(ui -> ui.navigate(SubmitAssignmentView.class,
                        new RouteParameters("assignment-ID", String.valueOf(targetAssignment.getId())))));

        add(newSubmission);
    }

    private void goBackButton () {

        VerticalLayout bottomRow = new VerticalLayout();

        bottomRow.setAlignItems(Alignment.END);

        Button goBackButton = new Button("Back");
        bottomRow.add(goBackButton);
        add(bottomRow);

        goBackButton.addClickListener(e ->

                goBackButton.getUI().ifPresent(ui -> ui.navigate(AssignmentMenu.class)));
    }

    private void showPastAttempts(ArrayList<Submission> submissions) {

        add(new H1("Past attempts:"));

        Grid<Submission> historyGrid = new Grid<>(Submission.class, false);
        historyGrid.addColumn(Submission::getAttemptNumber).setHeader("Attempt number");
        historyGrid.addColumn(Submission::getMarks).setHeader("Mark acquired");
        historyGrid.addColumn(Submission::getTotalMarks).setHeader("Marks available");
        historyGrid.addColumn(Submission::getSubmissionDataTimeFormatted).setHeader("Submission time");


        historyGrid.setItems(submissions);
        historyGrid.setAllRowsVisible(true);
        add(historyGrid);

    }


}
