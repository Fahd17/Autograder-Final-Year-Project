package com.example.autogradertyp.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("create-assignment")
public class CreateAssignmentView extends VerticalLayout {

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


        Button submit = new Button("Submit");
        add(submit);

        AssignmentMenu assignmentMenu = new AssignmentMenu();

        submit.addClickListener(e -> {

            assignmentMenu.createAssignment(assignmentName.getValue(), assignmentID.getValue(), courseID.getValue(),
                    testCaseInput.getValue(), testCaseExpectedOutput.getValue());

        });

    }

}
