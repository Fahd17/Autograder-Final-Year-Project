package com.example.autogradertyp.views;

import com.example.autogradertyp.backend.CanvasIntegrator;
import com.example.autogradertyp.backend.PlagiarismDetectorConnection;
import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Course;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.CourseService;
import com.example.autogradertyp.data.service.SubmissionService;
import com.github.appreciated.card.Card;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.server.StreamResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Optional;


@RolesAllowed({"ROLE_ADMIN"})
@Route(value = "course/:course-ID?", layout = MainLayout.class)
public class CourseView extends VerticalLayout implements BeforeEnterObserver {


    private Course targetCourse;

    @Autowired
    private AssignmentService assignmentService;


    @Autowired
    private CourseService courseService;

    @Autowired
    private SubmissionService submissionService;

    public CourseView(AssignmentService assignmentService) {


    }

    private void showAssignmentsCards() {
        ArrayList<Assignment> assignments = assignmentService.getAssignmentsForCourse(targetCourse);

        HorizontalLayout cardsLayout = new HorizontalLayout();


        for (Assignment assignment : assignments) {

            VerticalLayout assignmentView = new VerticalLayout();
            assignmentView.setWidth(70, Unit.MM);
            Card assignmentCard = new Card(new TitleLabel(assignment.getAssignmentName()).withWhiteSpaceNoWrap(),
                    new SecondaryLabel("Number of total submissions: " + submissionService.getSubmissionsForAssignment(assignment).size()),
                    new Actions(new ActionButton("Publish grades on Canvas", event -> {
                CanvasIntegrator.uploadStudentsGrade(submissionService.getFinalSubmissions(assignment));
            }), new ActionButton("Generate plagiarism report", event -> {
                try {

                    uploadFiles(submissionService.getFinalSubmissions(assignment));
                    PlagiarismDetectorConnection.startCheck(assignment.getPlagiarismCheckId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }), new ActionButton("Show plagiarism report", event -> {

                cardsLayout.getUI().ifPresent(ui -> ui.navigate(PlagiarismReportView.class, new RouteParameters("assignment-ID",
                        String.valueOf(assignment.getId()))));

            })));

            assignmentCard.setWidth(70, Unit.MM);
            Anchor anchor = new Anchor(getStreamResource(assignment.getAssignmentName() + ".csv",
                    assignment.getAssignmentResultsCSV()), "Download performance report");
            anchor.getElement().setAttribute("download", true);

            assignmentView.add(assignmentCard, anchor);

            cardsLayout.add(assignmentView);

        }

        add(cardsLayout);

    }

    private StreamResource getStreamResource(String filename, byte[] content) {
        return new StreamResource(filename, () -> new ByteArrayInputStream(content));
    }

    /**
     * A method to upload group of submissions to the plagiarism check
     *
     * @param submissions The submissions to  eb uploaded
     */
    private void uploadFiles(ArrayList<Submission> submissions) {

        for (Submission submission : submissions) {

            try {
                JSONObject respose = PlagiarismDetectorConnection.uploadFile(submission.getId() + "file",
                        submission.getData(), submission.getAssignment().getPlagiarismCheckId());

                JSONArray dataArray = (JSONArray) respose.get("data");

                JSONObject dataObject = (JSONObject) dataArray.get(0);

                System.out.println(respose.toJSONString());
                String checkUploadId = dataObject.get("id") + "";
                submission.setCheckUploadId(checkUploadId);
                submissionService.updateSubmission(submission);

            } catch (Exception e) {

            }
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        Optional<String> courseID = beforeEnterEvent.getRouteParameters().get("course-ID");

        this.targetCourse = courseService.getCourseById(Long.valueOf(courseID.get()));

        showAssignmentsCards();
        addBackButton();

    }

    private void addBackButton(){

        VerticalLayout bottomRow = new VerticalLayout();

        bottomRow.setAlignItems(FlexComponent.Alignment.END);

        Button goBackButton = new Button("Back");
        bottomRow.add(goBackButton);
        add(bottomRow);

        goBackButton.addClickListener(e ->

                goBackButton.getUI().ifPresent(ui -> ui.navigate(CourseMenu.class))
        );
    }

}
