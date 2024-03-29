package com.example.autogradertyp.views;

import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.service.SecurityUserDetailsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * A class that builds a UI for the choose assignment view
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 25/01/2023
 */

@Route("register")
@PageTitle("New user")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    @Autowired
    SecurityUserDetailsService userDetailsManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Creates register new user view
     */
    public RegisterView() {

        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(new H1("Register new user:"));

        TextField studentNumber = new TextField();
        studentNumber.setLabel("Entre student number:");
        studentNumber.setId("studentNumber");
        add(studentNumber);

        TextField userName = new TextField();
        userName.setLabel("Entre user name:");
        userName.setId("userName");
        add(userName);

        TextField email = new TextField();
        email.setLabel("Entre email:");
        email.setId("email");
        add(email);

        TextField password = new TextField();
        password.setLabel("Entre password:");
        password.setId("password");
        add(password);

        Button create = new Button("Create");
        create.setId("createUserButton");
        add(create);


        create.addClickListener(e -> {
            try {
                addUser(userName.getValue(), password.getValue(), email.getValue(), studentNumber.getValue());
            } finally {
                create.getUI().ifPresent(ui -> ui.navigate(LoginView.class));
            }
        });

    }

    /**
     * A method that adds new user to the database
     *
     * @param username      The username of the user
     * @param password      Unencrypted password of the user
     * @param email         Email of the user
     * @param studentNumber The university of the user
     */
    public void addUser(String username, String password, String email, String studentNumber) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountNonLocked(true);
        user.setEmail(email);
        user.setRole("USER");
        user.setStudentNumber(studentNumber);
        userDetailsManager.add(user);
    }
}
