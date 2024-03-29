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
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * A class that builds a UI an assignment where all the user past attempts are shown
 * along with the option to new submission
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 01/02/2023
 */

@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
@Route(value = "assignment/:assignment-ID?", layout = MainLayout.class)
public class AssignmentView extends VerticalLayout implements BeforeEnterObserver {

    private Assignment targetAssignment;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SecurityUserDetailsService userService;


    /**
     * Creates a new assigment view and adds the option to go back and to new submission
     */
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

    /**
     * A method that finds which submission is approved as final submission and display it to the user
     *
     * @param submissions The submission of the user at the targeted assignment
     */
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

    /**
     * A method that display an option to navigate to the new submission page
     */
    private void newSubmission() {
        Button newSubmission = new Button("New submission:");
        newSubmission.setWidthFull();
        newSubmission.setHeight(40, Unit.MM);
        newSubmission.addClickListener(e ->

                newSubmission.getUI().ifPresent(ui -> ui.navigate(SubmitAssignmentView.class, new RouteParameters(
                        "assignment-ID", String.valueOf(targetAssignment.getId())))));

        add(newSubmission);
    }

    /**
     * A method that display an option to go back to the assignment option
     */
    private void goBackButton() {

        VerticalLayout bottomRow = new VerticalLayout();

        bottomRow.setAlignItems(Alignment.END);

        Button goBackButton = new Button("Back");
        bottomRow.add(goBackButton);
        add(bottomRow);

        goBackButton.addClickListener(e ->

                goBackButton.getUI().ifPresent(ui -> ui.navigate(AssignmentMenu.class)));
    }

    /**
     * A method that shows all the past attempts of the user at a specific assignment
     *
     * @param submissions The submission of the user at the assignment
     */
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
