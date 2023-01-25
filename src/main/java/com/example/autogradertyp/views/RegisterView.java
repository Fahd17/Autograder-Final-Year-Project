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

@Route("register")
@PageTitle("New user")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    @Autowired
    SecurityUserDetailsService userDetailsManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public RegisterView(SecurityUserDetailsService userDetailsManager, PasswordEncoder passwordEncoder){

        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(new H1("Register new user:"));

        TextField studentNumber = new TextField();
        studentNumber.setLabel("Entre student number:");
        add(studentNumber);

        TextField userName = new TextField();
        userName.setLabel("Entre user name:");
        add(userName);

        TextField email = new TextField();
        email.setLabel("Entre email:");
        add(email);

        TextField password = new TextField();
        password.setLabel("Entre password:");
        add(password);

        Button submit = new Button("Create");
        add(submit);


        submit.addClickListener(e -> {
            try {
                addUser(userName.getValue(), password.getValue(),
                        email.getValue(), studentNumber.getValue());
            }finally {
                submit.getUI().ifPresent(ui -> ui.navigate(LoginView.class));
            }
        });

    }

    public void addUser(String username, String password, String email, String studentNumber) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountNonLocked(true);
        user.setEmail(email);
        user.setRole("USER");
        user.setStudentNumber(studentNumber);
        userDetailsManager.createUser(user);
    }
}
