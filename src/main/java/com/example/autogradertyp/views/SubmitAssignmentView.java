package com.example.autogradertyp.views;

import com.example.autogradertyp.backend.Assignment;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Optional;

@Route("submit-assignment/:assignment-ID?")
public class SubmitAssignmentView extends VerticalLayout implements BeforeEnterObserver {

    Assignment targetAssignment;
    ArrayList<Assignment> assignments;

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        assignments = AssignmentMenu.assignments;

        Optional<String> assignmentID = beforeEnterEvent.getRouteParameters().get("assignment-ID");

                    for (int i = 0; i < assignments.size(); i++) {

                        if (assignments.get(i).getAssignmentID().equalsIgnoreCase(assignmentID.get())) {

                            this.targetAssignment = assignments.get(i);
                        }
                    }

                    System.out.println(targetAssignment.getAssignmentName());

    }
}
