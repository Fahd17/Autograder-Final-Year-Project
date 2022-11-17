package com.example.autogradertyp.views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

@Route("main-menu")
public class MainMenu extends VerticalLayout {


    public MainMenu() {
        HorizontalLayout bannerLayout = new HorizontalLayout();
        H1 banner = new H1("Welcome to auto grade");
        bannerLayout.add(banner);

        Label chooseMessage = new Label("Please select from the following options:");

        HorizontalLayout optionsLayout = new HorizontalLayout();
        setAlignItems(Alignment.CENTER);


        Button createAssignmentButton = new Button("Create an assignment");
        createAssignmentButton.addClickListener(e ->

                createAssignmentButton.getUI().ifPresent(ui -> ui.navigate(CreateAssignmentView.class)));

        createAssignmentButton.setHeight(35, Unit.MM);
        createAssignmentButton.setWidth(70, Unit.MM);
        optionsLayout.add(createAssignmentButton);


        Button submitAssignmentButton = new Button("Submit an assignment");
        submitAssignmentButton.addClickListener(e ->

                submitAssignmentButton.getUI().ifPresent(ui -> ui.navigate(AssignmentMenu.class)));

        submitAssignmentButton.setHeight(35, Unit.MM);
        submitAssignmentButton.setWidth(70, Unit.MM);
        optionsLayout.add(submitAssignmentButton);

        add(bannerLayout, chooseMessage, optionsLayout);

    }
}

