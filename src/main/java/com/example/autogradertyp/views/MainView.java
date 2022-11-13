package com.example.autogradertyp.views;

import com.example.autogradertyp.backend.Assignment;
import com.example.autogradertyp.backend.FileManger;
import com.example.autogradertyp.backend.JavaGrader;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.util.ArrayList;

@Route("home")
public class MainView extends VerticalLayout {

    static  ArrayList<Assignment> assignments = new ArrayList<>();
    public MainView() {

        JavaGrader x23 = new JavaGrader();


        ArrayList<Component> cars = new ArrayList<Component>();
        
        H1 x = new H1();
        H1 x1 = new H1("AutoGrader211");
        cars.add(x);
        

        FileManger fileManger = new FileManger();

        MemoryBuffer memoryBuffer = new MemoryBuffer();

        Upload upload = new Upload(memoryBuffer);
        upload.addFinishedListener(e -> {

e.getFileName();
            fileManger.SaveUploadedFille(memoryBuffer);
            fileManger.createAFile();

        });

        add(x, upload);
        

        Button b = new Button("click me");
        
        add(b);
        b.addClickListener(e -> {
            try {
                Assignment xx = AssignmentMenu.assignments.get(0);
                System.out.println(x23.gradeProgram("p", xx.getTestCase().getTestCaseInput(),xx.getTestCase().getExpectedOutput()));

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
