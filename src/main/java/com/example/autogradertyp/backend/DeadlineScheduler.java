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

public class DeadlineScheduler extends TimerTask {

    private final Assignment assignment;
    SubmissionService submissionService;

    public DeadlineScheduler(Assignment assignment, SubmissionService submissionService) {

        this.assignment = assignment;
        this.submissionService = submissionService;
    }

    @Override
    public void run() {

        try {
            createResultsCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createResultsCSV() throws IOException {

        List<Submission> submissions = submissionService.getFinalSubmissions(assignment);

        List<String[]> csvData = new ArrayList<>();
        String[] header = {"Student id", "Mark", "Marks available", "email"};
        csvData.add(header);

        for (int i = 0; i < submissions.size(); i++) {
            csvData.add(submissions.get(i).ToStringCSV());
        }

        CSVWriter writer = new CSVWriter(new FileWriter(".\\ResultsCSV\\" + assignment.getAssignmentName()
                + assignment.getCourseID() + ".csv"));
        writer.writeAll(csvData);
        writer.close();

    }
}
