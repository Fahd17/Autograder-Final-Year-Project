package com.example.autogradertyp;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@Theme(variant = Lumo.DARK)
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
@SpringBootApplication
public class AutograderTypApplication extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(AutograderTypApplication.class, args);
    }

}
