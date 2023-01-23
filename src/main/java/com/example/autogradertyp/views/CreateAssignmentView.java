package com.example.autogradertyp.views;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.TestCaseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@PermitAll
@Route("create-assignment")
public class CreateAssignmentView extends VerticalLayout {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private TestCaseService testCaseService;
    public CreateAssignmentView() {

        TextField assignmentName = new TextField();
        assignmentName.setLabel("Entre assignment name:");
        add(assignmentName);

        TextField assignmentID = new TextField();
        assignmentID.setLabel("Entre assignment ID:");
        add(assignmentID);

        TextField courseID = new TextField();
        courseID.setLabel("Entre course ID:");
        add(courseID);

        Label testCaseMessage = new Label("Entre test case information:");
        add(testCaseMessage);
        HorizontalLayout testCaseLayout = new HorizontalLayout();

        TextField testCaseInput = new TextField();
        testCaseInput.setLabel("Input:");
        testCaseLayout.add(testCaseInput);

        TextField testCaseExpectedOutput = new TextField();
        testCaseExpectedOutput.setLabel("Expected output:");
        testCaseLayout.add(testCaseExpectedOutput);
        add(testCaseLayout);


        Button submit = new Button("Create");
        add(submit);


        submit.addClickListener(e -> {

                TestCase testCase = new TestCase(testCaseInput.getValue(), testCaseExpectedOutput.getValue());
                testCaseService.add(testCase);
                Assignment assignment = new Assignment(assignmentName.getValue(), assignmentID.getValue(), courseID.getValue(), testCase);
                assignmentService.saveAssigment(assignment);

        });

        VerticalLayout bottomRow = new VerticalLayout();

        bottomRow.setAlignItems(Alignment.END);

        Button goBackButton = new Button("Back");
        bottomRow.add(goBackButton);
        add(bottomRow);

        goBackButton.addClickListener(e ->

                goBackButton.getUI().ifPresent(ui -> ui.navigate(MainMenu.class)));
    }

}
