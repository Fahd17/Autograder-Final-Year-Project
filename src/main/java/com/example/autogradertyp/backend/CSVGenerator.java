package com.example.autogradertyp.backend;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.CourseService;
import com.example.autogradertyp.data.service.SubmissionService;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CSVGenerator {


    /**
     * This method gets the final submissions of the target assigment and create a CSV file of the results
     *
     * @throws IOException
     */
    public static void createResultsCSV(SubmissionService submissionService, Assignment assignment, AssignmentService assignmentService) throws IOException {

        List<Submission> submissions = submissionService.getFinalSubmissions(assignment);

        List<String[]> csvData = new ArrayList<>();
        String[] header = {"Student id", "Mark", "Marks available", "email"};
        csvData.add(header);

        for (int i = 0; i < submissions.size(); i++) {
            csvData.add(submissions.get(i).ToStringCSV());
        }

        CSVWriter writer = new CSVWriter(new FileWriter(".\\ResultsCSV\\" + assignment.getId() + ".csv"));
        writer.writeAll(csvData);
        writer.close();


        //saving the file in the database as bytes[]
        File file = new File(".\\ResultsCSV\\" + assignment.getId() + ".csv");

        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] bytes = new byte[(int)file.length()];
        fileInputStream.read(bytes);
        fileInputStream.close();

        assignment.setAssignmentResultsCSV(bytes);
        assignmentService.updateAssignment(assignment);

    }
}
