package com.example.autogradertyp.views;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.TestCaseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

@RolesAllowed({"ROLE_ADMIN"})
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

        TextField courseID = new TextField();
        courseID.setLabel("Entre course ID:");
        add(courseID);

        Label testCaseMessage = new Label("Entre test case information:");
        add(testCaseMessage);
        VerticalLayout testCaseSection =  new VerticalLayout();
        HorizontalLayout testCaseLayout = new HorizontalLayout();

        TextField testCaseInput = new TextField();
        testCaseInput.setLabel("Input:");
        testCaseLayout.add(testCaseInput);

        TextField testCaseExpectedOutput = new TextField();
        testCaseExpectedOutput.setLabel("Expected output:");
        testCaseLayout.add(testCaseExpectedOutput);

        TextField numberOfMarks = new TextField();
        numberOfMarks.setLabel("Marks:");
        testCaseLayout.add(numberOfMarks);


        testCaseSection.add(testCaseLayout);
        add(testCaseSection);

        ArrayList<TextField> testCasesValues = new ArrayList<>();
        ArrayList<HorizontalLayout> testCasesLayout = new ArrayList<>();

        Button addTestCase =  new Button("Add test case:");
        add(addTestCase);

        addTestCase.addClickListener(e -> {

            testCasesLayout.add(new HorizontalLayout());
            testCasesValues.add(new TextField());
            int index = testCasesValues.size()-1;
            testCasesValues.get(index).setLabel("Input:");
            testCasesLayout.get(testCasesLayout.size()-1).add(testCasesValues.get(index));

            testCasesValues.add(new TextField());
            index = testCasesValues.size()-1;
            testCasesValues.get(index).setLabel("Expected output:");
            testCasesLayout.get(testCasesLayout.size()-1).add(testCasesValues.get(index));

            testCasesValues.add(new TextField());
            index = testCasesValues.size()-1;
            testCasesValues.get(index).setLabel("Marks:");
            testCasesLayout.get(testCasesLayout.size()-1).add(testCasesValues.get(index));

            testCaseSection.add(testCasesLayout.get(testCasesLayout.size()-1));

        });

        Button submit = new Button("Create");
        add(submit);

        submit.addClickListener(e -> {

            Assignment assignment = new Assignment(assignmentName.getValue(), courseID.getValue());
            assignmentService.add(assignment);

            TestCase testCase = new TestCase(testCaseInput.getValue(), testCaseExpectedOutput.getValue(),
                    Integer.parseInt(numberOfMarks.getValue()), assignment);testCaseService.add(testCase);

            for (int i = 0; i < testCasesValues.size(); i = i + 3) {

                testCaseService.add(new TestCase(testCasesValues.get(i).getValue(), testCasesValues.get(i+1).getValue(),
                        Integer.parseInt(testCasesValues.get(i+2).getValue()), assignment));
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

}
