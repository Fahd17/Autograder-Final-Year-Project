package com.example.autogradertyp.views;

import com.example.autogradertyp.backend.Assignment;
import com.example.autogradertyp.backend.TestCase;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;

@Route("assignment-menu")
public class AssignmentMenu extends VerticalLayout {

    static ArrayList<Assignment> assignments = new ArrayList<>();

    public AssignmentMenu() {

        if (assignments != null) {

            if (assignments.size() == 0) {

                Label message = new Label("Not assignments available");
                add(message);

            } else {

                for (int i = 0; i < assignments.size(); i++) {

                    Label x = new Label(assignments.get(i).getAssignmentName());
                    add(x);
                }
            }
        }
    }

    public void createAssignment(String assignmentName, String assignmentID, String courseID, String testCaseInput, String expectedOutput) {

        TestCase testCase = new TestCase(testCaseInput, expectedOutput);
        Assignment assignment = new Assignment(assignmentName, assignmentID, courseID, testCase);

        assignments.add(assignment);

    }

}
