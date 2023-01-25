package com.example.autogradertyp.views;

import com.example.autogradertyp.security.SecurityService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.PermitAll;

@PermitAll
@Route("main-menu")
public class MainMenu extends VerticalLayout {

    private final SecurityService securityService;

    public MainMenu(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createBody();

    }
    private void createHeader() {
        Button logout = new Button("Log out", e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(logout);
        add(header);

    }
    private void createBody() {
        HorizontalLayout bannerLayout = new HorizontalLayout();
        H1 banner = new H1("Welcome to auto grade");
        bannerLayout.add(banner);

        Label chooseMessage = new Label("Please select from the following options:");

        HorizontalLayout optionsLayout = new HorizontalLayout();
        setAlignItems(FlexComponent.Alignment.CENTER);


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

