package com.example.autogradertyp.views;

import com.example.autogradertyp.backend.JavaGrader;
import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.SecurityUserDetailsService;
import com.example.autogradertyp.data.service.SubmissionService;
import com.example.autogradertyp.data.service.TestCaseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.PermitAll;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * A class that builds a UI for main menu
 *
 * @author Fahd Alsahali
 * @version 2.0
 * @date 14/11/2022
 * @since 01/02/2023
 */
@PermitAll
@Route("submit-assignment/:assignment-ID?")
public class SubmitAssignmentView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private TestCaseService testCaseService;
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SecurityUserDetailsService userService;
    private Assignment targetAssignment;
    private String submissionFileName;

    private byte[] submissionBytes;

    /**
     * Creates the UI components
     */
    public SubmitAssignmentView() {

        fileUploader();
        submitButton();
        goBackButton();

    }

    private void fileUploader() {

        add(new H1("Upload submission:"));

        MemoryBuffer memoryBuffer = new MemoryBuffer();

        Upload upload = new Upload(memoryBuffer);
        upload.addFinishedListener(e -> {
            submissionBytes = SaveUploadedFile(memoryBuffer, e.getFileName());
            this.submissionFileName = e.getFileName().replace(".java", "");

        });

        add(upload);
    }

    private void submitButton() {

        Button submitButton = new Button("Submit");
        add(submitButton);
        submitButton.addClickListener(e -> {
            try {
                gradeSubmission();
                submitButton.getUI().ifPresent(ui -> ui.navigate(AssignmentView.class, new RouteParameters("assignment-ID", String.valueOf(targetAssignment.getId()))));

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void goBackButton() {
        VerticalLayout bottomRow = new VerticalLayout();

        bottomRow.setAlignItems(Alignment.END);

        Button goBackButton = new Button("Back");
        bottomRow.add(goBackButton);
        add(bottomRow);

        goBackButton.addClickListener(e ->

                goBackButton.getUI().ifPresent(ui -> ui.navigate(AssignmentView.class, new RouteParameters("assignment-ID", String.valueOf(targetAssignment.getId())))));
    }


    /**
     * Gets the URL parameter which is the assignment id
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        Optional<String> assignmentID = beforeEnterEvent.getRouteParameters().get("assignment-ID");

        this.targetAssignment = assignmentService.getAssigmentById(Long.valueOf(assignmentID.get()));


    }

    private void gradeSubmission() throws IOException {

        JavaGrader javaGrader = new JavaGrader();
        ArrayList<TestCase> testCases = testCaseService.getTestCasesForAssignment(targetAssignment);
        int totalMarks = 0;
        int marksAcquired = 0;

        for (int i = 0; i < testCases.size(); i++) {

            totalMarks = totalMarks + testCases.get(i).getMarks();

            boolean result = javaGrader.gradeProgram(submissionFileName, testCases.get(i).getTestCaseInput(), testCases.get(i).getExpectedOutput());

            if (result) {
                marksAcquired = marksAcquired + testCases.get(i).getMarks();
            }

        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(authentication.getName());

        Submission submission = new Submission(marksAcquired, totalMarks, LocalDateTime.now(), submissionBytes, submissionService.nextAttemptNumber(targetAssignment, user), targetAssignment, user);
        submissionService.add(submission);

    }

    private byte[] SaveUploadedFile(MemoryBuffer memoryBuffer, String name) {

        InputStream inputStream = memoryBuffer.getInputStream();
        ByteArrayOutputStream ous = null;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(".\\submissions_directory\\" + name);
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            ous = new ByteArrayOutputStream();

            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                ous.write(dataBuffer, 0, bytesRead);
            }
            fileOutputStream.close();
            ous.close();

        } catch (IOException IO) {
            System.out.print("Invalid Path");

        }

        return ous.toByteArray();
    }
}
