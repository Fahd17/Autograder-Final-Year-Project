package com.example.autogradertyp.views;

import com.example.autogradertyp.data.entity.Assignment;
import com.example.autogradertyp.data.entity.Course;
import com.example.autogradertyp.data.entity.Submission;
import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.service.AssignmentService;
import com.example.autogradertyp.data.service.CourseService;
import com.example.autogradertyp.data.service.SubmissionService;
import com.github.appreciated.card.Card;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RolesAllowed({"ROLE_ADMIN"})
@Route("course/:course-ID?")
public class CourseView extends AppLayout implements BeforeEnterObserver {


    private Course targetCourse;

    @Autowired
    private AssignmentService assignmentService;


    @Autowired
    private CourseService courseService;


    public CourseView(SubmissionService x) {

        HorizontalLayout fahd = new HorizontalLayout();
        List<Submission> y = x.getAllSubmissions();


        Button filenameTextField = new Button();
        Card card = new Card(
                // if you don't want the title to wrap you can set the whitespace = nowrap
                new TitleLabel("Example Title").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text"),
                new SecondaryLabel("Some secondary text"),
                new Actions(
                        new ActionButton("Action 1", event -> {System.out.println("xxx");})
                )
                );
        Anchor anchor = new Anchor(getStreamResource("default.txt", "default content".getBytes()), "click me to download");
        anchor.getElement().setAttribute("download", true);
fahd.add(card);

        filenameTextField.addClickListener(e -> {
            anchor.setHref(new StreamResource("default.txt",
                    () -> new ByteArrayInputStream(y.get(1).getData())));
        });

        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("MyApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tab analytics = new Tab("Analytics");
        Tab analyticsq = new Tab("Analytics");
        Tab analytics1 = new Tab("Analytics1");

        VerticalLayout f = new VerticalLayout();
               Button x1 = new Button("hhhhhh");
               x1.setWidth(240, Unit.PIXELS);
        Button x2 = new Button("hhhhhh");Button x3 = new Button("hhhhhh");

               x1.addClickListener(e-> no());
Tabs tabs = new Tabs(analytics, analyticsq, analytics1);

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.setHeight("240px");
        tabs.setWidth("240px");
        f.add(x1, x2, x3);
        addToDrawer( f);
        addToNavbar(toggle, title);

        fahd.add(filenameTextField, anchor);
        setContent(fahd);
    }

    private void no(){
        System.out.println("FFFFFF");
    }

    public StreamResource getStreamResource(String filename, byte[] content) {
        return new StreamResource(filename,
                () -> new ByteArrayInputStream(content));
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        Optional<String> courseID = beforeEnterEvent.getRouteParameters().get("course-ID");

        this.targetCourse = courseService.getCourseById(Long.valueOf(courseID.get()));

    }

}
