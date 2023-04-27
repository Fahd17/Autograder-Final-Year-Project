package com.example.autogradertyp.views;


import com.example.autogradertyp.backend.PlagiarismDetectorConnection;
import com.example.autogradertyp.backend.PlagiarismInstance;
import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.SubmissionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;


/**
 * A class that builds a UI for plagiarism report
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 6/04/2023
 */

@RolesAllowed({"ROLE_ADMIN"})
@Route(value = "plagiarism/:assignment-ID?",  layout = MainLayout.class)
public class PlagiarismReportView extends VerticalLayout implements BeforeEnterObserver {


    @Autowired
    AssignmentService assignmentService;

    @Autowired
    SubmissionService submissionService;

    private Assignment targetAssignment;

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        Optional<String> assignmentID = beforeEnterEvent.getRouteParameters().get("assignment-ID");

        targetAssignment = assignmentService.getAssigmentById(Long.valueOf(assignmentID.get()));

        showPlagiarismResults();

    }

    private void showPlagiarismResults() {

        JSONObject response = null;
        try {
            response = PlagiarismDetectorConnection.getResultsOverview(targetAssignment.getPlagiarismCheckId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.toJSONString());

        if (response.get("error") != null) {
            add(new H1(response.get("error").toString()));
        } else {

            ArrayList<PlagiarismInstance> plagiarismArray = createPlagiarismArray(submissionService, response);

            System.out.println(response.toJSONString());

            add(new H1("Plagiarism results:"));

            Grid<PlagiarismInstance> historyGrid = new Grid<>(PlagiarismInstance.class, false);
            historyGrid.addColumn(PlagiarismInstance::getUserName).setHeader("User name");
            historyGrid.addColumn(PlagiarismInstance::getStudentNumber).setHeader("Student number");
            historyGrid.addColumn(PlagiarismInstance::getPlagiarismResult).setHeader("Plagiarism percentage");
            historyGrid.addColumn(PlagiarismInstance::getNumberOfPlagiarismMatches).setHeader("Number of matches found");


            //historyGrid.add
            historyGrid.setAllRowsVisible(true);
            historyGrid.setItems(plagiarismArray);
            add(historyGrid);
        }

        addBackButton();
    }

    private ArrayList<PlagiarismInstance> createPlagiarismArray (SubmissionService submissionService, JSONObject response) {

        ArrayList<PlagiarismInstance> plagiarismArray = new ArrayList<>();
        JSONArray dataArray = (JSONArray) response.get("submissions");

        for (Object submissionResult: dataArray){

            JSONObject jsonObject = (JSONObject) submissionResult;
            Long id = (Long)  jsonObject.get("id");

            String plagiarismResult = (String) jsonObject.get("result1");
            Long numberOfMatches = (Long)  jsonObject.get("matches_local");

            Submission submission = submissionService.getSubmissionByCheckUploadId(targetAssignment, String.valueOf(id));
            if (submission != null) {
                User user = submission.getUser();
                plagiarismArray.add(new PlagiarismInstance(user.getUsername(), user.getStudentNumber(),
                        plagiarismResult, numberOfMatches));
            }
        }
        return plagiarismArray;
    }

    private void addBackButton(){

        VerticalLayout bottomRow = new VerticalLayout();

        bottomRow.setAlignItems(Alignment.END);

        Button goBackButton = new Button("Back");
        bottomRow.add(goBackButton);
        add(bottomRow);

        goBackButton.addClickListener(e ->

                goBackButton.getUI().ifPresent(ui -> ui.navigate(CourseView.class,
                        new RouteParameters("course-ID", String.valueOf(targetAssignment.getCourse().getId()))))
        );
    }
}
