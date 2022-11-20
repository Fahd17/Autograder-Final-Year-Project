package com.example.autogradertyp.views;

import com.example.autogradertyp.backend.Assignment;
import com.example.autogradertyp.backend.JavaGrader;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

@Route("submit-assignment/:assignment-ID?")
public class SubmitAssignmentView extends VerticalLayout implements BeforeEnterObserver {

    Assignment targetAssignment;
    ArrayList<Assignment> assignments;
    String submissionFileName;

    public SubmitAssignmentView() {

        MemoryBuffer memoryBuffer = new MemoryBuffer();

        Upload upload = new Upload(memoryBuffer);
        upload.addFinishedListener(e -> {
            SaveUploadedFile(memoryBuffer, e.getFileName());
            this.submissionFileName = e.getFileName().replace(".java", "");

        });

        add(upload);

        Button submitButton = new Button("Submit");
        add(submitButton);
        submitButton.addClickListener(e -> {
            try {
                gradeSubmission();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VerticalLayout bottomRow = new VerticalLayout();

        bottomRow.setAlignItems(Alignment.END);

        Button goBackButton = new Button("Back");
        bottomRow.add(goBackButton);
        add(bottomRow);

        goBackButton.addClickListener(e ->

                goBackButton.getUI().ifPresent(ui -> ui.navigate(MainMenu.class)));

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        assignments = AssignmentMenu.assignments;

        Optional<String> assignmentID = beforeEnterEvent.getRouteParameters().get("assignment-ID");

        for (int i = 0; i < assignments.size(); i++) {

            if (assignments.get(i).getAssignmentID().equalsIgnoreCase(assignmentID.get())) {

                this.targetAssignment = assignments.get(i);
            }
        }
    }

    private void gradeSubmission() throws IOException {

        JavaGrader javaGrader = new JavaGrader();
        boolean result = javaGrader.gradeProgram(submissionFileName, targetAssignment.getTestCase().getTestCaseInput(),
                targetAssignment.getTestCase().getExpectedOutput());

        Label resultMessage = new Label(result + "");
        add(resultMessage);

    }

    private void SaveUploadedFile(MemoryBuffer memoryBuffer, String name) {
        InputStream inputStream = memoryBuffer.getInputStream();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(".\\submissions_directory\\"+ name);
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            fileOutputStream.close();

        } catch (IOException IO) {
            System.out.print("Invalid Path");

        }
    }
}
