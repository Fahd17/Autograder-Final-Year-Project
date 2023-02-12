package com.example.autogradertyp.backend;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.SubmissionService;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import static com.example.autogradertyp.backend.CSVGenerator.createResultsCSV;

/**
 * This class is a schedule which runs on every assignment deadline and
 * generate a CSV file of the assigment results.
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 02/02/2023
 */

public class DeadlineScheduler extends TimerTask {

    private final Assignment assignment;
    private final SubmissionService submissionService;

    private final AssignmentService assignmentService;

    /**
     * Creates a new deadline schedule instance for an assignment.
     *
     * @param assignment        The targeted assignment
     * @param submissionService To modify the submission table in the database
     */
    public DeadlineScheduler(Assignment assignment, SubmissionService submissionService, AssignmentService assignmentService) {

        this.assignment = assignment;
        this.submissionService = submissionService;
        this.assignmentService = assignmentService;
    }

    /**
     * A method which is run when it is the deadline of the assignment
     */
    @Override
    public void run() {

        try {
            createResultsCSV(submissionService, assignment, assignmentService);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
