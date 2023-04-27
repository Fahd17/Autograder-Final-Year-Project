package com.example.autogradertyp.views;


import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.service.AssignmentService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * A class that builds a UI for the choose assignment view
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 14/11/2022
 */

@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
@Route(value = "assignments", layout = MainLayout.class)
public class AssignmentMenu extends VerticalLayout {


    /**
     * Creates a view to the user with all the assignments and give a choice to
     * navigate to a specific assigment
     */
    public AssignmentMenu(AssignmentService assignmentService) {

        List<Assignment> assignments = assignmentService.getAllAssignments();
        add(new H1("Select assignment:"));
        
        FlexLayout assignmentsOptions = new FlexLayout();
        assignmentsOptions.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        assignmentsOptions.setJustifyContentMode(JustifyContentMode.BETWEEN);

        if (assignments != null) {

            if (assignments.size() == 0) {

                Label message = new Label("Not assignments available");
                add(message);

            } else {

                for (Assignment assignment : assignments) {

                    Button assignmentChoice = new Button(assignment.getAssignmentName());
                    assignmentChoice.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    assignmentChoice.addClickListener(e ->

                            assignmentChoice.getUI().ifPresent(ui -> ui.navigate(AssignmentView.class,
                                    new RouteParameters("assignment-ID", String.valueOf(assignment.getId())))));
                    assignmentChoice.setHeight(30, Unit.MM);
                    assignmentChoice.setWidth(60, Unit.MM);
                    assignmentChoice.setId(assignment.getAssignmentName());
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
