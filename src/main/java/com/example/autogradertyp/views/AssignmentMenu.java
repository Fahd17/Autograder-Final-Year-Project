package com.example.autogradertyp.views;


import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.TestCase;
import com.example.autogradertyp.data.service.AssignmentService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RolesAllowed({"ROLE_USER"})
@Route("assignment-menu")
public class AssignmentMenu extends VerticalLayout {

    public AssignmentMenu(AssignmentService assignmentService) {

            List<Assignment> assignments = assignmentService.getAllAssignments();
            add(new H1("Select assignment:"));
            HorizontalLayout assignmentsOptions = new HorizontalLayout();

            if (assignments != null) {

                if (assignments.size() == 0) {

                    Label message = new Label("Not assignments available");
                    add(message);

                } else {

                    for (Assignment assignment : assignments) {

                        Button assignmentChoice = new Button(assignment.getAssignmentName());
                        assignmentChoice.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                        assignmentChoice.addClickListener(e ->

                                assignmentChoice.getUI().ifPresent(ui -> ui.navigate(SubmitAssignmentView.class,
                                        new RouteParameters("assignment-ID", assignment.getAssignmentID()))));

                        assignmentChoice.setHeight(30, Unit.MM);
                        assignmentChoice.setWidth(60, Unit.MM);
                        assignmentsOptions.add(assignmentChoice);

                    }
                    add(assignmentsOptions);
                }

            }

            VerticalLayout bottomRow = new VerticalLayout();

            bottomRow.setAlignItems(Alignment.END);

            Button goBackButton = new Button("Back");
            bottomRow.add(goBackButton);
            add(bottomRow);

            goBackButton.addClickListener(e ->

                    goBackButton.getUI().ifPresent(ui -> ui.navigate(MainMenu.class)));

    }



}
