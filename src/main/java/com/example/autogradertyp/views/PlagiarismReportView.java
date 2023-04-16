package com.example.autogradertyp.views;


import com.example.autogradertyp.backend.PlagiarismDetectorConnection;
import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.SubmissionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.Optional;

@PermitAll
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

        add(new Button("",event -> {showPlagiarismResults();}));

    }

    private void showPlagiarismResults() {

        JSONObject response = null;
        try {
            response = PlagiarismDetectorConnection.getResultsOverview(targetAssignment.getPlagiarismCheckId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.toJSONString());

        createPlagiarismArray(submissionService, response);

        System.out.println(response.toJSONString());

        add(new H1("Past attempts:"));

        Grid<Submission> historyGrid = new Grid<>(Submission.class, false);
        historyGrid.addColumn(Submission::getAttemptNumber).setHeader("User name");
        historyGrid.addColumn(Submission::getMarks).setHeader("Student number");
        historyGrid.addColumn(Submission::getTotalMarks).setHeader("Plagiarism percentage");
        historyGrid.addColumn(Submission::getTotalMarks).setHeader("Number of matches");
        historyGrid.addColumn(Submission::getSubmissionDataTimeFormatted).setHeader("Submission time");


        //historyGrid.add
        historyGrid.setAllRowsVisible(true);
        add(historyGrid);
    }

    private ArrayList<String []> createPlagiarismArray (SubmissionService submissionService, JSONObject response) {

        ArrayList<String []> plagiarismArray = new ArrayList<>();
        JSONArray dataArray = (JSONArray) response.get("submissions");

        for (Object submissionResult: dataArray){

            JSONObject jsonObject = (JSONObject) submissionResult;
            Long x = (Long)  jsonObject.get("id");
            Submission submission = submissionService.getSubmissionByCheckUploadId(targetAssignment, String.valueOf(x));
            System.out.println(submission.getMarks());
        }
        return null;
    }
}
