package com.example.autogradertyp.views;

import com.example.autogradertyp.backend.DeadlineScheduler;
import com.example.autogradertyp.backend.PlagiarismDetectorConnection;
import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Course;
import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.service.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.frontend.installer.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import org.json.simple.JSONObject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.autogradertyp.backend.TestCasesReader.readTestCases;

/**
 * A class that builds a UI for creating a new assignment
 *
 * @author Fahd Alsahali
 * @version 2.0
 * @date 13/11/2022
 * @since 28/02/2023
 */

@RolesAllowed({"ROLE_ADMIN"})
@Route(value = "create-assignment", layout = MainLayout.class)
public class CreateAssignmentView extends VerticalLayout {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private SecurityUserDetailsService userService;

    @Autowired
    private SubmissionService submissionService;

    private ArrayList<TextField> testCasesValues;
    private ArrayList<HorizontalLayout> testCasesLayout;
    private VerticalLayout testCaseSection;

    private Select<Course> select;

    /**
     * Creates a create new assigment view
     */
    public CreateAssignmentView(CourseService courseService) {

        TextField assignmentName = new TextField();
        assignmentName.setLabel("Entre assignment name:");
        assignmentName.setId("AssignmentName");
        add(assignmentName);

        createCoursesSelectMenu(courseService);

        DateTimePicker deadline = new DateTimePicker("Assignment deadline:");
        deadline.setId("DeadlinePicker");
        add(deadline);

        Label testCaseMessage = new Label("Entre test case information:");
        add(testCaseMessage);

        testCaseSection = new VerticalLayout();
        HorizontalLayout testCaseLayout = new HorizontalLayout();

        TextField testCaseInput = new TextField();
        testCaseInput.setLabel("Input:");
        testCaseInput.setId("testCaseInput");
        testCaseLayout.add(testCaseInput);

        TextField testCaseExpectedOutput = new TextField();
        testCaseExpectedOutput.setLabel("Expected output:");
        testCaseExpectedOutput.setId("testCaseExpectedOutput");
        testCaseLayout.add(testCaseExpectedOutput);

        TextField numberOfMarks = new TextField();
        numberOfMarks.setLabel("Marks:");
        numberOfMarks.setId("numberOfMarks");
        testCaseLayout.add(numberOfMarks);

        add(new H1("To test cases from a file."));
        fileUploader();

        testCaseSection.add(testCaseLayout);
        add(testCaseSection);

        testCasesValues = new ArrayList<>();
        testCasesLayout = new ArrayList<>();


        Button addTestCase = new Button("Add test case:");
        add(addTestCase);

        addTestCase.addClickListener(e -> {

            testCasesLayout.add(new HorizontalLayout());
            testCasesValues.add(new TextField());
            int index = testCasesValues.size() - 1;
            testCasesValues.get(index).setLabel("Input:");
            testCasesLayout.get(testCasesLayout.size() - 1).add(testCasesValues.get(index));

            testCasesValues.add(new TextField());
            index = testCasesValues.size() - 1;
            testCasesValues.get(index).setLabel("Expected output:");
            testCasesLayout.get(testCasesLayout.size() - 1).add(testCasesValues.get(index));

            testCasesValues.add(new TextField());
            index = testCasesValues.size() - 1;
            testCasesValues.get(index).setLabel("Marks:");
            testCasesLayout.get(testCasesLayout.size() - 1).add(testCasesValues.get(index));

            testCaseSection.add(testCasesLayout.get(testCasesLayout.size() - 1));

        });

        Button submit = new Button("Create");
        submit.setId("CreateAssignment");
        add(submit);

        submit.addClickListener(e -> {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User author = userService.loadUserByUsername(authentication.getName());

            Assignment assignment = new Assignment(assignmentName.getValue(), select.getValue(), author, deadline.getValue());
            assignmentService.add(assignment);

            TestCase testCase = new TestCase(testCaseInput.getValue(), testCaseExpectedOutput.getValue(), Integer.parseInt(numberOfMarks.getValue()), assignment);
            testCaseService.add(testCase);

            for (int i = 0; i < testCasesValues.size(); i = i + 3) {

                testCaseService.add(new TestCase(testCasesValues.get(i).getValue(), testCasesValues.get(i + 1).getValue(), Integer.parseInt(testCasesValues.get(i + 2).getValue()), assignment));
            }
            startCountDown(assignment);


            JSONObject respose = null;
            try {
                respose = PlagiarismDetectorConnection.crateCheck("Assignment" + assignment.getId());
                String checkId = respose.get("id")+ "";
                assignment.setPlagiarismCheckId(checkId);
                assignmentService.updateAssignment(assignment);

            } catch (Exception ex) {
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

    private void createCoursesSelectMenu(CourseService courseService) {

        select = new Select<>();
        select.setLabel("Course");
        select.setId("SelectCourse");

        select.setItemLabelGenerator(Course::getName);

        List<Course> courses = courseService.getAllCourses();
        select.setItems(courses);
        add(select);

    }

    /**
     * A method that starts a timer to the deadline of the assignment
     *
     * @param assignment The assignment which the timer for its deadline will start
     */
    private void startCountDown(Assignment assignment) {

        Long remainingTime = LocalDateTime.now().until(assignment.getDeadline(), ChronoUnit.SECONDS);

        Timer timer = new Timer();
        TimerTask task = new DeadlineScheduler(assignment, submissionService, assignmentService);


        timer.schedule(task, remainingTime * 1000);
    }

    /**
     * Adds a new testcase to the view
     *
     * @param input  The input of teh testcase
     * @param output The output of the testcase
     * @param mark   The mark rewarded for that testcase
     */
    public void addTestCase(String input, String output, String mark) {

        testCasesLayout.add(new HorizontalLayout());
        testCasesValues.add(new TextField());
        int index = testCasesValues.size() - 1;
        testCasesValues.get(index).setLabel("Input:");
        testCasesValues.get(index).setValue(input);
        testCasesLayout.get(testCasesLayout.size() - 1).add(testCasesValues.get(index));

        testCasesValues.add(new TextField());
        index = testCasesValues.size() - 1;
        testCasesValues.get(index).setLabel("Expected output:");
        testCasesValues.get(index).setValue(output);
        testCasesLayout.get(testCasesLayout.size() - 1).add(testCasesValues.get(index));

        testCasesValues.add(new TextField());
        index = testCasesValues.size() - 1;
        testCasesValues.get(index).setLabel("Marks:");
        testCasesLayout.get(testCasesLayout.size() - 1).add(testCasesValues.get(index));
        testCasesValues.get(index).setValue(mark);
        testCaseSection.add(testCasesLayout.get(testCasesLayout.size() - 1));

    }

    private void fileUploader() {

        add(new H1("Upload testcases:"));

        MemoryBuffer memoryBuffer = new MemoryBuffer();

        Upload upload = new Upload(memoryBuffer);
        upload.setId("TestCaseFileUpload");
        upload.addFinishedListener(e -> {
            SaveUploadedFile(memoryBuffer, e.getFileName());
            String submissionFileName = e.getFileName().replace(".json", "");
            try {
                readTestCases(submissionFileName, this);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }


        });

        add(upload);
    }

    private void SaveUploadedFile(MemoryBuffer memoryBuffer, String name) {

        InputStream inputStream = memoryBuffer.getInputStream();


        try {
            FileOutputStream fileOutputStream = new FileOutputStream(".\\submissions_directory\\" + name);
            byte[] dataBuffer = new byte[1024];
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
