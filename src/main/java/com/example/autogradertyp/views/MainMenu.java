package com.example.autogradertyp.views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

@Route("main-menu")
public class MainMenu extends VerticalLayout {


    public MainMenu() {

        HorizontalLayout horizontalLayout = new HorizontalLayout();


        Button createAssignmentButton = new Button("Create an assignment");
        createAssignmentButton.addClickListener(e ->

                createAssignmentButton.getUI().ifPresent(ui -> ui.navigate(CreateAssignmentView.class)));

        createAssignmentButton.setHeight(35, Unit.MM);
        createAssignmentButton.setWidth(70, Unit.MM);
        horizontalLayout.add(createAssignmentButton);


        Button submitAssignmentButton = new Button("Submit an assignment");
        submitAssignmentButton.addClickListener(e ->

                submitAssignmentButton.getUI().ifPresent(ui -> ui.navigate(AssignmentMenu.class)));

        submitAssignmentButton.setHeight(35, Unit.MM);
        submitAssignmentButton.setWidth(70, Unit.MM);
        horizontalLayout.add(submitAssignmentButton);

        add(horizontalLayout);

    }
}

