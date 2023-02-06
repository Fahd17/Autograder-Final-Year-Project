package com.example.autogradertyp.backend;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.service.SubmissionService;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

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

    /**
     * Creates a new deadline schedule instance for an assignment.
     *
     * @param assignment        The targeted assignment
     * @param submissionService To modify the submission table in the database
     */
    public DeadlineScheduler(Assignment assignment, SubmissionService submissionService) {

        this.assignment = assignment;
        this.submissionService = submissionService;
    }

    /**
     * A method which is run when it is the deadline of the assignment
     */
    @Override
    public void run() {

        try {
            createResultsCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method gets the final submissions of the target assigment and create a CSV file of the results
     *
     * @throws IOException
     */
    public void createResultsCSV() throws IOException {

        List<Submission> submissions = submissionService.getFinalSubmissions(assignment);

        List<String[]> csvData = new ArrayList<>();
        String[] header = {"Student id", "Mark", "Marks available", "email"};
        csvData.add(header);

        for (int i = 0; i < submissions.size(); i++) {
            csvData.add(submissions.get(i).ToStringCSV());
        }

        CSVWriter writer = new CSVWriter(new FileWriter(".\\ResultsCSV\\" + assignment.getAssignmentName() + assignment.getCourseID() + ".csv"));
        writer.writeAll(csvData);
        writer.close();

    }
}
